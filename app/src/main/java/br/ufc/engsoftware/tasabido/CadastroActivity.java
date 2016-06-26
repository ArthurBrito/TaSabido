package br.ufc.engsoftware.tasabido;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import br.ufc.engsoftware.auxiliar.PostServerDataAsync;
import br.ufc.engsoftware.auxiliar.Statics;
import br.ufc.engsoftware.auxiliar.Utils;
import br.ufc.engsoftware.models.Perfil;
import br.ufc.engsoftware.serverDAO.PostCadastroUsuario;
import butterknife.ButterKnife;
import butterknife.InjectView;

public class CadastroActivity extends AppCompatActivity {
    private static final String TAG = "SignupActivity";

    @InjectView(R.id.input_nome) EditText _nomeText;
    @InjectView(R.id.input_usuario) EditText _usuarioText;
    @InjectView(R.id.input_email) EditText _emailText;
    @InjectView(R.id.input_senha) EditText _senhaText;
    @InjectView(R.id.btn_signup) Button _signupButton;
    @InjectView(R.id.link_login) TextView _loginLink;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);
        ButterKnife.inject(this);

        _signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signup();
            }
        });

        _loginLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Finish the registration screen and return to the Login activity

                Intent in = new Intent(getApplicationContext(), LoginActivity.class);
                in.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(in);
            }
        });
    }

    public void signup() {
        Log.d(TAG, "Signup");

        if (!validate()) {
            onSignupFailed();
            return;
        }

        _signupButton.setEnabled(false);

        Utils.callProgressDialog(this, "Cadastrando ...");

        String nome = _nomeText.getText().toString();
        String usuario = _usuarioText.getText().toString();
        String email = _emailText.getText().toString();
        String senha = _senhaText.getText().toString();

        Perfil perfil = new Perfil(nome, usuario, email, senha);

        String param = concatenateParam(perfil);

        // TODO: Implement your own signup logic here.

        new PostCadastroUsuario(param, new PostCadastroUsuario.AsyncResponse(){
            public void processFinish(String output, String mensagem){

                if (output.equals("true")){
                    Utils.progressDialog.setMessage(mensagem);
                    Utils.delayMessage();
                    finish();

                    Intent myIntent = new Intent(CadastroActivity.this, LoginActivity.class);
                    CadastroActivity.this.startActivity(myIntent);
                }else{
                    Utils.progressDialog.setMessage(mensagem);
                    Utils.delayMessage();
                    _signupButton.setEnabled(true);
                }
            }
        }).execute(Statics.CADASTRAR_USUARIO);

    }


    public void onSignupSuccess() {
        _signupButton.setEnabled(true);
        setResult(RESULT_OK, null);
        finish();
    }

    public void onSignupFailed() {
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();

        _signupButton.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String name = _nomeText.getText().toString();
        String email = _emailText.getText().toString();
        String password = _senhaText.getText().toString();

        if (name.isEmpty() || name.length() < 3) {
            _nomeText.setError("at least 3 characters");
            valid = false;
        } else {
            _nomeText.setError(null);
        }

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _emailText.setError("enter a valid email address");
            valid = false;
        } else {
            _emailText.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            _senhaText.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            _senhaText.setError(null);
        }

        return valid;
    }

    public String concatenateParam(Perfil perfil){
        String param = "first_name=";
        param += perfil.getNome();
        param += "&";
        param += "username=";
        param += perfil.getUsuario();
        param += "&";
        param += "email=";
        param += perfil.getEmail();
        param += "&";
        param += "password=";
        param += perfil.getSenha();

        return param;
    }
}