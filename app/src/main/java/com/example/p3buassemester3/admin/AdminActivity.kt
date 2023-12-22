package com.example.p3buassemester3.admin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.p3buassemester3.adapter.NoteAdapterDestination
import com.example.p3buassemester3.databinding.ActivityAdminBinding
import com.example.p3buassemester3.dataclass.DestinationModel
import com.example.p3buassemester3.dataclass.FavModel
import com.example.p3buassemester3.room.daoFavorite
import com.example.p3buassemester3.room.database
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObjects
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class AdminActivity : AppCompatActivity() {
    private lateinit var executors: ExecutorService
    private lateinit var db : database
    private lateinit var dao : daoFavorite

    private val firestore by lazy {
        FirebaseFirestore.getInstance()
    }


    private val noteCollectionRef by lazy {
        firestore.collection("note")
    }


    private val binding by lazy {
        ActivityAdminBinding.inflate(layoutInflater)
    }

    private val adapterRecyclerView by lazy {
        NoteAdapterDestination(
            listDestinationModel = emptyList<DestinationModel>().toMutableList(),
            onClickFav = {
                // TODO : do something
            }
        )
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)


        executors    = Executors.newSingleThreadExecutor()
        db = database.getDatabase(this)!!
        dao = db!!.daoFavorite()!!


        with(binding) {
            addNewDestination.setOnClickListener {
                val intent = Intent(this@AdminActivity, FormAdminActivity::class.java)
                startActivity(intent)
            }
        }

        setUpRecyclerView()
        refreshData()
    }

    private fun refreshData() {
        noteCollectionRef.addSnapshotListener { value, error ->
            if (error != null) {
                Toast.makeText(this, "Error listening to characters data", Toast.LENGTH_SHORT).show()
            }

            value?.let {
                val newList = value.toObjects<DestinationModel>()
                adapterRecyclerView.updateList(newList)
            }

        }
    }

    private fun setUpRecyclerView() {
        binding.top10.apply {
            layoutManager = LinearLayoutManager(this@AdminActivity)
            adapter = adapterRecyclerView
        }
    }

    override fun onResume() {
        super.onResume()
        refreshData()
    }

    fun insert(favModel: FavModel){
        executors.execute{
            dao.insert(favModel)
        }
    }

}