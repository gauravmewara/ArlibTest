package com.arlib.arlibtest.data.model

import androidx.room.*
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
@Entity
data class MedicineModel(
    @PrimaryKey(autoGenerate = true)
    val medId:Long,
    @SerializedName("name")
    @Expose
    val name:String,
    @SerializedName("dose")
    @Expose
    val dose: String,
    @SerializedName("strength")
    @Expose
    val strength:String,
)

@Entity
public data class MedicationClass(
    @PrimaryKey(autoGenerate = true)
    val classId:Long,

    @SerializedName("className")
    @Expose
    val nameval :String,

    @SerializedName("associatedDrug")
    @Expose
    val  drugs : List<MedicineModel>,
)

@Entity
public data class Disease(
    @PrimaryKey(autoGenerate = true)
    val diseaseId:Long,

    @SerializedName("name")
    @Expose
    val name:String,

    @SerializedName("medicationsClasses")
    @Expose
    val medicineClasses : List<MedicationClass>
)


