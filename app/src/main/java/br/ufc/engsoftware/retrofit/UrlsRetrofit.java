package br.ufc.engsoftware.retrofit;
import br.ufc.engsoftware.Ormlite.Monitoria;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by limaneto on 26/06/16.
 */
public interface UrlsRetrofit {

    @GET("moedas_usuario/{id}")
    Call<MoedaRetrofit> moedasUsuario(@Path("id") int id_usuario);

    @POST("cadastrar_monitoria/")
    Call<Monitoria> cadastrarMonitoria(@Body Monitoria monitoria);

}
