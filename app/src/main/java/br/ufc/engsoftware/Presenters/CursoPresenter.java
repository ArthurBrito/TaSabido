//package br.ufc.engsoftware.Presenters;
//
//import android.content.Context;
//import android.util.Log;
//
//import java.util.List;
//
//import br.ufc.engsoftware.Ormlite.Curso;
//import br.ufc.engsoftware.Ormlite.Cursos;
//import br.ufc.engsoftware.retrofit.CursosService;
//import retrofit2.Call;
//import retrofit2.Callback;
//import retrofit2.Response;
//
//
///**
// * Created by limaneto on 24/07/16.
// */
//public class CursoPresenter {
//    private CursosService cursosService;
//    private CursoPresenter.CursosCallbackListener activity;
//
//
//
//
//    public interface CursosCallbackListener {
//        void cursosPreparados(List<Cursos> cursos);
//    }
//
//
//    public CursoPresenter(CursoPresenter.CursosCallbackListener activity){
//        this.activity = activity;
//        cursosService = CursosService.getInstance();
//    }
//
//
//    public void getCursos(){
////        List<Curso> cursos =  (List<Curso>) CacheUtil.retrieveObject(context, CACHE_PODCASTS);
////
////        if( audios != null)
////            activity.podcastReady(audios);
//        pegarCursosFromService();
//    }
//
//
//    protected void pegarCursosFromService(){
//        cursosService
//                .getApi()
//                .getCursos()
//                .enqueue(new Callback<List<Cursos>>() {
//                    @Override
//                    public void onResponse(Call<List<Cursos>> call, Response<List<Cursos>> response) {
//                        List<Cursos> cursos = response.body();
//
//
//                        if (cursos != null) {
////                            CacheUtil.saveObject(context, CACHE_PODCASTS, audios);
//                            activity.cursosPreparados(cursos);
//                        }
//                    }
//
//                    @Override
//                    public void onFailure(Call<List<Cursos>> call, Throwable t) {
//                        Log.i("test", t.getMessage().toString());
//                    }
//                });
//    }
//
//}
