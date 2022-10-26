package com.example.wirtualnaprzychodnia

import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.*
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalTime
import java.util.*
import kotlin.collections.ArrayList

class ZapisyAdapter(private val recycler: ArrayList<Terminy>) : RecyclerView.Adapter<ZapisyAdapter.RecyclerViewHolder>() {
    private lateinit var database2: DatabaseReference
    private lateinit var arrayList: ArrayList<Godziny>
    private lateinit var start2: String
    private lateinit var stop2: String
    private lateinit var odstep: String
    private lateinit var start: LocalTime
    private lateinit var stop: LocalTime

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ZapisyAdapter.RecyclerViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.obiekt_zapisy, parent, false)
        return ZapisyAdapter.RecyclerViewHolder(itemView)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: ZapisyAdapter.RecyclerViewHolder, position: Int) {
        val biezacy = recycler[position]
        holder.nazwa.text = biezacy.termin
        var inf_nazwa = biezacy.termin
       // var inf_opis = biezacy.opis


            holder.itemView.setOnClickListener { v ->
                

                var zajetegodziny = arrayListOf<String>()

                arrayList = arrayListOf<Godziny>()


                database2 = FirebaseDatabase.getInstance().getReference("zapisy")

                database2.child(biezacy.termin.toString()).addListenerForSingleValueEvent(object : ValueEventListener {
                    @RequiresApi(Build.VERSION_CODES.O)
                    override fun onDataChange(snapshot: DataSnapshot) {

                        if (snapshot.exists()) {

                            val informacja = snapshot.getValue(Terminy::class.java)


                            start2 = informacja?.start.toString()
                            stop2 = informacja?.stop.toString()
                            odstep = informacja?.odstep.toString()
                            holder.godzinyprzyjec.text = informacja!!.start + " - " + informacja!!.stop



                        }
                    }

                    override fun onCancelled(error: DatabaseError) {
                        TODO("Not yet implemented")
                    }

                })




                database2 = FirebaseDatabase.getInstance().getReference("zapisy")

                database2.child(biezacy.termin.toString()).child("wizyty").addListenerForSingleValueEvent(object : ValueEventListener {
                    @RequiresApi(Build.VERSION_CODES.O)
                    override fun onDataChange(snapshot: DataSnapshot) {


                        stop = LocalTime.parse(stop2)
                        start = LocalTime.parse(start2)
                        // var odstep = Integer.parseInt(odstep)
                        var nastepnegodziny = start



                        if (snapshot.exists()) {


                            for (snapshot in snapshot.children) {

                                val informacja = snapshot.getValue(Godziny::class.java)


                                zajetegodziny.add(informacja!!.godzina.toString())
                                val date = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(
                                    Date()
                                )
                                var today = LocalDate.now().toString()

                                var poprzednie = start
                                var godzina =  LocalTime.now()

                                if (biezacy.termin == today ) {


                                    while (poprzednie.isBefore(godzina)){


                                        zajetegodziny.add(poprzednie.toString())
                                        poprzednie = poprzednie.plusMinutes(30)

                                    }
                                }

                            }


                        }


                        var st = start
                        var list: ArrayList<String> = arrayListOf()
                        while (st.isBefore(stop)) {

                            list.add(st.toString())

                            st = st.plusMinutes(30)
                        }


                        val diff = list.minus(zajetegodziny)

                        for (d in diff) {
                            var godz = Godziny()
                            godz.godzina = d.toString()
                            godz.pacjent = ""
                            godz.data = biezacy.termin
                            arrayList.add(godz)
                        }




                        println(list)
                        println(arrayList)
                        holder.recyclergodziny.adapter = GodzinyAdapter(arrayList)


                    }

                    override fun onCancelled(error: DatabaseError) {
                        TODO("Not yet implemented")
                    }


                }
                )



            }






    }
    override fun getItemCount(): Int {
        return recycler.size
    }

    class RecyclerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val nazwa : TextView = itemView.findViewById(R.id.nazwa)

        val godzinyprzyjec : TextView = itemView.findViewById(R.id.godzinyprzyjec)
        val recyclergodziny : RecyclerView = itemView.findViewById(R.id.recyclergodziny)
    }


}