package br.ufc.engsoftware.tasabido;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import br.ufc.engsoftware.DAO.DuvidasDAO;
import br.ufc.engsoftware.DAO.SubtopicosDAO;
import br.ufc.engsoftware.views.DuvidaListView;


public class DuvidasActivity extends AppCompatActivity {

    // Informaçoes da duvida selecionada
    String titulo;
    String descricao;
    int id_duvida;
    int id_materia;
    int id_subtopico;

    // Referencia para o ListView da interface
    ListView listviewDuvidas;

    // Estado do ListView e suas informações
    DuvidaListView gerenciadorDuvidasLV;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_duvidas);

        // Pega a intent que chamou essa activity
        Intent intent = getIntent();
        titulo = intent.getStringExtra("TITULO");
        descricao = intent.getStringExtra("DESCRICAO");
        id_duvida = intent.getIntExtra("ID_DUVIDA", 0);
        id_materia = intent.getIntExtra("ID", 0);

        // Seta as configurações da ActionBar
//        ActionBar ab = getSupportActionBar();
//        ab.setTitle(materia);
//        ab.setDisplayHomeAsUpEnabled(true);

        // Captura a referencia pro ListView a partir do id
        listviewDuvidas = (ListView) findViewById(R.id.listview_duvidas);

        //Metodo responsável por montar o ListView das Dúvidas
        montarListViewDuvidas();

    }

    private void montarListViewDuvidas(){
        // Executa o AsyncTask responsavel por preencher o ListView de Subtopicos
        new DuvidasDAO(this, this, listviewDuvidas).execute();
    }

    public void onClickMonitorias(View view) {
        chamarMonitoriaActivity();
    }

    public void onClickCriarDuvida(View view){
        Intent intent = new Intent(this, DuvidasActivity.class);
        startActivity(intent);
    }

    public void verDuvidas(View view){
        Intent intent = new Intent(this, DuvidasActivity.class);
        startActivity(intent);
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
