package com.arlib.arlibtest.common.data.db

import androidx.room.TypeConverter
import com.arlib.arlibtest.data.model.MedicationClass
import com.arlib.arlibtest.data.model.MedicineModel
import com.google.gson.reflect.TypeToken

import com.google.gson.Gson
import java.lang.reflect.Type


class Converters {
    @TypeConverter
    fun stringToMedicationClass(json: String):List<MedicationClass>{
        val gson = Gson()
        val type:Type = object:TypeToken<List<MedicationClass>>(){}.type
        return gson.fromJson<List<MedicationClass>>(json,type)
    }
    @TypeConverter
    fun medicationClassToString(list:List<MedicationClass>):String{
        val gson = Gson()
        val type:Type = object :TypeToken<List<MedicationClass>>(){}.type
        return gson.toJson(list,type)
    }
    @TypeConverter
    fun stringToDrug(json: String):List<MedicineModel>{
        val gson = Gson()
        val type:Type = object:TypeToken<List<MedicineModel>>(){}.type
        return gson.fromJson<List<MedicineModel>>(json,type)
    }
    @TypeConverter
    fun drugToString(list:List<MedicineModel>):String{
        val gson = Gson()
        val type:Type = object :TypeToken<List<MedicineModel>>(){}.type
        return gson.toJson(list,type)
    }
}
