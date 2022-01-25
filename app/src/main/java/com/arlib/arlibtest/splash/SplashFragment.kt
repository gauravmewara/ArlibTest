package com.arlib.arlibtest.splash

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.arlib.arlibtest.R
import com.arlib.arlibtest.common.utils.ArlibSharedPreference
import com.arlib.arlibtest.common.utils.IprefHelper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class SplashFragment : Fragment() {
    private lateinit var nav_controller : NavController
    private val preferenceHelper: IprefHelper by lazy { ArlibSharedPreference(requireActivity().applicationContext) }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_splash, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        CoroutineScope(Dispatchers.Main).launch {
            delay(2000)
            nav_controller = Navigation.findNavController(view)
            if(preferenceHelper.isLogin()){
                nav_controller.navigate(R.id.action_splashFragment_to_medicineListFragment)
            }else{
                nav_controller.navigate(R.id.action_splashFragment_to_loginFragment)
            }
        }

    }
}