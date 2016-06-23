package br.ufc.engsoftware.auxiliar;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import br.ufc.engsoftware.tasabido.R;

/**
 * Created by limaneto on 20/05/16.
 */
public class Utils {
    public static ProgressDialog progressDialog;
    public static final String MyPREFERENCES = "MyPrefs" ;
    public Context context;

    public Utils(){}

    public Utils(Context context){
        this.context = context;
    }



    public static void delayMessage() {
        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        progressDialog.dismiss();
                    }
                }, 3000);
    }

    public static void callProgressDialog(Activity ac, String message){
        progressDialog = new ProgressDialog(ac,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage(message);
        progressDialog.show();
    }

    public static boolean checkConnection(Activity activity){

        ConnectivityManager conMgr = (ConnectivityManager) activity
                .getSystemService(activity.CONNECTIVITY_SERVICE);
        NetworkInfo i = conMgr.getActiveNetworkInfo();
        if (i == null)
            return false;
        if (!i.isConnected())
            return false;
        if (!i.isAvailable())
            return false;
        return true;
    }

    public SharedPreferences sharedPreferences(){
        SharedPreferences sharedPreferences = context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        return sharedPreferences;
    }

    public boolean sharedPreferencesContains(String key){
        return sharedPreferences().contains(key);
    }

    public String getFromSharedPreferences(String key, String value){
        if (sharedPreferencesContains(key))
             return sharedPreferences().getString(key, value);
        else
            return "";
    }

    public int getIntFromSharedPreferences(String key, int value){
        if (sharedPreferencesContains(key))
            return sharedPreferences().getInt(key, value);
        else
            return 0;
    }

    public void saveStringInSharedPreferences(String key, String value){
        SharedPreferences.Editor editor = sharedPreferences().edit();
        editor.putString(key, value);
        editor.commit();
    }

    public void saveIntInSharedPreferences(String key, int value){
        SharedPreferences.Editor editor = sharedPreferences().edit();
        editor.putInt(key, value);
        editor.commit();
    }

}
