package com.example.nycschools.home.ui

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.nycschools.R
import com.example.nycschools.home.data.PostModel
import kotlinx.android.synthetic.main.home_rv_item_view.view.*

class HomeAdapter(var listener:HomeListener) : RecyclerView.Adapter<HomeAdapter.HomeViewHolder>(){

    private var data : ArrayList<PostModel>?=null

    interface HomeListener{
        fun onItemDeleted(postModel: PostModel, position: Int)
        fun onItemClicked(postModel: PostModel, itemView: View)
    }

    fun setData(list: ArrayList<PostModel>){
        data = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        return HomeViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.home_rv_item_view, parent, false))
    }

    override fun getItemCount(): Int {
        return data?.size ?: 0
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        val item = data?.get(position)
        holder.bindView(item, position)
    }


    inner class HomeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var currentposition : Int = 0

        init {
            itemView.setOnClickListener {
                data?.get(currentposition)
                    ?.let { it1 -> listener.onItemClicked(it1, itemView) }
            }
        }

        fun bindView(item: PostModel?, position: Int) {

            itemView.tv_home_item_title.text = item?.school_name
            itemView.tv_home_item_body.text = item?.location
            this.currentposition = position
        }
    }
}