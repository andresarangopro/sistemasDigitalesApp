package com.example.sistdigitales.Activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.sistdigitales.R
import android.view.MenuItem


class WarningsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.example.sistdigitales.R.layout.activity_warnings)
        val  actionBr = supportActionBar
        actionBr!!.title ="ALERTAS"
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.getItemId()) {
            android.R.id.home -> {
                finish()
                overridePendingTransition(R.anim.push_right_out, R.anim.push_right_in)
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
