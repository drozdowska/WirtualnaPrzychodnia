package com.example.wirtualnaprzychodnia

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.google.firebase.auth.FirebaseAuth

class Menu : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        var informacje = findViewById<Button>(R.id.informacje)
        informacje.setOnClickListener {
            val start_informacje = Intent(this, Informacje::class.java)
            startActivity(start_informacje)
        }
        var forum = findViewById<Button>(R.id.forum)
        forum.setOnClickListener {
            val start_forum = Intent(this, TematyForum::class.java)
            startActivity(start_forum)
        }
        var wyloguj = findViewById<Button>(R.id.wyloguj)
        wyloguj.setOnClickListener {
            FirebaseAuth.getInstance().signOut();
            val start_wyloguj = Intent(this, MainActivity::class.java)
            startActivity(start_wyloguj)
        }
        var profil = findViewById<Button>(R.id.profil)
        profil.setOnClickListener {
            val start_profil = Intent(this, Profil::class.java)
            startActivity(start_profil)
        }
        var zapisy = findViewById<Button>(R.id.zapisy)
        zapisy.setOnClickListener {
            val start_zapisy = Intent(this, Zapisy::class.java)
            startActivity(start_zapisy)
        }
    }
}

/*
* Tło https://pl.fotoomnia.com/photo/Blekitne-Tlo-Gradientowe-z-Fioletem-7841
*
*
*
* */