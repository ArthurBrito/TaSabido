package br.ufc.engsoftware.views;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.ListView;

import java.util.Vector;

import br.ufc.engsoftware.models.Monitoria;
import br.ufc.engsoftware.tasabido.MonitoriaActivity;
import br.ufc.engsoftware.tasabido.R;
import br.ufc.engsoftware.tasabido.VerMonitoriaActivity;

/**
 * Created by Thiago on 14/05/2016.
 */
public class MonitoriaListView {

    ListView listview;
    Filter filter;


    public MonitoriaListView(ListView listview, Context view, Vector<Monitoria> vector) {
        this.listview = listview;

        setListView(view, vector);
    }

    // Seta as configurações do ListView
    public void setListView(Context view, Vector<Monitoria> vector){

        // Seta o layout e os valores do ListView
        final ArrayAdapter<Monitoria> adapter = new ArrayAdapter<Monitoria>(view,
                R.layout.materias_listview_rowlayout, R.id.label, vector);
        listview.setAdapter(adapter);

        this.filter = adapter.getFilter();

        // Seta a ação quando clicar em um item do ListView
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onItemClick(AdapterView<?> parent, final View view,
                                    int position, long id) {

                final Monitoria item = (Monitoria) parent.getItemAtPosition(position);
                chamarMostrarMonitoriaActivity(view.getContext(), item);
            }

        });
    }

    // Intent para quando clicar no item da lista de matérias ir para a pagina de subtopicos
    private void chamarMostrarMonitoriaActivity(Context view, Monitoria item){
        Intent intent = new Intent(view, VerMonitoriaActivity.class);
        intent.setAction("br.ufc.engsoftware.tasabido.VER_MONITORIA");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        // Passa o nome da matéria para ser exibido na activity, e o id para pesquisar no banco
        intent.putExtra("TITULO", item.getTitulo());
        intent.putExtra("DESCRICAO", item.getDescricao());
        intent.putExtra("DATA", item.getData());
        intent.putExtra("ENDERECO", item.getEndereco());
        intent.putExtra("ID_SUBTOPICO", item.getId_subtopico());
        intent.putExtra("ID_MONITORIA", item.getId_monitoria());
        intent.putExtra("ID_MATERIA", item.getId_materia());
        intent.putExtra("ID_USUARIO", item.getId_usuario());
        intent.putExtra("DIA", item.getDia());
        intent.putExtra("HORARIO", item.getHorario());
        intent.putExtra("USERNAME", item.getUsername());

        view.startActivity(intent);
    }

    public Filter getFilter() {
        return filter;
    }
}
