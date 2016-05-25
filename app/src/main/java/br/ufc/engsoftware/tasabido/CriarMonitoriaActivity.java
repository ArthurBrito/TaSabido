package br.ufc.engsoftware.tasabido;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.Vector;

import br.ufc.engsoftware.BDLocalManager.SubtopicoBDManager;
import br.ufc.engsoftware.auxiliar.Utils;
import br.ufc.engsoftware.models.Subtopico;

public class CriarMonitoriaActivity extends AppCompatActivity {
    String materia;
    int id_materia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_criar_monitoria);

        // Pega a intent que chamou essa activity
        Intent intent = getIntent();
        materia = intent.getStringExtra("MATERIA");
        id_materia = intent.getIntExtra("ID", 0);
    }


    //esse metodo acessa o banco de dados local e retorna todos os subtopicos referentes a materia que o usuario esta vendo
    public Vector<Subtopico> pegarSubtopicos(int id_materia){
        SubtopicoBDManager sbd = new SubtopicoBDManager();
        Vector<Subtopico> subtopicos = sbd.pegarSubtopicosPorIdMateria(this, id_materia);
        return subtopicos;
    }


    // Seta as configurações do ListView
    public void setSpinnerView(Context view, Vector<Subtopico> vector){

        Vector<Subtopico> subtopicos = pegarSubtopicos(id_materia);

        // Seta o layout e os valores do ListView
        Spinner spinner = (Spinner) findViewById(R.id.spinner_lista_subtopicos);
        ArrayAdapter<Subtopico> adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, subtopicos);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

    }

}
