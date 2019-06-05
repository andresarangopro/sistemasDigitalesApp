package com.example.sistdigitales.Util

import android.icu.text.SimpleDateFormat
import android.os.Build
import android.support.annotation.RequiresApi
import android.util.Log
import com.example.sistdigitales.Models.Sensor
import com.example.sistdigitales.Models.SensorI

@RequiresApi(Build.VERSION_CODES.N)
fun bubbleOrderingList(vectorToOrder:ArrayList<Any>):ArrayList<Any>{
    var dataAux:Any
    for(i in 2..vectorToOrder.size-1){
        for(j in 0..vectorToOrder.size-i){
            var sensorObjj = vectorToOrder[j] as SensorI
            var sensorObjjOne = vectorToOrder[j+1] as SensorI
            val date1 = SimpleDateFormat("dd/MM/yyyy/HH:mm:ss").parse(sensorObjj.fecha)
            val date2 = SimpleDateFormat("dd/MM/yyyy/HH:mm:ss").parse(sensorObjjOne.fecha)
            if(date1.compareTo(date2) < 0){
                dataAux = vectorToOrder[j]
                vectorToOrder[j] = vectorToOrder[j+1]
                vectorToOrder[j+1] = dataAux
            }
        }
    }

    return vectorToOrder
}

