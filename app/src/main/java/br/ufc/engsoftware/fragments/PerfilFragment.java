package br.ufc.engsoftware.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import br.ufc.engsoftware.auxiliar.Utils;
import br.ufc.engsoftware.tasabido.PaginaPrincipalActivity;
import br.ufc.engsoftware.tasabido.R;
import br.ufc.engsoftware.views.RoundedImageView;
import io.realm.internal.Util;

/**
 * Created by Thiago on 09/05/2016.
 */
public class PerfilFragment extends Fragment {

    // Referencia para os elementos da barra de usuario
    RoundedImageView rivFotoUsuario;
    TextView tvNomeUsuario;
    TextView tvEmailUsuario;
    TextView qt_moedas;


    public void setarBarraUsuario(){
        final Context context = getActivity();
        SharedPreferences sharedPreferences = context.getSharedPreferences(PaginaPrincipalActivity.PREFERENCES_FILE_NAME, context.MODE_PRIVATE);
        Utils util = new Utils(getActivity());

        // Extrai os valores das preferencias
        final String nomeUsuario = util.getFromSharedPreferences("first_name", "");
        final String emailUsuario = util.getFromSharedPreferences("email", "");
        final String fotoUsuario = sharedPreferences.getString("USER_PHOTO_PATH", "http://khojmaster.com/ui/user/realestate/assets/img/no-user.jpg");
        final String quantidade_moedas = String.valueOf(util.getIntFromSharedPreferences("moedas", 0));

        // Alterando as informações da barra de usuario numa thread secundaria
        tvNomeUsuario.post(new Runnable() {
            @Override
            public void run() {
                tvNomeUsuario.setText(nomeUsuario);
                tvEmailUsuario.setText(emailUsuario);
                qt_moedas.setText(quantidade_moedas);
            }
        });

        rivFotoUsuario.post(new Runnable() {
            @Override
            public void run() {
                // Pega a imagem a partir da URL
                Picasso.with(context).load(fotoUsuario).into(rivFotoUsuario);
            }
        });

    }

    // Método principal do fragment, respectivo ao onCreate nas activities
    // Ele retorna a view que vai ser montada na tela
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {

        // Seta o layout que será o view do fragment
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_perfil, container, false);

        // Captura a referencia para os elementos da barra de usuario
        rivFotoUsuario = (RoundedImageView) rootView.findViewById(R.id.riv_foto_usuario);
        tvNomeUsuario = (TextView) rootView.findViewById(R.id.tv_nome_usuario);
        tvEmailUsuario = (TextView) rootView.findViewById(R.id.tv_email_usuario);
        qt_moedas = (TextView) rootView.findViewById(R.id.qt_moedas);


        // Insere os dados da barra de usuario
        setarBarraUsuario();

        return rootView;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

    }
}
