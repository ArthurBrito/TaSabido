package br.ufc.engsoftware.retrofit;

import java.util.concurrent.TimeUnit;
import br.ufc.engsoftware.auxiliar.Statics;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Copyed by jean from lima neto on 24/07/16.
 */

//Singleton Class
public class RetrofitService {
    private static Retrofit retrofit = null;


    public static Retrofit getInstance(){
        if( retrofit == null)
            retrofit = getNewRetrofitService();
        return retrofit;
    }

    private static  Retrofit getNewRetrofitService(){
        OkHttpClient.Builder okHttpClient = new OkHttpClient.Builder();

        okHttpClient.readTimeout(60, TimeUnit.SECONDS);
        okHttpClient.connectTimeout(60, TimeUnit.SECONDS);

        return new Retrofit
                .Builder()
                .baseUrl(Statics.BASE_URL)
                .client(okHttpClient.build())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
}

//    public static Retrofit getInstanceWithAuthorization(String token){
//        return getNewRetrofitServiceAuthorization(token);
//    }

//    private static Retrofit getNewRetrofitServiceAuthorization(final String token){
//        final String tokenAuth = "Token " + token.replace("\"", "");
//        //Authorization = “Token <the_auth_token>"
//
//        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
//        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
//
//        OkHttpClient.Builder okHttpClient = new OkHttpClient.Builder();
//
//        okHttpClient.readTimeout(60, TimeUnit.SECONDS);
//        okHttpClient.connectTimeout(60, TimeUnit.SECONDS);
//
//        okHttpClient.addInterceptor(new Interceptor() {
//            @Override
//            public Response intercept(Chain chain) throws IOException {
//                Request request = chain.request()
//                        .newBuilder()
//                        .addHeader("Content-Type", "application/json")
//                        .addHeader("Authorization", tokenAuth)
//                        .build();
//                return chain.proceed(request);
//            }
//        });
//
//        //LOG
//        okHttpClient.addInterceptor(logging);
//
//        return new Retrofit
//                .Builder()
//                .baseUrl(BASE_URL)
//                .addConverterFactory(GsonConverterFactory.create())
//                .client(okHttpClient.build())
//                .build();
//    }