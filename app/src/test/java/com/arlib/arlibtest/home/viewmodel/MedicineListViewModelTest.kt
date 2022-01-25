package com.arlib.arlibtest.home.viewmodel


import com.arlib.arlibtest.home.data.db.FakeMedicineRepositoryImpl
import com.google.common.truth.Truth.assertThat
import org.junit.Before
import org.junit.Test

class MedicineListViewModelTest{
    private lateinit var viewModel: MedicineListViewModel

    @Before
    fun setup() {
        viewModel = MedicineListViewModel(FakeMedicineRepositoryImpl())
    }

    @Test
    fun `test morning return true`(){
        val greet = viewModel.getGreeting(4)
        assertThat(greet).isEqualTo("Good Morning")
    }

    @Test
    fun `test morning return false`(){
        val greet = viewModel.getGreeting(13)
        assertThat(greet).isNotEqualTo("Good Morning")
    }

}