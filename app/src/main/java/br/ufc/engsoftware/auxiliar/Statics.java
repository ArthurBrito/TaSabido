package br.ufc.engsoftware.auxiliar;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by limaneto on 18/05/16.
 */
public class Statics {
//    public static String BASE_URL = "https://avalan.herokuapp.com/tasabido/";
//    public static String BASE_URL = "http://10.0.2.2:8080/tasabido/";
//    public static String BASE_URL = "http://127.0.0.1:8000/tasabido/";
//    public static String BASE_URL = "http://10.99.171.93:8000/tasabido/";

    public static String BASE_URL = "http://10.0.1.25:8000/tasabido/";
//    public static String BASE_URL = "http://192.168.25.13:8000/tasabido/";

    //CADASTRAR
    public static String CADASTRAR_USUARIO = BASE_URL + "cadastrar_usuario/";
    public static String CADASTRAR_DUVIDA = BASE_URL + "cadastrar_duvida/";
    public static String CADASTRAR_MONITORIA = BASE_URL + "cadastrar_monitoria/";
    public static String CADASTRAR_MATERIA = BASE_URL + "cadastrar_materia/";
    public static String CADASTRAR_SUBTOPICO = BASE_URL + "cadastrar_subtopico/";

//    AUTENTICAR
    public static String AUTENTICAR_USUARIO = BASE_URL + "autenticar_usuario/";

    //LISTAR
    public static String LISTAR_USUARIOS = BASE_URL + "listar_usuarios/";
    public static String LISTAR_CURSOS = BASE_URL + "listar_cursos/";

    //ATUALIZAR
    public static String ATUALIZAR_DUVIDA = BASE_URL + "atualizar_duvida/";
    public static String ATUALIZAR_MONITORIA = BASE_URL + "atualizar_monitoria/";

    //DELETAR
    public static String DELETAR_DUVIDA = BASE_URL + "deletar_duvida/";
    public static String DELETAR_MONITORIA = BASE_URL + "deletar_monitoria/";

    //RETRIEVE
    public static String BUSCAR_MOEDA = BASE_URL + "moedas_usuario/";
    public static String BUSCAR_USUARIO = BASE_URL + "usuario/";


    //AUX
    public static String ENVIAR_EMAIL = BASE_URL + "enviar_email/";
    public static String PAGAR = BASE_URL + "pagamento/";

}
