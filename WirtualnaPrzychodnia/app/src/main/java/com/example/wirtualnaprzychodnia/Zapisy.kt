package com.example.wirtualnaprzychodnia

import android.os.Build
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*
import com.google.firebase.firestore.FirebaseFirestore
import com.google.type.DateTime
import java.text.SimpleDateFormat
import java.time.Instant.now
import java.time.LocalDate
import java.time.LocalDate.now
import java.util.*
import java.time.LocalTime

class Zapisy : AppCompatActivity() {
    private lateinit var database: FirebaseFirestore
    private lateinit var database2: DatabaseReference
    private lateinit var arrayList: ArrayList<Terminy>
    private lateinit var recyclerView: RecyclerView
    @RequiresApi(Build.VERSION_CODES.O)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_zapisy)

        recyclerView = findViewById(R.id.recyclerzapisy)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)
        arrayList = arrayListOf<Terminy>()


        val date = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())


        var today = LocalDate.now()
        var tomorrow = today.plusDays(30);

         val time = LocalTime.of(3,15)


        pobieraniedanych()




    }

    private fun pobieraniedanych() {


        database2 = FirebaseDatabase.getInstance().getReference("zapisy")
        database2.addValueEventListener(object : ValueEventListener {

            @RequiresApi(Build.VERSION_CODES.O)
            override fun onDataChange(snapshot: DataSnapshot) {

                if (snapshot.exists()) {

                    for (snapshot in snapshot.children) {
                        val informacja = snapshot.getValue(Terminy::class.java)

                        val date = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())


                        var today = LocalDate.now()
                        var nastepnedni = today
                        for (i in 1..30){

                            if (informacja!!.termin == nastepnedni.toString()) {

                                arrayList.add(informacja!!)
                            }
                             nastepnedni = nastepnedni.plusDays(1)
                        }



                    }

                    recyclerView.adapter = ZapisyAdapter(arrayList)

                }

            }

            override fun onCancelled(error: DatabaseError) {

            }

        })




    }





    }
