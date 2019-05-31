package com.example.sistdigitales.Util

import android.app.ProgressDialog
import android.content.Context
import android.os.Environment
import com.example.sistdigitales.*
import com.example.sistdigitales.Models.*
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.io.*
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.support.annotation.RequiresApi
import android.util.Log
import java.net.URLConnection
import java.net.URLConnection.guessContentTypeFromName



class HandlingExcel{
    var buttonStyleList: ArrayList<Any>? = null
    var context:Context?=null
    var progressDialog: ProgressDialog? = null

    constructor(context: Context){
        this.context = context
    }

    @RequiresApi(Build.VERSION_CODES.N)
    fun writeFile(){

        progressDialog!!.setMessage("Guardando...")
        var nameDocument = "${System.currentTimeMillis()}_medida.csv"
        File("${Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS )}",nameDocument).printWriter().use { out ->
            out.println("FECHA; HORA;  MODULO TEMPERATURA; MODULO HUMEDAD; MODULO LUZ; MODULO LLUVIA ")
            var posToDiv = buttonStyleList!!.size/4
            buttonStyleList = bubbleOrderingList(buttonStyleList!!)
            var acum = 0
            for(sensor in 0..posToDiv-1){
                when(buttonStyleList!!.get(sensor)){
                    is SensorI ->{
                        var sensorTemperatura:SensorI = buttonStyleList!!.get(acum) as SensorI;   acum++
                        var sensorHumedad:SensorI = buttonStyleList!!.get(acum) as SensorI;   acum++
                        var sensorPorcentLuz:SensorI = buttonStyleList!!.get(acum) as SensorI;   acum++
                        var sensorTiempo:SensorI = buttonStyleList!!.get(acum) as SensorI; acum++
                        out.println("${getFecha(sensorTemperatura.fecha)}; ${getHour(sensorTemperatura.fecha)};${sensorTemperatura.valor};" +
                                " ${sensorHumedad.valor} ; ${sensorPorcentLuz.valor};${if(sensorTiempo.valor.toFloat().toInt() == 0)"NO LLOVIO" else "LLOVIO"}")
                    }
                }
            }
        }


       // openFolderR("${Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)}")
        shareFile(nameDocument)
    }

    fun getFecha(date:String):String{
        var dateSplit = date.split("/")
        return "${dateSplit.get(0)}/${dateSplit.get(1)}/${dateSplit.get(2)}"
    }

    fun getHour(hour:String):String{
        var hourSplit = hour.split("/")
        return "${hourSplit.get(3)}"
    }

    fun getFirebaseValues(){
        progressDialog = ProgressDialog(context)
        progressDialog!!.setMessage("Generando...")
        progressDialog!!.show()
        var firebaseDatabase = FirebaseDatabase.getInstance()
        var databaseReference = firebaseDatabase.reference

        databaseReference.addListenerForSingleValueEvent(object : ValueEventListener {

            @RequiresApi(Build.VERSION_CODES.N)
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                getModulos(dataSnapshot)
            }
            override fun onCancelled(error: DatabaseError) {

            }

        })
    }

    @RequiresApi(Build.VERSION_CODES.N)
    fun getModulos(dataFromFirebase: DataSnapshot){
        buttonStyleList = ArrayList()
        getListObjects(getData(dataFromFirebase, MODULO_TEMPERATURA), TEMPERATURA)
        getListObjects(getData(dataFromFirebase, MODULO_HUMEDAD) , HUMEDAD)
        getListObjects(getData(dataFromFirebase, MODULO_LUZ), PORCENTAJELUZ)
        getListObjects(getData(dataFromFirebase, MODULO_LLUVIA), TIEMPO)
        writeFile()

    }

    fun getListObjects(dataMap:HashMap<String, Any>, modulo:String){
        var Sensor:Any?=null

        for(k in dataMap.keys){
            var data = dataMap.get(k) as HashMap<String, Any>

            when(modulo){
                TEMPERATURA->{
                        Sensor = SensorTemperatura("","", TEMPERATURA, yelow_md_50,md_red_900)
                        Sensor.getValuesFromHashMap(data)
                        buttonStyleList!!.add(Sensor!!)
                }
                HUMEDAD->{
                        Sensor = SensorHumedad("", "", HUMEDAD, md_blue_100, md_blue_900)
                        Sensor.getValuesFromHashMap(data)
                        buttonStyleList!!.add(Sensor!!)

                }
                PORCENTAJELUZ->{
                        Sensor = SensorLuz("", "", PORCENTAJELUZ, md_blue_grey_400, md_black_1000)
                        Sensor.getValuesFromHashMap(data)
                        buttonStyleList!!.add(Sensor!!)
                }
                TIEMPO-> {
                        Sensor = SensorLluvia("", "", TIEMPO)
                        Sensor.getValuesFromHashMap(data)
                        buttonStyleList!!.add(Sensor!!)

                }

            }

        }
    }

    fun openFolderR(location:String) {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        val uri = Uri.parse("${location }")//Environment.getExternalStorageDirectory().path+"/Download" )
        intent.setDataAndType(uri, "*/*")
        context!!.startActivity((intent))
    }
    private fun shareFile(location: String) {
        progressDialog!!.setMessage("Listo para compartir")

        val intentShareFile = Intent(Intent.ACTION_SEND)
        var file = File("${Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)}/${location}")
        var newName = location.substring(0, location.indexOf("."))

        intentShareFile.type = URLConnection.guessContentTypeFromName("${newName}.csv")
        intentShareFile.putExtra(
            Intent.EXTRA_STREAM,
            Uri.parse("file://" + file.absolutePath)
        )
        //if you need
        //intentShareFile.putExtra(Intent.EXTRA_SUBJECT,"Sharing File Subject);
        //intentShareFile.putExtra(Intent.EXTRA_TEXT, "Sharing File Description");
        progressDialog!!.dismiss()
        context!!.startActivity(Intent.createChooser(intentShareFile, "Share File"))

    }

    fun openFolder(location:String){
          val data:Uri = Uri.parse(location)
          context!!.grantUriPermission(context!!.packageName, data, Intent.FLAG_GRANT_READ_URI_PERMISSION);
          var intent:Intent = Intent(Intent.ACTION_VIEW)
            .setDataAndType(data, "*/*")
            .addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
          context!!.startActivity(intent);
    }

    fun open(){
        val selectedUri = Uri.parse("${Environment.getExternalStorageDirectory() }")
        val intent = Intent(Intent.ACTION_VIEW)
        intent.setDataAndType(selectedUri, "resource/folder")

        if (intent.resolveActivityInfo(context!!.packageManager, 0) != null) {
            context!!.startActivity(intent)
        }
    }

    fun getData(dataFromFirebase: DataSnapshot, modulo:String):HashMap<String, Any>{
        var valHashMap = dataFromFirebase!!.value as HashMap<String, Any>
        var moduloReturn = valHashMap.get(modulo) as  HashMap<String, Any>
        return moduloReturn!!
    }
}

