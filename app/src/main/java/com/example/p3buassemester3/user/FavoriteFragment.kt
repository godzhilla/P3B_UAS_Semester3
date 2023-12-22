package com.example.p3buassemester3.user

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.p3buassemester3.PrefManager
import com.example.p3buassemester3.adapter.FavAdapter
import com.example.p3buassemester3.adapter.NoteAdapterDestination
import com.example.p3buassemester3.databinding.FragmentFavoriteBinding
import com.example.p3buassemester3.dataclass.DestinationModel
import com.example.p3buassemester3.dataclass.FavModel
import com.example.p3buassemester3.room.daoFavorite
import com.example.p3buassemester3.room.database
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class FavoriteFragment : Fragment() {
    private lateinit var executors: ExecutorService
    private lateinit var db : database
    private lateinit var dao : daoFavorite

    private lateinit var prefManager: PrefManager

    private val binding by lazy {
        FragmentFavoriteBinding.inflate(layoutInflater)
    }

    private val firestore by lazy {
        FirebaseFirestore.getInstance()
    }

    private val noteUserFavoriteCollectionRef by lazy {
        firestore.collection("noteUserFavorite")
    }

    private val adapterFavorite by lazy {
        FavAdapter(
            listDestinationModel = emptyList<FavModel>().toMutableList(),
            onClickFav = {
                // TODO : hapus favorite
            }
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        executors = Executors.newSingleThreadExecutor()
        db = database.getDatabase(this@FavoriteFragment.requireActivity())!!
        dao = db!!.daoFavorite()!!
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        prefManager = PrefManager.getInstance(requireActivity())

        setUpRecyclerView()
        refreshData()
    }

    private fun setUpRecyclerView() {
        binding.top10.apply {
            layoutManager = LinearLayoutManager(requireActivity())
            adapter = adapterFavorite
        }
    }

    override fun onResume() {
        super.onResume()
        refreshData()
    }

    private fun refreshData() {
        lifecycleScope.launch {
            val favoriteList = withContext(Dispatchers.IO) {
                dao.getAllFavByUser(prefManager.getUserId())
            }

            adapterFavorite.updateList(favoriteList.toMutableList())
        }
    }


}