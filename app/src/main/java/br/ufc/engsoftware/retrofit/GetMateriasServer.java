//package br.ufc.engsoftware.retrofit;
//
//import android.app.ProgressDialog;
//import android.content.Context;
//import android.os.AsyncTask;
//import android.util.Log;
//
//import java.io.IOException;
//import java.util.List;
//import java.util.Vector;
//
//import br.ufc.engsoftware.BDLocalManager.MateriaBDManager;
//import br.ufc.engsoftware.BDLocalManager.SubtopicoBDManager;
//import br.ufc.engsoftware.auxiliar.Statics;
//import br.ufc.engsoftware.auxiliar.Utils;
//import br.ufc.engsoftware.models.Materia;
//import br.ufc.engsoftware.models.Subtopico;
//import br.ufc.engsoftware.retrofit.TaSabidoApi;
//import retrofit2.Call;
//import retrofit2.Callback;
//import retrofit2.Response;
//import retrofit2.Retrofit;
//import retrofit2.converter.gson.GsonConverterFactory;
//
//import static java.lang.Integer.parseInt;
//
///**
// * Created by Thiago on 26/05/2016.
// */
//public class GetMateriasServer {
//
//
//        // Lista das Subtopicos obbtidas do web service
//        Vector<Materia> listaSubtopicos;
//
//        Context context;
//
//        Utils utils;
//
//        // Dialog com barra de progresso mostrado na tela
//        ProgressDialog proDialog;
//
//        public GetMateriasServer(Context context){
//            this.context = context;
//        }
//
//
//        public void pegarMateriasDoServidor(){
//
//            Retrofit retrofit = new Retrofit.Builder()
//                    .baseUrl(Statics.BASE_URL)
//                    .addConverterFactory(GsonConverterFactory.create())
//                    .build();
//
//            TaSabidoApi api = retrofit.create(TaSabidoApi.class);
//
//            Call<MateriaRetrofit> requestSubtopicos = api.listMaterias();
//
//            requestSubtopicos.enqueue(new Callback<MateriaRetrofit>() {
//                @Override
//                public void onResponse(Call<MateriaRetrofit> call, Response<MateriaRetrofit> response) {
//                    if (response.isSuccessful()) {
//                        MateriaRetrofit listaSubtopicos = response.body();
//                        salvarMateriasNoBDLocal(listaSubtopicos);
//                    } else {
//                    }
//                    utils.dismissProgressDialog();
//                }
//
//                @Override
//                public void onFailure(Call<MateriaRetrofit> call, Throwable t) {
//                    Log.e("deu problema", t.getMessage());
//
//                }
//            });
//        }
//
//
//
//        public void salvarMateriasNoBDLocal(MateriaRetrofit listaSubtopico){
//            //atualiza o banco de dados local com os dados vindos do servidor
//            MateriaBDManager sinc = new MateriaBDManager();
//            if (listaSubtopicos == null)
//                listaSubtopicos = new Vector<>();
//
//            for (Materia materia: listaSubtopico.listaMaterias) {
//                listaSubtopicos.add(materia);
//            }
//            sinc.atualizarMaterias(context, listaSubtopicos);
//        }
//    }
