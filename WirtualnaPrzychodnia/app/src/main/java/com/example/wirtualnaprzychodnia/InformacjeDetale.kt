package com.example.wirtualnaprzychodnia

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class InformacjeDetale : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_informacje_detale)

        var textView2 = findViewById<TextView>(R.id.textView2)
        var textView3 = findViewById<TextView>(R.id.textView3)

        val inf_nazwa = intent.getStringExtra("inf_nazwa").toString()
        textView2.setText(inf_nazwa)
        val inf_opis = intent.getStringExtra("inf_opis").toString()
        textView3.setText(inf_opis)
    }
}