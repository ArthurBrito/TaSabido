package br.ufc.engsoftware.auxiliar;

import br.ufc.engsoftware.models.Duvida;
import retrofit2.Callback;
import retrofit2.http.GET;

/**
 * Created by limaneto on 29/05/16.
 */
public interface MInterface {
    @GET("/users/mobilesiri")
    void getUser(Callback<Duvida> uscb);

}
