package com.example.packr

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.packr.Constants.MyConstants
import com.example.packr.Database.RoomDB
import com.example.packr.Data.AppData
import com.example.packr.Adapter.Adapter


class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var titles: MutableList<String>
    private lateinit var images: MutableList<Int>
    private lateinit var adapter: Adapter
    private lateinit var database: RoomDB

    companion object {
        private const val TIME_INTERVAL = 2000 // milliseconds
    }

    private var mBackPressed: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()

        recyclerView = findViewById(R.id.recyclerView)

        addTitles()
        addImages()
        persistAppData()

        database = RoomDB.getInstance(this)
        println("---------------->" + database.mainDao().getAllSelected(false)[0].itemname)

        adapter = Adapter(this, titles, images, this@MainActivity)
        val gridLayoutManager = GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false)
        recyclerView.layoutManager = gridLayoutManager
        recyclerView.adapter = adapter
    }

    override fun onBackPressed() {
        if (mBackPressed + TIME_INTERVAL > System.currentTimeMillis()) {
            super.onBackPressed()
        } else {
            Toast.makeText(this, "Tap back button in order to exit", Toast.LENGTH_SHORT).show()
        }
        mBackPressed = System.currentTimeMillis()
    }

    private fun persistAppData() {
        val prefs = PreferenceManager.getDefaultSharedPreferences(this)
        val editor = prefs.edit()

        database = RoomDB.getInstance(this)
        val appData = AppData(database)
        val last = prefs.getInt(AppData.LAST_VERSION, 0)

        if (!prefs.getBoolean(MyConstants.FIRST_TIME_CAMEL_CASE, false)) {
            appData.persistAllData()
            editor.putBoolean(MyConstants.FIRST_TIME_CAMEL_CASE, true)
            editor.apply()
        } else if (last < AppData.NEW_VERSION) {
            database.mainDao().deleteAllSystemItems(MyConstants.SYSTEM_SMALL)
            appData.persistAllData()
            editor.putInt(AppData.LAST_VERSION, AppData.NEW_VERSION)
            editor.apply()
        }
    }

    private fun addTitles() {
        titles = mutableListOf(
            MyConstants.BASIC_NEEDS_CAMEL_CASE,
            MyConstants.CLOTHING_CAMEL_CASE,
            MyConstants.PERSONAL_CARE_CAMEL_CASE,
            MyConstants.BABY_NEEDS_CAMEL_CASE,
            MyConstants.HEALTH_CAMEL_CASE,
            MyConstants.TECHNOLOGY_CAMEL_CASE,
            MyConstants.FOOD_CAMEL_CASE,
            MyConstants.BEACH_SUPPLIES_CAMEL_CASE,
            MyConstants.CAR_SUPPLIES_CAMEL_CASE,
            MyConstants.NEEDS_CAMEL_CASE,
            MyConstants.MY_LIST_CAMEL_CASE,
            MyConstants.MY_SELECTIONS_CAMEL_CASE
        )
    }

    private fun addImages() {
        images = mutableListOf(
            R.drawable.p1,
            R.drawable.p2,
            R.drawable.p3,
            R.drawable.p4,
            R.drawable.p5,
            R.drawable.p6,
            R.drawable.p7,
            R.drawable.p8,
            R.drawable.p9,
            R.drawable.p10,
            R.drawable.p11,
            R.drawable.p12
        )
    }
}
