package br.ufc.engsoftware.retrofit;

import android.content.Context;
import android.util.Log;

import java.util.Vector;

import br.ufc.engsoftware.BDLocalManager.MateriaBDManager;
import br.ufc.engsoftware.auxiliar.Statics;
import br.ufc.engsoftware.auxiliar.Utils;
import br.ufc.engsoftware.models.Materia;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by limaneto on 26/06/16.
 */
public class GetMateriasServerRetrofit {

    // Lista das materias obbtidas do web service
    Vector<Materia> listaMaterias;

    Context context;

    public GetMateriasServerRetrofit(Context context){
        this.context = context;
    }


    public void pegarMateriasDoServidor(){

        TaSabidoApi api = Utils.retornarApi();

        Call<TaSabidoListaDuvida> requestDuvidas = api.listDuvidas();

        requestDuvidas.enqueue(new Callback<TaSabidoListaDuvida>() {
            @Override
            public void onResponse(Call<TaSabidoListaDuvida> call, Response<TaSabidoListaDuvida> response) {
                if(response.isSuccessful()){
                    TaSabidoListaDuvida listaDuvidas = response.body();
                }else{
                }
            }

            @Override
            public void onFailure(Call<TaSabidoListaDuvida> call, Throwable t) {
                Log.e("deu problema", "error");
            }
        });
    }


    public void salvarMateriasNoBDLocal(TaSabidoListaDuvida listaDuvida){
        //atualiza o banco de dados local com os dados vindos do servidor
        MateriaBDManager sinc = new MateriaBDManager();
        if (listaMaterias == null)
            listaMaterias = new Vector<>();
        sinc.atualizarMaterias(context, listaMaterias);
    }
}
