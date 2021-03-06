package br.ufc.engsoftware.retrofit;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.JsonElement;

import java.io.IOException;
import java.util.List;
import java.util.Vector;

import br.ufc.engsoftware.BDLocalManager.MateriaBDManager;
import br.ufc.engsoftware.BDLocalManager.SubtopicoBDManager;
import br.ufc.engsoftware.auxiliar.Statics;
import br.ufc.engsoftware.auxiliar.Utils;
import br.ufc.engsoftware.models.Materia;
import br.ufc.engsoftware.models.Subtopico;
import br.ufc.engsoftware.retrofit.TaSabidoApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static java.lang.Integer.parseInt;

/**
 * Created by Thiago on 26/05/2016.
 */
public class GetMateriasServer {


        // Lista das Subtopicos obbtidas do web service
        Vector<Materia> listaSubtopicos;

        Context context;

        Utils utils;

        // Dialog com barra de progresso mostrado na tela
        ProgressDialog proDialog;

        public GetMateriasServer(Context context){
            this.context = context;
        }


        public void pegarMateriasDoServidor(){

            // Showing progress loading dialog
            proDialog = new ProgressDialog(context);
            proDialog.setMessage("Carregando Matérias ...");
            proDialog.setCancelable(false);
            proDialog.show();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Statics.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            TaSabidoApi api = retrofit.create(TaSabidoApi.class);

            Call<MateriaRetrofit> requestSubtopicos = api.listaMaterias();

            requestSubtopicos.enqueue(new Callback<MateriaRetrofit>() {
                @Override
                public void onResponse(Call<MateriaRetrofit> call, Response<MateriaRetrofit> response) {
                    if (response.isSuccessful()) {
                        MateriaRetrofit listaSubtopicos = response.body();
                        salvarMateriasNoBDLocal(listaSubtopicos);
                    } else {
                    }
                    // Tira o dialog de progresso da tela
                    if (proDialog.isShowing())
                        proDialog.dismiss();
                }

                @Override
                public void onFailure(Call<MateriaRetrofit> call, Throwable t) {
                    Log.e("deu problema", t.getMessage());
                    // Tira o dialog de progresso da tela
                    if (proDialog.isShowing())
                        proDialog.dismiss();

                }
            });
        }



        public void salvarMateriasNoBDLocal(MateriaRetrofit listaSubtopicoParam){
            //atualiza o banco de dados local com os dados vindos do servidor
            MateriaBDManager sinc = new MateriaBDManager();
            if (listaSubtopicos == null)
                listaSubtopicos = new Vector<>();

            for (Materia materia: listaSubtopicoParam.results) {
                listaSubtopicos.add(materia);
            }
            sinc.atualizarMaterias(context, listaSubtopicos);
        }
    }
