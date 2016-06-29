//package br.ufc.engsoftware.retrofit;
//
//import android.app.ProgressDialog;
//import android.content.Context;
//import android.util.Log;
//
//import java.util.List;
//import java.util.Vector;
//
//import br.ufc.engsoftware.BDLocalManager.SubtopicoBDManager;
//import br.ufc.engsoftware.auxiliar.Utils;
//import br.ufc.engsoftware.models.Subtopico;
//import br.ufc.engsoftware.models.Subtopico;
//import retrofit2.Call;
//import retrofit2.Callback;
//import retrofit2.Response;
//
///**
// * Created by limaneto on 26/06/16.
// */
//public class GetSubtopicosServer {
//
//    // Lista das Subtopicos obbtidas do web service
//    Vector<Subtopico> listaSubtopicos;
//
//    Context context;
//
//    Utils utils;
//
//    // Dialog com barra de progresso mostrado na tela
//    ProgressDialog proDialog;
//
//    public GetSubtopicosServer(Context context){
//        this.context = context;
//    }
//
//
//    public void pegarSubtopicosDoServidor(){
//        TaSabidoApi api = Utils.retornarApi();
//
//        Call<SubtopicoRetrofit> requestSubtopicos = api.listSubtopicos();
//
//        requestSubtopicos.enqueue(new Callback<SubtopicoRetrofit>() {
//            @Override
//            public void onResponse(Call<SubtopicoRetrofit> call, Response<SubtopicoRetrofit> response) {
//                if (response.isSuccessful()) {
//                    SubtopicoRetrofit listaSubtopicos = response.body();
//                    salvarSubtopicosNoBDLocal(listaSubtopicos);
//                } else {
//                }
//                utils.dismissProgressDialog();
//            }
//
//            @Override
//            public void onFailure(Call<SubtopicoRetrofit> call, Throwable t) {
//                Log.e("deu problema", t.getMessage());
//
//            }
//        });
//    }
//
//
//
//    public void salvarSubtopicosNoBDLocal(SubtopicoRetrofit listaSubtopicoParam){
//        //atualiza o banco de dados local com os dados vindos do servidor
//        SubtopicoBDManager sinc = new SubtopicoBDManager();
//        if (listaSubtopicos == null)
//            listaSubtopicos = new Vector<>();
//
//        for (Subtopico Subtopico: listaSubtopicoParam.listaSubtopicos) {
//            listaSubtopicos.add(Subtopico);
//        }
//        sinc.atualizarSubtopicos(context, listaSubtopicos);
//    }
//}
