package com.example.p3buassemester3.user

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.p3buassemester3.PrefManager
import com.example.p3buassemester3.adapter.FavAdapter
import com.example.p3buassemester3.adapter.NoteOrderAdapter
import com.example.p3buassemester3.databinding.FragmentRiwayatPemesananBinding
import com.example.p3buassemester3.dataclass.FavModel
import com.example.p3buassemester3.dataclass.NoteOrder
import com.example.p3buassemester3.dataclass.NoteOrder.Companion.FIELD_USER_ID
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Source
import com.google.firebase.firestore.toObjects
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RiwayatPemesananFragment : Fragment() {
    private val binding by lazy {
        FragmentRiwayatPemesananBinding.inflate(layoutInflater)
    }

    private val firestore by lazy {
        FirebaseFirestore.getInstance()
    }

    private val noteUserOrderCollectionRef by lazy {
        firestore.collection("order")
    }

    private val adapterOrder by lazy {
        NoteOrderAdapter(
            listDestinationModel = emptyList<NoteOrder>().toMutableList(),
            onClickFav = {
                // TODO : hapus favorite
            }
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        refreshData()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpRecyclerView()
        refreshData()

        with(binding) {
            val action = RiwayatPemesananFragmentDirections.actionRiwayatPemesananFragmentToOrderFragment()
            txtTambahPerjalanan.setOnClickListener {
                findNavController().navigate(action)
            }
        }
    }

    private fun refreshData() {
        lifecycleScope.launch {
            val prefManager = PrefManager.getInstance(requireActivity())
            val uid = withContext(Dispatchers.IO) { prefManager.getUserId() }
            noteUserOrderCollectionRef.whereEqualTo(FIELD_USER_ID,uid).get(Source.SERVER).addOnSuccessListener {
                val newList = it.toObjects<NoteOrder>()
                Log.d("Recycler", "Membuat recycler dengan list ${newList.size} from ${it.size()}")
                adapterOrder.updateList(newList.toMutableList())
            }
        }

    }

    private fun setUpRecyclerView() {
        binding.trip.apply {
            layoutManager = LinearLayoutManager(requireActivity())
            adapter = adapterOrder
        }
    }

}