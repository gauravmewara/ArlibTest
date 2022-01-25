package com.arlib.arlibtest.di

import androidx.fragment.app.Fragment
import com.arlib.arlibtest.home.RecyclerItemClickHandler
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent

@Module
@InstallIn(FragmentComponent::class)
object MedicineListModule {
    @Provides
    fun provideRecyclerItemClickHandler(fragment:Fragment)=fragment as RecyclerItemClickHandler
}