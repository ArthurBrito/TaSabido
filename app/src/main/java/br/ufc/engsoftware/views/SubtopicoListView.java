package br.ufc.engsoftware.views;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.Vector;


import br.ufc.engsoftware.models.Subtopico;
import br.ufc.engsoftware.tasabido.ListaDuvidasActivity;
import br.ufc.engsoftware.tasabido.R;

/**
 * Created by Thiago on 21/05/2016.
 */
public class SubtopicoListView {

    ListView listview;

    public SubtopicoListView(ListView listview, Context view, Vector<Subtopico> vector) {
        this.listview = listview;

        setListView(view, vector);
    }

    // Seta as configurações do ListView
    public void setListView(Context view, Vector<Subtopico> vector){

        // Seta o layout e os valores do ListView
        final ArrayAdapter<Subtopico> adapter = new ArrayAdapter<Subtopico>(view,
                R.layout.subtopicos_listview_rowlayout, R.id.label, vector);
        listview.setAdapter(adapter);


        // Seta a ação quando clicar em um item do ListView
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onItemClick(AdapterView<?> parent, final View view,
                                    int position, long id) {

                final Subtopico item = (Subtopico) parent.getItemAtPosition(position);
                chamarDuvidasActivity(view.getContext(), item);
            }

        });
    }

    // Intent para quando clicar no item da lista de subtopicos ir para a pagina de duvidas
    private void chamarDuvidasActivity(Context view, Subtopico item){
        Intent intent = new Intent(view, ListaDuvidasActivity.class);
        intent.setAction("br.ufc.engsoftware.tasabido.DUVIDAS");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        // Passa as informações do subtopico selecionado para poder fazer a pesquisa das duvidas
        intent.putExtra("ID_MATERIA", item.getId_materia());
        intent.putExtra("ID_SUBTOPICO", item.getId_subtopico());
        intent.putExtra("NOME_SUBTOPICO", item.getNome_subtopico());

        view.startActivity(intent);
    }

}
