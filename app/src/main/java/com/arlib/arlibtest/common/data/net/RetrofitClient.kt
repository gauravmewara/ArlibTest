package com.arlib.arlibtest.common.data.net

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitClient {
    //https://run.mocky.io/v3/fded8aa6-6bf1-46c5-988a-04d214269f60
    enum class AppMode {
        DEVELOPMENT, PRODUCTION
    }
    companion object{
        private val appMode : AppMode = AppMode.DEVELOPMENT
        private val BASE_URL = when(appMode){
            AppMode.DEVELOPMENT -> {"https://run.mocky.io/v3/"}
            AppMode.PRODUCTION -> {""}
        }
        fun getApiObject(): ArlibApi {
            val builder = OkHttpClient.Builder()
            val okHttpClient = builder.build()
            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build()
            return retrofit.create(ArlibApi::class.java)
        }
    }
    /*init {
        val builder = OkHttpClient.Builder()
        val okHttpClient = builder.build()
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
        arlibApi = retrofit.create(ArlibApi::class.java)
    }*/


}