package br.ufc.engsoftware.tasabido;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.util.HashSet;
import java.util.Set;

import br.ufc.engsoftware.auxiliar.Utils;


public class VerMonitoriaActivity extends AppCompatActivity {

    private String username;
    private int id_monitoria, id_usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_monitoria);

        // Seta as configurações da ActionBar
        ActionBar ab = getSupportActionBar();
        ab.setTitle("Monitoria");
        ab.setDisplayHomeAsUpEnabled(true);

        TextView tvTituloMonitoria = (TextView) findViewById(R.id.tv_titulo_monitoria);
        TextView tvDescricaoMonitoria = (TextView) findViewById(R.id.tv_descricao_monitoria);
        TextView tvDataMonitoria = (TextView) findViewById(R.id.tv_data_monitoria);
        TextView tvLocalMonitoria = (TextView) findViewById(R.id.tv_local_monitoria);
        TextView tvMonitorMonitoria = (TextView) findViewById(R.id.tv_monitor_monitoria);

        Intent intent = getIntent();
        String titulo = intent.getStringExtra("TITULO");
        String descricao = intent.getStringExtra("DESCRICAO");
        String dia = intent.getStringExtra("DIA");
        String horario = intent.getStringExtra("HORARIO");
        String local = intent.getStringExtra("ENDERECO");
        username = intent.getStringExtra("USERNAME");
        id_monitoria = intent.getIntExtra("ID_MONITORIA", 0);
        id_usuario = intent.getIntExtra("ID_USUARIO", 0);

        tvTituloMonitoria.setText(titulo);
        tvDescricaoMonitoria.setText(descricao);
        tvDataMonitoria.setText(dia + " - " + horario);
        tvLocalMonitoria.setText(local);
        tvMonitorMonitoria.setText(username);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        final String id_usuario_cache = sharedPreferences.getString("id_usuario", "Usuario");

        // Só mostra o botão de editar se este for o usuario dono da monitoria
        if(Integer.valueOf(id_usuario_cache) == id_usuario)
        {
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.ver_monitoria_bar_menu, menu);
        }
        return true;
    }

    // Seta ação dos botões da ActionBar
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;

            case R.id.action_edit:
                chamarEditarMonitoriaActivity();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void chamarEditarMonitoriaActivity(){
        Intent intent = new Intent(this, EditMonitoriaActivity.class);
        intent.setAction("br.ufc.engsoftware.tasabido.EDITAR_MONITORIA");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        Intent intentAnt = getIntent();

        // Passa o nome da matéria para ser exibido na activity, e o id para pesquisar no banco
        intent.putExtra("TITULO", intentAnt.getStringExtra("TITULO"));
        intent.putExtra("DESCRICAO", intentAnt.getStringExtra("DESCRICAO"));
        intent.putExtra("DATA", intentAnt.getStringExtra("DATA"));
        intent.putExtra("ENDERECO", intentAnt.getStringExtra("ENDERECO"));
        intent.putExtra("ID_SUBTOPICO", intentAnt.getIntExtra("ID_SUBTOPICO", 0));
        intent.putExtra("ID_MONITORIA", intentAnt.getIntExtra("ID_MONITORIA", 0));
        intent.putExtra("ID_MATERIA", intentAnt.getIntExtra("ID_MATERIA", 0));
        intent.putExtra("ID_USUARIO", intentAnt.getIntExtra("ID_USUARIO", 0));
        intent.putExtra("DIA", intentAnt.getStringExtra("DIA"));
        intent.putExtra("HORARIO", intentAnt.getStringExtra("HORARIO"));
        intent.putExtra("USERNAME", intentAnt.getStringExtra("USERNAME"));

        startActivity(intent);
    }

    public void onClickParticiparMonitoria(View view){
        Utils utils = new Utils(this);
        Set<String> array_ids = new HashSet<String>();
        array_ids = utils.getMonitoriasConfirmadasFromSharedPreferences("monitorias", array_ids);
        array_ids.add(String.valueOf(id_monitoria));
        utils.saveMonitoriasConfirmadasSharedPreferences(array_ids);

        String first_name = utils.getFromSharedPreferences("first_name", "");
        String email = utils.getFromSharedPreferences("email", "");
        String mensagem = first_name + " confirmou presença na sua monitoria.\n" + "Email: " + email;
        String param = concatenateParam(String.valueOf(id_usuario), "Monitoria", mensagem);
        utils.sendEmail(param, this);
    }

    public String concatenateParam(String id_usuario, String assunto, String mensagem){
        String param = "id_to=";
        param += id_usuario;
        param += "&";
        param += "assunto=";
        param += assunto;
        param += "&";
        param += "message=";
        param += mensagem;

        return param;
    }
}
