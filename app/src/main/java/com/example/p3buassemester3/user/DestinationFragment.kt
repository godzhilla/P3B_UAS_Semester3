package com.example.p3buassemester3.user

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.p3buassemester3.adapter.NoteAdapterDestination
import com.example.p3buassemester3.databinding.FragmentDestinationBinding
import com.example.p3buassemester3.dataclass.DestinationModel
import com.example.p3buassemester3.dataclass.FavModel
import com.example.p3buassemester3.room.daoFavorite
import com.example.p3buassemester3.room.database
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObjects
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class DestinationFragment : Fragment() {
    private lateinit var executors: ExecutorService
    private lateinit var db : database
    private lateinit var dao : daoFavorite

    private val binding by lazy {
        FragmentDestinationBinding.inflate(layoutInflater)
    }

    private val firestore by lazy {
        FirebaseFirestore.getInstance()
    }

    private val noteCollectionRef by lazy {
        firestore.collection("note")
    }

    private val adapterTop10 by lazy {
        NoteAdapterDestination(
            listDestinationModel = emptyList<DestinationModel>().toMutableList(),
            onClickFav = { note ->
                val fav = FavModel(
                    destinationDesc = note.destinationDesc,
                    destinationName = note.destinationName,
                    uid = FirebaseAuth.getInstance().currentUser?.uid.toString()
                )
                Toast.makeText(this@DestinationFragment.requireActivity(), "Destination added to favorite", Toast.LENGTH_SHORT).show()
                insert(fav)
            }

        )

    }



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        executors = Executors.newSingleThreadExecutor()
        db = database.getDatabase(this@DestinationFragment.requireActivity())!!
        dao = db!!.daoFavorite()!!
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {

            val action = DestinationFragmentDirections.actionDestinationFragmentToFavoriteFragment()
            top10.setOnClickListener {
                findNavController().navigate(action)
            }

        }

        setUpRecyclerView()
        refreshData()
    }

    private fun refreshData() {
        noteCollectionRef.addSnapshotListener { value, error ->
            if (error != null) {
                Toast.makeText(this@DestinationFragment.requireActivity(), "Error listening to characters data", Toast.LENGTH_SHORT).show()
            }

            value?.let {
                val newList = value.toObjects<DestinationModel>()
                adapterTop10.updateList(newList)
            }

        }
    }

    private fun setUpRecyclerView() {
        binding.top10.apply {
            layoutManager = LinearLayoutManager(requireActivity())
            adapter = adapterTop10
        }
    }

    override fun onResume() {
        super.onResume()
        refreshData()
    }


    private fun insert(favModel: FavModel){
        executors.execute{
            dao.insert(favModel)
        }
    }

}