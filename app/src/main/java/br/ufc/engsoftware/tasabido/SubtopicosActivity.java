package br.ufc.engsoftware.tasabido;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import br.ufc.engsoftware.BDLocalManager.DuvidaBDManager;
import br.ufc.engsoftware.BDLocalManager.SubtopicoBDManager;
import br.ufc.engsoftware.DAO.SubtopicosDAO;
import br.ufc.engsoftware.views.SubtopicoListView;


public class SubtopicosActivity extends AppCompatActivity {

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

        // Captura a referencia pro ListView a partir do id
        listviewSubtopicos = (ListView) findViewById(R.id.listview_subtopicos);

        //Metodo responsável por montar o ListView das Dúvidas
        montarListViewSubtopicos();

    }

    private void montarListViewSubtopicos(){
        //pega os subtopicos salvos no banco de dados local pela requisição com o servidor
        SubtopicoBDManager subtopicoDB = new SubtopicoBDManager();
        gerenciadorSubtopicosLV = new SubtopicoListView(listviewSubtopicos, this, subtopicoDB.pegarSubtopicosPorIdMateria(this, id_materia));

        /* não precisa mais dessa parte porque ele vai pegar do banco de dados local
        * o DAO só vai pegar as info do servidor no momento que a gente decidir que o app vai sincronizar*/
        // Monta o ListView com os dados obtidos do web service
        // Executa o AsyncTask responsavel por preencher o ListView de Subtopicos
//        new SubtopicosDAO(this, this, listviewSubtopicos).execute();
    }

    public void onClickCriarMonitoria(View view) {
        chamarMonitoriaActivity();
    }

    // Chama a activity de monitoria
    private void chamarMonitoriaActivity(){
        Intent intent = new Intent(this, CriarMonitoriaActivity.class);
        intent.setAction("br.ufc.engsoftware.tasabido.CRIAR_MONITORIA");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        // Passa o nome da matéria para ser exibido na activity, e o id para pesquisar no banco
        intent.putExtra("MATERIA", materia);
        intent.putExtra("ID", id_materia);

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
