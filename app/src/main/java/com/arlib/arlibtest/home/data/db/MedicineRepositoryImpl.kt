package com.arlib.arlibtest.home.data.db

import android.util.Log
import androidx.lifecycle.LiveData
import com.arlib.arlibtest.data.model.Disease
import com.arlib.arlibtest.common.data.net.ArlibApi
import com.arlib.arlibtest.common.utils.Resource
import com.arlib.arlibtest.home.data.net.MedicineResponse
import javax.inject.Inject

class MedicineRepositoryImpl @Inject constructor(
    private val medDao: MedicineDao,
    private val api: ArlibApi
) : MedicineRepository {

    override fun getSavedDiseases(): LiveData<List<Disease>?> {
        return medDao.getDiseases()
        /*var list = medDao.getDiseases()
        if(list==null||list.size==0){
            list = loadDiseases()
        }
        return list*/
    }

    override suspend fun saveDiseases(diseases: List<Disease>) {
            medDao.insertAll(diseases)
    }

    /*override suspend fun loadDiseases(): Resource<MedicineResponse> {
        var list : List<Disease>?= null
        api.getMedicines().enqueue(object:Callback<MedicineResponse>{
            override fun onResponse(
                call: Call<MedicineResponse>,
                response: Response<MedicineResponse>
            ) {
                list = response.body()?.diseases
                if(list!=null){
                    saveDiseases(list!!)
                }
                Log.d(this.javaClass.simpleName, "Response: ${response.body()?.diseases}")
            }
            override fun onFailure(call: Call<MedicineResponse>, t: Throwable) {
                Log.d(this.javaClass.simpleName, "Failure")
            }
        })
        return list
    }*/

    override suspend fun loadDiseases(): Resource<MedicineResponse> {
        return try{
            val response = api.getMedicines()
            if(response.isSuccessful){
                response.body()?.let {
                    return@let Resource.success(it)
                } ?: Resource.error("An unknown error occured", null)
            }else{
                Resource.error("An unknown error occured", null)
            }
        }catch(e:Exception){
            Log.e("EXCEPTION", "EXCEPTION:", e)
            Resource.error("Couldn't reach the server. Check your internet connection", null)
        }
    }
}