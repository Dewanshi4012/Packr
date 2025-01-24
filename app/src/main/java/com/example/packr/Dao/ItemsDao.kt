package com.example.packr.Dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.packr.Models.Items

@Dao
interface ItemsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveItem(items: Items)

    @Query("SELECT * FROM items WHERE category = :category ORDER BY id ASC")
    fun getAll(category: String): List<Items>

    @Delete
    fun delete(items: Items)

    @Query("UPDATE items SET checked = :checked WHERE id = :id")
    fun checkUncheck(id: Int, checked: Boolean)

    @Query("SELECT COUNT(*) FROM items")
    fun getItemsCount(): Int

    @Query("DELETE FROM items WHERE addedby = :addedBy")
    fun deleteAllSystemItems(addedBy: String): Int

    @Query("DELETE FROM items WHERE category = :category")
    fun deleteAllByCategory(category: String): Int

    @Query("DELETE FROM items WHERE category = :category AND addedby = :addedBy")
    fun deleteAllByCategoryAndAddedBy(category: String, addedBy: String): Int

    @Query("SELECT * FROM items WHERE checked = :checked ORDER BY id ASC")
    fun getAllSelected(checked: Boolean): List<Items>
}