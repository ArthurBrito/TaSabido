package br.ufc.engsoftware.tasabido;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Vector;

import br.ufc.engsoftware.BDLocalManager.SubtopicoBDManager;
import br.ufc.engsoftware.auxiliar.Locais;
import br.ufc.engsoftware.auxiliar.Statics;
import br.ufc.engsoftware.auxiliar.Utils;
import br.ufc.engsoftware.fragments.DatePickerFragment;
import br.ufc.engsoftware.fragments.TimePickerFragment;
import br.ufc.engsoftware.models.Monitoria;
import br.ufc.engsoftware.models.Subtopico;
import br.ufc.engsoftware.serverDAO.PostCriarMonitoria;
import butterknife.ButterKnife;
import butterknife.InjectView;

public class CriarMonitoriaActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    String materia;
    int id_materia, id_subtopico;
    private Spinner spinner;
    private Spinner spinnerLocais;
    private Vector<Subtopico> subtopicos;
    private Vector<Integer> subtopicos_selecionados;
    private int spinnerSelectedCount=0;
    Activity activity;

    private String endereco = null;
    private String dia = null;
    private String horario = null;

    Utils utils;


    //@InjectView(R.id.data) EditText _data;
    //@InjectView(R.id.horario) EditText _horario;
    @InjectView(R.id.input_titulo) EditText _titulo;
    @InjectView(R.id.input_descricao) EditText _descricao;
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
        materia = intent.getStringExtra("MATERIA");
        id_materia = intent.getIntExtra("ID", 0);

        subtopicos = pegarSubtopicos(id_materia);
        setSpinnerView(this, subtopicos);

        // Seta as configurações da ActionBar
        ActionBar ab = getSupportActionBar();
        ab.setTitle("Nova Monitoria");
        ab.setDisplayHomeAsUpEnabled(true);
    }

    private void povoateSpinnerLocais(){
        Locais locais = new Locais();

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, locais.getLocaisList());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerLocais.setOnItemSelectedListener(this);
        spinnerLocais.setAdapter(adapter);
    }

    //esse metodo acessa o banco de dados local e retorna todos os subtopicos referentes a materia que o usuario esta vendo
    public Vector<Subtopico> pegarSubtopicos(int id_materia){
        SubtopicoBDManager sbd = new SubtopicoBDManager();
        Vector<Subtopico> subtopicos = sbd.pegarSubtopicosPorIdMateria(this, id_materia);
        return subtopicos;
    }


    // Seta as configurações do ListView
    public void setSpinnerView(Context view, Vector<Subtopico> vector){

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

        while(titulo.isEmpty() || descricao.isEmpty() || endereco == null || dia == null || horario == null){
            Toast toast = Toast.makeText(activity, "Preencha todos os campos", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER_HORIZONTAL|Gravity.CENTER_VERTICAL, 0, 0);
            toast.show();
            return;
        }

        Monitoria monitoria = new Monitoria(id_usuario, id_materia, id_subtopico, titulo, descricao, data, endereco);
        monitoria.setDia(dia);
        monitoria.setHorario(horario);

        JSONObject jsonParam = createJsonParam(monitoria);

        Log.d("Teste", "Json - " + jsonParam.toString());

        try {
            utils.createProgressDialog("Monitoria sendo criada");
            new PostCriarMonitoria(this, jsonParam, Statics.CADASTRAR_MONITORIA, new PostCriarMonitoria.AsyncResponse(){
                public void processFinish(String output){
                    if (output.equals("200")){
                        utils.progressDialog.setMessage("Monitoria criada.");
                        finish();
                    }else{
                        utils.progressDialog.setMessage("Monitoria não foi criada.");
                    }
                }
            }).execute();
        } catch (Exception e) {
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

        Log.d("Teste" , "Entrou no onItemSelected");

        switch (parent.getId()){
            case R.id.spinner_lista_subtopicos:
                Subtopico subtopico = subtopicos.get(position);
                id_subtopico = subtopico.getId_subtopico();


        //        if (spinnerSelectedCount == 0)
        //            spinnerSelectedCount++;
        //        else
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

    public void onRadioButtonClickedDia(View view){
        boolean checked = ((RadioButton)view).isChecked();

        switch (view.getId()){
            case R.id.radio_segunda:
                if(checked)
                {
                    dia = "Segunda";
                }
                break;
            case R.id.radio_terca:
                if(checked)
                {
                    dia = "Terça";
                }
                break;
            case R.id.radio_quarta:
                if(checked)
                {
                    dia = "Quarta";
                }
                break;
            case R.id.radio_quinta:
                if(checked)
                {
                    dia = "Quinta";
                }
                break;
            case R.id.radio_sexta:
                if(checked)
                {
                    dia = "Sexta";
                }
                break;
        }
    }

    public void onRadioButtonClickedHorario(View view){
        boolean checked = ((RadioButton)view).isChecked();

        switch (view.getId()){
            case R.id.radio_horario1:
                if(checked)
                {
                    horario = "08:00 as 10:00 h";
                }
                break;
            case R.id.radio_horario2:
                if(checked)
                {
                    horario = "10:00 as 12:00 h";
                }
                break;
            case R.id.radio_horario3:
                if(checked)
                {
                    horario = "12:00 as 14:00 h";
                }
                break;
            case R.id.radio_horario4:
                if(checked)
                {
                    horario = "14:00 as 16:00 h";
                }
                break;
            case R.id.radio_horario5:
                if(checked)
                {
                    horario = "16:00 as 18:00 h";
                }
                break;
        }
    }
}
