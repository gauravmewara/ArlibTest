package com.arlib.arlibtest.home.data.db

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.arlib.arlibtest.common.utils.Resource
import com.arlib.arlibtest.data.model.Disease
import com.arlib.arlibtest.home.data.net.MedicineResponse
import org.junit.Assert.*

class FakeMedicineRepositoryImpl : MedicineRepository{

    private val diseases = mutableListOf<Disease>()
    private val observableDiseases = MutableLiveData<List<Disease>>(diseases)

    private var shouldReturnNetworkError = false

    fun setShouldReturnNetworkError(value: Boolean) {
        shouldReturnNetworkError = value
    }

    private fun refreshLiveData() {
        observableDiseases.postValue(diseases)

    }

    override fun getSavedDiseases(): LiveData<List<Disease>?> {
        return observableDiseases
    }

    override suspend fun saveDiseases(diseases: List<Disease>) {
        this.diseases.clear()
        this.diseases.addAll(diseases)
        refreshLiveData()
    }

    override suspend fun loadDiseases(): Resource<MedicineResponse> {
        return if(shouldReturnNetworkError) {
            Resource.error("Error", null)
        } else {
            Resource.success(MedicineResponse(listOf()))
        }
    }

}