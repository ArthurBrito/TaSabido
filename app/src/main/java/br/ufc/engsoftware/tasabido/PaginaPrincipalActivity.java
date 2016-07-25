//package br.ufc.engsoftware.tasabido;
//
//import android.app.Activity;
//import android.content.Intent;
//import android.support.design.widget.TabLayout;
//import android.support.v4.app.Fragment;
//import android.support.v4.app.FragmentActivity;
//import android.support.v4.app.FragmentManager;
//import android.support.v4.app.FragmentPagerAdapter;
//import android.support.v4.view.PagerAdapter;
//import android.support.v4.view.ViewPager;
//import android.os.Bundle;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.widget.ImageView;
//
//import com.google.zxing.integration.android.IntentIntegrator;
//import com.google.zxing.integration.android.IntentResult;
//
//import org.json.JSONObject;
//
//import java.util.ArrayList;
//
//import br.ufc.engsoftware.Ormlite.Data;
//import br.ufc.engsoftware.Presenters.DataPresenter;
//import br.ufc.engsoftware.auxiliar.Utils;
//import br.ufc.engsoftware.fragments.MateriaFragment;
//import br.ufc.engsoftware.fragments.PerfilFragment;
//import br.ufc.engsoftware.Ormlite.Perfil;
//
//
//public class PaginaPrincipalActivity extends FragmentActivity implements DataPresenter.DataCallbackListener {
//
//    /******************* A T E N Ç Ã O *******************/
//
//    /* As alterações de cada página NÃO devem ser feitas aqui.
//     * As alterações devem ser feitas nas classes e layouts
//     * respectivos de cada FRAGMENT.
//     *
//     * Alterações na página de PERFIL devem ser feita na classe
//     * PerfilFragment e no layout fragment_perfil
//     *
//     * * Alterações na página de GPS devem ser feita na classe
//     * GpsFragment e no layout fragment_gps
//     *
//     * * Alterações na página de MATERIAS devem ser feita na classe
//     * MateriaFragment e no layout fragment_materias
//     *
//     * Sabendo disso caso você faça alguma alteração NESTA classe
//     * você deve ter um bom motivo :D
//     */
//
//
//    final Activity activity = this;
//    JSONObject obj;
//    ArrayList<Perfil> arrayPerfilList;
//
//    Utils utils;
//
//    // Nome do arquivo de preferencias compartilhadas
//    public static final String PREFERENCES_FILE_NAME = "TaSabidoSharedPreferences";
//
//    // Número de páginas que devem ser criadas do ViewPager
//    private static final int NUM_PAGES = 3;
//
//    // Atributo que vai guardar o ViewPager da pagina inicial
//    private ViewPager mPager;
//
//    // PageAdapter do ViewPager
//    private PagerAdapter mPagerAdapter;
//
//    PerfilFragment perfilFragment;
//
//    DataPresenter dataPresenter;
//
//    //public static FragmentManager fragmentManager;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_pagina_principal);
//
//
//        // Instancia o ViewPager e o PageAdapter
//        mPager = (ViewPager) findViewById(R.id.pager);
//        mPagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
//        mPager.setAdapter(mPagerAdapter);
//
//        dataPresenter = new DataPresenter(this);
//
//        utils = new Utils(this);
//
//        // Seta o TabLayout (abas) para trabalhar em conjunto com o ViewPager
//        TabLayout tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
//        tabLayout.setupWithViewPager(mPager);
//
//        //fragmentManager = getSupportFragmentManager();
//
//        sincronizarInfo();
//
//        // Muda o layout das abas para colocar os ícones
//        for (int i = 0; i < tabLayout.getTabCount(); i++) {
//            TabLayout.Tab tab = tabLayout.getTabAt(i);
//            tab.setCustomView(((ScreenSlidePagerAdapter) mPagerAdapter).getTabView(i));
//        }
//
//    }
//
//    // Metodo que seta a funcionalidade do botão de voltar
//    @Override
//    public void onBackPressed() {
//        if (mPager.getCurrentItem() == 0) {
//            // If the user is currently looking at the first step, allow the system to handle the
//            // Back button. This calls finish() on this activity and pops the back stack.
//            super.onBackPressed();
//        } else {
//            // Otherwise, select the previous step.
//            mPager.setCurrentItem(mPager.getCurrentItem() - 1);
//        }
//    }
//
//
//    //Classe do PageAdapater para inserir os fragments no ViewPager
//    private class ScreenSlidePagerAdapter extends FragmentPagerAdapter {
//
//        // Atributo com os icones que vão ser usado nas abas do TabLayout
//        private int[] imageResId = {
//                R.drawable.listazul ,
//                R.drawable.gps,
//                R.drawable.profile_maleazul
//        };
//
//        public ScreenSlidePagerAdapter(FragmentManager fm) {
//            super(fm);
//        }
//
//        // Metodo que retorna os fragments que vão popular o ViewPager
//        @Override
//        public Fragment getItem(int position) {
//            perfilFragment = new PerfilFragment();
//            if (position == 0) {
//                return new MateriaFragment();
//            } else if (position == 1) {
//                return new PerfilFragment();
//            } else if (position == 2) {
//                return perfilFragment;
//            } else {
//                return null;
//            }
//        }
//
//        // Metodo que diz o numero de páginas que o ViewPager terá
//        @Override
//        public int getCount() {
//            return NUM_PAGES;
//        }
//
//        // Seta o titulo das paginas, apesar que é sobrescrito pelos ícones
//        @Override
//        public CharSequence getPageTitle(int position) {
//            // Generate title based on item position
//            CharSequence titles[] = {"Pagina 1", "Pagina 2", "Pagina 3"};
//            return titles[position];
//        }
//
//        // Seta os ícones das abas
//        public View getTabView(int position) {
//            // Given you have a custom layout in `res/layout/custom_tab.xml` with a TextView and ImageView
//            View v = LayoutInflater.from(getApplicationContext()).inflate(R.layout.tab_layout, null);
//            ImageView img = (ImageView) v.findViewById(R.id.imgView);
//            img.setImageResource(imageResId[position]);
//
//            return v;
//        }
//
//    }
//
//    // Baixando dados do servidor e salvando no BD local
//    public void sincronizarInfo(){
//
//        utils.createProgressDialog("Sincronizando informações.");
//        if(utils.checkConnection(this)){
//            dataPresenter.getData();
////            new GetMateriasServer(this).execute();
////            new GetMoedasServer(this).execute();
//        }
//    }
//
//    @Override
//    public void dadosPreparados(Data cursos) {
//        utils.dismissProgressDialog();
//    }
//
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) { //recebe o resultado da câmera e manda de volta para ser tratado na classe PerfilFragment
//        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
//        perfilFragment.recebeQr(result);
//    }
//}
