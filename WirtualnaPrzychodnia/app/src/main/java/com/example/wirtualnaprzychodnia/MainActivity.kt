package com.example.wirtualnaprzychodnia

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var start = findViewById<Button>(R.id.logowanie)
        /* Obsługa przycisku start, przechodzenie do logowania lub menu */
        start.setOnClickListener {


                val user = FirebaseAuth.getInstance().currentUser
                if (user != null) {
                    val intent = Intent(this, Menu::class.java)
                    startActivity(intent)
                } else {
                    val start_logowanie = Intent(this, Logowanie::class.java)
                    startActivity(start_logowanie)
                }




        }



    }

    override fun onBackPressed() {

    }
}
/*
* Tło https://pl.freepik.com/darmowe-wektory/cardio-bicie-serca-tlo-medyczne_4249612.htm
*
*
*
* */