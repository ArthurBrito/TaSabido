package br.ufc.engsoftware.auxiliar;

/**
 * Created by limaneto on 18/05/16.
 */
public class Statics {
    public static String ROOT_URL = "https://avalan.herokuapp.com/tasabido/";
//    public static String ROOT_URL = "http://10.0.2.2:8000/tasabido/";

    //CADASTRAR
    public static String CADASTRAR_USUARIO = ROOT_URL + "cadastrar_usuario/";
    public static String CADASTRAR_DUVIDA = ROOT_URL + "cadastrar_duvida/";
    public static String CADASTRAR_MONITORIA = ROOT_URL + "cadastrar_monitoria/";
    public static String CADASTRAR_MATERIA = ROOT_URL + "cadastrar_materia/";
    public static String CADASTRAR_SUBTOPICO = ROOT_URL + "cadastrar_subtopico/";

    //AUTENTICAR
    public static String AUTENTICAR_USUARIO = ROOT_URL + "autenticar_usuario/";

    //LISTAR
    public static String LISTAR_USUARIOS = ROOT_URL + "listar_usuarios/";
    public static String LISTAR_DUVIDAS = ROOT_URL + "listar_duvidas/";
    public static String LISTAR_MONITORIAS = ROOT_URL + "listar_monitorias/";
    public static String LISTAR_MATERIAS = ROOT_URL + "listar_materias/";
    public static String LISTAR_SUBTOPICOS = ROOT_URL + "listar_subtopicos/";

    //ATUALIZAR
    public static String ATUALIZAR_DUVIDA = ROOT_URL + "atualizar_duvida/";
    public static String ATUALIZAR_MONITORIA = ROOT_URL + "atualizar_monitoria/";

    //DELETAR
    public static String DELETAR_DUVIDA = ROOT_URL + "deletar_duvida/";
    public static String DELETAR_MONITORIA = ROOT_URL + "deletar_monitoria/";

    //RETRIEVE
    public static String BUSCAR_MOEDA = ROOT_URL + "moedas_usuario/";
    public static String BUSCAR_USUARIO = ROOT_URL + "usuario/";


    //AUX
    public static String ENVIAR_EMAIL = ROOT_URL + "enviar_email/";
    public static String PAGAR = ROOT_URL + "pagamento/";
}
