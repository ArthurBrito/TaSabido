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

import br.ufc.engsoftware.models.Duvida;
import br.ufc.engsoftware.tasabido.DuvidaActivity;
import br.ufc.engsoftware.tasabido.R;

/**
 * Created by Thiago on 14/05/2016.
 */
public class DuvidaListView {

    ListView listview;
    Filter filter;


    public DuvidaListView(ListView listview, Context view, Vector<Duvida> vector) {
        this.listview = listview;

        setListView(view, vector);
    }

    // Seta as configurações do ListView
    public void setListView(Context view, Vector<Duvida> vector){

        // Seta o layout e os valores do ListView
        final ArrayAdapter<Duvida> adapter = new ArrayAdapter<Duvida>(view,
                R.layout.duvidas_listview_rowlayout, R.id.label_duvida, vector);
        listview.setAdapter(adapter);

        this.filter = adapter.getFilter();

        // Seta a ação quando clicar em um item do ListView
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onItemClick(AdapterView<?> parent, final View view,
                                    int position, long id) {

                final Duvida item = (Duvida) parent.getItemAtPosition(position);
                chamarDuvidaActivity(view.getContext(), item);
            }

        });
    }

    // Intent para quando clicar no item da lista de matérias ir para a pagina de subtopicos
    private void chamarDuvidaActivity(Context view, Duvida item){
        Intent intent = new Intent(view, DuvidaActivity.class);
        intent.setAction("br.ufc.engsoftware.tasabido.DUVIDA");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        // Passa o nome da matéria para ser exibido na activity, e o id para pesquisar no banco
        intent.putExtra("TITULO", item.getTitulo());
        intent.putExtra("DESCRICAO", item.getDescricao());
        intent.putExtra("ID_MATERIA", item.getId_materia());
        intent.putExtra("ID_SUBTOPICO", item.getId_subtopico());
        intent.putExtra("ID", item.getId_duvida());
        intent.putExtra("ID_USUARIO", item.getId_usuario());

        view.startActivity(intent);
    }

    public Filter getFilter() {
        return filter;
    }
}
