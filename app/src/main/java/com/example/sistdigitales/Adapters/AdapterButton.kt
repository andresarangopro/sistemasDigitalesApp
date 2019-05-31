package com.example.sistdigitales.Adapters

import android.content.Context
import android.content.Intent
import android.os.Build
import android.support.annotation.RequiresApi
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import com.example.sistdigitales.*
import com.example.sistdigitales.Activities.RecordsListByButton
import com.example.sistdigitales.Models.ButtonStyle
import com.example.sistdigitales.Models.SensorI
import com.example.sistdigitales.Models.SensorLluvia
import com.example.sistdigitales.Util.orderByData
import com.squareup.picasso.Picasso
import pl.pawelkleczkowski.customgauge.CustomGauge
import java.math.RoundingMode
import java.sql.Date
import java.text.DecimalFormat
import java.time.LocalDate
import java.time.LocalDate.parse

class AdapterButton(private val listButtonstyle:ArrayList<Any>,
                    private val context:Context,private val layout:Int): RecyclerView.Adapter<AdapterButton.ViewHolder>() {
    var gauge:RelativeLayout?=null
    var ivButton:ImageView?=null
    var tvButton:TextView?=null
    var llBtnvalues:LinearLayout?=null
    var tvModGeneric:TextView? =null
    var tvFechaModHour:TextView? =null
    var tvVal:TextView? =null

    override fun onCreateViewHolder(parent: ViewGroup, position: Int): AdapterButton.ViewHolder {
       val view = LayoutInflater.from(context)
           .inflate(layout,parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
      return listButtonstyle.size
    }

    override fun onBindViewHolder(holder: AdapterButton.ViewHolder, position: Int) {
        holder.bindView(listButtonstyle[position])
    }

    inner class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView),View.OnClickListener {
        override fun onClick(v: View?) {
            v as LinearLayout
            when(v!!.id){
               R.id.btnValues->{
                   var objectToC:Any = listButtonstyle[adapterPosition]
                   when(objectToC){
                       is ButtonStyle->{openActivity(objectToC.text)}
                   }
               }
           }
        }

        fun bindView(objectToPrint:Any){
            when(objectToPrint){
                is ButtonStyle->{ buttonStyle(objectToPrint)}
                is SensorI->{

                    gaugeMeditor(objectToPrint)}
            }

        }

        fun buttonStyle(buttonStyle:ButtonStyle){
            var ivButton = itemView.findViewById<ImageView>(R.id.ivBtn)
            var tvButton = itemView.findViewById<TextView>(R.id.tvButtonIv)
            var llBtnvalues = itemView.findViewById<LinearLayout>(R.id.btnValues)

            tvButton.text = buttonStyle.text
            llBtnvalues.setOnClickListener(this)
            if(!TextUtils.isEmpty(buttonStyle.image.toString())){
                Picasso.get()
                    .load(buttonStyle.image!!)
                    .placeholder(android.R.drawable.ic_menu_report_image)
                    .error(android.R.drawable.ic_menu_report_image)
                    .into(ivButton)
            }else{
                Picasso.get().load(R.mipmap.ic_launcher).into(ivButton)
            }
        }

          fun initComponents(){
            gauge        =  itemView.findViewById<RelativeLayout>(R.id.llGauge)
            ivButton     = itemView.findViewById<ImageView>(R.id.ivBtnGeneric)
            tvButton     = itemView.findViewById<TextView>(R.id.tvFechaModHum)
            tvFechaModHour = itemView.findViewById<TextView>(R.id.tvFechaModHumH)
            llBtnvalues  = itemView.findViewById<LinearLayout>(R.id.btnValuesGeneric)
            tvModGeneric = itemView.findViewById<TextView>(R.id.tvModGeneric)
            tvVal = itemView.findViewById<TextView>(R.id.tvVal)

        }

        fun putDataLLuviaStyle(lluviaStyle:SensorI){
            initComponents()
            tvModGeneric!!.text = lluviaStyle.name
            ivButton!!.visibility = View.VISIBLE
            gauge!!.visibility = View.GONE
            tvButton!!.text  = getFecha(lluviaStyle.fecha)
            tvFechaModHour!!.text = getHour(lluviaStyle.fecha)
            llBtnvalues!!.setOnClickListener(this)
            tvVal!!.visibility = View.VISIBLE
            if(!TextUtils.isEmpty(lluviaStyle.valor.toString())){
                when(lluviaStyle.valor!!.toFloat().toInt()){
                    1->{putImage(ivButton!!, R.mipmap.ic_rain); tvVal!!.text = "LLOVIÓ"}
                    0->{putImage(ivButton!!, R.mipmap.ic_clear_sky); tvVal!!.text = "NO LLOVIÓ"}
                }
            }else{
                Picasso.get().load(R.mipmap.ic_launcher).into(ivButton)
            }
        }

        fun putImage(ivButton:ImageView, imageToPut:Int){
            Picasso.get()
                .load(imageToPut)
                .placeholder(android.R.drawable.ic_menu_report_image)
                .error(android.R.drawable.ic_menu_report_image)
                .into(ivButton)
        }


        fun gaugeMeditor(sensor: SensorI){
            if(sensor.modulo.equals(TIEMPO)){
                putDataLLuviaStyle(sensor)
            }else {
                var porcent:Int?=null
                var ivButton = itemView.findViewById<ImageView>(R.id.ivBtnGeneric)
                var gauge = itemView.findViewById<CustomGauge>(R.id.gaugeGeneric)
                var tvFechaMod = itemView.findViewById<TextView>(R.id.tvFechaModHum)
                var tvFechaModHour = itemView.findViewById<TextView>(R.id.tvFechaModHumH)
                var tvModGeneric = itemView.findViewById<TextView>(R.id.tvModGeneric)
                var tvGeneric = itemView.findViewById<TextView>(R.id.tvPorcentGeneric)
                var tvVal = itemView.findViewById<TextView>(R.id.tvVal)

                tvFechaMod.text = getFecha(sensor.fecha)
                tvFechaModHour.text = getHour(sensor.fecha)
                gauge.pointStartColor = sensor.startColor
                gauge.pointEndColor = sensor.endColor
                porcent = sensor.valor.toFloat().toInt()
                gauge.value = porcent!!
                tvGeneric.text = sensor.valor + " %"
                ivButton.visibility = View.GONE
                gauge.visibility = View.VISIBLE
                tvModGeneric.text = sensor.name
                if(sensor.modulo.equals(PORCENTAJELUZ)){
                    tvVal.visibility = View.VISIBLE
                    val df = DecimalFormat("#.##")
                    df.roundingMode = RoundingMode.CEILING
                    tvVal.text = "${sensor.valor} lumens"
                    porcent  = sensor.getPorLuz()
                    gauge.value = porcent!!
                    tvGeneric.text = "${df.format((sensor.valor.toFloat()*100)/20000)} %"
                }
                // tvPorcentTemp.text = gaugeTemp.value.toString() +" C°"
            }
        }



        fun getFecha(date:String):String{
            var dateSplit = date.split("/")
            return "${dateSplit.get(0)}/${dateSplit.get(1)}/${dateSplit.get(2)}"
        }

        fun getHour(hour:String):String{
            var hourSplit = hour.split("/")
            return "${hourSplit.get(3)}"
        }

        fun openActivity(monitor:String?){
            var recordsListByButtonActivity = Intent(context, RecordsListByButton::class.java)
            Log.d("MO",monitor!!.trim())
            when(monitor!!.trim()){
                TEMPERATURA->{
                    recordsListByButtonActivity.putExtra("modulo", MODULO_TEMPERATURA)
                    context.startActivity(recordsListByButtonActivity)
                }
                HUMEDAD->{
                    recordsListByButtonActivity.putExtra("modulo", MODULO_HUMEDAD)
                    context.startActivity(recordsListByButtonActivity)
                }
                TIEMPO->{
                    recordsListByButtonActivity.putExtra("modulo", MODULO_LLUVIA)
                    context.startActivity(recordsListByButtonActivity)
                }
                PORCENTAJELUZ->{
                    recordsListByButtonActivity.putExtra("modulo", MODULO_LUZ)
                    context.startActivity(recordsListByButtonActivity)
                }
            }
        }
    }
}

