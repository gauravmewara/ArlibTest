package com.arlib.arlibtest.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.arlib.arlibtest.common.data.db.models.Med
import com.arlib.arlibtest.databinding.MedicineItemBinding
import javax.inject.Inject

class MedicineAdapter @Inject constructor(
    //private val list:MutableList<Med>,
    val handler: RecyclerItemClickHandler
) : RecyclerView.Adapter<ItemViewHolder>() {
    private val list:MutableList<Med> = mutableListOf()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding = MedicineItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ItemViewHolder(binding)
    }

    fun setMeds(movieList: List<Med>) {
        this.list.clear()
        this.list.addAll(movieList)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        with(holder){
            with(list[position]){
                binding.tvMedname.text = medName
                binding.tvDose.text = dose
                binding.tvStrength.text = strength
                binding.tvMedicationClass.text = className
                binding.item.setOnClickListener{
                    handler.onRecyclerItemClick(this)
                }
            }
        }
    }

    override fun getItemCount(): Int {
       return list.size
    }
}
class ItemViewHolder(val binding: MedicineItemBinding) : RecyclerView.ViewHolder(binding.root) {
}
interface RecyclerItemClickHandler{
    fun onRecyclerItemClick(med: Med)
}