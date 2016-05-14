package br.ufc.engsoftware.tasabido;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import br.ufc.engsoftware.DAO.MateriaDAO;

/**
 * Created by Thiago on 09/05/2016.
 */
public class MateriaFragment extends Fragment {

    // Referencia para o ListView da interface
    ListView listviewMaterias;

    // Estado do ListView e suas informações
    MateriaListView gerenciadorMateriasLV;


    // Método principal do fragment, respectivo ao onCreate nas activities
    // Ele retorna a view que vai ser montada na tela
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Seta o layout que será o view do fragment
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_materias, container, false);

        // Captura a referencia pro ListView a partir do id
        listviewMaterias = (ListView) rootView.findViewById(R.id.listview_materias);

        //Metodo responsável por montar o ListView das Dúvidas
        montarListViewMaterias();

        return rootView;
    }

    private void montarListViewMaterias(){

        // Apagar quando mudar para o banco de dados, pois como  vai ser uma classe estatica
        // não precisa ser instanciada
        MateriaDAO materiaDAO = new MateriaDAO();

        gerenciadorMateriasLV = new MateriaListView(listviewMaterias, getActivity(), materiaDAO.list());

        /*
        swipeRefresh.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        listView.post(new Runnable() {
                            @Override
                            public void run() {
                                listViewExample.setListView(getApplicationContext(), tweeterVector.list());
                            }
                        });

                        swipeRefresh.setRefreshing(false);
                    }
                }
        );
        */

    }

}
