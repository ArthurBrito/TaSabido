//package br.ufc.engsoftware.tasabido;
//
//import android.app.Activity;
//import android.content.Intent;
//import android.content.SharedPreferences;
//import android.support.v7.app.ActionBar;
//import android.support.v7.app.AppCompatActivity;
//import android.os.Bundle;
//import android.view.Gravity;
//import android.view.Menu;
//import android.view.MenuInflater;
//import android.view.MenuItem;
//import android.view.View;
//import android.widget.Button;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import com.j256.ormlite.android.apptools.OpenHelperManager;
//import com.j256.ormlite.dao.Dao;
//
//import org.json.JSONArray;
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import java.sql.SQLException;
//import java.util.HashSet;
//import java.util.Set;
//
//import br.ufc.engsoftware.Ormlite.Monitoria;
//import br.ufc.engsoftware.Ormlite.config.OpenDatabaseHelper;
//import br.ufc.engsoftware.auxiliar.Statics;
//import br.ufc.engsoftware.auxiliar.Utils;
//import br.ufc.engsoftware.serverDAO.PostDeleteMonitoria;
//
//
//public class VerMonitoriaActivity extends AppCompatActivity {
//
//    private String username;
//    private int id_monitoria, id_usuario;
//    Activity activity;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_ver_monitoria);
//
//        // Seta as configurações da ActionBar
//        ActionBar ab = getSupportActionBar();
//        ab.setTitle("Monitoria");
//        ab.setDisplayHomeAsUpEnabled(true);
//        activity = this;
//
//        TextView tvTituloMonitoria = (TextView) findViewById(R.id.tv_titulo_monitoria);
//        TextView tvDescricaoMonitoria = (TextView) findViewById(R.id.tv_descricao_monitoria);
//        TextView tvDataMonitoria = (TextView) findViewById(R.id.tv_data_monitoria);
//        TextView tvLocalMonitoria = (TextView) findViewById(R.id.tv_local_monitoria);
//        TextView tvMonitorMonitoria = (TextView) findViewById(R.id.tv_monitor_monitoria);
//        Button bt_delete = (Button) findViewById(R.id.btn_delete);
//        Button participar = (Button) findViewById(R.id.btn_participar);
//        bt_delete.setVisibility(View.GONE);
//
//        Intent intent = getIntent();
//        String titulo = intent.getStringExtra("TITULO");
//        String descricao = intent.getStringExtra("DESCRICAO");
//        String dia = intent.getStringExtra("DIA");
//        String horario = intent.getStringExtra("HORARIO");
//        String local = intent.getStringExtra("ENDERECO");
//        username = intent.getStringExtra("USERNAME");
//        id_monitoria = intent.getIntExtra("ID_MONITORIA", 0);
//        id_usuario = intent.getIntExtra("ID_USUARIO", 0);
//
//
//        Utils utils = new Utils(this);
//        String id = utils.getFromSharedPreferences("id_usuario", "");
//        if (id_usuario == Integer.parseInt(id)){
//            participar.setVisibility(View.GONE);
//            bt_delete.setVisibility(View.VISIBLE);
//        }
//
//        tvTituloMonitoria.setText(titulo);
//        tvDescricaoMonitoria.setText(descricao);
//        tvDataMonitoria.setText(dia + " - " + horario);
//        tvLocalMonitoria.setText(local);
//        tvMonitorMonitoria.setText(username);
//    }
//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
//        final String id_usuario_cache = sharedPreferences.getString("id_usuario", "Usuario");
//
//        // Só mostra o botão de editar se este for o usuario dono da monitoria
//        if(Integer.valueOf(id_usuario_cache) == id_usuario)
//        {
//            MenuInflater inflater = getMenuInflater();
//            inflater.inflate(R.menu.ver_monitoria_bar_menu, menu);
//        }
//        return true;
//    }
//
//    // Seta ação dos botões da ActionBar
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//            case android.R.id.home:
//                finish();
//                return true;
//
//            case R.id.action_edit:
//                chamarEditarMonitoriaActivity();
//                return true;
//
//            default:
//                return super.onOptionsItemSelected(item);
//        }
//    }
//
//    private void chamarEditarMonitoriaActivity(){
//        Intent intent = new Intent(this, EditMonitoriaActivity.class);
//        intent.setAction("br.ufc.engsoftware.tasabido.EDITAR_MONITORIA");
//        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//
//        Intent intentAnt = getIntent();
//
//        // Passa o nome da matéria para ser exibido na activity, e o id para pesquisar no banco
//        intent.putExtra("TITULO", intentAnt.getStringExtra("TITULO"));
//        intent.putExtra("DESCRICAO", intentAnt.getStringExtra("DESCRICAO"));
//        intent.putExtra("DATA", intentAnt.getStringExtra("DATA"));
//        intent.putExtra("ENDERECO", intentAnt.getStringExtra("ENDERECO"));
//        intent.putExtra("ID_SUBTOPICO", intentAnt.getIntExtra("ID_SUBTOPICO", 0));
//        intent.putExtra("ID_MONITORIA", intentAnt.getIntExtra("ID_MONITORIA", 0));
//        intent.putExtra("ID_MATERIA", intentAnt.getIntExtra("ID_MATERIA", 0));
//        intent.putExtra("ID_USUARIO", intentAnt.getIntExtra("ID_USUARIO", 0));
//        intent.putExtra("DIA", intentAnt.getStringExtra("DIA"));
//        intent.putExtra("HORARIO", intentAnt.getStringExtra("HORARIO"));
//        intent.putExtra("USERNAME", intentAnt.getStringExtra("USERNAME"));
//
//        startActivity(intent);
//    }
//
//    public void onClickParticiparMonitoria(View view){
//        Utils utils = new Utils(this);
//        Set<String> array_ids = new HashSet<String>();
//        array_ids = utils.getMonitoriasConfirmadasFromSharedPreferences("monitorias", array_ids);
//        array_ids.add(String.valueOf(id_monitoria));
//        utils.saveMonitoriasConfirmadasSharedPreferences(array_ids);
//
//        String first_name = utils.getFromSharedPreferences("first_name", "");
//        String email = utils.getFromSharedPreferences("email", "");
//        String mensagem = first_name + " confirmou presença na sua monitoria.\n" + "Email: " + email;
//        String param = concatenateParam(String.valueOf(id_usuario), "Monitoria", mensagem);
//        utils.sendEmail(param, this);
//    }
//
//    public void onClickDeletarMonitoria(View view){
//        Utils utils = new Utils(this);
//        String id_usuario_string = utils.getFromSharedPreferences("id_usuario", "");
//        int id_usuario = Integer.parseInt(id_usuario_string);
//
//        Monitoria monitoria = new Monitoria(id_monitoria, id_usuario);
//        JSONObject jsonParam = createJsonParamToDeleteMonitoria(monitoria);
//
//        try {
//            new PostDeleteMonitoria(this, jsonParam, Statics.DELETAR_MONITORIA, new PostDeleteMonitoria.AsyncResponse(){
//                Toast toast;
//                public void processFinish(String output){
//                    if (output.equals("200")){
//                        toast = Toast.makeText(activity, "Monitoria deletada.", Toast.LENGTH_SHORT);
//                        deletarMonitoriaBDLocal();
//                        finish();
//                    }else{
//                        toast = Toast.makeText(activity, "Algum erro ocorreu, tente denovo mais tarde.", Toast.LENGTH_SHORT);
//                    }
//                    toast.setGravity(Gravity.CENTER, 0, 0);
//                    toast.show();
//                }
//            }).execute();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    private void deletarMonitoriaBDLocal(){
//        try {
//            getDao().deleteById((long) id_monitoria);
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public JSONObject createJsonParamToDeleteMonitoria(Monitoria monitoria) {
//        JSONObject json = new JSONObject();
//        JSONArray subtopicosJson = new JSONArray();
//        try {
//            json.put("id_usuario", monitoria.getId_usuario());
//            json.put("id_monitoria", monitoria.getId_monitoria());
//
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        return json;
//    }
//
//    public String concatenateParam(String id_usuario, String assunto, String mensagem){
//        String param = "id_to=";
//        param += id_usuario;
//        param += "&";
//        param += "assunto=";
//        param += assunto;
//        param += "&";
//        param += "message=";
//        param += mensagem;
//
//        return param;
//    }
//
//    public Dao getDao(){
//        OpenDatabaseHelper monitoriaOpenDatabaseHelper = OpenHelperManager.getHelper(this,
//                OpenDatabaseHelper.class);
//
//        Dao<Monitoria, Long> monitoriaDao = null;
//        try {
//            monitoriaDao = monitoriaOpenDatabaseHelper.getMonitoriaDao();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//
//        return monitoriaDao;
//    }
//}
