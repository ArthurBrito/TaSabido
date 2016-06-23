package br.ufc.engsoftware.tasabido;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import br.ufc.engsoftware.BDLocalManager.DuvidaBDManager;
import br.ufc.engsoftware.auxiliar.Statics;
import br.ufc.engsoftware.auxiliar.Utils;
import br.ufc.engsoftware.models.Duvida;
import br.ufc.engsoftware.serverDAO.PostCriarDuvida;
import br.ufc.engsoftware.serverDAO.PostDeletarDuvida;
import butterknife.ButterKnife;
import butterknife.InjectView;

public class DuvidaActivity extends AppCompatActivity {

    public int id_duvida, id_materia, id_subtopico;
    String titulo, descricao;
    Duvida duvida;
    public Activity activity;

    @InjectView(R.id.titulo) EditText _titulo;
    @InjectView(R.id.descricao) EditText _descricao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_duvida);
        ButterKnife.inject(this);
        activity = this;


        // Pega a intent que chamou essa activity
        Intent intent = getIntent();
        id_subtopico = intent.getIntExtra("ID_SUBTOPICO", 0);
        id_materia = intent.getIntExtra("ID_MATERIA", 0);
        id_duvida = intent.getIntExtra("ID", 0);
        descricao = intent.getStringExtra("DESCRICAO");
        titulo = intent.getStringExtra("TITULO");


        _titulo.setText(titulo);
        _descricao.setText(descricao);
    }

    public void onClickAtualizarDuvida(View view){
        Utils utils = new Utils(this);
        String id_usuario_string = utils.getFromSharedPreferences("id_usuario","");
        int id_usuario = Integer.parseInt(id_usuario_string);
        String titulo = _titulo.getText().toString();
        String descricao = _descricao.getText().toString();

        duvida = new Duvida(id_duvida, id_usuario, id_materia, id_subtopico, titulo, descricao);
        String param = concatenateParam(duvida);

        try {
            new PostCriarDuvida(this, param, new PostCriarDuvida.AsyncResponse(){
                public void processFinish(String output, int id_duvida, String mensagem){
                    if (output.equals("true")){
                        Utils.callProgressDialog(activity, mensagem);
                        Utils.delayMessage();
                        duvida.setId_duvida(id_duvida);
                        salvarDuvidaBDLocal();
                        finish();
                    }else{
                        Utils.callProgressDialog(activity, mensagem);
                        Utils.delayMessage();

                    }
                }
            }).execute(Statics.ATUALIZAR_DUVIDA);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onClickDeletarDuvida(View view){
        Utils utils = new Utils(this);
        String id_usuario_string = utils.getFromSharedPreferences("id_usuario", "");
        int id_usuario = Integer.parseInt(id_usuario_string);
        duvida = new Duvida(id_duvida, id_usuario);
        String param = concatenateToDelete(duvida);

        try {
            new PostDeletarDuvida(this, param, new PostDeletarDuvida.AsyncResponse(){
                public void processFinish(String output, String mensagem){
                    if (output.equals("true")){
                        Utils.callProgressDialog(activity, mensagem);
                        deleteDuvida();
                        Utils.delayMessage();
                        finish();
                    }else{
                        Utils.callProgressDialog(activity, mensagem);
                        Utils.delayMessage();
                    }
                }
            }).execute(Statics.DELETAR_DUVIDA);
        } catch (Exception e) {
            e.printStackTrace();
        }
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

}