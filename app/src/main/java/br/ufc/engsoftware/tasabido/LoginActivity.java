package br.ufc.engsoftware.tasabido;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import br.ufc.engsoftware.auxiliar.PostServerDataAsync;
import br.ufc.engsoftware.auxiliar.Statics;
import br.ufc.engsoftware.auxiliar.Utils;
import br.ufc.engsoftware.models.Perfil;
import butterknife.ButterKnife;
import butterknife.InjectView;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";
    private static final int REQUEST_SIGNUP = 0;
    private Activity activity;

    @InjectView(R.id.input_login) EditText _loginText;
    @InjectView(R.id.input_password) EditText _passwordText;
    @InjectView(R.id.btn_login) Button _loginButton;
    @InjectView(R.id.link_signup) TextView _signupLink;
    @InjectView(R.id.btn_conexao) Button btn_conexao;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.inject(this);
        activity = this;


        // pega login e senha que ja foi usado pra logar
        lembrarLoginSenha();

        btn_conexao.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                boolean ok = Utils.checkConnection(activity);
                if(ok){
                    Toast.makeText(getBaseContext(), "SIM", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getBaseContext(), "NAO", Toast.LENGTH_SHORT).show();
                }

            }
        });

        _loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                login();
            }
        });

        _signupLink.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Start the Signup activity
                Intent intent = new Intent(getApplicationContext(), CadastroActivity.class);
                startActivityForResult(intent, REQUEST_SIGNUP);
            }
        });
    }

    public void login() {
        Log.d(TAG, "Login");

        String usuario = _loginText.getText().toString();
        String password = _passwordText.getText().toString();

        if (!validate()) {
            onLoginFailed();
            return;
        }

        _loginButton.setEnabled(false);

        Utils.callProgressDialog(this, "Autenticando ...");

        if (usuario.equals("") && password.equals(""))
            onLoginSuccess();

        // TODO: Implement your own authentication logic here.
        Perfil perfil = new Perfil(usuario, password);
        String param = concatenateParam(perfil);

        try {
            new PostServerDataAsync(this, param, new PostServerDataAsync.AsyncResponse(){
                public void processFinish(String output){
                    if (output.equals("true")){
                        Utils.progressDialog.setMessage("Autenticado");
                        Utils.delayMessage();
                        onLoginSuccess();
                    }else{
                        Utils.progressDialog.setMessage("Usuario ou Senha Incorretos");
                        Utils.delayMessage();
                        onLoginFailed();
                    }
                }
            }).execute(Statics.AUTENTICAR_USUARIO);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_SIGNUP) {
            if (resultCode == RESULT_OK) {

                // TODO: Implement successful signup logic here
                // By default we just finish the Activity and log them in automatically
                this.finish();
            }
        }
    }

    @Override
    public void onBackPressed() {
        // disable going back to the MainActivity
        moveTaskToBack(true);
    }

    public void onLoginSuccess() {
        _loginButton.setEnabled(true);
//        finish();
        // Start the Signup activity
        salvarLoginSenha(_loginText.getText().toString(), _passwordText.getText().toString());

        Intent myIntent = new Intent(LoginActivity.this, PaginaPrincipalActivity.class);
        LoginActivity.this.startActivity(myIntent);

    }

    public void onLoginFailed() {
//        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();

        _loginButton.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String login = _loginText.getText().toString();
        String password = _passwordText.getText().toString();

        //tava no if  || !android.util.Patterns.EMAIL_ADDRESS.matcher(login).matches()
        if (login.isEmpty() && password.isEmpty()) {
//            _loginText.setError("Esse Usuário é inválido");
            onLoginSuccess();
            valid = false;
        } else {
            _loginText.setError(null);
        }

//        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
//            _passwordText.setError("Entre 4 e 10 algarismos");
//            valid = false;
//        } else {
//            _passwordText.setError(null);
//        }

        return valid;
    }

    public String concatenateParam(Perfil perfil){
        String param = "username=";
        param += perfil.getUsuario();
        param += "&";
        param += "password=";
        param += perfil.getSenha();

        return param;
    }

    public void lembrarLoginSenha(){
        Utils utils = new Utils(this);
        String login = utils.getFromSharedPreferences("login", "");
        String senha = utils.getFromSharedPreferences("senha", "");
        _loginText.setText(utils.getFromSharedPreferences("login", ""));
        _passwordText.setText(utils.getFromSharedPreferences("senha", ""));
    }

    public void salvarLoginSenha(String login, String senha){
        Utils utils = new Utils(this);
        utils.saveInSharedPreferences("login", login);
        utils.saveInSharedPreferences("senha", senha);
    }
}