package br.ufc.engsoftware.tasabido;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashSet;
import java.util.Set;

import br.ufc.engsoftware.BDLocalManager.MonitoriaBDManager;
import br.ufc.engsoftware.auxiliar.Statics;
import br.ufc.engsoftware.auxiliar.Utils;
import br.ufc.engsoftware.models.Monitoria;
import br.ufc.engsoftware.serverDAO.PostCriarMonitoria;
import br.ufc.engsoftware.serverDAO.PostEnviarEmail;
import butterknife.ButterKnife;
import butterknife.InjectView;

public class MonitoriaActivity extends AppCompatActivity {

    String titulo, descricao, data, endereco;
    int id_monitoria, id_subtopico, id_materia, id_usuario;
    Activity activity;

    @InjectView(R.id.data) EditText _data;
    @InjectView(R.id.input_descricao) EditText _descricao;
    @InjectView(R.id.input_titulo) EditText _titulo;
    @InjectView(R.id.input_endereco) EditText _endereco;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monitoria);
        ButterKnife.inject(this);
        activity = this;

        // Pega a intent que chamou essa activity
        Intent intent = getIntent();
        data = intent.getStringExtra("DATA");
        titulo = intent.getStringExtra("TITULO");
        descricao = intent.getStringExtra("DESCRICAO");
        endereco = intent.getStringExtra("ENDERECO");
        id_monitoria = intent.getIntExtra("ID_MONITORIA", 0);
        id_subtopico = intent.getIntExtra("ID_SUBTOPICO", 0);
        id_materia = intent.getIntExtra("ID_MATERIA", 0);
        id_usuario = intent.getIntExtra("ID_USUARIO", 0);


        _data.setText(data);
        _descricao.setText(descricao);
        _titulo.setText(titulo);
    }


    public void onClickAtualizarMonitoria(View view){
        Utils utils = new Utils(this);
        String id_usuario_string = utils.getFromSharedPreferences("id_usuario", "");
        int id_usuario = Integer.parseInt(id_usuario_string);
        String titulo = _titulo.getText().toString();
        String descricao = _descricao.getText().toString();
        String endereco = _endereco.getText().toString();
        String data = _data.getText().toString();

        Monitoria monitoria = new Monitoria(id_monitoria, id_usuario, id_materia, id_subtopico, titulo, descricao, data, "lima neto", "Quarta", "08:00 as 10:00 h", endereco);
        JSONObject jsonParam = createJsonParam(monitoria);

        try {
            new PostCriarMonitoria(this, jsonParam, Statics.ATUALIZAR_MONITORIA, new PostCriarMonitoria.AsyncResponse(){
                public void processFinish(String output){
                    if (output.equals("200")){
                        Utils.progressDialog.setMessage("Monitoria atualizada.");
                        deletarMonitoriaBDLocal();
                        finish();
                    }else{
                        Utils.progressDialog.setMessage("Algum erro ocorreu, tente denovo mais tarde.");
                    }
                }
            }).execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void salvarDuvidaBDLocal() {
        MonitoriaBDManager db = new MonitoriaBDManager(this);
        //deleta duvida antiga
        deletarMonitoriaBDLocal();
        //salva nova duvida (duvida atualizada)
//        db.salvarMonitoria(duvida);
    }

    private void deletarMonitoriaBDLocal() {
        MonitoriaBDManager db = new MonitoriaBDManager(this);
        db.deletarMonitoriaPorId(id_monitoria, this);
    }

    public void onClickDeletarMonitoria(View view){
        Utils utils = new Utils(this);
        String id_usuario_string = utils.getFromSharedPreferences("id_usuario", "");
        int id_usuario = Integer.parseInt(id_usuario_string);

        Monitoria monitoria = new Monitoria(id_monitoria, id_usuario);
        JSONObject jsonParam = createJsonParamToDeleteMonitoria(monitoria);

        try {
            new PostCriarMonitoria(this, jsonParam, Statics.DELETAR_MONITORIA, new PostCriarMonitoria.AsyncResponse(){
                public void processFinish(String output){
                    if (output.equals("200")){
                        Utils.progressDialog.setMessage("Monitoria deletada.");
                        deletarMonitoriaBDLocal();
                        finish();
                    }else{
                        Utils.progressDialog.setMessage("Algum erro ocorreu, tente denovo mais tarde.");
                    }
                }
            }).execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onClickParticiparMonitoria(View view){
        Utils utils = new Utils(this);
        Set<String> array_ids = new HashSet<String>();
        array_ids = utils.getMonitoriasConfirmadasFromSharedPreferences("monitorias", array_ids);
        array_ids.add(String.valueOf(id_monitoria));
        utils.saveMonitoriasConfirmadasSharedPreferences(array_ids);

        String login = utils.getFromSharedPreferences("login", "");
        String mensagem = login + " confirmou presença na sua monitoria.";
        String param = concatenateParam(String.valueOf(id_usuario), "Monitoria", mensagem);
        utils.sendEmail(param, this);
    }


    public JSONObject createJsonParam(Monitoria monitoria) {
        JSONObject json = new JSONObject();
        JSONArray subtopicosJson = new JSONArray();
        try {
            json.put("titulo", monitoria.getTitulo());
            json.put("descricao", monitoria.getDescricao());
            json.put("endereco", monitoria.getEndereco());
            json.put("id_usuario", monitoria.getId_usuario());
            json.put("id_materia", monitoria.getId_materia());
            json.put("data_monitoria", "2016-07-01 08:00:00");
            json.put("dia", monitoria.getDia());
            json.put("hora", monitoria.getHorario());


//            for (Integer id_sub : subtopicos_selecionados) {
//                subtopicosJson.put(id_sub);
//            }

            json.put("ids_subtopicos", 1);



        } catch (JSONException e) {
            e.printStackTrace();
        }
        return json;
    }

    public String concatenateParam(String id_usuario, String assunto, String mensagem){
        String param = "id_to=";
        param += id_usuario;
        param += "&";
        param += "assunto=";
        param += assunto;
        param += "&";
        param += "message=";
        param += mensagem;

        return param;
    }

    public JSONObject createJsonParamToDeleteMonitoria(Monitoria monitoria) {
        JSONObject json = new JSONObject();
        JSONArray subtopicosJson = new JSONArray();
        try {
            json.put("id_usuario", monitoria.getId_usuario());
            json.put("id_monitoria", monitoria.getId_monitoria());

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return json;
    }
}
