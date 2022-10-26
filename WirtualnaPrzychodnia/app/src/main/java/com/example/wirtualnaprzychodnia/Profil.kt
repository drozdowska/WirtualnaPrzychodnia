package com.example.wirtualnaprzychodnia

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class Profil : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profil)

        var imie = findViewById<TextView>(R.id.imie)
        var nazwisko = findViewById<TextView>(R.id.nazwisko)
        var nick = findViewById<TextView>(R.id.nick)
        var telefon = findViewById<TextView>(R.id.telefon)
        var email = findViewById<TextView>(R.id.email)


        var database = FirebaseDatabase.getInstance().getReference("uzytkownicy")
        val uzytkownik= FirebaseAuth.getInstance().currentUser
        var uid=uzytkownik!!.uid


        database!!.child(uid!!).addValueEventListener(object: ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val uzytkownik = dataSnapshot.getValue(Uzytkownik::class.java)
                imie.text = uzytkownik?.imie
                nazwisko.text = uzytkownik?.nazwisko
                nick.text =  uzytkownik?.nick
                telefon.text =  uzytkownik?.telefon
                email.text =  uzytkownik?.email

            }

            override fun onCancelled(error: DatabaseError) {

            }
        })

        var edytuj = findViewById<Button>(R.id.edytuj)
        edytuj.setOnClickListener {
            var start_edytuj = Intent(this, EdycjaProfilu::class.java)
            startActivity(start_edytuj)
        }
        var menu =findViewById<Button>(R.id.powrot_menu)
        menu.setOnClickListener {
            var start_menu = Intent(this, Menu::class.java)
            startActivity(start_menu)
        }


    }
}