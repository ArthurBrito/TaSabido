package br.ufc.engsoftware.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;

import java.util.Vector;

import br.ufc.engsoftware.BDLocalManager.MateriaBDManager;
import br.ufc.engsoftware.models.Materia;
import br.ufc.engsoftware.views.MateriaListView;
import br.ufc.engsoftware.views.MateriaSearchView;
import br.ufc.engsoftware.tasabido.PaginaPrincipalActivity;
import br.ufc.engsoftware.tasabido.R;
import br.ufc.engsoftware.views.RoundedImageView;

/**
 * Created by Thiago on 09/05/2016.
 */
public class MateriaFragment extends Fragment {

    // Referencia para o SearchView da interface
    SearchView searchviewMaterias;

    // Referencia para o ListView da interface
    ListView listviewMaterias;

    // Estado do ListView e suas informações
    MateriaListView gerenciadorMateriasLV;

    // Referencia para os elementos da barra de usuario
    RoundedImageView rivFotoUsuario;
    TextView tvNomeUsuario;
    TextView tvEmailUsuario;


    // Método principal do fragment, respectivo ao onCreate nas activities
    // Ele retorna a view que vai ser montada na tela
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Seta o layout que será o view do fragment
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_materias, container, false);

        // Captura a referencia para os elementos da barra de usuario
        rivFotoUsuario = (RoundedImageView) rootView.findViewById(R.id.riv_foto_usuario);
        tvNomeUsuario = (TextView) rootView.findViewById(R.id.tv_nome_usuario);
        tvEmailUsuario = (TextView) rootView.findViewById(R.id.tv_email_usuario);

        // Captura a referencia pro ListView a partir do id
        listviewMaterias = (ListView) rootView.findViewById(R.id.listview_materias);

        // Insere os dados da barra de usuario
        setarBarraUsuario();

        // Metodo responsável por montar o ListView das Materias
        montarListViewMaterias();
        Filter filter = gerenciadorMateriasLV.getFilter();

        // Captura referencia pro SearchView
        searchviewMaterias = (SearchView) rootView.findViewById(R.id.searchview_materias);
        searchviewMaterias.clearFocus();

        MateriaSearchView configSearchView = new MateriaSearchView(listviewMaterias, searchviewMaterias, filter);

        return rootView;
    }


    // Esse metodo configura o SearchView
    public void montarSearchViewMaterias(){

        listviewMaterias.setTextFilterEnabled(true);
        Filter filter = gerenciadorMateriasLV.getFilter();

        MateriaSearchView configSearchView = new MateriaSearchView(listviewMaterias, searchviewMaterias, filter);
    }

    // Monta o ListView Materias com os dados do BD local
    private void montarListViewMaterias(){

        MateriaBDManager materiaDB = new MateriaBDManager();
        Vector<Materia> vM = materiaDB.pegarMaterias(getActivity());
        Vector<Materia> vMAux = new Vector<Materia>();

        // Copiando o vector pois pegar ele do Realm da problema no searchview
        for(int i = 0; i < vM.size(); i++)
        {
            Materia mAux = vM.get(i);
            vMAux.add(new Materia(mAux.getId_materia(), mAux.getNome()));
        }

        gerenciadorMateriasLV = new MateriaListView(listviewMaterias, getContext(), vMAux);
    }

    // Seta as informações da barra de usuario, verificando se o usuario esta logado ou nao
    public void setarBarraUsuario(){
        // Agora a barra de usuário somente aparece o nome TaSabido
        Typeface typeface = Typeface.createFromAsset(getActivity().getAssets(),"fonts/beyond_the_mountains.ttf");
        tvNomeUsuario.setTypeface(typeface);
        tvNomeUsuario.setText("Ta Sabido");
    }

    @Override
    public void onResume() {
        super.onResume();
    }
}