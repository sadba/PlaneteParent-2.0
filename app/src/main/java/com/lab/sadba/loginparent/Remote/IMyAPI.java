package com.lab.sadba.loginparent.Remote;

import com.lab.sadba.loginparent.Model.Bulletin;
import com.lab.sadba.loginparent.Model.Enfant;
import com.lab.sadba.loginparent.Model.Evaluation;
import com.lab.sadba.loginparent.Model.InfoEtab;
import com.lab.sadba.loginparent.Model.InfosEleves;
import com.lab.sadba.loginparent.Model.Note;
import com.lab.sadba.loginparent.Model.PostRegisterUser;
import com.lab.sadba.loginparent.Model.PostUser;
import com.lab.sadba.loginparent.Model.PostVerifUser;
import com.lab.sadba.loginparent.Model.RegisterUser;
import com.lab.sadba.loginparent.Model.Temps;
import com.lab.sadba.loginparent.Model.User;
import com.lab.sadba.loginparent.Model.VerifUser;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface IMyAPI {

    @Headers("Content-type: application/json")
    @POST("auth_parent")
    Call<User> loginUser(@Body PostUser postUser);

    @Headers("Content-type: application/json")
    @POST("verif_parent")
    Call<VerifUser> verifUser(@Body PostVerifUser postVerifUser);

    @Headers("Content-type: application/json")
    @POST("save_parent")
    Call<RegisterUser> registerUser(@Body PostRegisterUser postRegisterUser);

    /*@Headers("Content-type: application/json")
    @GET("enfants")
    Call<List<Enfant>> getEnfants(@Query("ien") String ien);*/

    @GET("enfants")
    Observable<List<Enfant>> getEnfants(@Query("ien") String ien);

    @GET("evaluation-eleve/index.php")
    Observable<List<Evaluation>> getEval(@Query("ien") String ien);

    @GET("infos_eleve/index.php")
    Observable<List<InfosEleves>> getInfosEleves(@Query("ien") String ien);

    @GET("etab_by_code_structure")
    Observable<InfoEtab> getInfos(@Query("code") String code);

    @GET("planning-eleve/index.php")
    Observable<List<Temps>> getTemps(@Query("ien") String ien);

    @GET("notes_eleve/index.php")
    Observable<List<Note>> getNotes(@Query("ien") String ien);

    @GET("info_bulletin/index.php")
    Observable<List<Bulletin>> getBulletins(@Query("ien") String ien);
}
