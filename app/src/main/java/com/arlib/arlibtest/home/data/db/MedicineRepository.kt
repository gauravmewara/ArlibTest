package com.arlib.arlibtest.home.data.db

import androidx.lifecycle.LiveData
import com.arlib.arlibtest.data.model.Disease
import com.arlib.arlibtest.common.utils.Resource
import com.arlib.arlibtest.home.data.net.MedicineResponse

interface MedicineRepository {
    fun getSavedDiseases(): LiveData<List<Disease>?>

    suspend fun saveDiseases(diseases: List<Disease>)

    suspend fun loadDiseases(): Resource<MedicineResponse>
}