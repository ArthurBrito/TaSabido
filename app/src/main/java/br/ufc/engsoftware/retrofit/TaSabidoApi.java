package br.ufc.engsoftware.retrofit;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by limaneto on 26/06/16.
 */
public interface TaSabidoApi {

    @GET("listar_duvidas/")
    Call<TaSabidoListaDuvida> listDuvidas();

    @GET("listar_monitorias/")
    Call<TaSabidoListaDuvida> listMonitorias();

    @GET("listar_materias/")
    Call<TaSabidoListaDuvida> listMaterias();

    @GET("listar_subtopicos/")
    Call<TaSabidoListaDuvida> listSubtopicos();

    @GET("moedas_usuario/{id}")
    Call<TaSabidoListaDuvida> moedasUsuario(@Path("id") int id_usuario);
}
