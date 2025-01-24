package com.example.packr.Adapter

import android.app.AlertDialog
import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.LinearLayout
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.packr.Constants.MyConstants
import com.example.packr.Database.RoomDB
import com.example.packr.Models.Items
import com.example.packr.R

class CheckListAdapter(
    private val context: Context,
    private var itemsList: List<Items>,
    private val database: RoomDB,
    private val show: String
) : RecyclerView.Adapter<CheckListViewHolder>() {

    init {
        if (itemsList.isEmpty()) {
            Toast.makeText(context.applicationContext, "Nothing to show", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CheckListViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.check_list_item, parent, false)
        return CheckListViewHolder(view)
    }

    override fun onBindViewHolder(holder: CheckListViewHolder, position: Int) {
        val currentItem = itemsList[position]

        holder.checkBox.text = currentItem.itemname
        holder.checkBox.isChecked = currentItem.checked

        if (MyConstants.FALSE_STRING == show) {
            holder.btnDelete.visibility = View.GONE
            holder.layout.setBackgroundResource(R.drawable.border_one)
        } else {
            if (currentItem.checked) {
                holder.layout.setBackgroundColor(Color.parseColor("#E6D0B5"))
            } else {
                holder.layout.setBackgroundResource(R.drawable.border_one)
            }
        }

        holder.checkBox.setOnClickListener {
            val isChecked = holder.checkBox.isChecked
            database.mainDao().checkUncheck(currentItem.id, isChecked)

            if (MyConstants.FALSE_STRING == show) {
                itemsList = database.mainDao().getAllSelected(true)
                notifyDataSetChanged()
            } else {
                currentItem.checked = isChecked
                notifyDataSetChanged()

                var toastMessage: Toast? = null
                toastMessage?.cancel()
                toastMessage = if (currentItem.checked) {
                    Toast.makeText(context, "(${holder.checkBox.text}) Packed", Toast.LENGTH_SHORT)
                } else {
                    Toast.makeText(context, "(${holder.checkBox.text}) Un-packed", Toast.LENGTH_SHORT)
                }
                toastMessage.show()
            }
        }

        holder.btnDelete.setOnClickListener {
            AlertDialog.Builder(context)
                .setTitle("Delete (${currentItem.itemname})")
                .setMessage("Are you sure?")
                .setPositiveButton("Confirm") { _, _ ->
                    database.mainDao().delete(currentItem)
                    itemsList = itemsList.toMutableList().apply { remove(currentItem) }
                    notifyDataSetChanged()
                }
                .setNegativeButton("Cancel") { _, _ ->
                    Toast.makeText(context, "Cancelled", Toast.LENGTH_SHORT).show()
                }
                .setIcon(R.drawable.ic_delete_forever)
                .show()
        }
    }

    override fun getItemCount(): Int = itemsList.size
}

class CheckListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val layout: LinearLayout = itemView.findViewById(R.id.linearLayout)
    val checkBox: CheckBox = itemView.findViewById(R.id.checkbox)
    val btnDelete: Button = itemView.findViewById(R.id.btnDelete)
}