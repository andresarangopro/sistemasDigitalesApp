package com.example.sistdigitales.Activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import com.example.sistdigitales.*
import com.example.sistdigitales.Adapters.AdapterButton
import com.example.sistdigitales.Models.*
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

        getFirebaseValues()
    }

    fun adapterOrganized(){
        //buttonStyleList = ArrayList<Any>()

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

        databaseReference.addValueEventListener(object :ValueEventListener{

            override fun onDataChange(dataSnapshot: DataSnapshot) {

                buttonStyleList = ArrayList<Any>()
                getModulos(dataSnapshot)
            }
            override fun onCancelled(error: DatabaseError) {

            }

        })
    }

    fun getModulos(dataFromFirebase: DataSnapshot){
        getListObjects(getData(dataFromFirebase, MODULO_TEMPERATURA), TEMPERATURA)
        getListObjects(getData(dataFromFirebase, MODULO_HUMEDAD) , HUMEDAD)
        getListObjects(getData(dataFromFirebase, MODULO_LUZ), PORCENTAJELUZ)

        getListObjects(getData(dataFromFirebase, MODULO_LLUVIA), TIEMPO)
        adapterOrganized()
    }

    fun getListObjects(dataMap:HashMap<String, Any>, modulo:String){
        var Sensor:Any?=null
        var isLate_uno= false
        var isLate_dos= false
        var isLate_tres= false
        var isLate_cuatro= false
        for(k in dataMap.keys){
            var data = dataMap.get(k) as HashMap<String, Any>

            when(modulo){
                TEMPERATURA->{
                   if(!isLate_uno){
                       Sensor = SensorTemperatura("","", TEMPERATURA, yelow_md_50,md_red_900)
                       Sensor.getValuesFromHashMap(data)
                       isLate_uno = true
                       pickLastData(dataMap)
                       buttonStyleList!!.add(Sensor!!)
                   }else{
                       Log.d("wtd", "wtf")
                   }
                }
                HUMEDAD->{
                    if(!isLate_dos) {
                        Sensor = SensorHumedad("", "", HUMEDAD, md_blue_100, md_blue_900)
                        Sensor.getValuesFromHashMap(data)
                        isLate_dos = true
                        buttonStyleList!!.add(Sensor!!)
                    }
                }
                PORCENTAJELUZ->{
                    if(!isLate_tres) {
                        Sensor = SensorLuz("", "", PORCENTAJELUZ, md_blue_grey_400, md_black_1000)
                        Sensor.getValuesFromHashMap(data)
                        isLate_tres = true
                        buttonStyleList!!.add(Sensor!!)
                    }
                }
                TIEMPO-> {
                    if (!isLate_cuatro) {
                        Sensor = SensorLluvia("", "", TIEMPO)
                        Sensor.getValuesFromHashMap(data)
                        isLate_cuatro = true
                        buttonStyleList!!.add(Sensor!!)
                    }
                }

            }

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




}
