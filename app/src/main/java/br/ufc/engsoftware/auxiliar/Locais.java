package br.ufc.engsoftware.auxiliar;

import android.location.Location;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;


/**
 * Created by Thiago on 27/06/2016.
 */
public class Locais {

    private HashMap<String, LatLng> locaisMap;

    public Locais(){
        locaisMap = new HashMap<String, LatLng>();
        locaisMap.put("Lec 1", new LatLng(-3.745833, -38.573955));
        locaisMap.put("Lec 2", new LatLng(-3.745842, -38.574046));
        locaisMap.put("Biblioteca da Matemática", new LatLng(-3.746800, -38.573853));
    }

    public List<String> getLocaisList(){
        Set<String> set = locaisMap.keySet();
        List<String> list = new ArrayList<String>();

        Iterator<String> iterator = set.iterator();
        while(iterator.hasNext())
        {
            String temp = iterator.next();
            list.add(temp);
        }

        return list;
    }

    public LatLng getLatLng(String local){
        return locaisMap.get(local);
    }
}
