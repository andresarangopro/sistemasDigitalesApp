package com.example.sistdigitales.Activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.Menu
import android.view.View
import android.widget.Adapter
import android.widget.Button
import com.example.sistdigitales.Adapters.AdapterButton
import com.example.sistdigitales.Models.ButtonStyle
import com.example.sistdigitales.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.button_image.*
import kotlinx.android.synthetic.main.button_records.*

class MainActivity : AppCompatActivity(), View.OnClickListener {


    override fun onClick(v: View?) {
        when(v!!.id){
            R.id.btnMeditions ->{startActivity(Intent(this, MeditionsActivity::class.java))}
            R.id.btnRecords ->{startActivity(Intent(this, RecordsActivity::class.java))}
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
    }

    fun init(){

        var ivBtnStadistics = ivBtn
        var tvStadistics = tvButtonIv

        var ivBtnRecords = ivRecord
        var tvRecords = tvButtonRecord

        tvStadistics.text = "ÚLTIMA MEDICIÓN"
        tvRecords.text = "HISTORIAL"

        Picasso.get().load(R.mipmap.ic_stadistics).into(ivBtnStadistics);
        Picasso.get().load(R.mipmap.ic_clipboard).into(ivBtnRecords);
    }




}
