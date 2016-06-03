package br.ufc.engsoftware.tasabido;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Vector;

import br.ufc.engsoftware.BDLocalManager.SubtopicoBDManager;
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
    private Vector<Subtopico> subtopicos;
    private Vector<Integer> subtopicos_selecionados;
    private int spinnerSelectedCount=0;


    @InjectView(R.id.data) EditText _data;
    @InjectView(R.id.horario) EditText _horario;
    @InjectView(R.id.input_titulo) EditText _titulo;
    @InjectView(R.id.input_descricao) EditText _descricao;
    @InjectView(R.id.input_endereco) EditText _endereco;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_criar_monitoria);
        ButterKnife.inject(this);
        spinner = (Spinner) findViewById(R.id.spinner_lista_subtopicos);
        spinner.setOnItemSelectedListener(this);
        subtopicos_selecionados = new Vector<>();

        // Pega a intent que chamou essa activity
        Intent intent = getIntent();
        materia = intent.getStringExtra("MATERIA");
        id_materia = intent.getIntExtra("ID", 0);

        subtopicos = pegarSubtopicos(id_materia);
        setSpinnerView(this, subtopicos);
    }

    //esse metodo acessa o banco de dados local e retorna todos os subtopicos referentes a materia que o usuario esta vendo
    public Vector<Subtopico> pegarSubtopicos(int id_materia){
        SubtopicoBDManager sbd = new SubtopicoBDManager();
        Vector<Subtopico> subtopicos = sbd.pegarSubtopicosPorIdMateria(this, id_materia);
        return subtopicos;
    }

    public void escolherData(View view){
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    public void escolherHorario(View view){
        DialogFragment newFragment = new TimePickerFragment();
        newFragment.show(getSupportFragmentManager(), "timePicker");
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
        Utils utils = new Utils(this);
        String id_usuario_string = utils.getFromSharedPreferences("id_usuario", "");
        int id_usuario = Integer.parseInt(id_usuario_string);
        String titulo = _titulo.getText().toString();
        String descricao = _descricao.getText().toString();
        String endereco = _endereco.getText().toString();
        String data = _data.getText().toString();
        data += " ";
        data += _horario.getText().toString();
        data += ":00";

        Monitoria monitoria = new Monitoria(id_usuario, id_materia, id_subtopico, titulo, descricao, data, endereco);
        JSONObject jsonParam = createJsonParam(monitoria);


        PostCriarMonitoria cm = (PostCriarMonitoria) new PostCriarMonitoria(this, jsonParam, Statics.CADASTRAR_MONITORIA).execute();


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
            json.put("data_monitoria", monitoria.getData());
            json.put("lat", "0.00");
            json.put("long", "0.00");

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
        Subtopico subtopico = subtopicos.get(position);
        id_subtopico = subtopico.getId_subtopico();


//        if (spinnerSelectedCount == 0)
//            spinnerSelectedCount++;
//        else
            subtopicos_selecionados.add(0, id_subtopico);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
