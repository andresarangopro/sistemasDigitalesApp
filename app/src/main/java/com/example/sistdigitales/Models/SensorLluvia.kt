package com.example.sistdigitales.Models

import java.sql.Timestamp
import java.util.*
import kotlin.collections.HashMap

class SensorLluvia:Sensor {

    constructor(valor:String, fecha:String,name:String){
        this.valor = valor
        this.fecha = fecha
        this.name = name
    }
    fun getValuesFromHashMap(mapValues: HashMap<String, Any>) {
        this.fecha = mapValues.get("fecha").toString()
        this.valor = mapValues.get("value").toString()
    }
    fun timeStampToDate(): String {
        val ts = Timestamp(this.fecha!!.toLong());
        val date = Date(ts.time)
        return date.toString()
    }
    fun getHourFromTimeStamp() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


}