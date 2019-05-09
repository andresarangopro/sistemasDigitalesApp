package com.example.sistdigitales.Models

interface SensorI {

    var fecha:String
    var valor:String
    var name:String
    var startColor:Int
    var endColor:Int

    fun getValuesFromHashMap(mapValues:HashMap<String, Any>)
    fun timeStampToDate():String
    fun getHourFromTimeStamp():String
}