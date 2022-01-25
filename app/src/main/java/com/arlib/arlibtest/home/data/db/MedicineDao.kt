package com.arlib.arlibtest.home.data.db


import androidx.lifecycle.LiveData
import androidx.room.*
import com.arlib.arlibtest.data.model.Disease


@Dao
interface MedicineDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDisease(disease: Disease)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(diseases: List<Disease>)

    @Transaction
    @Query("SELECT * FROM Disease")
    fun getDiseases():LiveData<List<Disease>?>

}
