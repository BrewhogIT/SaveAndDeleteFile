package com.practicum.saveanddeletefile

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.practicum.saveanddeletefile.databinding.FileNameBinding


class MainAdapter(val onClick:(Int)->Unit) : RecyclerView.Adapter<MainAdapter.MainViewHolder>() {
    var list = mutableListOf<String>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    class MainViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        lateinit var bindind : FileNameBinding

        constructor(b : FileNameBinding) : this (b.root){
            bindind = b
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        var fileNameBinding = FileNameBinding.inflate(LayoutInflater.from(parent.context))
        return MainViewHolder(fileNameBinding)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        holder.bindind.fileName.text = list[position]

        holder.itemView.setOnClickListener{
            onClick(position)
        }
    }
}