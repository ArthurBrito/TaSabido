package br.ufc.engsoftware.tasabido;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import br.ufc.engsoftware.BDLocalManager.DuvidaBDManager;
import br.ufc.engsoftware.views.DuvidaListView;


public class DuvidasActivity extends AppCompatActivity {

    // Informaçoes da duvida selecionada
    String nome_subtopico;
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
        nome_subtopico = intent.getStringExtra("NOME_SUBTOPICO");
        id_subtopico = intent.getIntExtra("ID_SUBTOPICO", 0);
        id_materia = intent.getIntExtra("ID_MATERIA", 0);

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
        //pega os subtopicos salvos no banco de dados local pela requisição com o servidor
        DuvidaBDManager duvidaDB = new DuvidaBDManager();
        gerenciadorDuvidasLV = new DuvidaListView(listviewDuvidas, this, duvidaDB.pegarDuvidasPorIdSubtopico(this, id_subtopico));

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
