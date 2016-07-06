package br.ufc.engsoftware.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.journeyapps.barcodescanner.BarcodeEncoder;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import br.ufc.engsoftware.auxiliar.Statics;
import br.ufc.engsoftware.auxiliar.Utils;
import br.ufc.engsoftware.serverDAO.PostPagamento;
import br.ufc.engsoftware.tasabido.R;
import br.ufc.engsoftware.views.RoundedImageView;

/**
 * Created by Thiago on 09/05/2016.
 */
public class PerfilFragment extends Fragment {

    static String quantidade_moedas;

    // Referencia para os elementos da barra de usuario
    RoundedImageView rivFotoUsuario;
    TextView tvNomeUsuario;
    TextView tvEmailUsuario;
    TextView qt_moedas;
    Button btReadQr,btCreateQr;
    ImageView ivQrCode;
    Utils utils;


    public void setarBarraUsuario(){
        final Context context = getActivity();
        Utils util = new Utils(getActivity());

        // Extrai os valores das preferencias
        final String nomeUsuario = util.getFromSharedPreferences("first_name", "");
        final String emailUsuario = util.getFromSharedPreferences("email", "");
        final String fotoUsuario = util.getFromSharedPreferences("USER_PHOTO_PATH", "http://khojmaster.com/ui/user/realestate/assets/img/no-user.jpg");
        quantidade_moedas = String.valueOf(util.getIntFromSharedPreferences("moedas", 0));

        // Alterando as informações da barra de usuario numa thread secundaria
        tvNomeUsuario.post(new Runnable() {
            @Override
            public void run() {
                tvNomeUsuario.setText(nomeUsuario);
                tvEmailUsuario.setText(emailUsuario);
                qt_moedas.setText(quantidade_moedas);
            }
        });

        rivFotoUsuario.post(new Runnable() {
            @Override
            public void run() {
                // Pega a imagem a partir da URL
                Picasso.with(context).load(fotoUsuario).into(rivFotoUsuario);
            }
        });

    }

    // Método principal do fragment, respectivo ao onCreate nas activities
    // Ele retorna a view que vai ser montada na tela
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {

                // Seta o layout que será o view do fragment
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_perfil, container, false);

        utils = new Utils(getActivity());

        // Captura a referencia para os elementos da barra de usuario
        rivFotoUsuario = (RoundedImageView) rootView.findViewById(R.id.riv_foto_usuario);
        tvNomeUsuario = (TextView) rootView.findViewById(R.id.tv_nome_usuario);
        tvEmailUsuario = (TextView) rootView.findViewById(R.id.tv_email_usuario);
        qt_moedas = (TextView) rootView.findViewById(R.id.qt_moedas);
        ivQrCode = (ImageView) rootView.findViewById(R.id.ivQrCode);
        ivQrCode.setVisibility(View.GONE); // a imageview é criada porém não fica visível na tela
        btCreateQr = (Button) rootView.findViewById(R.id.btCreateQr);
        btCreateQr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickMostrarQR(v);
            }
        });
        btReadQr = (Button) rootView.findViewById(R.id.btReadQr);

        btReadQr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               onClickReaderQR(v);
            }
        });

        // Insere os dados da barra de usuario
        setarBarraUsuario();
        createQr();
        Log.d("activity", "lol");

        return rootView;
    }

    public void createQr(){ // cria QR CODE, o QR sempre é criado porém só mostra na tela ao criar se clicar no botão
//        String text2Qr = "CarteiraDoFulano"; // texto que será transformado em QR CODE
        Utils utils = new Utils(getActivity());
        String username = utils.getFromSharedPreferences("username", "");
        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
        try {
            BitMatrix bitMatrix = multiFormatWriter.encode(username, BarcodeFormat.QR_CODE,500,500); //escolhe o tamanho
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
            ivQrCode.setImageBitmap(bitmap); //setando a imagem do QR na image view
        } catch (WriterException e) {
            e.printStackTrace();
        }
    }

    public void onClickMostrarQR(View view){ // Mostra e esconde o QRCODE e muda o botão do texto
        if (ivQrCode.getVisibility() == View.GONE) {
            ivQrCode.setVisibility(View.VISIBLE);
            btCreateQr.setText("Esconder carteira");
        } else {
            ivQrCode.setVisibility(View.GONE);
            btCreateQr.setText("Mostrar carteira");
        }
    }

    public void onClickReaderQR(View view){ // chama a câmera para ler o QR code
        IntentIntegrator integrator = new IntentIntegrator(getActivity());
        integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
        integrator.setPrompt("Scan");
        integrator.setCameraId(0);
        integrator.setBeepEnabled(false);
        integrator.setBarcodeImageEnabled(false);
        integrator.initiateScan(); //chama a câmera e a activity espera o resultado na activity pai
        Log.d("OnclickReader", "Entrou");
    }

    public void recebeQr(IntentResult intentResult){ //intentResult.getContents() retorna a mensagem contida no QrCode
        if(intentResult.getContents() == null) { // usuário cancelou a câmera
            Log.d("MainActivity", "Cancelado");
        } else {
            Log.d("MainActivity", "Scanned"); // Leu qr
            Utils utils = new Utils(getActivity());
            String id_usuario = utils.getFromSharedPreferences("id_usuario", "");
            realizarPagamento(id_usuario, intentResult.getContents());
        }
    }

    public void realizarPagamento(String id_usuario, String username){

        if(Integer.parseInt(quantidade_moedas) == 0 ){
            utils.createProgressDialog("Você não tem moedas suficientes.");
        }else {

            JSONObject jsonParam = createJsonParam(id_usuario, username);

            try {
                utils.createProgressDialog("Pagamento sendo efetuado");
                new PostPagamento(getContext(), jsonParam, Statics.PAGAR, new PostPagamento.AsyncResponse() {

                    @Override
                    public void processFinish(String output) {
                        if (output.equals("200")) {
                            utils.progressDialog.setMessage("Pagamento realizado.");
                        } else {
                            utils.progressDialog.setMessage("Algum erro ocorreu, tente denovo mais tarde.");
                        }
                    }
                }).execute();
                utils.dismissProgressDialog();
            } catch (Exception e) {
                e.printStackTrace();
            }

            utils.dismissProgressDialog();
        }
    }

    public JSONObject createJsonParam(String id_usuario, String username) {
        JSONObject json = new JSONObject();
        JSONArray subtopicosJson = new JSONArray();
        try {
            json.put("id_usuario_pagante", id_usuario);
            json.put("username", username);
            json.put("quantia", "2");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return json;
    }


}
