//package br.ufc.engsoftware.tasabido;
//
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.support.v7.app.ActionBar;
//import android.support.v7.app.AppCompatActivity;
//import android.view.MenuItem;
//import android.widget.ListView;
//
//import java.util.HashSet;
//import java.util.Set;
//import java.util.Vector;
//
//import br.ufc.engsoftware.auxiliar.Utils;
//import br.ufc.engsoftware.views.MonitoriaListView;
//
//
//public class ListaMonitoriasConfirmadasActivity extends AppCompatActivity {
//
//    // Informaçoes da duvida selecionada
//
//    // Referencia para o ListView da interface
//    ListView listViewMonitorias;
//
//    // Estado do ListView e suas informações
//    MonitoriaListView gerenciadorMonitoriasLV;
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_lista_monitorias_confirmadas);
//
//        // Pega a intent que chamou essa activity
//        Intent intent = getIntent();
//
//        // Captura a referencia pro ListView a partir do id
//        listViewMonitorias = (ListView) findViewById(R.id.listview_monitorias_confirmadas);
//
//        //Metodo responsável por montar o ListView das Dúvidas
//        montarListViewMonitorias();
//
//    }
//
//    private void montarListViewMonitorias(){
//        //pega os subtopicos salvos no banco de dados local pela requisição com o servidor
//        MonitoriaBDManager duvidaDB = new MonitoriaBDManager();
//        Utils utils = new Utils(this);
//        Set<String> array_ids = new HashSet<>();
//        array_ids = utils.getMonitoriasConfirmadasFromSharedPreferences("monitorias", array_ids);
//        Vector<Monitoria> vector_monitorias = new Vector<Monitoria>();
//
//        for (String id_monitoria: array_ids) {
//            Monitoria monitoria = duvidaDB.pegarMonitoriasPorIdMonitoria(this, Integer.parseInt(id_monitoria));
//            vector_monitorias.add(monitoria);
//        }
//
//        gerenciadorMonitoriasLV = new MonitoriaListView(listViewMonitorias, this, vector_monitorias);
//
//    }
//
//    // Seta ação do botão de voltar na ActionBar
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//            case android.R.id.home:
//                finish();
//                return true;
//            default:
//                return super.onOptionsItemSelected(item);
//        }
//    }
//
//}
