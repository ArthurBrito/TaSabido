package br.ufc.engsoftware.retrofit;

import java.util.List;

import br.ufc.engsoftware.Ormlite.Data;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.http.GET;

/**
 * Created by limaneto on 28/06/16.
 */
public class DataService {
    private static DataService instance = null;

    public interface DataAPI {
        @GET("listar_cursos/")
        Call <Data> getData();
    }


    public static DataService getInstance() {
        if (instance == null)
            instance = new DataService();

        return instance;
    }

    public DataService.DataAPI getApi(){
        Retrofit retrofit = RetrofitService.getInstance();
        return retrofit.create(DataService.DataAPI.class);
    }
}
