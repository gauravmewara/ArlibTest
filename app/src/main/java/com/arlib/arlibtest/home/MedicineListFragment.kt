package com.arlib.arlibtest.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arlib.arlibtest.R
import com.arlib.arlibtest.common.data.db.models.Med
import com.arlib.arlibtest.common.utils.MyRecyclerDecorator
import androidx.navigation.NavController
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.arlib.arlibtest.databinding.AppbarBinding
import com.arlib.arlibtest.databinding.FragmentMedicineListBinding
import com.arlib.arlibtest.common.utils.IprefHelper
import com.arlib.arlibtest.common.utils.Status
import com.arlib.arlibtest.home.viewmodel.MedicineListViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.*
import javax.inject.Inject

@AndroidEntryPoint
class MedicineListFragment @Inject constructor() : Fragment() , RecyclerItemClickHandler {
    private lateinit var navController: NavController
    private lateinit var _binding : FragmentMedicineListBinding
    private val binding get() = _binding!!
    private lateinit var appBarBinding : AppbarBinding
    @Inject
    lateinit var adapter : MedicineAdapter
    private lateinit var viewModel: MedicineListViewModel
    @Inject
    lateinit var preferenceHelper: IprefHelper

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMedicineListBinding.inflate(inflater,container,false)
        appBarBinding = binding.appbar
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        viewModel = ViewModelProvider(this).get(MedicineListViewModel::class.java)
        setAppBar()
        showLoading()
        setUI()
        setObservers()
    }
    fun setObservers(){
        viewModel?.diseases?.observe(viewLifecycleOwner,Observer{
            if(it!=null){
                if(it.isEmpty()){
                    viewModel.loadDiseases()
                }else{
                    viewModel.setMed(it)
                }
            }else{
                viewModel.loadDiseases()
            }
        })
        viewModel?.meds?.observe(viewLifecycleOwner, Observer {
            val decorator = MyRecyclerDecorator(
                resources.getDimensionPixelSize(R.dimen.recycler_section_header_height),
                true,
                viewModel.getListener(it))
            binding.rvDisease.addItemDecoration(decorator)
            adapter.setMeds(it)
            hideLoading()
        })
        viewModel?.loadResponse?.observe(viewLifecycleOwner, Observer {
            it.getContentIfNotHandled()?.let { result ->
                when(result.status) {
                    Status.SUCCESS -> {
                        val d = result.data
                        viewModel.saveDisease(d?.diseases!!)
                    }
                    Status.ERROR -> {

                    }
                    Status.LOADING -> {
                    }
                }
            }
        })
        viewModel.insertDiseaseStatus.observe(viewLifecycleOwner,Observer{
            it.getContentIfNotHandled()?.let { result ->
                when(result.status){
                    Status.SUCCESS -> {
                        val d = result.data
                        viewModel.setMed(d)
                    }
                    Status.ERROR -> {

                    }
                    Status.LOADING -> {
                    }
                }
            }
        })

    }

    fun setAppBar(){
        appBarBinding.ivBack.visibility = View.INVISIBLE
        appBarBinding.ivLogout.visibility = View.VISIBLE
        appBarBinding.tvTitle.text = "${viewModel.getGreeting(Calendar.getInstance().get(Calendar.HOUR_OF_DAY))}, ${preferenceHelper.getUserName()}"
        appBarBinding.ivLogout.setOnClickListener {
            logOut()
        }
    }

    fun setUI(){
        //binding.tvGreeting.text = "${viewModel.getGreeting(Calendar.getInstance().get(Calendar.HOUR_OF_DAY))}, ${preferenceHelper.getUserName()}"
        binding.rvDisease.layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false)
        binding.rvDisease.adapter = adapter
    }

    private fun showLoading() {
        binding.rvDisease.isEnabled = false
        binding.progress.visibility = View.VISIBLE
    }

    private fun hideLoading() {
        binding.rvDisease.isEnabled = true
        binding.progress.visibility = View.GONE
    }

    private fun logOut(){
        preferenceHelper.logOut()
        navController.navigate(R.id.action_medicineListFragment_to_loginFragment)
    }
    override fun onRecyclerItemClick(med: Med) {
        val action = MedicineListFragmentDirections.actionMedicineListFragmentToDetailFragment(med)
        navController.navigate(action)
    }
}