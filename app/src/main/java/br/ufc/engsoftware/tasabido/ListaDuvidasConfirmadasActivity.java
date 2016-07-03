package br.ufc.engsoftware.tasabido;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.ListView;

import java.util.HashSet;
import java.util.Set;
import java.util.Vector;

import br.ufc.engsoftware.BDLocalManager.DuvidaBDManager;
import br.ufc.engsoftware.auxiliar.Utils;
import br.ufc.engsoftware.models.Duvida;
import br.ufc.engsoftware.views.DuvidaListView;


public class ListaDuvidasConfirmadasActivity extends AppCompatActivity {

    // Informaçoes da duvida selecionada

    // Referencia para o ListView da interface
    ListView listViewDuvidas;

    // Estado do ListView e suas informações
    DuvidaListView gerenciadorDuvidasLV;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_duvidas_confirmadas);

        // Pega a intent que chamou essa activity
        Intent intent = getIntent();

        // Captura a referencia pro ListView a partir do id
        listViewDuvidas = (ListView) findViewById(R.id.listview_duvidas_confirmadas);

        //Metodo responsável por montar o ListView das Dúvidas
        montarListViewDuvidas();

    }

    private void montarListViewDuvidas(){
        //pega os subtopicos salvos no banco de dados local pela requisição com o servidor
        DuvidaBDManager duvidaDB = new DuvidaBDManager();
        Utils utils = new Utils(this);
        Set<String> array_ids = new HashSet<>();
        array_ids = utils.getDuvidasConfirmadasFromSharedPreferences("duvidas", array_ids);
        Vector<Duvida> vector_duvidas = new Vector<Duvida>();

        for (String id_duvida: array_ids) {
            Duvida duvida = duvidaDB.pegarDuvidasPorIdDuvida(this, Integer.parseInt(id_duvida));
            vector_duvidas.add(duvida);
        }
        gerenciadorDuvidasLV = new DuvidaListView(listViewDuvidas, this, vector_duvidas);
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
