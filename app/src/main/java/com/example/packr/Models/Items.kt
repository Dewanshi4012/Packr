package com.example.packr.Models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "items")
data class Items(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,

    @ColumnInfo(name = "itemname")
    var itemname: String? = null,

    @ColumnInfo(name = "category")
    var category: String? = null,

    @ColumnInfo(name = "addedby")
    var addedby: String = "system",

    @ColumnInfo(name = "checked")
    var checked: Boolean = false
) : Serializable {
    constructor(itemname: String, category: String, checked: Boolean) : this(
        itemname = itemname,
        category = category,
        addedby = "system",
        checked = checked
    )

    constructor(itemname: String, category: String, addedby: String, checked: Boolean) : this(){
        this.itemname = itemname
        this.category = category
        this.addedby = addedby
        this.checked = checked
    }




}