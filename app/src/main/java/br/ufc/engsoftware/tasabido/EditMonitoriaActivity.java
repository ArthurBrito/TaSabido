package br.ufc.engsoftware.tasabido;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.SQLException;
import java.util.Vector;

import br.ufc.engsoftware.BDLocalManager.SubtopicoBDManager;
import br.ufc.engsoftware.Ormlite.Monitoria;
import br.ufc.engsoftware.auxiliar.Locais;
import br.ufc.engsoftware.auxiliar.MonitoriaOpenDatabaseHelper;
import br.ufc.engsoftware.auxiliar.Statics;
import br.ufc.engsoftware.auxiliar.Utils;
import br.ufc.engsoftware.models.Subtopico;
import br.ufc.engsoftware.serverDAO.PostCriarMonitoria;
import br.ufc.engsoftware.serverDAO.PutMonitoria;
import butterknife.ButterKnife;
import butterknife.InjectView;

public class EditMonitoriaActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private Spinner spinner;
    private Spinner spinnerLocais;

    int id_materia, id_subtopico, id_monitoria, id_usuario;
    private Vector<Subtopico> subtopicos;
    private Vector<Integer> subtopicos_selecionados;
    private int spinnerSelectedCount = 0;
    Utils utils;

    private String endereco = null;
    private String dia = null;
    private String horario = null;
    private String titulo = null;
    private String descricao = null;
    Monitoria monitoria;

    Activity activity;

    @InjectView(R.id.input_titulo)
    EditText _titulo;
    @InjectView(R.id.input_descricao)
    EditText _descricao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_monitoria);

        ButterKnife.inject(this);
        spinner = (Spinner) findViewById(R.id.spinner_lista_subtopicos);
        spinner.setOnItemSelectedListener(this);
        subtopicos_selecionados = new Vector<>();
        activity = this;
        utils = new Utils(this);

        // Seta as configurações da ActionBar
        ActionBar ab = getSupportActionBar();
        ab.setTitle("Editar Monitoria");
        ab.setDisplayHomeAsUpEnabled(true);

        // Configurando spinner do local
        spinnerLocais = (Spinner) findViewById(R.id.spinner_sel_local);
        povoateSpinnerLocais();

        // Configurando spinner dos subtopicos
        id_materia = getIntent().getIntExtra("ID_MATERIA", 0);
        subtopicos = pegarSubtopicos(id_materia);
        setSpinnerView(this, subtopicos);

        setarDados();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.editar_monitoria_bar_menu, menu);

        return true;
    }

    // Seta ação dos botões da ActionBar
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;

            case R.id.action_save:
                salvarAlteracoesMonitoria();
                return true;

            case R.id.action_delete:
                Toast.makeText(this, "Deletando Monitoria...", Toast.LENGTH_SHORT);
                deletarMonitoria();
                break;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void setarDados(){
        // Pega os dados da intent que chamou essa activity
        Intent intent = getIntent();
        titulo = intent.getStringExtra("TITULO");
        descricao = intent.getStringExtra("DESCRICAO");
        id_monitoria = intent.getIntExtra("ID_MONITORIA", 0);
        int id_sub = intent.getIntExtra("ID_SUBTOPICO" , 0);
        id_usuario = intent.getIntExtra("ID_USUARIO", 0);
        dia = intent.getStringExtra("DIA");
        horario = intent.getStringExtra("HORARIO");
        String local = intent.getStringExtra("ENDERECO");

        // Seta o valor dos editTexts de titulo e descrição
        _titulo.setText(titulo);
        _descricao.setText(descricao);

        // Seta o valor do local do spinner locais
        for(int i = 0; i < spinnerLocais.getCount(); i++){
            if(spinnerLocais.getItemAtPosition(i).toString().equals(local)){
                spinnerLocais.setSelection(i);
                endereco = local;
                break;
            }
        }

        setarRadioButtons();

        // Seta o valor do local do spinner subtopicos
        int subPos = 0;
        int i = 0;
        while(subPos == 0 && i < subtopicos.size())
        {
            if(subtopicos.get(i).getId_subtopico() == id_sub)
            {
                spinner.setSelection(i);
                subPos = i;
            }
            i++;
        }
    }

    private void setarRadioButtons(){
        RadioButton radioButtonTemp;

        // Setando o valor do radiobox dos dias da semana
        switch (dia) {
            case "Segunda" :
                radioButtonTemp = (RadioButton) findViewById(R.id.radio_segunda);
                radioButtonTemp.setChecked(true);
                break;

            case "Terça" :
                radioButtonTemp = (RadioButton) findViewById(R.id.radio_terca);
                radioButtonTemp.setChecked(true);
                break;

            case "Quarta" :
                radioButtonTemp = (RadioButton) findViewById(R.id.radio_quarta);
                radioButtonTemp.setChecked(true);
                break;

            case "Quinta" :
                radioButtonTemp = (RadioButton) findViewById(R.id.radio_quinta);
                radioButtonTemp.setChecked(true);
                break;

            case "Sexta" :
                radioButtonTemp = (RadioButton) findViewById(R.id.radio_sexta);
                radioButtonTemp.setChecked(true);
                break;
        }

        // Setando o valor do radiobox dos horarios
        switch (horario) {
            case "08:00 as 10:00 h" :
                radioButtonTemp = (RadioButton) findViewById(R.id.radio_horario1);
                radioButtonTemp.setChecked(true);
                break;

            case "10:00 as 12:00 h" :
                radioButtonTemp = (RadioButton) findViewById(R.id.radio_horario2);
                radioButtonTemp.setChecked(true);
                break;

            case "12:00 as 14:00 h" :
                radioButtonTemp = (RadioButton) findViewById(R.id.radio_horario3);
                radioButtonTemp.setChecked(true);
                break;

            case "14:00 as 16:00 h" :
                radioButtonTemp = (RadioButton) findViewById(R.id.radio_horario4);
                radioButtonTemp.setChecked(true);
                break;

            case "16:00 as 18:00 h" :
                radioButtonTemp = (RadioButton) findViewById(R.id.radio_horario5);
                radioButtonTemp.setChecked(true);
                break;
        }
    }

    // Seta as configurações do Spinner de subtopicos
    public void setSpinnerView(Context view, Vector<Subtopico> vector) {

        // Seta o layout e os valores do ListView
        spinner = (Spinner) findViewById(R.id.spinner_lista_subtopicos);
        ArrayAdapter<Subtopico> adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, subtopicos);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    // Esse metodo acessa o banco de dados local e retorna todos os subtopicos referentes a materia que o usuario esta vendo
    public Vector<Subtopico> pegarSubtopicos(int id_materia) {
        SubtopicoBDManager sbd = new SubtopicoBDManager();
        Vector<Subtopico> subtopicos = sbd.pegarSubtopicosPorIdMateria(this, id_materia);
        return subtopicos;
    }

    private void povoateSpinnerLocais() {
        Locais locais = new Locais();

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, locais.getLocaisList());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerLocais.setOnItemSelectedListener(this);
        spinnerLocais.setAdapter(adapter);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        Log.d("Teste", "Entrou no onItemSelected");

        switch (parent.getId()) {
            case R.id.spinner_lista_subtopicos:
                Subtopico subtopico = subtopicos.get(position);
                id_subtopico = subtopico.getId_subtopico();

                //if (spinnerSelectedCount == 0)
                //    spinnerSelectedCount++;
                //else
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

    private void salvarAlteracoesMonitoria() {
        //String id_usuario_string = utils.getFromSharedPreferences("id_usuario", "");
        //int id_usuario = Integer.parseInt(id_usuario_string);
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

        } catch (Exception e) {
            e.printStackTrace();
        }
        utils.dismissProgressDialog();
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

    public void updateMonitoria(Monitoria monitoria){
        try {
            getDao().update(monitoria);
        } catch (SQLException e) {
            e.printStackTrace();
        }
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

    // TODO Metodo para deletar a monitoria que esta sendo editada
    private void deletarMonitoria(){

    }
}
