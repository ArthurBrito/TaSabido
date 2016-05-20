package br.ufc.engsoftware.tasabido;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import br.ufc.engsoftware.aux.PostServerDataAsync;
import br.ufc.engsoftware.aux.Statics;
import br.ufc.engsoftware.models.Perfil;
import butterknife.ButterKnife;
import butterknife.InjectView;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";
    private static final int REQUEST_SIGNUP = 0;
    public static ProgressDialog progressDialog;

    @InjectView(R.id.input_login) EditText _loginText;
    @InjectView(R.id.input_password) EditText _passwordText;
    @InjectView(R.id.btn_login) Button _loginButton;
    @InjectView(R.id.link_signup) TextView _signupLink;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.inject(this);

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

        progressDialog = new ProgressDialog(LoginActivity.this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Authenticating...");
        progressDialog.show();


        if (usuario.equals("") && password.equals(""))
            onLoginSuccess();

        // TODO: Implement your own authentication logic here.
        Perfil perfil = new Perfil(usuario, password);
        String param = concatenateParam(perfil);

        try {
            new PostServerDataAsync(param, new PostServerDataAsync.AsyncResponse(){
                public void processFinish(String output){

                    if (output.equals("0")){
                        onLoginSuccess();
                    }else{
                        onLoginFailed();
                    }
                    
                }
            }).execute(Statics.AUTENTICAR_USUARIO_LOCAL);
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
        Intent myIntent = new Intent(LoginActivity.this, PaginaPrincipalActivity.class);
        LoginActivity.this.startActivity(myIntent);

    }

    public void onLoginFailed() {
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();

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
}