package com.arlib.arlibtest.common.data.net

import com.arlib.arlibtest.home.data.net.MedicineResponse
import retrofit2.Response
import retrofit2.http.GET

interface ArlibApi {
    @GET("fded8aa6-6bf1-46c5-988a-04d214269f60")
    suspend fun getMedicines(): Response<MedicineResponse>
}