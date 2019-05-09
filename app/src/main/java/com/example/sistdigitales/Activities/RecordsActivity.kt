package com.example.sistdigitales.Activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import com.example.sistdigitales.*
import com.example.sistdigitales.Adapters.AdapterButton
import com.example.sistdigitales.Models.*
import com.example.sistdigitales.Util.HandlingExcel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.content_adapter_button.*

class RecordsActivity : AppCompatActivity() {


    var buttonStyleList: ArrayList<Any>? = null
    var buttonStyleAdapter: AdapterButton? = null
    var layoutManager: RecyclerView.LayoutManager? = null
    val handlingExcel = HandlingExcel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_records)
        adapterOrganized()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.top_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item!!.itemId){
            R.id.print_menu_button->{
                val filepath = "./test.xlsx"
//                handlingExcel.writeToExcelFile(filepath)
                Log.d("Item clicked", "Item print clicked")}
        }
        return super.onOptionsItemSelected(item)
    }

    fun adapterOrganized(){
        buttonStyleList = ArrayList<Any>()
        buttonStyleList!!.add(ButtonStyle("TEMPERATURA",R.mipmap.ic_temperature))
        buttonStyleList!!.add(ButtonStyle("HUMEDAD",R.mipmap.ic_weather_))
        buttonStyleList!!.add(ButtonStyle("TIEMPO",R.mipmap.ic_time))
        buttonStyleList!!.add(ButtonStyle("PORCENTAJE LUZ",R.mipmap.ic_light))

        buttonStyleAdapter = AdapterButton(buttonStyleList!!, this, R.layout.button_image)
        layoutManager = LinearLayoutManager(this)

        ///
        rvAdapterButton.layoutManager = layoutManager
        rvAdapterButton.adapter = buttonStyleAdapter

        buttonStyleAdapter!!.notifyDataSetChanged()
    }


}
