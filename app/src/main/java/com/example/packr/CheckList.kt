package com.example.packr

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.packr.Adapter.CheckListAdapter
import com.example.packr.Constants.MyConstants
import com.example.packr.Data.AppData
import com.example.packr.Database.RoomDB
import com.example.packr.Models.Items

class CheckList : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var checkListAdapter: CheckListAdapter
    private lateinit var database: RoomDB
    private var itemsList: MutableList<Items> = mutableListOf()
    private var header: String? = null
    private var show: String? = null

    private lateinit var txtAdd: EditText
    private lateinit var btnAdd: android.widget.Button
    private lateinit var linearLayout: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_check_list)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val intent = intent
        header = intent.getStringExtra(MyConstants.HEADER_SMALL)
        show = intent.getStringExtra(MyConstants.SHOW_SMALL)

        supportActionBar?.title = header

        txtAdd = findViewById(R.id.txtAdd)
        btnAdd = findViewById(R.id.btnAdd)
        recyclerView = findViewById(R.id.recyclerView)
        linearLayout = findViewById(R.id.Linearlayout)

        database = RoomDB.getInstance(this)

        if (MyConstants.FALSE_STRING == show) {
            linearLayout.visibility = android.view.View.GONE
            itemsList = database.mainDao().getAllSelected(true).toMutableList()
        } else {
            itemsList = database.mainDao().getAll(header!!).toMutableList()
        }

        updateRecycler(itemsList)

        btnAdd.setOnClickListener {
            val itemName = txtAdd.text.toString()
            if (itemName.isNotEmpty()) {
                addNewItems(itemName)
            } else {
                Toast.makeText(this, "Empty item can't be added.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onCreatePanelMenu(featureId: Int, menu: android.view.Menu): Boolean {
        menuInflater.inflate(R.menu.menu_one, menu)

        if (MyConstants.MY_SELECTIONS == header) {
            menu.getItem(0).isVisible = false
            menu.getItem(2).isVisible = false
            menu.getItem(3).isVisible = false
        } else if (MyConstants.MY_LIST_CAMEL_CASE == header) {
            menu.getItem(1).isVisible = false
        }

        val menuItem = menu.findItem(R.id.btnSearch)
        val searchView = menuItem.actionView as SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                val filteredList = itemsList.filter {
                    // Safely handle nullable itemname
                    it.itemname?.lowercase()?.startsWith(newText?.lowercase() ?: "") ?: false
                }
                updateRecycler(filteredList)
                return false
            }
        })

        return super.onCreatePanelMenu(featureId, menu)
    }

    override fun onOptionsItemSelected(item: android.view.MenuItem): Boolean {
        val appData = AppData(database, this)
        val intent = Intent(this, CheckList::class.java)

        when (item.itemId) {
            R.id.btnMySelections -> {
                intent.putExtra(MyConstants.HEADER_SMALL, MyConstants.MY_SELECTIONS)
                intent.putExtra(MyConstants.SHOW_SMALL, MyConstants.FALSE_STRING)
                startActivityForResult(intent, 101)
                return true
            }
            R.id.btnCustomList -> {
                intent.putExtra(MyConstants.HEADER_SMALL, MyConstants.MY_LIST_CAMEL_CASE)
                intent.putExtra(MyConstants.SHOW_SMALL, MyConstants.TRUE_STRING)
                startActivity(intent)
                return true
            }
            R.id.btnDeleteDefault -> {
                AlertDialog.Builder(this)
                    .setTitle("Delete default data")
                    .setMessage("Are you sure?\n\nAs this will delete the data provided by Packwise while installing.")
                    .setPositiveButton("Confirm") { _, _ ->
                        appData.persistDataByCategory(header!!, true)
                        itemsList = database.mainDao().getAll(header!!).toMutableList()
                        updateRecycler(itemsList)
                    }
                    .setNegativeButton("Cancel", null)
                    .setIcon(R.drawable.ic_warn)
                    .show()
                return true
            }
            R.id.btnReset -> {
                AlertDialog.Builder(this)
                    .setTitle("Reset to default")
                    .setMessage("Are you sure?\n\nAs this will load the default data provided by Packwise and will delete the custom data you have added in ($header)")
                    .setPositiveButton("Confirm") { _, _ ->
                        appData.persistDataByCategory(header!!, false)
                        itemsList = database.mainDao().getAll(header!!).toMutableList()
                        updateRecycler(itemsList)
                    }
                    .setNegativeButton("Cancel", null)
                    .setIcon(R.drawable.ic_warn)
                    .show()
                return true
            }
            R.id.btnAboutUs -> {
                startActivity(Intent(this, AboutUs::class.java))
                return true
            }
            R.id.btnExit -> {
                finishAffinity()
                Toast.makeText(this, "Pack your bag\nExit completed", Toast.LENGTH_SHORT).show()
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == 101) {
            itemsList = database.mainDao().getAll(header!!).toMutableList()
            updateRecycler(itemsList)
        }
    }

    private fun addNewItems(itemName: String) {
        val item = Items().apply {
            checked = false
            category = header
            itemname = itemName
            addedby = MyConstants.USER_SMALL
        }
        database.mainDao().saveItem(item)
        itemsList = database.mainDao().getAll(header!!).toMutableList()
        updateRecycler(itemsList)
        recyclerView.scrollToPosition(checkListAdapter.itemCount - 1)
        txtAdd.text.clear()
    }

    private fun updateRecycler(itemsList: List<Items>) {
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = StaggeredGridLayoutManager(1, LinearLayoutManager.VERTICAL)
        checkListAdapter = CheckListAdapter(this, itemsList, database, show!!)
        recyclerView.adapter = checkListAdapter
    }
}
