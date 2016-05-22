package br.ufc.engsoftware.tasabido;


import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import br.ufc.engsoftware.DAO.MateriasDAO;
import br.ufc.engsoftware.DAO.SubtopicosDAO;


public class SubtopicoActivity extends AppCompatActivity {

    // Informaçoes da materia selecionada
    String materia;
    int id_materia;

    // Referencia para o ListView da interface
    ListView listviewSubtopicos;

    // Estado do ListView e suas informações
    SubtopicoListView gerenciadorSubtopicosLV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subtopico);

        // Pega a intent que chamou essa activity
        Intent intent = getIntent();
        materia = intent.getStringExtra("MATERIA");
        id_materia = intent.getIntExtra("ID", 0);

        // Seta as configurações da ActionBar
        ActionBar ab = getSupportActionBar();
        ab.setTitle(materia);
        ab.setDisplayHomeAsUpEnabled(true);

        // Captura a referencia pro ListView a partir do id
        listviewSubtopicos = (ListView) findViewById(R.id.listview_subtopicos);

        //Metodo responsável por montar o ListView das Dúvidas
        montarListViewSubtopicos();

    }

    private void montarListViewSubtopicos(){
        // Executa o AsyncTask responsavel por preencher o ListView de Subtopicos
        new SubtopicosDAO(this, listviewSubtopicos).execute();
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
