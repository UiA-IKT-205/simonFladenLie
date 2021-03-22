package com.example.inventory

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.recycler_item.view.*
import java.nio.file.Files.size

class RecyclerItemAdapter(private val recyclerList: List<RecyclerViewItem>) : RecyclerView.Adapter<RecyclerItemAdapter.RecyclerViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.recycler_item,
        parent, false)

        return RecyclerViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
        val currentItem = recyclerList[position]

        // Retrieves from the view we already chached from the viewHolder because the function gets called over and over again.
        holder.imageView.setImageResource(currentItem.imageResource)
        holder.textView1.text = currentItem.text1
        holder.textView2.text = currentItem.text2

    }

    override fun getItemCount() = recyclerList.size

    class RecyclerViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        // Using this syntethic property instead of (R.id.image_view)
        val imageView: ImageView = itemView.image_view
        val textView1: TextView = itemView.text_view_1
        val textView2: TextView = itemView.text_view_2
    }
}