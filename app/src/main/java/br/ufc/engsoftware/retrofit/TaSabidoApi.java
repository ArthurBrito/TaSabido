package br.ufc.engsoftware.retrofit;

import java.util.List;

import br.ufc.engsoftware.auxiliar.Statics;
import br.ufc.engsoftware.models.Duvida;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by limaneto on 26/06/16.
 */
public interface TaSabidoApi {

    @GET("listar_duvidas/")
    Call<TaSabidoListas> listDuvidas();

    @GET("listar_monitorias/")
    Call<TaSabidoListas> listMonitorias();

    @GET("listar_materias/")
    Call<TaSabidoListas> listMaterias();

    @GET("listar_subtopicos/")
    Call<TaSabidoListas> listSubtopicos();

    @GET("moedas_usuario/{id}")
    Call<TaSabidoListas> moedasUsuario(@Path("id") int id_usuario);
}
