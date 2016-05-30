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

import br.ufc.engsoftware.models.Materia;
import br.ufc.engsoftware.tasabido.ListaSubtopicosActivity;
import br.ufc.engsoftware.tasabido.R;

/**
 * Created by Thiago on 14/05/2016.
 */
public class MateriaListView {

    ListView listview;
    Filter filter;


    public MateriaListView(ListView listview, Context view, Vector<Materia> vector) {
        this.listview = listview;

        setListView(view, vector);
    }

    // Seta as configurações do ListView
    public void setListView(Context view, Vector<Materia> vector){

        // Seta o layout e os valores do ListView
        final ArrayAdapter<Materia> adapter = new ArrayAdapter<Materia>(view,
                R.layout.materias_listview_rowlayout, R.id.label, vector);
        listview.setAdapter(adapter);

        this.filter = adapter.getFilter();

        // Seta a ação quando clicar em um item do ListView
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onItemClick(AdapterView<?> parent, final View view,
                                    int position, long id) {

                final Materia item = (Materia) parent.getItemAtPosition(position);
                chamarSubtopicoActivity(view.getContext(), item);
            }

        });
    }

    // Intent para quando clicar no item da lista de matérias ir para a pagina de subtopicos
    private void chamarSubtopicoActivity(Context view, Materia item){
        Intent intent = new Intent(view, ListaSubtopicosActivity.class);
        intent.setAction("br.ufc.engsoftware.tasabido.SUBTOPICOS");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        // Passa o nome da matéria para ser exibido na activity, e o id para pesquisar no banco
        intent.putExtra("MATERIA", item.getNome());
        intent.putExtra("ID", item.getId_materia());
        view.startActivity(intent);
    }

    public Filter getFilter() {
        return filter;
    }
}
