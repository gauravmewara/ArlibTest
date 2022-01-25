package com.arlib.arlibtest.home.data.net

import com.arlib.arlibtest.data.model.Disease
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class MedicineResponse(
    @SerializedName("problems")
    @Expose
    var diseases: List<Disease>
)
