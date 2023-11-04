package org.lijun.kotlin_demos.flows.eg.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.lijun.kotlin_demos.databinding.ItemUserBinding
import org.lijun.kotlin_demos.flows.eg.database.User

class UserAdapter(val context: Context) : RecyclerView.Adapter<BingViewHolder>() {

    private val data = ArrayList<User>()

    fun setData(data: List<User>) {
        this.data.clear()
        this.data.addAll(data)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BingViewHolder {
        val binding = ItemUserBinding.inflate(LayoutInflater.from(context), parent, false)
        return BingViewHolder(binding)
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: BingViewHolder, position: Int) {
        val item = data[position]
        val binding = holder.binding as ItemUserBinding
        binding.textviewUserItem.text = "ID:${item.uid},Name:${item.name},Age:${item.age},Sex:${item.sex}"
    }
}