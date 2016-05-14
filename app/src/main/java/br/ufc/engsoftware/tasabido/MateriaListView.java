package br.ufc.engsoftware.tasabido;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.Collections;
import java.util.Vector;

import br.ufc.engsoftware.DAO.MateriaDAO;
import br.ufc.engsoftware.models.Materia;

/**
 * Created by Thiago on 14/05/2016.
 */
public class MateriaListView {

    ListView listview;


    public MateriaListView(ListView listview, Context view, Vector<Materia> vector) {
        this.listview = listview;

        setListView(view, vector);
    }

    public void setListView(Context view, Vector<Materia> vector){

        final ArrayAdapter<Materia> adapter = new ArrayAdapter<Materia>(view,
                R.layout.materias_listview_rowlayout, R.id.label, vector);
        listview.setAdapter(adapter);

        /*
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onItemClick(AdapterView<?> parent, final View view,
                                    int position, long id) {
                final Materia item = (Materia) parent.getItemAtPosition(position);

                int itemId = vectorOriginal.getId(item);

                chamarEditPostActivity(view.getContext(), item, itemId);
            }

        });*/
    }

    /*
    private void chamarEditPostActivity(Context view, Materia item, int id){
        Intent intent = new Intent();
        intent.setAction("br.ufc.dc.dspm.action.EDITTWEETER");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setComponent(null);
        intent.addCategory("br.ufc.dc.dspm.category.CATEGORIA");
        intent.putExtra("TEXT", item.getText());
        intent.putExtra("ID", id);

        view.startActivity(intent);
    }
    */
}
