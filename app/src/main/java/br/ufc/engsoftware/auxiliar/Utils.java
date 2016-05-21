package br.ufc.engsoftware.auxiliar;

import android.app.Activity;
import android.app.ProgressDialog;

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
                }, 2000);
    }

    public static void callProgressDialog(Activity ac, String message){
        progressDialog = new ProgressDialog(ac,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage(message);
        progressDialog.show();
    }
}
