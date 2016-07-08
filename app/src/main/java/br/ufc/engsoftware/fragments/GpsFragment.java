package br.ufc.engsoftware.fragments;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

import br.ufc.engsoftware.Ormlite.Monitoria;
import br.ufc.engsoftware.auxiliar.Locais;
import br.ufc.engsoftware.tasabido.LoginActivity;
import br.ufc.engsoftware.tasabido.PaginaPrincipalActivity;
import br.ufc.engsoftware.tasabido.R;

/**
 * Created by Thiago on 09/05/2016.
 */
public class GpsFragment extends Fragment implements OnMapReadyCallback , GoogleMap.OnInfoWindowClickListener {

    // Create the hash map on the beginning
    HashMap<String, Monitoria> markerPostoMap;

    LocationManager locationManager;
    LatLng meLocationLatLong;
    MarkerOptions markerMe = null;
    private GoogleMap mMap = null;

    // Método principal do fragment, respectivo ao onCreate nas activities
    // Ele retorna a view que vai ser montada na tela
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_gps2, container, false);

        /*********** Apagar Depois ************//*
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
                logout();
            }
        });

        */
        /*********************/


        MapView mMapView = (MapView) rootView.findViewById(R.id.mapView);
        mMapView.onCreate(savedInstanceState);

        mMapView.onResume();// needed to get the map to display immediately

        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        mMapView.getMapAsync(this);

        markerPostoMap = new HashMap<String, Monitoria>();


        buildLocationService();

        //mapFragment.getMapAsync(this);

        return rootView;
    }


    /***** Sets up the map if it is possible to do so *****/
    /*
    public void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) PaginaPrincipalActivity.fragmentManager
                    .findFragmentById(R.id.map)).getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null)
            {
                setUpMap();
            }
        }
    }

    private void setUpMap() {
        mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        mMap.setOnInfoWindowClickListener(this);

        if(markerMe != null) {
            setCamera();
        }
    }
    */

    private void logout(){
        final Context context = getActivity();
        SharedPreferences sharedPreferences = context.getSharedPreferences("MyPrefs", context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.commit();

        Intent in = new Intent(context, LoginActivity.class);
        in.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(in);

        getActivity().finish();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        googleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        mMap = googleMap;

        mMap.setOnInfoWindowClickListener(this);

        if(markerMe != null) {
            setCamera();
        }
    }

    private void buildLocationService() {
        locationManager = (LocationManager) getActivity().getSystemService(getActivity().LOCATION_SERVICE);
        boolean enabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        // check if enabled and if not send user to the GSP settings
        // Better solution would be to display a dialog and suggesting to
        // go to the settings
        if (!enabled) {
            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivity(intent);
        }

        getLocation();
    }

    private Location getLocation(){

        Criteria criteria = new Criteria();
        String provider = locationManager.getBestProvider(criteria, false);

        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            //return;
        }

        //LatLng locationLatLong = null;
        Log.d("Response", "Entrou getLocation");
        Location location = locationManager.getLastKnownLocation(provider);
        if(location == null)
        {
            meLocationLatLong = new LatLng(-3.731628, -38.588563);
        }
        else
        {
            meLocationLatLong = new LatLng(location.getLatitude(), location.getLongitude());
        }

        // Adiciona o marcador com a nova posição
        markerMe = new MarkerOptions().position(meLocationLatLong).title("Você está aqui!");

        if(mMap != null){
            setCamera();
        }
        return location;
    }

    private void setCamera(){
        mMap.addMarker(markerMe);

        mMap.moveCamera(CameraUpdateFactory.newLatLng(meLocationLatLong));
        CameraPosition cameraPosition = new CameraPosition.Builder().target(meLocationLatLong).zoom(16).build();
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

        // Chama o método responsável por construid os marcadores dos postos
        buildMarkers(meLocationLatLong.latitude, meLocationLatLong.longitude);
    }

    // Esse método é responsável por extrair a informação da base de dados SQLite e criar os
    // marcadores referentes a cada posto
    private void buildMarkers(double latitude, double longitude){
        List<Monitoria> list = new ArrayList<Monitoria>();

        // TODO construir a classe do SQLite
        // Aqui o list receberia o list do SQLite com os postos perto da latitude e longitude informados

        Location location = new Location("eu"); //= getLocation(); //pega a localização do usuário
        location.setLatitude(-3.731628);
        location.setLongitude(-38.588563);
        Location location2 = new Location("posto"); // localização de algum posto
        Monitoria pTemp;


        // TODO Pegar as monitorias do BDLocal
        // Dados para simulação
        Monitoria p1 = new Monitoria();
        p1.setEndereco("Lec 1");
        Monitoria p2 = new Monitoria();
        p2.setEndereco("Lec 2");
        Monitoria p3 = new Monitoria();
        p3.setEndereco("Biblioteca da Matemática");

        list.add(p1);
        list.add(p2);
        list.add(p3);

        Locais locais = new Locais();

        // Inserindo os marcadores de cada posto no mapa
        for (int i = 0; i < list.size(); i++) //(Posto pTemp: list)
        {
            pTemp = list.get(i);
            LatLng locationLatLong = locais.getLatLng(pTemp.getEndereco());
            Log.d("Endereco" , pTemp.getEndereco());

            // Adiciona o marcador com a nova posição
            MarkerOptions markerPosto = new MarkerOptions()
                    .position(locationLatLong)
                    .title(pTemp.getTitulo())
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN))
                    .snippet("Aperte aqui");

            Marker m = mMap.addMarker(markerPosto);

            // Adicionando marcador e posto no hashMap para pegar quando o usuario clicar
            markerPostoMap.put(m.getId(), pTemp);
        }

    }

    @Override
    public void onInfoWindowClick(Marker marker) {

        Monitoria pTemp = markerPostoMap.get(marker.getId());
        Toast.makeText(getActivity(), "Clicou" , Toast.LENGTH_SHORT).show();

        /*
        Intent intent = new Intent(this, PostoActivity.class);
        intent.setAction("br.ufc.dc.dspm.cadesaude.POSTOACTIVITY");
        intent.putExtra("ID", pTemp.getId());
        intent.putExtra("NOME", pTemp.getName());
        startActivity(intent);
        */

    }

}
