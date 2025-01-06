package com.mobile.crud_berita.service

import com.mobile.crud_berita.model.LoginResponse
import com.mobile.crud_berita.model.RegisterResponse
import com.mobile.crud_berita.model.ResponseBerita
import com.mobile.crud_berita.model.UserResponse
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface BeritaService {
    @GET("getBerita.php")
    fun getAllBerita(): Call<ResponseBerita>

    @FormUrlEncoded
    @POST("register.php")
    fun registerUser(
        @Field("username") username: String,
        @Field("password") password: String,
        @Field("fullname") fullname: String,
        @Field("email") email: String,
    ):Call<RegisterResponse>

    @FormUrlEncoded
    @POST("login.php")
    fun login(
        @Field("username") username: String,
        @Field("password") password: String,
    ):Call<LoginResponse>

    @GET("listmahasiswa.php")
    fun getAllUser() : Call<UserResponse>
}