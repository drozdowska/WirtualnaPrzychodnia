package com.example.wirtualnaprzychodnia

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ServerValue.TIMESTAMP
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firestore.v1.DocumentTransform
import java.sql.Timestamp
import java.sql.Types.TIMESTAMP
import java.time.LocalDate
import java.time.LocalDateTime

class ForumDodawanieTematu : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forum_dodawanie_tematu)



        var tytul = findViewById<EditText>(R.id.tytul)
        var tresc = findViewById<EditText>(R.id.tresc)
        var wczytaj = findViewById<Button>(R.id.dodaj)

        wczytaj.setOnClickListener (View.OnClickListener {
            if (TextUtils.isEmpty(tytul!!.text.toString().trim())) {
                tytul!!.setError(getString(R.string.puste_pole))
                tytul!!.requestFocus()
                return@OnClickListener
            }
            if (TextUtils.isEmpty(tresc!!.text.toString().trim())) {
                tresc!!.setError(getString(R.string.puste_pole))
                tresc!!.requestFocus()
                return@OnClickListener
            }
            var auth = FirebaseAuth.getInstance()
            val db = FirebaseFirestore.getInstance()
            val nowy: MutableMap<String, Any> = HashMap()
            nowy["tytul"] = tytul.text.toString()
            nowy["tresc"] = tresc.text.toString()
            nowy["autor"] = auth.currentUser!!.uid
            nowy["data"] = LocalDateTime.now().toString()



            db.collection("forum")
                    .add(nowy)
                    .addOnSuccessListener {
                        Toast.makeText(applicationContext, "Dodanie wątku przebiegło pomyślnie", Toast.LENGTH_SHORT ).show()
                    }
                    .addOnFailureListener{
                        Toast.makeText(applicationContext, "Błąd dodania wątku", Toast.LENGTH_SHORT ).show()
                    }



                val start_forum = Intent(this, TematyForum::class.java)
                startActivity(start_forum)




        })

    }

}