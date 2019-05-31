package com.example.sistdigitales.Activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.sistdigitales.R

class WarningsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_warnings)
        val  actionBr = supportActionBar
        actionBr!!.title ="ALERTAS"
    }
}
