package br.ufc.engsoftware.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import br.ufc.engsoftware.tasabido.PaginaPrincipalActivity;
import br.ufc.engsoftware.tasabido.R;

/**
 * Created by Thiago on 09/05/2016.
 */
public class GpsFragment extends Fragment {

    // Método principal do fragment, respectivo ao onCreate nas activities
    // Ele retorna a view que vai ser montada na tela
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_gps, container, false);

        /*********** Apagar Depois ************/
        Button btnLogar = (Button) rootView.findViewById(R.id.btn_logar);

        btnLogar.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                final Context context = getActivity();
                SharedPreferences sharedPreferences = context.getSharedPreferences(PaginaPrincipalActivity.PREFERENCES_FILE_NAME, context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("USER_NAME", "Thiago Nóbrega");
                editor.putString("USER_EMAIL", "thiago_nrodrigues@yahoo.com.br");
                editor.putString("USER_PHOTO_PATH", "https://fbcdn-sphotos-d-a.akamaihd.net/hphotos-ak-xlp1/v/t1.0-9/10178102_442828355820162_3068988865121800083_n.jpg?oh=efb06641c8dec400d8fb65f8b06e1c63&oe=57D983D4&__gda__=1473790918_44887d2d63084bb7b33ed4df2f8756d3");
                editor.commit();
            }
        });

        Button btnLogout = (Button) rootView.findViewById(R.id.btn_logout);

        btnLogout.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                final Context context = getActivity();
                SharedPreferences sharedPreferences = context.getSharedPreferences(PaginaPrincipalActivity.PREFERENCES_FILE_NAME, context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear();
                editor.commit();
            }
        });

        /*********************/

        return rootView;
    }

}
