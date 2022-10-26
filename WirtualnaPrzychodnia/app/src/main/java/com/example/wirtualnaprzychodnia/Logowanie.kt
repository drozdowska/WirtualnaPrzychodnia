package com.example.wirtualnaprzychodnia

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth

class Logowanie : AppCompatActivity() {
    private var auth: FirebaseAuth? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_logowanie)

        /* Obsługa przycisku rejestracja */
        var rejestracja = findViewById<Button>(R.id.rejestracja)
        rejestracja.setOnClickListener {
            val start_rejestracja = Intent(this, Rejestracja::class.java)
            startActivity(start_rejestracja)
        }


        var login = findViewById<Button>(R.id.login)
        var email = findViewById(R.id.email) as EditText
        var haslo = findViewById(R.id.haslo) as EditText

        auth = FirebaseAuth.getInstance()
        /* Obsługa przycisku logowanie */
        login!!.setOnClickListener(View.OnClickListener {
                var email_tekst = email!!.text.toString().trim()
                var haslo_tekst = haslo!!.text.toString().trim()

                 /* Sprawdzanie, czy pole email nie jest puste */
                if (TextUtils.isEmpty(email!!.text.toString().trim())) {
                    email!!.setError(getString(R.string.rejestracja_puste_email))
                    email!!.requestFocus()
                    return@OnClickListener

                }
                 /* Sprawdzanie, czy pole hasło nie jest puste */
                if (TextUtils.isEmpty(haslo!!.text.toString().trim())) {
                    haslo!!.setError(getString(R.string.rejestracja_puste_haslo))
                    haslo!!.requestFocus()
                    return@OnClickListener
                }
                /* Próba zalogowania */
                auth!!.signInWithEmailAndPassword(email_tekst, haslo_tekst)
                        .addOnCompleteListener(this, OnCompleteListener { task ->

                            /* Jeżeli logowanie zakończy się błędnie */
                            if (!task.isSuccessful){
                                Toast.makeText(applicationContext, "Błędne dane logowania", Toast.LENGTH_SHORT)
                                        .show()

                            }else{
                                /* Jeżeli logowanie zakończy się pomyślnie */
                                startActivity(Intent(this@Logowanie, com.example.wirtualnaprzychodnia.Menu::class.java))
                                finish()
                                Toast.makeText(applicationContext, "Zalogowano pomyślnie", Toast.LENGTH_SHORT)
                                        .show()
                            }
                        })


        })
    }
    /* Wyłączenie możliwości cofania się */
    override fun onBackPressed() {
    }

}