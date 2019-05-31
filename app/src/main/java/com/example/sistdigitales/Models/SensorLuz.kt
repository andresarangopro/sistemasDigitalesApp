package com.example.sistdigitales.Models

import android.util.Log
import com.example.sistdigitales.PORCENTAJELUZ
import java.sql.Timestamp
import java.util.*
import kotlin.collections.HashMap

class SensorLuz(override var valor: String,
                override var fecha: String,
                override var name: String,
                override var startColor: Int,
                override var endColor: Int
) :SensorI {
    override fun getPorLuz(): Int {
        var value = this.valor.toDouble()
        value = (value*100)/20000
        Log.d("val", "${value.toInt()}")
        return value.toInt()
    }

    override var modulo: String
        get() = PORCENTAJELUZ
        set(value) {PORCENTAJELUZ}

    override fun timeStampToDate(): String {
        val ts = Timestamp(this.fecha!!.toLong());
        val date = Date(ts.time)
        return date.toString()
    }

    override fun getHourFromTimeStamp(): String {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getValuesFromHashMap(mapValues: HashMap<String, Any>) {
        this.fecha = mapValues.get("fecha").toString()
        this.valor = mapValues.get("value").toString()
    }


}