package br.ufc.engsoftware.tasabido;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;

import android.view.Gravity;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import br.ufc.engsoftware.BDLocalManager.SubtopicoBDManager;
import br.ufc.engsoftware.Ormlite.IdSubtopico;
import br.ufc.engsoftware.Ormlite.Monitoria;
import br.ufc.engsoftware.auxiliar.Locais;
import br.ufc.engsoftware.auxiliar.MonitoriaOpenDatabaseHelper;
import br.ufc.engsoftware.auxiliar.Statics;
import br.ufc.engsoftware.auxiliar.Utils;
import br.ufc.engsoftware.models.Subtopico;
import br.ufc.engsoftware.retrofit.TaSabidoApi;
import br.ufc.engsoftware.serverDAO.PostCriarMonitoria;
import br.ufc.engsoftware.serverDAO.PutMonitoria;
import butterknife.ButterKnife;
import butterknife.InjectView;
import io.realm.RealmList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CriarMonitoriaActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    String materia;
    int id_materia, id_subtopico, id_monitoria;
    private Spinner spinner;
    private Spinner spinnerLocais;
    private Vector<Subtopico> subtopicos;
    private Vector<Integer> subtopicos_selecionados;
    private int spinnerSelectedCount = 0;
    Activity activity;

    private String endereco = null;
    private String dia = null;
    private String horario = null;
    private String titulo = null;
    private String descricao = null;
    Monitoria monitoria;

    Utils utils;


    //@InjectView(R.id.data) EditText _data;
    //@InjectView(R.id.horario) EditText _horario;
    @InjectView(R.id.input_titulo)
    EditText _titulo;
    @InjectView(R.id.input_descricao)
    EditText _descricao;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;
    //@InjectView(R.id.input_endereco) EditText _endereco;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_criar_monitoria);
        ButterKnife.inject(this);
        spinner = (Spinner) findViewById(R.id.spinner_lista_subtopicos);
        spinner.setOnItemSelectedListener(this);
        subtopicos_selecionados = new Vector<>();
        activity = this;
        utils = new Utils(this);

        // Configurando spinner do local
        spinnerLocais = (Spinner) findViewById(R.id.spinner_sel_local);
        povoateSpinnerLocais();

        // Pega a intent que chamou essa activity
        Intent intent = getIntent();
        titulo = intent.getStringExtra("TITULO");
        descricao = intent.getStringExtra("DESCRICAO");
        id_monitoria = intent.getIntExtra("ID_MONITORIA", 0);
        materia = intent.getStringExtra("MATERIA");
        id_materia = intent.getIntExtra("ID", 0);

        if (titulo != null)
            _titulo.setText(titulo);
        if (descricao != null)
            _descricao.setText(descricao);

        subtopicos = pegarSubtopicos(id_materia);
        setSpinnerView(this, subtopicos);

        // Seta as configurações da ActionBar
        ActionBar ab = getSupportActionBar();
        ab.setTitle("Nova Monitoria");
        ab.setDisplayHomeAsUpEnabled(true);
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    private void povoateSpinnerLocais() {
        Locais locais = new Locais();

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, locais.getLocaisList());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerLocais.setOnItemSelectedListener(this);
        spinnerLocais.setAdapter(adapter);
    }

    //esse metodo acessa o banco de dados local e retorna todos os subtopicos referentes a materia que o usuario esta vendo
    public Vector<Subtopico> pegarSubtopicos(int id_materia) {
        SubtopicoBDManager sbd = new SubtopicoBDManager();
        Vector<Subtopico> subtopicos = sbd.pegarSubtopicosPorIdMateria(this, id_materia);
        return subtopicos;
    }


    // Seta as configurações do ListView
    public void setSpinnerView(Context view, Vector<Subtopico> vector) {

        // Seta o layout e os valores do ListView
        spinner = (Spinner) findViewById(R.id.spinner_lista_subtopicos);
        ArrayAdapter<Subtopico> adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, subtopicos);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    public void onClickConfirmarMonitoria(View view) {
        String id_usuario_string = utils.getFromSharedPreferences("id_usuario", "");
        int id_usuario = Integer.parseInt(id_usuario_string);
        String titulo = _titulo.getText().toString();
        String descricao = _descricao.getText().toString();

        String data = dia + " - " + horario;

        while (titulo.isEmpty() || descricao.isEmpty() || endereco == null || dia == null || horario == null) {
            Toast toast = Toast.makeText(activity, "Preencha todos os campos", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL, 0, 0);
            toast.show();
            return;
        }

        monitoria = new Monitoria(id_usuario, id_materia, id_subtopico, titulo, descricao, data, dia, horario, endereco);
        monitoria.setDia(dia);
        monitoria.setHorario(horario);

        JSONObject jsonParam = createJsonParam(monitoria);

        Log.d("Teste", "Json - " + jsonParam.toString());

        try {
            if (id_monitoria == 0) {
                utils.createProgressDialog("Monitoria sendo criada");
                new PostCriarMonitoria(this, jsonParam, new PostCriarMonitoria.AsyncResponse() {
                    Toast toast;
                    public void processFinish(String output, int id) {
                        if (output.equals("true")) {
                            monitoria.setId_monitoria(id);
                            saveMonitoria(monitoria);
                            toast = Toast.makeText(activity, "Monitoria cadastrada.", Toast.LENGTH_SHORT);
                            finish();
                        } else {
                            toast = Toast.makeText(activity, "Monitoria não foi cadastrada.", Toast.LENGTH_SHORT);
                        }
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();
                    }
                }).execute(Statics.CADASTRAR_MONITORIA + id_monitoria);

            } else {
                utils.createProgressDialog("Monitoria sendo atualizada");
                String url = Statics.ATUALIZAR_MONITORIA + id_monitoria;
                new PutMonitoria(this, jsonParam, url, new PutMonitoria.AsyncResponse() {
                    Toast toast;
                    public void processFinish(String output) {
                        if (output.equals("true")) {
                            updateMonitoria(monitoria);
                            toast = Toast.makeText(activity, "Monitoria atualizada.", Toast.LENGTH_SHORT);
                            finish();
                        } else {
                            toast = Toast.makeText(activity, "Monitoria não foi atualizada.", Toast.LENGTH_SHORT);
                        }
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();
                    }
                }).execute();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        utils.dismissProgressDialog();
    }

    public void updateMonitoria(Monitoria monitoria){
        try {
            getDao().update(monitoria);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void saveMonitoria(Monitoria monitoria){
        try {
            getDao().create(monitoria);
        } catch (SQLException e) {
            e.printStackTrace();
        }
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


            for (Integer id_sub : subtopicos_selecionados) {
                subtopicosJson.put(id_sub);
            }

            json.put("ids_subtopicos", subtopicosJson);


        } catch (JSONException e) {
            e.printStackTrace();
        }
        return json;
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        Log.d("Teste", "Entrou no onItemSelected");

        switch (parent.getId()) {
            case R.id.spinner_lista_subtopicos:
                Subtopico subtopico = subtopicos.get(position);
                id_subtopico = subtopico.getId_subtopico();


                if (spinnerSelectedCount == 0)
                    spinnerSelectedCount++;
                else
                    subtopicos_selecionados.add(0, id_subtopico);
                break;

            case R.id.spinner_sel_local:
                endereco = parent.getItemAtPosition(position).toString();
                break;
        }


    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

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

    public void onRadioButtonClickedDia(View view) {
        boolean checked = ((RadioButton) view).isChecked();

        switch (view.getId()) {
            case R.id.radio_segunda:
                if (checked) {
                    dia = "Segunda";
                }
                break;
            case R.id.radio_terca:
                if (checked) {
                    dia = "Terça";
                }
                break;
            case R.id.radio_quarta:
                if (checked) {
                    dia = "Quarta";
                }
                break;
            case R.id.radio_quinta:
                if (checked) {
                    dia = "Quinta";
                }
                break;
            case R.id.radio_sexta:
                if (checked) {
                    dia = "Sexta";
                }
                break;
        }
    }

    public void onRadioButtonClickedHorario(View view) {
        boolean checked = ((RadioButton) view).isChecked();

        switch (view.getId()) {
            case R.id.radio_horario1:
                if (checked) {
                    horario = "08:00 as 10:00 h";
                }
                break;
            case R.id.radio_horario2:
                if (checked) {
                    horario = "10:00 as 12:00 h";
                }
                break;
            case R.id.radio_horario3:
                if (checked) {
                    horario = "12:00 as 14:00 h";
                }
                break;
            case R.id.radio_horario4:
                if (checked) {
                    horario = "14:00 as 16:00 h";
                }
                break;
            case R.id.radio_horario5:
                if (checked) {
                    horario = "16:00 as 18:00 h";
                }
                break;
        }
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "CriarMonitoria Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://br.ufc.engsoftware.tasabido/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "CriarMonitoria Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://br.ufc.engsoftware.tasabido/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
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
}


//                new PostCriarMonitoria(this, jsonParam, Statics.CADASTRAR_MONITORIA, new PostCriarMonitoria.AsyncResponse(){
//                    public void processFinish(String output, int id){
//                        if (output.equals("200")){
//                            utils.progressDialog.setMessage("Monitoria criada.");
//                            finish();
//                        }else{
//                            utils.progressDialog.setMessage("Monitoria não foi criada.");
//                        }
//                    }
//                }).execute();
