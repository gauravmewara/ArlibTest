package com.arlib.arlibtest.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.arlib.arlibtest.common.data.db.models.Med
import com.arlib.arlibtest.databinding.AppbarBinding
import com.arlib.arlibtest.databinding.FragmentDetailBinding


class DetailFragment : Fragment() {
    private lateinit var _binding: FragmentDetailBinding
    private lateinit var appBarBinding:AppbarBinding
    private val binding get() =_binding!!
    private val args : DetailFragmentArgs by navArgs()
    private lateinit var med: Med


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentDetailBinding.inflate(inflater,container,false)
        appBarBinding = binding.appBar
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setAppBar()
        med = args.medicine
        setUI()
    }
    fun setAppBar(){
        appBarBinding.ivBack.visibility = View.VISIBLE
        appBarBinding.ivLogout.visibility = View.GONE
        appBarBinding.tvTitle.text = "Medicine Detail"
        appBarBinding.ivBack.setOnClickListener {
            requireActivity().onBackPressed()
        }
    }
    fun setUI(){
        binding.tvDisease.text = med.disease
        binding.tvDose.text = med.dose
        binding.tvStrength.text = med.strength
        binding.tvMedicationClass.text = med.className
    }
    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            DetailFragment().apply {
            }
    }
}