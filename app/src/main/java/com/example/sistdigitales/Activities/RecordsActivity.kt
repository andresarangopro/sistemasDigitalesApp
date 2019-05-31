package com.example.sistdigitales.Activities

import android.Manifest
import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v4.content.ContextCompat.checkSelfPermission
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
import javax.xml.xpath.XPathConstants.STRING

@SuppressLint("ByteOrderMark")
class RecordsActivity : AppCompatActivity() {

    private val TAG = "PermissionDemo"
    private val RECORD_REQUEST_CODE = 101
    private val RECORD_REQUEST_CODE_READ = 100

    var buttonStyleList: ArrayList<Any>? = null
    var buttonStyleAdapter: AdapterButton? = null
    var layoutManager: RecyclerView.LayoutManager? = null
    var handlingExcel:HandlingExcel?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_records)
        val  actionBr = supportActionBar
        actionBr!!.title ="MODULOS DE MEDICIÓN"
        adapterOrganized()
         handlingExcel = HandlingExcel(this)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.top_menu, menu)
        return true
    }


    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item!!.itemId){
            R.id.print_menu_button->{
                makeRequest()
                handlingExcel!!.getFirebaseValues()
                //handlingExcel!!.getPublicAlbumStorageDir("casta.txt")
                Log.d("Item clicked", "Item print clicked")
            }
            android.R.id.home -> {
                finish()
                overridePendingTransition(R.anim.push_right_out, R.anim.push_right_in)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun makeRequest() {
        ActivityCompat.requestPermissions(this,
            arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
            RECORD_REQUEST_CODE)

        ActivityCompat.requestPermissions(this,
            arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
            RECORD_REQUEST_CODE_READ)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when (requestCode) {
            RECORD_REQUEST_CODE -> {
                if (grantResults.isEmpty() || grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    Log.i(TAG, "Permission has been denied by user")
                } else {
                    Log.i(TAG, "Permission has been granted by user")
                }
            }
            RECORD_REQUEST_CODE_READ ->{
                if (grantResults.isEmpty() || grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    Log.i(TAG, "Permission has been denied by user")
                } else {
                    Log.i(TAG, "Permission has been granted by user")
                }
            }
        }
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
