//package br.ufc.engsoftware.retrofit;
//
//import android.app.ProgressDialog;
//import android.content.Context;
//import android.util.Log;
//
//import java.util.Vector;
//
//import br.ufc.engsoftware.auxiliar.Statics;
//import br.ufc.engsoftware.auxiliar.Utils;
//import br.ufc.engsoftware.models.MonitoriaNaoRealm;
//import retrofit2.Call;
//import retrofit2.Callback;
//import retrofit2.Response;
//import retrofit2.Retrofit;
//import retrofit2.converter.gson.GsonConverterFactory;
//
///**
// * Created by Thiago on 26/05/2016.
// */
//public class GetMonitoriasServer {
//
//
//        // Lista das Subtopicos obbtidas do web service
//        Vector<MonitoriaNaoRealm> listaMonitorias;
//
//        Context context;
//
//        Utils utils;
//
//        // Dialog com barra de progresso mostrado na tela
//        ProgressDialog proDialog;
//
//        public GetMonitoriasServer(Context context){
//            this.context = context;
//        }
//
//
//        public void pegarMonitoriasDoServidor(){
//
//            // Showing progress loading dialog
//            proDialog = new ProgressDialog(context);
//            proDialog.setMessage("Carregando Monitorias ...");
//            proDialog.setCancelable(false);
//            proDialog.show();
//
//            Retrofit retrofit = new Retrofit.Builder()
//                    .baseUrl(Statics.BASE_URL)
//                    .addConverterFactory(GsonConverterFactory.create())
//                    .build();
//
//            TaSabidoApi api = retrofit.create(TaSabidoApi.class);
//
//            Call<MonitoriaRetrofit> requestMonitorias = api.listMonitorias();
//
//            requestMonitorias.enqueue(new Callback<MonitoriaRetrofit>() {
//                @Override
//                public void onResponse(Call<MonitoriaRetrofit> call, Response<MonitoriaRetrofit> response) {
//                    if (response.isSuccessful()) {
//                        MonitoriaRetrofit listaMonitorias = response.body();
//                        salvarMonitoriasNoBDLocal(listaMonitorias);
//                    } else {
//                    }
//                    // Tira o dialog de progresso da tela
//                    if (proDialog.isShowing())
//                        proDialog.dismiss();
//                }
//
//                @Override
//                public void onFailure(Call<MonitoriaRetrofit> call, Throwable t) {
//                    Log.e("deu problema", t.getMessage());
//                    // Tira o dialog de progresso da tela
//                    if (proDialog.isShowing())
//                        proDialog.dismiss();
//
//                }
//            });
//        }
//
//
//
//        public void salvarMonitoriasNoBDLocal(MonitoriaRetrofit listaMonitoriasParam){
//            //atualiza o banco de dados local com os dados vindos do servidor
////            MonitoriaBDManager sinc = new MonitoriaBDManager();
//            if (listaMonitorias == null)
//                listaMonitorias = new Vector<>();
//
//            for (MonitoriaNaoRealm monitoria: listaMonitoriasParam.results) {
//                listaMonitorias.add(monitoria);
//            }
////            sinc.atualizarMonitorias(context, listaMonitorias);
//        }
//    }
