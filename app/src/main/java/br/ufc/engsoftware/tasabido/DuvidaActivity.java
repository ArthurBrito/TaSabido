package br.ufc.engsoftware.tasabido;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
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
    String titulo, descricao, data;
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

        // Seta as configurações da ActionBar
        ActionBar ab = getSupportActionBar();
        ab.setTitle("Duvida");
        ab.setDisplayHomeAsUpEnabled(true);


        // Pega a intent que chamou essa activity
        Intent intent = getIntent();
        id_usuario = intent.getIntExtra("ID_USUARIO", 0);
        id_subtopico = intent.getIntExtra("ID_SUBTOPICO", 0);
        id_duvida = intent.getIntExtra("ID", 0);
        descricao = intent.getStringExtra("DESCRICAO");
        titulo = intent.getStringExtra("TITULO");
        data = intent.getStringExtra("DATA_DUVIDA");

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

        Intent intent = new Intent(this, CriarDuvidaActivity.class);
        intent.setAction("br.ufc.engsoftware.tasabido.CRIAR_DUVIDA");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("TITULO", titulo);
        intent.putExtra("DESCRICAO", descricao);
        intent.putExtra("DATA", data);
        intent.putExtra("ID_SUBTOPICO", id_subtopico);
        intent.putExtra("ID_USUARIO", id_usuario);
        intent.putExtra("ID_DUVIDA", id_duvida);
        startActivity(intent);
    }

    public void onClickDeletarDuvida(View view){

        String id_usuario_string = utils.getFromSharedPreferences("id_usuario", "");
        int id_usuario = Integer.parseInt(id_usuario_string);
        duvida = new Duvida(id_duvida, id_usuario);
        String param = concatenateToDelete(duvida);

        try {
            utils.createProgressDialog("Deletando Dúvida");
            if(utils.checkConnection(this))
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

    public void onClickConsultarHorario(View view){
        Intent intent = new Intent(this, TirarDuvidaActivity.class);
        intent.setAction("br.ufc.engsoftware.tasabido.TIRAR_DUVIDA");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("DATA_DUVIDA", data);
        intent.putExtra("ID_USUARIO", id_usuario);
        intent.putExtra("ID", id_duvida);
        startActivity(intent);
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

    // Seta ação do botão de voltar na ActionBar
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


}