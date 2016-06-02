package br.ufc.engsoftware.tasabido;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import br.ufc.engsoftware.BDLocalManager.DuvidaBDManager;
import br.ufc.engsoftware.serverDAO.PostCriarDuvida;
import br.ufc.engsoftware.auxiliar.Statics;
import br.ufc.engsoftware.auxiliar.Utils;
import br.ufc.engsoftware.models.Duvida;
import butterknife.ButterKnife;
import butterknife.InjectView;

public class CriarDuvidaActivity extends AppCompatActivity {

    int id_subtopico;
    int id_materia;
    private Duvida duvida;
    public Activity activity;
//    @InjectView(R.id.time_picker) TextView time_picker;
//    @InjectView(R.id.date_picker) TextView date_picker;
    @InjectView(R.id.titulo) EditText _titulo;
    @InjectView(R.id.descricao) EditText _descricao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_criar_duvidas);
        ButterKnife.inject(this);
        activity = this;

        // Pega a intent que chamou essa activity
        Intent intent = getIntent();
        id_subtopico = intent.getIntExtra("ID_SUBTOPICO", 0);
        id_materia = intent.getIntExtra("ID_MATERIA", 0);


    }

//    public void escolherData(View view) {
//        DialogFragment newFragment = new DatePickerFragment();
//        newFragment.show(getSupportFragmentManager(), "datePicker");
//    }
//
//    public void escolherHorario(View view) {
//        DialogFragment newFragment = new TimePickerFragment();
//        newFragment.show(getSupportFragmentManager(), "timePicker");
//    }


    public void onClickConfirmarDuvida(View view){

        Utils utils = new Utils(this);
        String id_usuario_string = utils.getFromSharedPreferences("id_usuario","");
        int id_usuario = Integer.parseInt(id_usuario_string);
        String titulo = _titulo.getText().toString();
        String descricao = _descricao.getText().toString();

        duvida = new Duvida(id_usuario, id_materia, id_subtopico, titulo, descricao);
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
            }).execute(Statics.CADASTRAR_DUVIDA);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void salvarDuvidaBDLocal() {
        DuvidaBDManager db = new DuvidaBDManager(this);
        db.salvarDuvida(duvida);
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
        param += "id_materia=";
        param += duvida.getId_materia();
        param += "&";
        param += "id_subtopico=";
        param += duvida.getId_subtopico();

        return param;
    }
}