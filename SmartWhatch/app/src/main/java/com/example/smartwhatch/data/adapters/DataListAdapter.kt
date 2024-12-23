package com.example.smartwhatch.data.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.smartwhatch.R
import com.example.smartwhatch.databinding.ItemHealthDataBinding
import com.example.smartwhatch.data.data.HealthData
import com.example.smartwhatch.data.database.HealthDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class DataListAdapter(private val context: Context, private var dataList: List<HealthData>) :
    RecyclerView.Adapter<DataListAdapter.DataViewHolder>() {

    private lateinit var database: HealthDatabase

    init {
        database = HealthDatabase.getDatabase(context)
    }

    class DataViewHolder(val binding: ItemHealthDataBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder {
        val binding = ItemHealthDataBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DataViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        val healthData = dataList[position]

        GlobalScope.launch(Dispatchers.IO) {
            val dataName = database.dataNameDao().getDataNameList().find{it.id == healthData.dataNameId}
            launch(Dispatchers.Main){
                holder.binding.dataNameTextView.text = dataName?.name
            }
        }

        holder.binding.valueTextView.text = healthData.value.toString()

        holder.binding.deleteButton.setOnClickListener {
            deleteItem(healthData, position)
        }
    }

    override fun getItemCount(): Int = dataList.size

    fun updateData(newDataList: List<HealthData>) {
        dataList = newDataList
        notifyDataSetChanged()
    }

    private fun deleteItem(healthData: HealthData, position: Int) {
        GlobalScope.launch(Dispatchers.IO) {
            database.healthDataDao().delete(healthData)
            val updatedList = dataList.toMutableList()
            updatedList.removeAt(position)
            launch(Dispatchers.Main){
                updateData(updatedList)
            }
        }
    }
}