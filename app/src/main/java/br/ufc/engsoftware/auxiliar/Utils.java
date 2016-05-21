package br.ufc.engsoftware.auxiliar;

import android.app.Activity;
import android.app.ProgressDialog;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.net.InetAddress;

import br.ufc.engsoftware.tasabido.R;

/**
 * Created by limaneto on 20/05/16.
 */
public class Utils {
    public static ProgressDialog progressDialog;

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
//        try {
//            InetAddress ipAddr = InetAddress.getByName("google.com"); //You can replace it with your name
//
//            if (ipAddr.equals("")) {
//                return false;
//            } else {
//                return true;
//            }
//
//        } catch (Exception e) {
//            return false;
//        }
    }

}
