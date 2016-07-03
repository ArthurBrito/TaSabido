package br.ufc.engsoftware.views;

import android.text.TextUtils;
import android.widget.Adapter;
import android.widget.Filter;
import android.widget.ListView;
import android.widget.SearchView;

/**
 * Created by Thiago on 15/05/2016.
 */
public class MateriaSearchView implements SearchView.OnQueryTextListener {

    ListView listView;
    SearchView searchView;
    Filter filter;

    public MateriaSearchView(ListView listView, SearchView searchView, Filter filter) {
        this.listView = listView;
        this.searchView = searchView;
        this.searchView.setFocusableInTouchMode(true);
        this.filter = filter;

        setupSearchView();
    }

    private void setupSearchView() {
        searchView.setIconifiedByDefault(false);
        searchView.setOnQueryTextListener(this);
        searchView.setSubmitButtonEnabled(false);
        searchView.setQueryHint("Procurar Matéria");
        searchView.setQueryRefinementEnabled(false);
    }


    @Override
    public boolean onQueryTextChange(String newText) {
        if (TextUtils.isEmpty(newText)) {
            filter.filter(null);
        } else {
            filter.filter(newText);
        }
        return true;
    }

    public boolean onQueryTextSubmit(String query) {
        return false;
    }
}
