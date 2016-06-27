package br.ufc.engsoftware.auxiliar;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.util.HashSet;
import java.util.Set;

import br.ufc.engsoftware.retrofit.TaSabidoApi;
import br.ufc.engsoftware.serverDAO.PostEnviarEmail;
import br.ufc.engsoftware.tasabido.R;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

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
//                        progressDialog.dismiss();
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

    public Set<String> getMonitoriasConfirmadasFromSharedPreferences(String key, Set<String> value){
        if (sharedPreferencesContains(key))
            return sharedPreferences().getStringSet(key, value);
        else{
            return value;
        }
    }

    public Set<String> getDuvidasConfirmadasFromSharedPreferences(String key, Set<String> value){
        if (sharedPreferencesContains(key))
            return sharedPreferences().getStringSet(key, value);
        else{
            return value;
        }
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

    public void saveMonitoriasConfirmadasSharedPreferences(Set<String> ids){
        SharedPreferences.Editor editor = sharedPreferences().edit();
        editor.putStringSet("monitorias", ids);
        editor.commit();
    }

    public void saveDuvidasConfirmadasSharedPreferences(Set<String> ids){
        SharedPreferences.Editor editor = sharedPreferences().edit();
        editor.putStringSet("duvidas", ids);
        editor.commit();
    }

    public void sendEmail(String param, final Activity activity) {

        try{
            new PostEnviarEmail(activity, param, new PostEnviarEmail.AsyncResponse(){

                @Override
                public void processFinish(String output) {
                    if (output.equals("200")){
                        Utils.callProgressDialog(activity, "Email enviado");

                    }else{
                        Utils.callProgressDialog(activity, "Email não enviado");
                    }
                    Utils.delayMessage();
                    activity.finish();
                }
            }).execute(Statics.ENVIAR_EMAIL);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static TaSabidoApi retornarApi(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Statics.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit.create(TaSabidoApi.class);
    }
}
