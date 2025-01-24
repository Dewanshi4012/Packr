package com.example.packr.Adapter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.packr.CheckList
import com.example.packr.Constants.MyConstants
import com.example.packr.MainActivity
import com.example.packr.R

class Adapter(
    context: Context,
    private val titles: MutableList<String>,
    private val images: MutableList<Int>,
    private val activity: Activity
) : RecyclerView.Adapter<Adapter.MyViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = inflater.inflate(R.layout.main_item, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.title.text = titles[position]
        holder.img.setImageResource(images[position])
        holder.linearLayout.alpha = 0.8F

        holder.linearLayout.setOnClickListener {
            val intent = Intent(it.context, CheckList::class.java).apply {
                putExtra(MyConstants.HEADER_SMALL, titles[position])
                putExtra(
                    MyConstants.SHOW_SMALL,
                    if (MyConstants.MY_SELECTIONS == titles[position]) MyConstants.FALSE_STRING else MyConstants.TRUE_STRING
                )
            }
            it.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = titles.size

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.title)
        val img: ImageView = itemView.findViewById(R.id.img)
        val linearLayout: LinearLayout = itemView.findViewById(R.id.linearLayout)
    }
}