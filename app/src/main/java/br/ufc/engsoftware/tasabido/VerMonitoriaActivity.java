package br.ufc.engsoftware.tasabido;

import android.content.Context;
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
import android.widget.Toast;

import br.ufc.engsoftware.auxiliar.Utils;
import br.ufc.engsoftware.models.Monitoria;

public class VerMonitoriaActivity extends AppCompatActivity {

    String username;

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
        //TextView tvSubtopicoMonitoria = (TextView) findViewById(R.id.tv_subtopico_monitoria);
        TextView tvMonitorMonitoria = (TextView) findViewById(R.id.tv_monitor_monitoria);

        Intent intent = getIntent();
        String titulo = intent.getStringExtra("TITULO");
        String descricao = intent.getStringExtra("DESCRICAO");
        String dia = intent.getStringExtra("DIA");
        String horario = intent.getStringExtra("HORARIO");
        String local = intent.getStringExtra("ENDERECO");
        username = intent.getStringExtra("USERNAME");

        tvTituloMonitoria.setText(titulo);
        tvDescricaoMonitoria.setText(descricao);
        tvDataMonitoria.setText(dia + " - " + horario);
        tvLocalMonitoria.setText(local);
        tvMonitorMonitoria.setText(username);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        final String user = sharedPreferences.getString("username", "Visitante");

        // Só mostra o botão de editar se este for o usuario dono da monitoria
        //if(user.equals(username))
        //{
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.edit_monitoria_bar_menu, menu);
        //}
        return true;
    }

    // Seta ação do botão de voltar na ActionBar
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
        Intent intent = new Intent(this, MonitoriaActivity.class);
        intent.setAction("br.ufc.engsoftware.tasabido.LISTA_MONITORIA");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        Intent intentAnt = getIntent();

        // Passa o nome da matéria para ser exibido na activity, e o id para pesquisar no banco
        intent.putExtra("TITULO", intentAnt.getStringExtra("TITULO"));
        intent.putExtra("DESCRICAO", intentAnt.getStringExtra("DESCRICAO"));
        intent.putExtra("DATA", intentAnt.getStringExtra("DATA"));
        intent.putExtra("ENDERECO", intentAnt.getStringExtra("ENDERECO"));
        intent.putExtra("ID_SUBTOPICO", intentAnt.getStringExtra("ID_SUBTOPICO"));
        intent.putExtra("ID_MONITORIA", intentAnt.getStringExtra("ID_MONITORIA"));
        intent.putExtra("ID_MATERIA", intentAnt.getStringExtra("ID_MATERIA"));
        intent.putExtra("ID_USUARIO", intentAnt.getStringExtra("ID_USUARIO"));
        intent.putExtra("DIA", intentAnt.getStringExtra("DIA"));
        intent.putExtra("HORARIO", intentAnt.getStringExtra("HORARIO"));
        intent.putExtra("USERNAME", intentAnt.getStringExtra("USERNAME"));

        startActivity(intent);
    }
}
