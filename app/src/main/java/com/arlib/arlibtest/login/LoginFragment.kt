package com.arlib.arlibtest.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.arlib.arlibtest.R
import com.arlib.arlibtest.databinding.FragmentLoginBinding
import com.arlib.arlibtest.common.utils.ArlibSharedPreference
import com.arlib.arlibtest.common.utils.IprefHelper
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment() {
    private lateinit var navController: NavController
    private lateinit var _binding : FragmentLoginBinding
    private val binding get() = _binding!!

    private val preferenceHelper: IprefHelper by lazy { ArlibSharedPreference(requireActivity().applicationContext) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentLoginBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        binding.tvSubmit.setOnClickListener {
            binding.tvSubmit.isClickable = false
            if (validateInput(binding.etUsername.text.toString(),binding.etPassword.text.toString())) {
                preferenceHelper.logIn(binding.etUsername.text.toString())
                navController.navigate(R.id.action_loginFragment_to_medicineListFragment)
                binding.tvSubmit.isClickable = true
            }else{
                binding.tvSubmit.isClickable = true
                Toast.makeText(requireContext(),"Username or Password Invalid",Toast.LENGTH_LONG).show()
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            LoginFragment().apply {
            }
    }

    private fun validateInput(usr:String,pwd:String):Boolean{
        if(usr.isNullOrEmpty()||pwd.isNullOrEmpty()){
            return false
        }
        return true
    }

}