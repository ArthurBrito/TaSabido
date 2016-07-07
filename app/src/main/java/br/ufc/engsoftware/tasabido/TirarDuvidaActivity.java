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
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.gson.Gson;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.Vector;

import br.ufc.engsoftware.BDLocalManager.SubtopicoBDManager;
import br.ufc.engsoftware.Ormlite.Monitoria;
import br.ufc.engsoftware.auxiliar.Locais;
import br.ufc.engsoftware.auxiliar.MonitoriaOpenDatabaseHelper;
import br.ufc.engsoftware.auxiliar.Statics;
import br.ufc.engsoftware.auxiliar.Utils;
import br.ufc.engsoftware.models.Subtopico;
import br.ufc.engsoftware.serverDAO.PostCriarMonitoria;
import br.ufc.engsoftware.serverDAO.PostDeleteMonitoria;
import br.ufc.engsoftware.serverDAO.PutMonitoria;
import butterknife.ButterKnife;
import butterknife.InjectView;

public class TirarDuvidaActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private Spinner spinner;
    private Spinner spinnerLocais;

    int id_duvida, id_usuario;
    String data, endereco;
    Vector<String> listaHorarios;
    private Vector<String> horario_selecionado;
    private int spinnerSelectedCount = 0;
    Utils utils;
    Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tirar_duvida);

        ButterKnife.inject(this);
        spinner = (Spinner) findViewById(R.id.spinner_lista_horario);
        horario_selecionado = new Vector<>();
        activity = this;
        utils = new Utils(this);

        // Seta as configurações da ActionBar
        ActionBar ab = getSupportActionBar();
        ab.setTitle("Tirar Duvida");
        ab.setDisplayHomeAsUpEnabled(true);

        // Configurando spinner do local
        spinnerLocais = (Spinner) findViewById(R.id.spinner_sel_local_duvida);
        povoateSpinnerLocais();

        Intent intent = getIntent();
        data = intent.getStringExtra("DATA_DUVIDA");
        id_duvida = intent.getIntExtra("ID", 0);
        id_usuario = intent.getIntExtra("ID_USUARIO", 0);

        listaHorarios = extrairHorarios(data);
        setSpinnerView(this);
    }

    private Vector extrairHorarios(String json){
        Gson gson = new Gson();

        Vector<String> resultados = new Vector<>();

        String[] dia = {"Segunda - " , "Terça - " , "Quarta - " , "Quinta - " , "Sexta - "};
        String[] horario = {"08:00 as 10:00 h" , "10:00 as 12:00 h" , "12:00 as 14:00 h" , "14:00 as 16:00 h" , "16:00 as 18:00 h"};

        Boolean[][] arrayBoolean = gson.fromJson(json, (Boolean[][].class));


        for(int i = 0; i <= 4; i++)
        {
            for(int j = 0; j <= 4; j++)
            {
                if(arrayBoolean[i][j] == true)
                {
                    resultados.add(dia[i] + horario[j]);
                }
            }
        }

        Log.d("Response json", json.toString());
        Log.d("Response resultados", resultados.toString());

        return resultados;
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.editar_monitoria_bar_menu, menu);
//
//        return true;
//    }

//    // Seta ação dos botões da ActionBar
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//            case android.R.id.home:
//                finish();
//                return true;
//            default:
//                return super.onOptionsItemSelected(item);
//        }
//    }

    // Seta as configurações do Spinner de subtopicos
    public void setSpinnerView(Context view) {

        // Seta o layout e os valores do ListView
        ArrayAdapter<String> adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, listaHorarios);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setOnItemSelectedListener(this);
        spinner.setAdapter(adapter);
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
            case R.id.spinner_lista_horario:
                data = listaHorarios.get(position);

                if (spinnerSelectedCount == 0)
                    spinnerSelectedCount++;
                else
                    horario_selecionado.add(0, data);
                break;

            case R.id.spinner_sel_local_duvida:
                endereco = parent.getItemAtPosition(position).toString();
                break;
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void onClickConfirmarTirarDuvida(View view) {
        Utils utils = new Utils(this);
        Set<String> array_ids = new HashSet<String>();
        array_ids = utils.getDuvidasConfirmadasFromSharedPreferences("duvidas", array_ids);
        array_ids.add(String.valueOf(String.valueOf(id_duvida)));
        utils.saveDuvidasConfirmadasSharedPreferences(array_ids);

        String first_name = utils.getFromSharedPreferences("first_name", "");
        String meu_email = utils.getFromSharedPreferences("email", "");

        String mensagem = first_name + " se disponibiliza pra tirar sua dúvida. \n"  +  "Email: " + meu_email + "\n" +  data + "\n" + "Local: " + endereco;
        String param = concatenateParamDuvida(String.valueOf(id_usuario), "Monitoria", mensagem);
        utils.sendEmail(param, this);
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
