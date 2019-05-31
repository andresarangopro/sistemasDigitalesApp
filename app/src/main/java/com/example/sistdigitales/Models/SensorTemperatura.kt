package com.example.sistdigitales.Models

import com.example.sistdigitales.TEMPERATURA
import java.sql.Timestamp
import java.util.*
import kotlin.collections.HashMap

class SensorTemperatura(override var valor: String,
                        override var fecha: String,
                        override var name: String,
                        override var startColor: Int,
                        override var endColor: Int
) :SensorI {
    override fun getPorLuz(): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override var modulo: String
        get() = TEMPERATURA
        set(value) {TEMPERATURA}

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