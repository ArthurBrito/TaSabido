//package br.ufc.engsoftware.fragments;
//
//import android.Manifest;
//import android.content.Intent;
//import android.content.pm.PackageManager;
//import android.location.Criteria;
//import android.location.Location;
//import android.location.LocationManager;
//import android.os.Bundle;
//import android.provider.Settings;
//import android.support.v4.app.ActivityCompat;
//import android.support.v4.app.Fragment;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//
//import com.google.android.gms.maps.CameraUpdateFactory;
//import com.google.android.gms.maps.GoogleMap;
//import com.google.android.gms.maps.MapView;
//import com.google.android.gms.maps.MapsInitializer;
//import com.google.android.gms.maps.OnMapReadyCallback;
//import com.google.android.gms.maps.model.BitmapDescriptorFactory;
//import com.google.android.gms.maps.model.CameraPosition;
//import com.google.android.gms.maps.model.LatLng;
//import com.google.android.gms.maps.model.Marker;
//import com.google.android.gms.maps.model.MarkerOptions;
//import com.j256.ormlite.android.apptools.OpenHelperManager;
//import com.j256.ormlite.dao.Dao;
//
//import java.sql.SQLException;
//import java.util.ArrayList;
//import java.util.Calendar;
//import java.util.HashMap;
//import java.util.List;
//
//import br.ufc.engsoftware.Ormlite.Monitoria;
//import br.ufc.engsoftware.auxiliar.Locais;
//import br.ufc.engsoftware.Ormlite.config.OpenDatabaseHelper;
//import br.ufc.engsoftware.tasabido.R;
//import br.ufc.engsoftware.tasabido.VerMonitoriaActivity;
//
///**
// * Created by Thiago on 09/05/2016.
// */
//public class GpsFragment extends Fragment implements OnMapReadyCallback , GoogleMap.OnInfoWindowClickListener {
//
//    // Create the hash map on the beginning
//    HashMap<String, Monitoria> markerMonitoriaMap;
//
//    LocationManager locationManager;
//    LatLng meLocationLatLong;
//    MarkerOptions markerMe = null;
//    private GoogleMap mMap = null;
//    String markerMeId;
//
//    // Método principal do fragment, respectivo ao onCreate nas activities
//    // Ele retorna a view que vai ser montada na tela
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//
//        ViewGroup rootView = (ViewGroup) inflater.inflate(
//                R.layout.fragment_gps2, container, false);
//
//        /*********** Apagar Depois ************//*
//        Button btnLogar = (Button) rootView.findViewById(R.id.btn_logar);
//
//        btnLogar.setOnClickListener(new View.OnClickListener()
//        {
//            @Override
//            public void onClick(View v)
//            {
//                final Context context = getActivity();
//                SharedPreferences sharedPreferences = context.getSharedPreferences(PaginaPrincipalActivity.PREFERENCES_FILE_NAME, context.MODE_PRIVATE);
//                SharedPreferences.Editor editor = sharedPreferences.edit();
//                editor.putString("USER_NAME", "Thiago Nóbrega");
//                editor.putString("USER_EMAIL", "thiago_nrodrigues@yahoo.com.br");
//                editor.putString("USER_PHOTO_PATH", "https://fbcdn-sphotos-d-a.akamaihd.net/hphotos-ak-xlp1/v/t1.0-9/10178102_442828355820162_3068988865121800083_n.jpg?oh=efb06641c8dec400d8fb65f8b06e1c63&oe=57D983D4&__gda__=1473790918_44887d2d63084bb7b33ed4df2f8756d3");
//                editor.commit();
//            }
//        });
//
//        /*********************/
//
//
//        MapView mMapView = (MapView) rootView.findViewById(R.id.mapView);
//        mMapView.onCreate(savedInstanceState);
//
//        mMapView.onResume();// needed to get the map to display immediately
//
//        try {
//            MapsInitializer.initialize(getActivity().getApplicationContext());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        mMapView.getMapAsync(this);
//
//        markerMonitoriaMap = new HashMap<String, Monitoria>();
//
//
//        buildLocationService();
//
//        //mapFragment.getMapAsync(this);
//
//
//        return rootView;
//    }
//
//
//    @Override
//    public void onMapReady(GoogleMap googleMap) {
//
//        googleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
//        mMap = googleMap;
//
//        mMap.setOnInfoWindowClickListener(this);
//
//        if(markerMe != null) {
//            setCamera();
//        }
//    }
//
//    private void buildLocationService() {
//        locationManager = (LocationManager) getActivity().getSystemService(getActivity().LOCATION_SERVICE);
//        boolean enabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
//        // check if enabled and if not send user to the GSP settings
//        // Better solution would be to display a dialog and suggesting to
//        // go to the settings
//        if (!enabled) {
//            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
//            startActivity(intent);
//        }
//
//        getLocation();
//    }
//
//    private Location getLocation(){
//
//        Criteria criteria = new Criteria();
//        String provider = locationManager.getBestProvider(criteria, false);
//
//        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            //return;
//        }
//
//        //LatLng locationLatLong = null;
//        Log.d("Response", "Entrou getLocation");
//        Location location = locationManager.getLastKnownLocation(provider);
//        if(location == null)
//        {
//            meLocationLatLong = new LatLng(-3.745942, -38.574203);
//            markerMe = new MarkerOptions().position(meLocationLatLong).title("Local Padrão").snippet("Não foi possivel obter sua localização");
//        }
//        else
//        {
//            meLocationLatLong = new LatLng(location.getLatitude(), location.getLongitude());
//            markerMe = new MarkerOptions().position(meLocationLatLong).title("Você está aqui!");
//        }
//
//
//        if(mMap != null){
//            setCamera();
//        }
//        return location;
//    }
//
//    private void setCamera(){
//        markerMeId = mMap.addMarker(markerMe).getId();
//
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(meLocationLatLong));
//        CameraPosition cameraPosition = new CameraPosition.Builder().target(meLocationLatLong).zoom(16).build();
//        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
//
//        // Chama o método responsável por construid os marcadores dos postos
//        buildMarkers(meLocationLatLong.latitude, meLocationLatLong.longitude);
//    }
//
//    // Esse método é responsável por extrair a informação da base de dados SQLite e criar os
//    // marcadores referentes a cada posto
//    private void buildMarkers(double latitude, double longitude){
//        List<Monitoria> list = new ArrayList<Monitoria>();
//
//        Monitoria pTemp;
//
//        // Pega o dia da semana
//        String[] dias = new String[]{"Domingo" , "Segunda" , "Terça" , "Quarta" , "Quinta" , "Sexta" , "Sabado"};
//        String hoje = dias[(Calendar.getInstance().get(Calendar.DAY_OF_WEEK)) - 1];
//        Log.d("Data", hoje);
//
//        // Pega as monitorias do dia, do BDLocal
//        try {
//            list = getDao().queryBuilder().where().eq("dia", hoje).query();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//
//
//        Locais locais = new Locais();
//
//        // Inserindo os marcadores de cada monitoria no mapa
//        for (int i = 0; i < list.size(); i++)
//        {
//            pTemp = list.get(i);
//            LatLng locationLatLong = locais.getLatLng(pTemp.getEndereco());
//            Log.d("Endereco" , pTemp.getEndereco());
//
//            // Adiciona o marcador com a nova posição
//            MarkerOptions markerPosto = new MarkerOptions()
//                    .position(locationLatLong)
//                    .title(pTemp.getTitulo())
//                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN))
//                    .snippet("Aperte aqui");
//
//            Marker m = mMap.addMarker(markerPosto);
//
//            // Adicionando marcador e monitoria no hashMap para pegar quando o usuario clicar
//            markerMonitoriaMap.put(m.getId(), pTemp);
//        }
//
//    }
//
//    @Override
//    public void onInfoWindowClick(Marker marker) {
//
//        if(!marker.getId().equals(markerMeId)) {
//
//            Monitoria item = markerMonitoriaMap.get(marker.getId());
//            //Toast.makeText(getActivity(), "Clicou" , Toast.LENGTH_SHORT).show();
//
//            Intent intent = new Intent(getActivity(), VerMonitoriaActivity.class);
//            intent.setAction("br.ufc.engsoftware.tasabido.VER_MONITORIA");
//            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//
//            // Passa o nome da matéria para ser exibido na activity, e o id para pesquisar no banco
//            intent.putExtra("TITULO", item.getTitulo());
//            intent.putExtra("DESCRICAO", item.getDescricao());
//            intent.putExtra("DATA", item.getData());
//            intent.putExtra("ENDERECO", item.getEndereco());
//            intent.putExtra("DIA", item.getDia());
//            intent.putExtra("HORARIO", item.getHorario());
//            intent.putExtra("ID_SUBTOPICO", "1");
//            intent.putExtra("ID_MONITORIA", item.getId_monitoria());
//            intent.putExtra("ID_MATERIA", "1");
//            intent.putExtra("ID_USUARIO", item.getId_usuario());
//            intent.putExtra("DIA", item.getDia());
//            intent.putExtra("HORARIO", item.getHorario());
//            intent.putExtra("USERNAME", item.getUsername());
//
//            startActivity(intent);
//        }
//
//    }
//
//    public Dao getDao(){
//        OpenDatabaseHelper monitoriaOpenDatabaseHelper = OpenHelperManager.getHelper(getActivity() , OpenDatabaseHelper.class);
//
//        Dao<Monitoria, Long> monitoriaDao = null;
//
//        try{
//            monitoriaDao = monitoriaOpenDatabaseHelper.getMonitoriaDao();
//        } catch(SQLException e){
//            e.printStackTrace();
//        }
//
//        return monitoriaDao;
//    }
//
//}
