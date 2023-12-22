package com.example.p3buassemester3.user

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.room.Database
import com.example.p3buassemester3.MainActivity
import com.example.p3buassemester3.PrefManager
import com.example.p3buassemester3.R
import com.example.p3buassemester3.databinding.FragmentProfileBinding
import com.example.p3buassemester3.room.database
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * A simple [Fragment] subclass.
 * Use the [ProfileFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ProfileFragment : Fragment() {

    private val binding by lazy {
        FragmentProfileBinding.inflate(layoutInflater)
    }
    private lateinit var prefManager: PrefManager


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            val prefManager = PrefManager.getInstance(requireActivity())
            btnLogout.setOnClickListener() {
                prefManager.clear()
                val db = database.getDatabase(requireActivity())
                lifecycleScope.launch(Dispatchers.IO) {
                    db?.daoFavorite()?.deleteAllData()
                }

                val intentToLogin = Intent(requireActivity(), MainActivity::class.java)
                startActivity(intentToLogin)
                requireActivity().finish()


            }

            txtName.text = prefManager.getEmail().split('@')[0]
            txtEmail.text = prefManager.getEmail()

        }
    }



}