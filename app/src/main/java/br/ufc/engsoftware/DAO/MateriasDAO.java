package br.ufc.engsoftware.DAO;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.widget.ListView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.util.ArrayList;

import br.ufc.engsoftware.BDLocalManager.MateriaBDManager;
import br.ufc.engsoftware.BDLocalManager.SubtopicoBDManager;
import br.ufc.engsoftware.auxiliar.WebRequest;
import br.ufc.engsoftware.fragments.MateriaFragment;
import br.ufc.engsoftware.models.Materia;
import br.ufc.engsoftware.views.MateriaListView;


import static java.lang.Integer.parseInt;

/**
 * Created by Thiago on 18/05/2016.
 */
public class MateriasDAO extends AsyncTask<Void, Void, Void> {

    // Referencia ao fragment que está sendo mostrado
    Fragment fragment;
    Context context;

    // Referencia ao ListView do MateriaFragment
    ListView listviewMaterias;

    // Classe responsável por montar o ListView
    MateriaListView gerenciadorMateriasLV;

    // Dialog com barra de progresso mostrado na tela
    ProgressDialog proDialog;

    public MateriasDAO(Context context, ListView listviewMaterias, Fragment fragment) {
        this.context = context;
        this.listviewMaterias = listviewMaterias;
        this.fragment = fragment;
    }

    // Mostra a barra de progresso na tela
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        // Showing progress loading dialog
        proDialog = new ProgressDialog(context);
        proDialog.setMessage("Carregando Matérias...");
        proDialog.setCancelable(false);
        proDialog.show();
    }

    // Monta ListView com os dados do BD local
    @Override
    protected Void doInBackground(Void... arg0) {



        return null;
    }

    // Metodo chamado ou terminar o doInBackground
    @Override
    protected void onPostExecute(Void requestresult) {
        super.onPostExecute(requestresult);
        // Tira o dialog de progresso da tela
        if (proDialog.isShowing())
            proDialog.dismiss();

        MateriaBDManager materiaDB = new MateriaBDManager();
        gerenciadorMateriasLV = new MateriaListView(listviewMaterias, context, materiaDB.pegarMaterias(context));

        // Chama o metodo do MateriaFragment responsavel por configurar o SearchView
        ((MateriaFragment)fragment).montarSearchViewMaterias();
    }

    public MateriaListView getGerenciadorMateriasLV() {
        return gerenciadorMateriasLV;
    }

}
