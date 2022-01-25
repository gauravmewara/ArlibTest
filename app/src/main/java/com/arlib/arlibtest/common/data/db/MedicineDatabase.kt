package com.arlib.arlibtest.common.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.arlib.arlibtest.home.data.db.MedicineDao
import com.arlib.arlibtest.data.model.Disease
import com.arlib.arlibtest.data.model.MedicationClass
import com.arlib.arlibtest.data.model.MedicineModel

@Database(entities = [Disease::class,MedicationClass::class,MedicineModel::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class MedicineDatabase: RoomDatabase() {
    abstract fun medicineDao(): MedicineDao
    companion object {
        private val lock = Any()
        private const val DB_NAME = "MedicineDatabase"
        private var INSTANCE: MedicineDatabase? = null
        fun getInstance(context: Context): MedicineDatabase {
            synchronized(lock) {
                if (INSTANCE == null) {
                    INSTANCE =
                        Room.databaseBuilder(context, MedicineDatabase::class.java, DB_NAME)
                            //.allowMainThreadQueries()
                            .build()
                }
            }
            return INSTANCE!!
        }
    }
}