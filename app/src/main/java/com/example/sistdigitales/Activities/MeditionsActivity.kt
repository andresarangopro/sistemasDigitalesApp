package com.example.sistdigitales.Activities

import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.MenuItem
import com.example.sistdigitales.*
import com.example.sistdigitales.Adapters.AdapterButton
import com.example.sistdigitales.Models.*
import com.example.sistdigitales.Util.bubbleOrderingList
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.content_adapter_generi_gauge.*

class MeditionsActivity : AppCompatActivity() {

    var buttonStyleList: ArrayList<Any>? = null
    var buttonStyleAdapter: AdapterButton? = null
    var layoutManager: RecyclerView.LayoutManager? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_meditions)
        val  actionBr = supportActionBar
        actionBr!!.title ="ÚLTIMA MEDICIÓN"
        getFirebaseValues()
    }

    @RequiresApi(Build.VERSION_CODES.N)
    fun adapterOrganized(){
        if(buttonStyleList!=null){
            buttonStyleAdapter = AdapterButton(buttonStyleList!!, this, R.layout.row_meditors_generic)
            layoutManager = LinearLayoutManager(this)

            rvGenericGauge.layoutManager = layoutManager
            rvGenericGauge.adapter = buttonStyleAdapter

            buttonStyleAdapter!!.notifyDataSetChanged()
        }
    }

    fun cutListOnlyfirst(list: ArrayList<Any>?):ArrayList<Any>{
        return arrayListOf<Any>(list!!.get(0),list!!.get(1),list!!.get(2),list!!.get(3))
    }

    fun getFirebaseValues(){
        var firebaseDatabase = FirebaseDatabase.getInstance()
        var databaseReference = firebaseDatabase.reference

        databaseReference.addValueEventListener(object :ValueEventListener{

            @RequiresApi(Build.VERSION_CODES.N)
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                buttonStyleList = ArrayList<Any>()
                getModulos(dataSnapshot)
            }
            override fun onCancelled(error: DatabaseError) {

            }

        })
    }

    @RequiresApi(Build.VERSION_CODES.N)
    fun getModulos(dataFromFirebase: DataSnapshot){

        getListObjects(getData(dataFromFirebase, MODULO_TEMPERATURA), MODULO_TEMPERATURA)
        getListObjects(getData(dataFromFirebase, MODULO_HUMEDAD) , MODULO_HUMEDAD)
        getListObjects(getData(dataFromFirebase, MODULO_LUZ), MODULO_LUZ)
        getListObjects(getData(dataFromFirebase, MODULO_LLUVIA), MODULO_LLUVIA)
        buttonStyleList = bubbleOrderingList(buttonStyleList!!)
        buttonStyleList = cutListOnlyfirst(buttonStyleList!!)
        adapterOrganized()
    }

    fun getListObjects(dataMap:HashMap<String, Any>, modulo:String){
        var Sensor:Any?=null

        for(k in dataMap.keys){
            var data = dataMap.get(k) as HashMap<String, Any>
            when(modulo){
                MODULO_TEMPERATURA ->{Sensor = SensorTemperatura("","", TEMPERATURA, yelow_md_50, md_red_900)
                    Sensor.getValuesFromHashMap(data)}
                MODULO_HUMEDAD ->{Sensor = SensorHumedad("","", HUMEDAD, md_blue_100, md_blue_900)
                    Sensor.getValuesFromHashMap(data)}
                MODULO_LUZ ->{Sensor = SensorLuz("","", PORCENTAJELUZ,  md_blue_grey_400, md_black_1000)
                    Sensor.getValuesFromHashMap(data)}
                MODULO_LLUVIA ->{Sensor = SensorLluvia("","", TIEMPO)
                    Sensor.getValuesFromHashMap(data) }

            }
            buttonStyleList!!.add(Sensor!!)
        }
    }

    fun pickLastData(hashMapToCompare:HashMap<String, Any>):HashMap<String, Any>{
        for(medition in hashMapToCompare.keys){
            var medOut = hashMapToCompare.get(medition) as HashMap<String, Any>
            Log.d("val", medOut.get("fecha").toString())
            for(meditionIn in hashMapToCompare.keys){

                var medIn = hashMapToCompare.get(meditionIn) as HashMap<String, Any>

            }
        }
        return hashMapToCompare
    }

    fun getData(dataFromFirebase: DataSnapshot, modulo:String):HashMap<String, Any>{
        var valHashMap = dataFromFirebase!!.value as HashMap<String, Any>
        var moduloReturn = valHashMap.get(modulo) as  HashMap<String, Any>
       return moduloReturn!!
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.getItemId()) {
            android.R.id.home -> {
                finish()
                overridePendingTransition(R.anim.push_right_out, R.anim.push_right_in)
            }
        }
        return super.onOptionsItemSelected(item)
    }

}
