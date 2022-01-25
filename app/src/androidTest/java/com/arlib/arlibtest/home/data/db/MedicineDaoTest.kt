package com.arlib.arlibtest.home.data.db

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.androiddevs.shoppinglisttestingyt.getOrAwaitValue
import com.arlib.arlibtest.common.data.db.MedicineDatabase
import com.arlib.arlibtest.data.model.Disease
import com.arlib.arlibtest.data.model.MedicationClass
import com.arlib.arlibtest.data.model.MedicineModel
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@SmallTest
class MedicineDaoTest {
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()
    private lateinit var database : MedicineDatabase
    private lateinit var dao:MedicineDao

    @Before
    fun setup(){
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            MedicineDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        dao = database.medicineDao()
    }

    @After
    fun tearDown(){
        database.close()
    }

    @Test
    fun insertDiseases()= runBlocking {
        var diseaselist = ArrayList<Disease>()
        var classlist1 = ArrayList<MedicationClass>()
        var medlist1  = ArrayList<MedicineModel>()
        var medlist2 = ArrayList<MedicineModel>()


        medlist1.add( MedicineModel(1,"med1","2","500"))
        medlist1.add(MedicineModel(2,"med2","2","500"))
        medlist2.add(MedicineModel(3,"med3","2","500"))
        medlist2.add(MedicineModel(4,"med4","2","500"))
        classlist1.add(MedicationClass(1,"class1",medlist1))
        classlist1.add(MedicationClass(2,"class2",medlist2))
        diseaselist.add( Disease(1,"Diabetes",classlist1))

        dao.insertAll(diseaselist)
        val allDiseases = dao.getDiseases().getOrAwaitValue()
        val str1 = diseaselist.toString()
        val str2 = allDiseases.toString()
        assertThat(str2).contains(str1)
    }

}
