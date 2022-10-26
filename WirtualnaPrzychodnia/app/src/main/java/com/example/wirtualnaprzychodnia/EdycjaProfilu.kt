package com.example.wirtualnaprzychodnia

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class EdycjaProfilu : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edycja_profilu)

        var edycja_imie = findViewById<EditText>(R.id.edycja_imie)
        var edycja_nazwisko = findViewById<EditText>(R.id.edycja_nazwisko)
        var edycja_telefon = findViewById<EditText>(R.id.edycja_telefon)
        var edycja_haslo = findViewById<EditText>(R.id.edycja_haslo)
        var edycja_powtorzhaslo = findViewById<EditText>(R.id.edycja_powtorzhaslo)
        var edycja_obecnehaslo = findViewById<EditText>(R.id.edycja_obecnehaslo)

        var zapisz = findViewById<Button>(R.id.zapisz)
        var powrot_profil = findViewById<Button>(R.id.powrot_profil)
        var zmiana_hasla = findViewById<Button>(R.id.zmiana_hasla)
        powrot_profil.setOnClickListener {
            var start_profil = Intent(this, Profil::class.java)
            startActivity(start_profil)
        }

        var auth = FirebaseAuth.getInstance()
        var database = FirebaseDatabase.getInstance().getReference("uzytkownicy")
        val uzytkownik= FirebaseAuth.getInstance().currentUser
        var uid=uzytkownik!!.uid
        var email="X"

        database!!.child(uid!!).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val uzytkownik = dataSnapshot.getValue(Uzytkownik::class.java)
                edycja_imie.setText(uzytkownik?.imie)
                edycja_nazwisko.setText(uzytkownik?.nazwisko)
                edycja_telefon.setText(uzytkownik?.telefon)
                email = uzytkownik!!.email


            }

            override fun onCancelled(error: DatabaseError) {

            }
        })




        zapisz!!.setOnClickListener(View.OnClickListener {
            if (TextUtils.isEmpty(edycja_imie!!.text.toString().trim())) {
                edycja_imie!!.setError(getString(R.string.rejestracja_puste_imie))
                edycja_imie!!.requestFocus()
                return@OnClickListener
            }
            /* Sprawdzanie czy pole imię nie zawiera błędnych znaków */
            if (!"[A-ZĄĆĘŁŃÓŚŹŻ]{1}[a-ząćęłńóśźż]+".toRegex().matches(
                    edycja_imie!!.text.toString().trim()
                )
                && (!"[A-ZĄĆĘŁŃÓŚŹŻ]{1}[a-ząćęłńóśźż]+[-]{1}[A-ZĄĆĘŁŃÓŚŹŻ]{1}[a-ząćęłńóśźż]+".toRegex()
                    .matches(
                        edycja_imie!!.text.toString().trim()
                    ))
            ) {
                edycja_imie!!.setError(getString(R.string.rejestracja_bledne_imie))
                edycja_imie!!.requestFocus()
                return@OnClickListener
            }
            /* Sprawdzanie czy pole nazwisko nie jest puste */

            if (TextUtils.isEmpty(edycja_nazwisko!!.text.toString().trim())) {
                edycja_nazwisko!!.setError(getString(R.string.rejestracja_puste_nazwisko))
                edycja_nazwisko!!.requestFocus()
                return@OnClickListener
            }
            /* Sprawdzanie czy pole nazwisko nie zawiera żadnych błędów
            * Zgodnie z zasadami nazwiska mogą być maksymalnie dwuczłonowe oraz pisane łącznikiem
            * */
            if (!"[A-ZĄĆĘŁŃÓŚŹŻ]{1}[a-ząćęłńóśźż]+".toRegex().matches(
                    edycja_nazwisko!!.text.toString().trim()
                )
                && (!"[A-ZĄĆĘŁŃÓŚŹŻ]{1}[a-ząćęłńóśźż]+[-]{1}[A-ZĄĆĘŁŃÓŚŹŻ]{1}[a-ząćęłńóśźż]+".toRegex()
                    .matches(
                        edycja_nazwisko!!.text.toString().trim()
                    ))
                && (!"[A-ZĄĆĘŁŃÓŚŹŻ]{1}[a-ząćęłńóśźż]+[ ]{1}[A-ZĄĆĘŁŃÓŚŹŻ]{1}[a-ząćęłńóśźż]+".toRegex()
                    .matches(
                        edycja_nazwisko!!.text.toString().trim()
                    ))
            ) {
                edycja_nazwisko!!.setError(getString(R.string.rejestracja_bledne_nazwisko))
                edycja_nazwisko!!.requestFocus()
                return@OnClickListener
            }
            /* Sprawdzanie czy pole telefon nie jest puste */
            if (TextUtils.isEmpty(edycja_telefon!!.text.toString().trim())) {
                edycja_telefon!!.setError(getString(R.string.rejestracja_puste_telefon))
                edycja_telefon!!.requestFocus()
                return@OnClickListener
            }
            /* Sprawdzanie czy pole telefon nie zawiera żadnych błędów */
            if (!"[0-9]{9}".toRegex().matches(edycja_telefon!!.text.toString().trim())) {
                edycja_telefon!!.setError(getString(R.string.rejestracja_bledne_telefon))
                edycja_telefon!!.requestFocus()
                return@OnClickListener
            }

            database!!.child(FirebaseAuth.getInstance().currentUser!!.uid).child("imie").setValue(
                edycja_imie!!.text.toString().trim()
            )
            database!!.child(FirebaseAuth.getInstance().currentUser!!.uid).child("nazwisko")
                .setValue(
                    edycja_nazwisko!!.text.toString().trim()
                )
            database!!.child(FirebaseAuth.getInstance().currentUser!!.uid).child("telefon")
                .setValue(
                    edycja_telefon!!.text.toString().trim()
                )
            Toast.makeText(applicationContext, "Zmiany zostały zapisane", Toast.LENGTH_LONG).show()

        }
        )


        zmiana_hasla!!.setOnClickListener(View.OnClickListener {
            if (TextUtils.isEmpty(edycja_haslo!!.text.toString().trim())) {
                edycja_haslo!!.setError(getString(R.string.rejestracja_puste_haslo))
                edycja_haslo!!.requestFocus()
                return@OnClickListener
            }
            /* Sprawdzanie czy pole hasło nie zawiera żadnych błędów */
            if (!"^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?#^()])[A-Za-z\\d@$!%*?#^()]{9,}$".toRegex()
                    .matches(
                        edycja_haslo!!.text.toString().trim()
                    )
            ) {
                edycja_haslo!!.setError(getString(R.string.rejestracja_bledne_haslo))
                edycja_haslo!!.requestFocus()
                return@OnClickListener
            }
            /* Sprawdzanie czy pole potwierdzenie hasła nie jest puste */
            if (TextUtils.isEmpty(edycja_powtorzhaslo!!.text.toString().trim())) {
                edycja_powtorzhaslo!!.setError(getString(R.string.rejestracja_puste_haslo))
                edycja_powtorzhaslo!!.requestFocus()
                return@OnClickListener
            }
            /* Sprawdzanie czy pola potwierdzenie hasła i hasło nie różnią się */
            if (!edycja_powtorzhaslo!!.text.toString().trim().equals(
                    edycja_haslo!!.text.toString().trim()
                )
            ) {
                edycja_powtorzhaslo!!.setError(getString(R.string.rejestracja_bledne_haslo2))
                edycja_powtorzhaslo!!.requestFocus()
                return@OnClickListener
            }
            if (TextUtils.isEmpty(edycja_obecnehaslo!!.text.toString().trim())) {
                edycja_obecnehaslo!!.setError(getString(R.string.rejestracja_puste_haslo))
                edycja_obecnehaslo!!.requestFocus()
                return@OnClickListener
            }

            val auth2 = EmailAuthProvider.getCredential(email, edycja_obecnehaslo!!.text.toString().trim())

            uzytkownik.reauthenticate(auth2).addOnCompleteListener(this, OnCompleteListener { task ->

                if (!task.isSuccessful){
                    Toast.makeText(applicationContext, "Dane logowania nie zgadzają się", Toast.LENGTH_SHORT)
                        .show()

                }else{
                    if (!edycja_obecnehaslo!!.text.toString().trim().equals(edycja_haslo!!.text.toString().trim())){
                    uzytkownik!!.updatePassword(edycja_haslo!!.text.toString().trim()).addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Toast.makeText(applicationContext, "Hasło zostało zmienione", Toast.LENGTH_SHORT)
                                .show()
                        } else {
                            Toast.makeText(applicationContext, "Błąd zmiany hasła", Toast.LENGTH_SHORT)
                                .show()
                        }
                    }

                }
                    else
                        Toast.makeText(applicationContext, "Nowe hasło nie może być takie samo jak obecne", Toast.LENGTH_SHORT)
                            .show()
                }
            })


            })




    }
}