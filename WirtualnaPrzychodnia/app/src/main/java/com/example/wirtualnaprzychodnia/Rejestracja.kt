package com.example.wirtualnaprzychodnia

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.ktx.Firebase

class Rejestracja : AppCompatActivity() {

    private var email: EditText? = null
    private var haslo: EditText? = null
    private var haslo2: EditText? = null
    private var imie: EditText? = null
    private var nazwisko: EditText? = null
    private var telefon: EditText? = null
    private var nick: EditText? = null
    private var rejestracja: Button? = null
    private var auth: FirebaseAuth? = null
    private var database: DatabaseReference?=null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rejestracja)

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance().reference

        rejestracja = findViewById(R.id.Rejestracja) as Button
        email = findViewById(R.id.Email) as EditText
        haslo = findViewById(R.id.Haslo) as EditText
        imie = findViewById(R.id.Imie) as EditText
        nazwisko = findViewById(R.id.Nazwisko) as EditText
        telefon = findViewById(R.id.Telefon) as EditText
        haslo2 = findViewById(R.id.Haslo2) as EditText
        nick = findViewById(R.id.Nick) as EditText


        var logowanie = findViewById<Button>(R.id.Logowanie)
        logowanie.setOnClickListener {
            val start_logowanie = Intent(this, Logowanie::class.java)
            startActivity(start_logowanie)
        }


        /* Walidacja danych */

        /* Sprawdzanie czy pole imię nie jest puste */
        rejestracja!!.setOnClickListener(View.OnClickListener {
            if (TextUtils.isEmpty(imie!!.text.toString().trim())) {
                imie!!.setError(getString(R.string.rejestracja_puste_imie))
                imie!!.requestFocus()
                return@OnClickListener
            }
            /* Sprawdzanie czy pole imię nie zawiera błędnych znaków */
            if (!"[A-ZĄĆĘŁŃÓŚŹŻ]{1}[a-ząćęłńóśźż]+".toRegex().matches(imie!!.text.toString().trim())
                && (!"[A-ZĄĆĘŁŃÓŚŹŻ]{1}[a-ząćęłńóśźż]+[-]{1}[A-ZĄĆĘŁŃÓŚŹŻ]{1}[a-ząćęłńóśźż]+".toRegex().matches(imie!!.text.toString().trim()))) {
                imie!!.setError(getString(R.string.rejestracja_bledne_imie))
                imie!!.requestFocus()
                return@OnClickListener
            }
            /* Sprawdzanie czy pole nazwisko nie jest puste */

            if (TextUtils.isEmpty(nazwisko!!.text.toString().trim())) {
                nazwisko!!.setError(getString(R.string.rejestracja_puste_nazwisko))
                nazwisko!!.requestFocus()
                return@OnClickListener
            }
            /* Sprawdzanie czy pole nazwisko nie zawiera żadnych błędów
            * Zgodnie z zasadami nazwiska mogą być maksymalnie dwuczłonowe oraz pisane łącznikiem
            * */
            if (!"[A-ZĄĆĘŁŃÓŚŹŻ]{1}[a-ząćęłńóśźż]+".toRegex().matches(nazwisko!!.text.toString().trim())
                && (!"[A-ZĄĆĘŁŃÓŚŹŻ]{1}[a-ząćęłńóśźż]+[-]{1}[A-ZĄĆĘŁŃÓŚŹŻ]{1}[a-ząćęłńóśźż]+".toRegex().matches(nazwisko!!.text.toString().trim()))
                && (!"[A-ZĄĆĘŁŃÓŚŹŻ]{1}[a-ząćęłńóśźż]+[ ]{1}[A-ZĄĆĘŁŃÓŚŹŻ]{1}[a-ząćęłńóśźż]+".toRegex().matches(nazwisko!!.text.toString().trim()))) {
                nazwisko!!.setError(getString(R.string.rejestracja_bledne_nazwisko))
                nazwisko!!.requestFocus()
                return@OnClickListener
            }
            /* Sprawdzanie czy pole email nie jest puste */
            if (TextUtils.isEmpty(email!!.text.toString().trim())) {
                email!!.setError(getString(R.string.rejestracja_puste_email))
                email!!.requestFocus()
                return@OnClickListener
            }
            /* Sprawdzanie czy pole email nie zawiera żadnych błędów */
            if (!"[a-ząćęłńóśźżA-ZĄĆĘŁŃÓŚŹŻ0-9._-]+@[a-ząćęłńóśźżA-ZĄĆĘŁŃÓŚŹŻ0-9]+\\.+[a-z]+".toRegex().matches(email!!.text.toString().trim())){
                email!!.setError(getString(R.string.rejestracja_bledne_email))
                email!!.requestFocus()
                return@OnClickListener
            }
            /* Sprawdzanie czy pole telefon nie jest puste */
            if (TextUtils.isEmpty(telefon!!.text.toString().trim())) {
                telefon!!.setError(getString(R.string.rejestracja_puste_telefon))
                telefon!!.requestFocus()
                return@OnClickListener
            }
            /* Sprawdzanie czy pole telefon nie zawiera żadnych błędów */
            if (!"[0-9]{9}".toRegex().matches(telefon!!.text.toString().trim())) {
                telefon!!.setError(getString(R.string.rejestracja_bledne_telefon))
                telefon!!.requestFocus()
                return@OnClickListener
            }
            /* Sprawdzanie czy pole nick nie jest puste */
            if (TextUtils.isEmpty(nick!!.text.toString().trim())) {
                nick!!.setError(getString(R.string.rejestracja_puste_nick))
                nick!!.requestFocus()
                return@OnClickListener
            }
            /* Sprawdzanie czy pole hasło nie jest puste */
            if (TextUtils.isEmpty(haslo!!.text.toString().trim())) {
                haslo!!.setError(getString(R.string.rejestracja_puste_haslo))
                haslo!!.requestFocus()
                return@OnClickListener
            }
            /* Sprawdzanie czy pole hasło nie zawiera żadnych błędów */
            if (!"^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?#^()])[A-Za-z\\d@$!%*?#^()]{9,}$".toRegex().matches(haslo!!.text.toString().trim())) {
                haslo!!.setError(getString(R.string.rejestracja_bledne_haslo))
                haslo!!.requestFocus()
                return@OnClickListener
            }
            /* Sprawdzanie czy pole potwierdzenie hasła nie jest puste */
            if (TextUtils.isEmpty(haslo2!!.text.toString().trim())) {
                haslo2!!.setError(getString(R.string.rejestracja_puste_haslo))
                haslo2!!.requestFocus()
                return@OnClickListener
            }
            /* Sprawdzanie czy pola potwierdzenie hasła i hasło nie różnią się */
            if (!haslo2!!.text.toString().trim().equals(haslo!!.text.toString().trim())) {
                haslo2!!.setError(getString(R.string.rejestracja_bledne_haslo2))
                haslo2!!.requestFocus()
                return@OnClickListener
            }
            /* Łączenie się z bazą danych Firebase */
            var Firebase_nick = FirebaseDatabase.getInstance().getReference().child("nick").child(nick!!.text.toString().trim())
            var valueEventListener = Firebase_nick.addValueEventListener(object : ValueEventListener {
                @Override
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    /* Sprawdzanie, czy nick już istnieje w bazie */
                    if (dataSnapshot.exists()) {

                        nick!!.setError(getString(R.string.rejestracja_bledne_nick))
                        nick!!.requestFocus()


                    } else {
                        /* Zakładanie konta oraz dodawanie danych do bazy */
                        auth!!.createUserWithEmailAndPassword(email!!.text.toString().trim(), haslo!!.text.toString().trim()).addOnCompleteListener(this@Rejestracja, OnCompleteListener { task ->

                            if (!task.isSuccessful) {
                                Toast.makeText(this@Rejestracja, getString(R.string.zalozenie_nie_powiodlo_sie), Toast.LENGTH_SHORT).show()
                                return@OnCompleteListener
                            } else {
                                val uzytkownik = Uzytkownik(imie!!.text.toString().trim(), nazwisko!!.text.toString().trim(), email!!.text.toString().trim(), telefon!!.text.toString().trim(), nick!!.text.toString().trim())
                                database!!.child("uzytkownicy").child(auth!!.currentUser!!.uid).setValue(uzytkownik)
                                database!!.child("nick").child(nick!!.text.toString().trim()).setValue(nick!!.text.toString().trim())
                                startActivity(Intent(this@Rejestracja, MainActivity::class.java))
                                Toast.makeText(this@Rejestracja, getString(R.string.zalozenie_powiodlo_sie), Toast.LENGTH_SHORT).show()
                                finish()
                            }

                        })
                    }
                }

                @Override
                override fun onCancelled(databaseError: DatabaseError) {
                }
            })
            Firebase_nick.addListenerForSingleValueEvent(valueEventListener)

        })


    }
    override fun onResume() {
        super.onResume()

    }

}