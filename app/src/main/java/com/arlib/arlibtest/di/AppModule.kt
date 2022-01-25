package com.arlib.arlibtest.di

import android.app.Application
import com.arlib.arlibtest.home.data.db.MedicineDao
import com.arlib.arlibtest.common.data.db.MedicineDatabase
import com.arlib.arlibtest.common.data.net.ArlibApi
import com.arlib.arlibtest.common.data.net.RetrofitClient
import com.arlib.arlibtest.common.utils.ArlibSharedPreference
import com.arlib.arlibtest.common.utils.IprefHelper
import com.arlib.arlibtest.home.data.db.MedicineDao_Impl
import com.arlib.arlibtest.home.data.db.MedicineRepository
import com.arlib.arlibtest.home.data.db.MedicineRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Singleton
    @Provides
    fun provideDB(context: Application): MedicineDatabase {
        return MedicineDatabase.getInstance(context)
    }

    @Singleton
    @Provides
    fun provideMedDao(db: MedicineDatabase): MedicineDao {
        return db.medicineDao()
    }

    @Singleton
    @Provides
    fun provideDefaultShoppingRepository(
        dao: MedicineDao,
        api: ArlibApi
    ) = MedicineRepositoryImpl(dao, api) as MedicineRepository


    @Singleton
    @Provides
    fun provideApi(): ArlibApi {
        return RetrofitClient.getApiObject()
    }

    @Singleton
    @Provides
    fun provideSharedPref(context:Application): IprefHelper {
        return ArlibSharedPreference(context)
    }

}