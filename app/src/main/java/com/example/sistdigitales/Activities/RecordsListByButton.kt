package com.example.sistdigitales.Activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.example.sistdigitales.*
import com.example.sistdigitales.Adapters.AdapterButton
import com.example.sistdigitales.Models.*
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.content_adapter_generi_gauge.*

class RecordsListByButton : AppCompatActivity() {

    var buttonStyleList: ArrayList<Any>? = null
    var buttonStyleAdapter: AdapterButton? = null
    var layoutManager: RecyclerView.LayoutManager? = null
    var moduloExtras:String?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_record_temperature)
        var extras = intent.extras
        moduloExtras = extras.get("modulo").toString()
        getFirebaseValues()
    }

    fun adapterOrganized(){

        buttonStyleAdapter = AdapterButton(buttonStyleList!!, this, R.layout.row_meditors_generic)
        layoutManager = LinearLayoutManager(this)

        ///
        rvGenericGauge.layoutManager = layoutManager
        rvGenericGauge.adapter = buttonStyleAdapter

        buttonStyleAdapter!!.notifyDataSetChanged()
    }


    fun getFirebaseValues(){
        var firebaseDatabase = FirebaseDatabase.getInstance()
        var databaseReference = firebaseDatabase.reference

        databaseReference.addValueEventListener(object : ValueEventListener {

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                getModulos(dataSnapshot, moduloExtras)
            }
            override fun onCancelled(error: DatabaseError) {

            }

        })
    }

    fun getModulos(dataFromFirebase: DataSnapshot, moduloToGet:String?){
        buttonStyleList = ArrayList<Any>()
        getListObjects(getData(dataFromFirebase,moduloToGet!!), moduloToGet)
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

    fun getData(dataFromFirebase: DataSnapshot, modulo:String):HashMap<String, Any>{
        var valHashMap = dataFromFirebase!!.value as HashMap<String, Any>
        var moduloReturn = valHashMap.get(modulo) as  HashMap<String, Any>
        return moduloReturn!!
    }
}
