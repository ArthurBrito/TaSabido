package br.ufc.engsoftware.retrofit;

import com.google.gson.JsonElement;

import java.util.List;

import br.ufc.engsoftware.models.Duvida;
import br.ufc.engsoftware.models.Materia;
import br.ufc.engsoftware.models.Moeda;
import br.ufc.engsoftware.models.Monitoria;
import br.ufc.engsoftware.models.Subtopico;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by limaneto on 26/06/16.
 */
public interface TaSabidoApi {

    @GET("listar_duvidas/")
    Call<DuvidaRetrofit> listDuvidas();

    @GET("listar_monitorias/")
    Call<MonitoriaRetrofit> listMonitorias();

    @GET("listar_materias/")
    Call<MateriaRetrofit> listaMaterias();

    @GET("listar_subtopicos/")
    Call<SubtopicoRetrofit> listSubtopicos();

    @GET("moedas_usuario/{id}")
    Call<MoedaRetrofit> moedasUsuario(@Path("id") int id_usuario);
}
