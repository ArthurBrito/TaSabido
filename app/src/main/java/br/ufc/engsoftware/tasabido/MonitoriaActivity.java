package br.ufc.engsoftware.tasabido;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;
import java.util.Vector;

import br.ufc.engsoftware.Ormlite.Monitoria;
import br.ufc.engsoftware.auxiliar.MonitoriaOpenDatabaseHelper;
import br.ufc.engsoftware.auxiliar.Statics;
import br.ufc.engsoftware.auxiliar.Utils;
import br.ufc.engsoftware.serverDAO.PostDeleteMonitoria;
import butterknife.ButterKnife;
import butterknife.InjectView;

public class MonitoriaActivity extends AppCompatActivity {


    Button bt_delete, btn_atualizar, btn_participar;

    String titulo, descricao, data, endereco, dia, horario;
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
        bt_delete = (Button) findViewById(R.id.btn_delete);
        btn_atualizar = (Button) findViewById(R.id.bt_atualizar_monitoria);
        btn_participar = (Button) findViewById(R.id.btn_participar);
        bt_delete.setVisibility(View.GONE);
        btn_atualizar.setVisibility(View.GONE);
        btn_participar.setVisibility(View.VISIBLE);

        // Pega a intent que chamou essa activity
        Intent intent = getIntent();
        data = intent.getStringExtra("DATA");
        dia = intent.getStringExtra("DIA");
        horario = intent.getStringExtra("HORARIO");
        titulo = intent.getStringExtra("TITULO");
        descricao = intent.getStringExtra("DESCRICAO");
        endereco = intent.getStringExtra("ENDERECO");
        id_monitoria = intent.getIntExtra("ID_MONITORIA", 0);
        id_subtopico = intent.getIntExtra("ID_SUBTOPICO", 0);
        id_materia = intent.getIntExtra("ID_MATERIA", 0);
        id_usuario = intent.getIntExtra("ID_USUARIO", 0);


        mostrarBotoes();

        _titulo.setKeyListener(null);
        _descricao.setKeyListener(null);
        _data.setKeyListener(null);
        _endereco.setKeyListener(null);

        _titulo.setText(titulo);
        _descricao.setText(descricao);
        _data.setText(dia + " " + horario);
        _endereco.setText(endereco);
    }

    private void mostrarBotoes() {
        Utils util = new Utils(this);
        int id = Integer.parseInt(util.getFromSharedPreferences("id_usuario", ""));

        if (id_usuario == id){
            bt_delete.setVisibility(View.VISIBLE);
            btn_atualizar.setVisibility(View.VISIBLE);
            btn_participar.setVisibility(View.GONE);
        }

        if(participareiDaMonitoria()){
            btn_participar.setVisibility(View.GONE);
        }
    }

    private boolean participareiDaMonitoria() {
        Utils util = new Utils(this);
        Set<String> array_ids = new HashSet<>();
        array_ids = util.getMonitoriasConfirmadasFromSharedPreferences("monitorias", array_ids);

        for (String m: array_ids) {
            if (m.equals(String.valueOf(id_monitoria))){
                return true;
            }
        }
        return false;
    }


    public void onClickAtualizarMonitoria(View view){
        // Pega a intent que chamou essa activity
        Intent intent = new Intent(this, CriarMonitoriaActivity.class);
        intent.setAction("br.ufc.engsoftware.tasabido.CRIAR_MONITORIA");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("TITULO", titulo);
        intent.putExtra("DESCRICAO", descricao);
        intent.putExtra("ID", id_materia);
        intent.putExtra("ID_MONITORIA", id_monitoria);
        startActivity(intent);
    }


    private void deletarMonitoriaBDLocal(){
        try {
            getDao().deleteById((long) id_monitoria);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void onClickDeletarMonitoria(View view){
        Utils utils = new Utils(this);
        String id_usuario_string = utils.getFromSharedPreferences("id_usuario", "");
        int id_usuario = Integer.parseInt(id_usuario_string);

        Monitoria monitoria = new Monitoria(id_monitoria, id_usuario);
        JSONObject jsonParam = createJsonParamToDeleteMonitoria(monitoria);

        try {
            new PostDeleteMonitoria(this, jsonParam, Statics.DELETAR_MONITORIA, new PostDeleteMonitoria.AsyncResponse(){
                Toast toast;
                public void processFinish(String output){
                    if (output.equals("200")){
                        toast = Toast.makeText(activity, "Monitoria deletada.", Toast.LENGTH_SHORT);
                        deletarMonitoriaBDLocal();
                        finish();
                    }else{
                        toast = Toast.makeText(activity, "Algum erro ocorreu, tente denovo mais tarde.", Toast.LENGTH_SHORT);
                    }
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
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

    public Dao getDao(){
        MonitoriaOpenDatabaseHelper monitoriaOpenDatabaseHelper = OpenHelperManager.getHelper(this,
                MonitoriaOpenDatabaseHelper.class);

        Dao<Monitoria, Long> monitoriaDao = null;
        try {
            monitoriaDao = monitoriaOpenDatabaseHelper.getDao();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return monitoriaDao;
    }
    //        Utils utils = new Utils(this);
//        String id_usuario_string = utils.getFromSharedPreferences("id_usuario", "");
//        int id_usuario = Integer.parseInt(id_usuario_string);
//        String titulo = _titulo.getText().toString();
//        String descricao = _descricao.getText().toString();
//        String endereco = _endereco.getText().toString();
//        String data = _data.getText().toString();
//
//        Monitoria monitoria = new Monitoria(id_monitoria, id_usuario, id_materia, id_subtopico, titulo, descricao, data, "lima neto", "Quarta", "08:00 as 10:00 h", endereco);
//        JSONObject jsonParam = createJsonParam(monitoria);
//
//        try {
//            new PostCriarMonitoria(this, jsonParam, Statics.ATUALIZAR_MONITORIA, new PostCriarMonitoria.AsyncResponse(){
//                public void processFinish(String output){
//                    if (output.equals("200")){
//                        Utils.progressDialog.setMessage("Monitoria atualizada.");
//                        deletarMonitoriaBDLocal();
//                        finish();
//                    }else{
//                        Utils.progressDialog.setMessage("Algum erro ocorreu, tente denovo mais tarde.");
//                    }
//                }
//            }).execute();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
}
