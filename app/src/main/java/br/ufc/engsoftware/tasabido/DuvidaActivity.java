package br.ufc.engsoftware.tasabido;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashSet;
import java.util.Set;

import br.ufc.engsoftware.BDLocalManager.DuvidaBDManager;
import br.ufc.engsoftware.auxiliar.Statics;
import br.ufc.engsoftware.auxiliar.Utils;
import br.ufc.engsoftware.models.Duvida;
import br.ufc.engsoftware.serverDAO.PostCriarDuvida;
import br.ufc.engsoftware.serverDAO.PostDeletarDuvida;
import br.ufc.engsoftware.serverDAO.PostEnviarEmail;
import butterknife.ButterKnife;
import butterknife.InjectView;

public class DuvidaActivity extends AppCompatActivity {

    Button bt_delete, btn_atualizar, btn_tirar_duvida;
    public int id_duvida, id_materia, id_subtopico, id_usuario;
    String titulo, descricao;
    Duvida duvida;
    Utils utils;
    public Activity activity;

    @InjectView(R.id.titulo) EditText _titulo;
    @InjectView(R.id.descricao) EditText _descricao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_duvida);
        ButterKnife.inject(this);
        activity = this;
        utils = new Utils(this);

        bt_delete = (Button) findViewById(R.id.bt_delete);
        btn_atualizar = (Button) findViewById(R.id.btn_atualizar);
        btn_tirar_duvida = (Button) findViewById(R.id.btn_tirar_duvida);
        bt_delete.setVisibility(View.GONE);
        btn_atualizar.setVisibility(View.GONE);
        btn_tirar_duvida.setVisibility(View.VISIBLE);


        // Pega a intent que chamou essa activity
        Intent intent = getIntent();
        id_usuario = intent.getIntExtra("ID_USUARIO", 0);
        id_subtopico = intent.getIntExtra("ID_SUBTOPICO", 0);
        id_materia = intent.getIntExtra("ID_MATERIA", 0);
        id_duvida = intent.getIntExtra("ID", 0);
        descricao = intent.getStringExtra("DESCRICAO");
        titulo = intent.getStringExtra("TITULO");

        mostrarBotoes();

        _titulo.setKeyListener(null);
        _descricao.setKeyListener(null);

        _titulo.setText(titulo);
        _descricao.setText(descricao);
    }

    private void mostrarBotoes() {
        Utils util = new Utils(this);
        int id = Integer.parseInt(util.getFromSharedPreferences("id_usuario", ""));

        if (id_usuario == id){
            bt_delete.setVisibility(View.VISIBLE);
            btn_atualizar.setVisibility(View.VISIBLE);
            btn_tirar_duvida.setVisibility(View.GONE);
        }

        if(tirareiDuvida()){
            btn_tirar_duvida.setVisibility(View.GONE);
        }
    }

    private boolean tirareiDuvida() {
        Utils util = new Utils(this);
        Set<String> array_ids = new HashSet<>();
        array_ids = util.getMonitoriasConfirmadasFromSharedPreferences("duvidas", array_ids);

        for (String m: array_ids) {
            if (m.equals(String.valueOf(id_duvida))){
                return true;
            }
        }
        return false;
    }

    public void onClickAtualizarDuvida(View view){

        String id_usuario_string = utils.getFromSharedPreferences("id_usuario","");
        int id_usuario = Integer.parseInt(id_usuario_string);
        String titulo = _titulo.getText().toString();
        String descricao = _descricao.getText().toString();

        duvida = new Duvida(id_duvida, id_usuario, id_materia, id_subtopico, titulo, descricao);
        String param = concatenateParam(duvida);

        try {
            utils.createProgressDialog("Atualizando Dúvida");
            new PostCriarDuvida(this, param, new PostCriarDuvida.AsyncResponse(){
                public void processFinish(String output, int id_duvida, String mensagem){
                    if (output.equals("true")){
                        utils.progressDialog.setMessage(mensagem);
                        duvida.setId_duvida(id_duvida);
                        salvarDuvidaBDLocal();
                        finish();
                    }else{
                        utils.progressDialog.setMessage(mensagem);

                    }
                }
            }).execute(Statics.ATUALIZAR_DUVIDA);
            utils.dismissProgressDialog();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onClickDeletarDuvida(View view){

        String id_usuario_string = utils.getFromSharedPreferences("id_usuario", "");
        int id_usuario = Integer.parseInt(id_usuario_string);
        duvida = new Duvida(id_duvida, id_usuario);
        String param = concatenateToDelete(duvida);

        try {
            utils.createProgressDialog("Deletando Dúvida");
            new PostDeletarDuvida(this, param, new PostDeletarDuvida.AsyncResponse(){
                Toast toast;
                public void processFinish(String output, String mensagem){
                    if (output.equals("true")){
                        toast = Toast.makeText(activity, mensagem, Toast.LENGTH_SHORT);
                        deleteDuvida();
                        finish();
                    }else{
                        toast = Toast.makeText(activity, mensagem, Toast.LENGTH_SHORT);
                    }
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }
            }).execute(Statics.DELETAR_DUVIDA);
            utils.dismissProgressDialog();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onClickTirarDuvida(View view){
        Utils utils = new Utils(this);
        Set<String> array_ids = new HashSet<String>();
        array_ids = utils.getDuvidasConfirmadasFromSharedPreferences("duvidas", array_ids);
        array_ids.add(String.valueOf(String.valueOf(id_duvida)));
        utils.saveDuvidasConfirmadasSharedPreferences(array_ids);

        String login = utils.getFromSharedPreferences("login", "");
        String mensagem = login + " se disponibiliza pra tirar sua dúvida.";
        String param = concatenateParamDuvida(String.valueOf(id_usuario), "Monitoria", mensagem);
        utils.sendEmail(param, this);
    }

    private void salvarDuvidaBDLocal() {
        DuvidaBDManager db = new DuvidaBDManager(this);
        //deleta duvida antiga
        deleteDuvida();
        //salva nova duvida (duvida atualizada)
        db.salvarDuvida(duvida);
    }

    public void deleteDuvida(){
        DuvidaBDManager db = new DuvidaBDManager(this);
        //deleta duvida antiga
        db.deleteDuvidaPorIdDuvida(this, id_duvida);
    }

    public String concatenateParam(Duvida duvida){
        String param = "titulo=";
        param += duvida.getTitulo();
        param += "&";
        param += "descricao=";
        param += duvida.getDescricao();
        param += "&";
        param += "id_duvida=";
        param += duvida.getId_duvida();
        param += "&";
        param += "id_usuario=";
        param += duvida.getId_usuario();
        param += "&";
        param += "id_materia=";
        param += duvida.getId_materia();
        param += "&";
        param += "id_subtopico=";
        param += duvida.getId_subtopico();

        return param;
    }

    public String concatenateToDelete(Duvida duvida){
        String param = "id_duvida=";
        param += duvida.getId_duvida();
        param += "&";
        param += "id_usuario=";
        param += duvida.getId_usuario();

        return param;
    }

    public String concatenateParamDuvida(String id_usuario, String assunto, String mensagem){
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
}