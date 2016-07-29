package br.ufc.engsoftware.Presenters;

import android.content.Context;
import android.util.Log;

import java.util.List;

import br.ufc.engsoftware.Ormlite.Data;
import br.ufc.engsoftware.retrofit.DataService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by limaneto on 24/07/16.
 */
public class DataPresenter {
    private DataService dataService;
    private DataPresenter.DataCallbackListener activity;




    public interface DataCallbackListener {
        void finishedGettingServerData(Data dados);
    }


    public DataPresenter(DataPresenter.DataCallbackListener activity){
        this.activity = activity;
        dataService = DataService.getInstance();
    }


    public void getServerData(){
        getDataFromServer();
    }


    protected void getDataFromServer(){
        dataService
                .getApi()
                .getData()
                .enqueue(new Callback<Data>() {
                    @Override
                    public void onResponse(Call<Data> call, Response<Data> response) {
                        Data dados = response.body();

                        if (dados != null) {
                            activity.finishedGettingServerData(dados);
                        }
                    }

                    @Override
                    public void onFailure(Call<Data> call, Throwable t) {
                        Log.i("test", t.getMessage().toString());
                    }
                });
    }

}
