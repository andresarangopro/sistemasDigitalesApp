package com.example.sistdigitales.Models

import com.example.sistdigitales.HUMEDAD
import com.example.sistdigitales.TIEMPO
import java.sql.Timestamp
import java.util.*
import kotlin.collections.HashMap

class SensorLluvia(
    override var fecha: String,
    override var valor: String,
    override var name: String
) :SensorI {
    override fun getPorLuz(): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getValuesFromHashMap(mapValues: HashMap<String, Any>) {
        this.fecha = mapValues.get("fecha").toString()
        this.valor = mapValues.get("value").toString()
    }

    override fun timeStampToDate(): String {
        val ts = Timestamp(this.fecha!!.toLong());
        val date = Date(ts.time)
        return date.toString()
    }

    override fun getHourFromTimeStamp(): String {
        TODO("not implemented")
    }

    override var modulo: String
        get() = TIEMPO
        set(value) {TIEMPO}
    override var startColor: Int
        get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.
        set(value) {}
    override var endColor: Int
        get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.
        set(value) {}




}