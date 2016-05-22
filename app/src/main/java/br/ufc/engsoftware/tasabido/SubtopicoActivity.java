package br.ufc.engsoftware.tasabido;


import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;


public class SubtopicoActivity extends AppCompatActivity {

    String materia;
    int id_materia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subtopico);

        // Pega a intent que chamou essa activity
        Intent intent = getIntent();
        materia = intent.getStringExtra("MATERIA");
        id_materia = intent.getIntExtra("ID", 0);

        ActionBar ab = getSupportActionBar();
        ab.setTitle(materia);
        ab.setDisplayHomeAsUpEnabled(true);


    }

    public void onClickMonitorias(View view){
        chamarMonitoriaActivity();
    }

    // Chama a activity de monitoria
    private void chamarMonitoriaActivity(){
        Intent intent = new Intent(this, MonitoriaActivity.class);
        intent.setAction("br.ufc.engsoftware.tasabido.MONITORIA");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        // Passa o nome da matéria para ser exibido na activity, e o id para pesquisar no banco
        //intent.putExtra("MATERIA", item.getNome());
        //intent.putExtra("ID", item.getId());

        startActivity(intent);
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
