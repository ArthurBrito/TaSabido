package br.ufc.engsoftware.retrofit;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;

import java.util.List;
import java.util.Vector;

import br.ufc.engsoftware.BDLocalManager.DuvidaBDManager;
import br.ufc.engsoftware.auxiliar.Utils;
import br.ufc.engsoftware.models.Duvida;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by limaneto on 26/06/16.
 */
public class GetDuvidasServer {

    // Lista das materias obbtidas do web service
    Vector<Duvida> listaDuvidas;

    ProgressDialog proDialog;

    Context context;

    Utils utils;

    public GetDuvidasServer(Context context){
        this.context = context;
    }

    public void pegarDuvidasDoServidor(){


        // Showing progress loading dialog
        proDialog = new ProgressDialog(context);
        proDialog.setMessage("Carregando Dúvidas ...");
        proDialog.setCancelable(false);
        proDialog.show();

        TaSabidoApi api = Utils.retornarApi();

        Call<DuvidaRetrofit> requestDuvidas = api.listDuvidas();

        requestDuvidas.enqueue(new Callback<DuvidaRetrofit>() {
            @Override
            public void onResponse(Call<DuvidaRetrofit> call, Response<DuvidaRetrofit> response) {
                if (response.isSuccessful()) {
                    DuvidaRetrofit listaDuvidas = response.body();
                    salvarDuvidasBDLocal(listaDuvidas);
                } else {
                }
                // Tira o dialog de progresso da tela
                if (proDialog.isShowing())
                    proDialog.dismiss();
            }

            @Override
            public void onFailure(Call<DuvidaRetrofit> call, Throwable t) {
                // Tira o dialog de progresso da tela
                if (proDialog.isShowing())
                    proDialog.dismiss();
            }
        });
    }

    public void salvarDuvidasBDLocal(DuvidaRetrofit listaDuvidasParam){
        //atualiza o banco de dados local com os dados vindos do servidor
        DuvidaBDManager sinc = new DuvidaBDManager();
        if (listaDuvidas == null)
            listaDuvidas = new Vector<>();

        for (Duvida duvida: listaDuvidasParam.results) {
            listaDuvidas.add(duvida);
        }
        sinc.atualizarDuvidas(context, listaDuvidas);
    }
}
