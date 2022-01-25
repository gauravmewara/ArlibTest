package com.arlib.arlibtest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.arlib.arlibtest.common.utils.currentNavigationFragment
import com.arlib.arlibtest.detail.DetailFragment
import com.arlib.arlibtest.login.LoginFragment
import com.arlib.arlibtest.home.MedicineListFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var navController:NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.main_navigation) as NavHostFragment
        navController = navHostFragment.navController
    }

    override fun onBackPressed() {
        handleBackPress()
    }

 private fun handleBackPress(){
     when(val frg =supportFragmentManager.currentNavigationFragment){
         is LoginFragment ->{
             finish()
         }
         is MedicineListFragment ->{
             finish()
         }
         is DetailFragment ->{
             navController.navigate(R.id.action_detailFragment_to_medicineListFragment)
         }

     }
 }
}