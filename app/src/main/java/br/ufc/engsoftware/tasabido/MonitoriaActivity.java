package br.ufc.engsoftware.tasabido;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class MonitoriaActivity extends AppCompatActivity {

    String titulo, descricao, data;
    int id_monitoria;

    @InjectView(R.id.data) EditText _data;
    @InjectView(R.id.input_descricao) EditText _descricao;
    @InjectView(R.id.input_titulo) EditText _titulo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monitoria);
        ButterKnife.inject(this);


        // Pega a intent que chamou essa activity
        Intent intent = getIntent();
        data = intent.getStringExtra("DATA");
        titulo = intent.getStringExtra("TITULO");
        descricao = intent.getStringExtra("DESCRICAO");
        id_monitoria = intent.getIntExtra("ID_MONITORIA", 0);


        _data.setText(data);
        _descricao.setText(descricao);
        _titulo.setText(titulo);


    }


    public void onClickAtualizarMonitoria(View view){

    }

    public void onClickDeletarMonitoria(View view){

    }

}
