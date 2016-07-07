package br.ufc.engsoftware.tasabido;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Vector;

import br.ufc.engsoftware.BDLocalManager.DuvidaBDManager;
import br.ufc.engsoftware.serverDAO.PostCriarDuvida;
import br.ufc.engsoftware.auxiliar.Statics;
import br.ufc.engsoftware.auxiliar.Utils;
import br.ufc.engsoftware.models.Duvida;
import br.ufc.engsoftware.serverDAO.PostCriarMonitoria;
import butterknife.ButterKnife;
import butterknife.InjectView;

public class CriarDuvidaActivity extends AppCompatActivity {

    static final int REQ_INFO = 1;
    String horariosJSON = null;
    String titulo, descricao, data;
    int id_subtopico, id_usuario, id_duvida;
    private Duvida duvida;
    public Activity activity;
    Utils utils;
    @InjectView(R.id.titulo) EditText _titulo;
    @InjectView(R.id.descricao) EditText _descricao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_criar_duvidas);
        ButterKnife.inject(this);
        activity = this;
        utils = new Utils(this);

        // Pega a intent que chamou essa activity
        Intent intent = getIntent();
        id_subtopico = intent.getIntExtra("ID_SUBTOPICO", 0);

        titulo = intent.getStringExtra("TITULO");
        descricao = intent.getStringExtra("DESCRICAO");
        data = intent.getStringExtra("DATA");
        id_subtopico = intent.getIntExtra("ID_SUBTOPICO", 0);
        id_duvida = intent.getIntExtra("ID_DUVIDA", 0);
        id_usuario = intent.getIntExtra("ID_USUARIO", 0);
    }


    public void onClickConfirmarDuvida(View view){

        String id_usuario_string = utils.getFromSharedPreferences("id_usuario", "");
        int id_usuario = Integer.parseInt(id_usuario_string);
        String titulo = _titulo.getText().toString();
        String descricao = _descricao.getText().toString();

        while(titulo.isEmpty() || descricao.isEmpty()){
            Toast toast = Toast.makeText(activity, "Preencha todos os campos", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER_HORIZONTAL|Gravity.CENTER_VERTICAL, 0, 0);
            toast.show();
            return;
        }

        duvida = new Duvida(id_usuario, id_subtopico, titulo, descricao, horariosJSON);

        try {
            if (id_duvida == 0)
                criarDuvida();
            else
                editarDuvida();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void criarDuvida(){
        String param = concatenateParam(duvida);
        utils.createProgressDialog("Criando Duvida");

        if(utils.checkConnection(this))
            new PostCriarDuvida(this, param, new PostCriarDuvida.AsyncResponse(){
            Toast toast;
            public void processFinish(String output, int id_duvida, String mensagem){
                if (output.equals("true")){
                    toast = Toast.makeText(activity, mensagem, Toast.LENGTH_SHORT);
                    duvida.setId_duvida(id_duvida);
                    salvarDuvidaBDLocal();
                    finish();
                }else{
                    toast = Toast.makeText(activity, mensagem, Toast.LENGTH_SHORT);
                }
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
            }
        }).execute(Statics.CADASTRAR_DUVIDA);
        else
            utils.dismissProgressDialog();
    }

    public void editarDuvida(){
        duvida.setId_duvida(id_duvida);
        duvida.setData_duvida(horariosJSON);
        String param = concatenateEdit(duvida);
        if(utils.checkConnection(this))
            new PostCriarDuvida(this, param, new PostCriarDuvida.AsyncResponse(){
            Toast toast;
            public void processFinish(String output, int id_duvida, String mensagem){
                if (output.equals("true")){
                    toast = Toast.makeText(activity, mensagem, Toast.LENGTH_SHORT);
                    duvida.setId_duvida(id_duvida);
                    atualizarDuvidaBDLocal();
                    finish();
                }else{
                    toast = Toast.makeText(activity, mensagem, Toast.LENGTH_SHORT);
                }
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
            }
        }).execute(Statics.ATUALIZAR_DUVIDA);
    }

    private void salvarDuvidaBDLocal() {
        DuvidaBDManager db = new DuvidaBDManager(this);
        Vector<Duvida> duvidas = new Vector<>();
        duvidas.add(duvida);
        db.atualizarDuvidas(this, duvidas);
    }

    private void atualizarDuvidaBDLocal() {
        DuvidaBDManager db = new DuvidaBDManager(this);
        db.updateDuvida(duvida);
    }

    public String concatenateParam(Duvida duvida){
        String param = "titulo=";
        param += duvida.getTitulo();
        param += "&";
        param += "descricao=";
        param += duvida.getDescricao();
        param += "&";
        param += "id_usuario=";
        param += duvida.getId_usuario();
        param += "&";
        param += "id_subtopico=";
        param += duvida.getId_subtopico();
        param += "&";
        param += "data_duvida=";
        param += horariosJSON;


        return param;
    }

    public String concatenateEdit(Duvida duvida){
        String param = "titulo=";
        param += duvida.getTitulo();
        param += "&";
        param += "descricao=";
        param += duvida.getDescricao();
        param += "&";
        param += "id_usuario=";
        param += duvida.getId_usuario();
        param += "&";
        param += "id_subtopico=";
        param += duvida.getId_subtopico();
        param += "&";
        param += "id_duvida=";
        param += duvida.getId_duvida();
        param += "&";
        param += "data_duvida=";
        param += horariosJSON;


        return param;
    }

    public void onClickReqHorarios(View view){
        Intent reqHorariosIntent = new Intent(this , CalendarioActivity.class );
        //reqInfoIntent.setType();
        startActivityForResult(reqHorariosIntent, REQ_INFO);
    }

    // Metodo que trata o retorno da CalendarioActivity
    public void onActivityResult(int requestCode, int resultCode, Intent	data)	{
        if (requestCode	==	REQ_INFO)	{
            //	Possible values:	RESULT_OK,	RESULT_CANCELED	or	RESULT_FIRST_USER
            if ((data != null) && (resultCode == RESULT_OK))	{
                String horariosJson = data.getStringExtra("HORARIOS");
                this.horariosJSON = horariosJson;

                Log.d("Response horariosJson", horariosJson);
            }
        }
    }
}