package org.lijun.kotlin_demos.flows.eg.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.lijun.kotlin_demos.coroutines.eg.api.NormalUser
import org.lijun.kotlin_demos.databinding.ItemNormalUserBinding
import org.lijun.kotlin_demos.databinding.ItemUserBinding

class NormalUserAdapter(val context: Context) : RecyclerView.Adapter<BingViewHolder>() {

    private val data = ArrayList<NormalUser>()

    fun setData(data: List<NormalUser>) {
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
        binding.textviewUserItem.text =
            """
            ID:${item.userId}
            Name:${item.userName}
            NickName:${item.nickName}
            Sex:${item.sex}"
            """
    }
}