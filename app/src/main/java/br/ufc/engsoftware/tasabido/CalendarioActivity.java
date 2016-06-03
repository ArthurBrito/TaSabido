package br.ufc.engsoftware.tasabido;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;

import com.google.gson.Gson;

public class CalendarioActivity extends AppCompatActivity {

    Boolean[] segundaCal;
    Boolean[] tercaCal;
    Boolean[] quartaCal;
    Boolean[] quintaCal;
    Boolean[] sextaCal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendario);

        segundaCal = new Boolean[]{false, false, false, false, false};
        tercaCal = new Boolean[]{false, false, false, false, false};
        quartaCal = new Boolean[]{false, false, false, false, false};
        quintaCal = new Boolean[]{false, false, false, false, false};
        sextaCal = new Boolean[]{false, false, false, false, false};

    }



    public void onCheckboxSegunda(View view){
        boolean checked = ((CheckBox) view).isChecked();

        switch (view.getId()) {
            case R.id.segunda1 :
                if(checked)
                {
                    segundaCal[0] = true;
                }
                else
                {
                    segundaCal[0] = false;
                }
                break;
            case R.id.segunda2 :
                if(checked)
                {
                    segundaCal[1] = true;
                }
                else
                {
                    segundaCal[1] = false;
                }
                break;
            case R.id.segunda3 :
                if(checked)
                {
                    segundaCal[2] = true;
                }
                else
                {
                    segundaCal[2] = false;
                }
                break;
            case R.id.segunda4 :
                if(checked)
                {
                    segundaCal[3] = true;
                }
                else
                {
                    segundaCal[3] = false;
                }
                break;
            case R.id.segunda5 :
                if(checked)
                {
                    segundaCal[4] = true;
                }
                else
                {
                    segundaCal[4] = false;
                }
                break;

        }
    }

    public void onCheckboxTerca(View view){
        boolean checked = ((CheckBox) view).isChecked();

        switch (view.getId()) {
            case R.id.terca1 :
                if(checked)
                {
                    tercaCal[0] = true;
                }
                else
                {
                    tercaCal[0] = false;
                }
                break;
            case R.id.terca2 :
                if(checked)
                {
                    tercaCal[1] = true;
                }
                else
                {
                    tercaCal[1] = false;
                }
                break;
            case R.id.terca3 :
                if(checked)
                {
                    tercaCal[2] = true;
                }
                else
                {
                    tercaCal[2] = false;
                }
                break;
            case R.id.terca4 :
                if(checked)
                {
                    tercaCal[3] = true;
                }
                else
                {
                    tercaCal[3] = false;
                }
                break;
            case R.id.terca5 :
                if(checked)
                {
                    tercaCal[4] = true;
                }
                else
                {
                    tercaCal[4] = false;
                }
                break;
        }
    }

    public void onCheckboxQuarta(View view){
        boolean checked = ((CheckBox) view).isChecked();

        switch (view.getId()) {
            case R.id.quarta1 :
                if(checked)
                {
                    quartaCal[0] = true;
                }
                else
                {
                    quartaCal[0] = false;
                }
                break;
            case R.id.quarta2 :
                if(checked)
                {
                    quartaCal[1] = true;
                }
                else
                {
                    quartaCal[1] = false;
                }
                break;
            case R.id.quarta3 :
                if(checked)
                {
                    quartaCal[2] = true;
                }
                else
                {
                    quartaCal[2] = false;
                }
                break;
            case R.id.quarta4 :
                if(checked)
                {
                    quartaCal[3] = true;
                }
                else
                {
                    quartaCal[3] = false;
                }
                break;
            case R.id.quarta5 :
                if(checked)
                {
                    quartaCal[4] = true;
                }
                else
                {
                    quartaCal[4] = false;
                }
                break;
        }
    }

    public void onCheckboxQuinta(View view){
        boolean checked = ((CheckBox) view).isChecked();

        switch (view.getId()) {
            case R.id.quinta1 :
                if(checked)
                {
                    quintaCal[0] = true;
                }
                else
                {
                    quintaCal[0] = false;
                }
                break;
            case R.id.quinta2 :
                if(checked)
                {
                    quintaCal[1] = true;
                }
                else
                {
                    quintaCal[1] = false;
                }
                break;
            case R.id.quinta3 :
                if(checked)
                {
                    quintaCal[2] = true;
                }
                else
                {
                    quintaCal[2] = false;
                }
                break;
            case R.id.quinta4 :
                if(checked)
                {
                    quintaCal[3] = true;
                }
                else
                {
                    quintaCal[3] = false;
                }
                break;
            case R.id.quinta5 :
                if(checked)
                {
                    quintaCal[4] = true;
                }
                else
                {
                    quintaCal[4] = false;
                }
                break;
        }
    }

    public void onCheckboxSexta(View view){
        boolean checked = ((CheckBox) view).isChecked();

        switch (view.getId()) {
            case R.id.sexta1 :
                if(checked)
                {
                    sextaCal[0] = true;
                }
                else
                {
                    sextaCal[0] = false;
                }
                break;
            case R.id.sexta2 :
                if(checked)
                {
                    sextaCal[1] = true;
                }
                else
                {
                    sextaCal[1] = false;
                }
                break;
            case R.id.sexta3 :
                if(checked)
                {
                    sextaCal[2] = true;
                }
                else
                {
                    sextaCal[2] = false;
                }
                break;
            case R.id.sexta4 :
                if(checked)
                {
                    sextaCal[3] = true;
                }
                else
                {
                    sextaCal[3] = false;
                }
                break;
            case R.id.sexta5 :
                if(checked)
                {
                    sextaCal[4] = true;
                }
                else
                {
                    sextaCal[4] = false;
                }
                break;
        }
    }

    private String montarJson(){

        Boolean[][] calList = new Boolean[5][5];
        calList[0] = segundaCal;
        calList[1] = tercaCal;
        calList[2] = quartaCal;
        calList[3] = quintaCal;
        calList[4] = sextaCal;


        Gson gson = new Gson();
        String json = gson.toJson(calList);

        return json;
    }

    public void onClickSalvar(View view){

        String json = montarJson();

        //Log.d("Response select", json);

        Intent intentMessage = new Intent();
        intentMessage.putExtra("HORARIOS", json);
        setResult(RESULT_OK, intentMessage);
        finish();
    }

    public void onClickCancelar(View view){

        Intent intentMessage = new Intent();
        setResult(RESULT_CANCELED, intentMessage);
        finish();

    }
}
