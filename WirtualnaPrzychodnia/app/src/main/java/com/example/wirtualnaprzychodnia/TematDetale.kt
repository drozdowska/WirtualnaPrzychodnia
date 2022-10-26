package com.example.wirtualnaprzychodnia

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import java.time.LocalDateTime


class TematDetale : AppCompatActivity() {



    private lateinit var recyclerView: RecyclerView
    private lateinit var arrayList: ArrayList<Komentarz>
    private lateinit var database: FirebaseFirestore
    var id = ""

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_temat_detale)
        var dodawanie_komentarza = findViewById<Button>(R.id.dodawanie_kom)
        id = intent.getStringExtra("id").toString()


        var tytultemat = findViewById<TextView>(R.id.tytultematu)
        var tresctemat = findViewById<TextView>(R.id.tresctematu)
        database = FirebaseFirestore.getInstance()
        val docTemat = database.collection("forum").document(id)
        docTemat.get().addOnSuccessListener { documentSnapshot ->
            val temat = documentSnapshot.toObject(Temat::class.java)
            tytultemat.setText(temat!!.tytul)
            tresctemat.setText(temat!!.tresc)

        }


        var linear = findViewById<LinearLayout>(R.id.linear)
        dodawanie_komentarza.setOnClickListener{
            if (linear.visibility == View.VISIBLE) {
                linear.visibility = View.GONE
                dodawanie_komentarza.text = "dodawanie komentarza"
            }
            else {
                linear.visibility = View.VISIBLE
                dodawanie_komentarza.text = "ukryj dodawanie komentarza"
            }
        }
        var opublikuj = findViewById<Button>(R.id.opublikuj)
        var tresc = findViewById<EditText>(R.id.tresc_komentarza)
        opublikuj.setOnClickListener (View.OnClickListener {

            if (TextUtils.isEmpty(tresc!!.text.toString().trim())) {
                tresc!!.setError(getString(R.string.puste_pole))
                tresc!!.requestFocus()
                return@OnClickListener
            }
            var auth = FirebaseAuth.getInstance()
            val db = FirebaseFirestore.getInstance()
            val nowy: MutableMap<String, Any> = HashMap()
            nowy["tresc"] = tresc.text.toString()
            nowy["autor"] = auth.currentUser!!.uid
            nowy["data"] = LocalDateTime.now().toString()

            db.collection("forum").document(id).collection("komentarze")
                .add(nowy)
                .addOnSuccessListener {
                    Toast.makeText(applicationContext, "Dodanie komentarza przebiegło pomyślnie", Toast.LENGTH_SHORT ).show()
                    this.recreate()
                }
                .addOnFailureListener{
                    Toast.makeText(applicationContext, "Błąd dodania komentarza", Toast.LENGTH_SHORT ).show()
                }

        })




        recyclerView = findViewById(R.id.recyclertemat)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)
        arrayList = arrayListOf<Komentarz>()
        database = FirebaseFirestore.getInstance()
        pobieraniedanych()



    }

    private fun pobieraniedanych() {

        database.collection("forum").document(id).collection("komentarze").orderBy("data").get().addOnSuccessListener{ result ->

            for (document in result) {
                val informacja = document.toObject(Komentarz::class.java)

                arrayList.add(informacja)

            }
            recyclerView.adapter = KomentarzeAdapter(arrayList)

        }
                .addOnFailureListener{
                    Toast.makeText(applicationContext, "Błąd pobierania danych", Toast.LENGTH_SHORT ).show()
                }
    }




}