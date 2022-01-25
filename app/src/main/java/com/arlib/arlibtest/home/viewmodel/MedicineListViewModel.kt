package com.arlib.arlibtest.home.viewmodel

import androidx.lifecycle.*
import com.arlib.arlibtest.data.model.Disease
import com.arlib.arlibtest.common.data.db.models.Med
import com.arlib.arlibtest.common.utils.DecoratorListener
import com.arlib.arlibtest.common.utils.Event
import com.arlib.arlibtest.common.utils.Resource
import com.arlib.arlibtest.home.data.db.MedicineRepository
import com.arlib.arlibtest.home.data.db.MedicineRepositoryImpl
import com.arlib.arlibtest.home.data.net.MedicineResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList

@HiltViewModel
class MedicineListViewModel @Inject constructor(
    private val repository: MedicineRepository
) : ViewModel() {
    val diseases = repository.getSavedDiseases()

    private val _meds : MutableLiveData<List<Med>> = MutableLiveData()
    val meds : LiveData<List<Med>> = _meds

    private val _loadResponse = MutableLiveData<Event<Resource<MedicineResponse>>>()
    val loadResponse: LiveData<Event<Resource<MedicineResponse>>> = _loadResponse

    private val _insertDiseaseStatus = MutableLiveData<Event<Resource<List<Disease>>>>()
    val insertDiseaseStatus: LiveData<Event<Resource<List<Disease>>>> = _insertDiseaseStatus

    fun insertDiseasesIntoDb(diseases: List<Disease>) = viewModelScope.launch {
        repository.saveDiseases(diseases)
    }

    fun saveDisease(list:List<Disease>){
        insertDiseasesIntoDb(list)
        _insertDiseaseStatus.postValue(Event(Resource.success(list)))
    }

    fun loadDiseases() {
        _loadResponse.value = Event(Resource.loading(null))
        viewModelScope.launch {
            val response = repository.loadDiseases()
            _loadResponse.value = Event(response)
        }
    }

    fun diseaseToMeds(list:List<Disease>?):List<Med>{
        var meds = ArrayList<Med>()
        if (list != null) {
            for (disease in list) {
                for(mclass in disease.medicineClasses){
                    for(drug in mclass.drugs){
                        meds.add(Med(drug.name,drug.dose,drug.strength,mclass.nameval,disease.name))
                    }
                }
            }
        }
        return meds
    }

    fun getGreeting(timeOfDay:Int):String{
        return if (timeOfDay in 4..11) {
            "Good Morning"
        } else if (timeOfDay in 12..15) {
            "Good Afternoon"
        } else if (timeOfDay in 16..20) {
            "Good Evening"
        } else if (timeOfDay in 21..24||timeOfDay in 0..3 ) {
            "Good Night"
        }else{
            ""
        }
    }

    fun getListener(medlist: List<Med>): DecoratorListener {
        //val medlist = allMeds.value
        return object : DecoratorListener {
            override fun isHeader(position: Int): Boolean {
                return (position == 0
                        || medlist!![position]
                    .disease !== medlist!![position - 1]
                    .disease)

            }
            override fun getHeaderText(position: Int): String {
                return medlist!![position]
                    .disease
            }
        }
    }
    fun setMed(list:List<Disease>?){
        _meds.value = diseaseToMeds(list)
    }
}