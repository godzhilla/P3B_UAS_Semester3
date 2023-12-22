package com.example.p3buassemester3.admin

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.p3buassemester3.R
import com.example.p3buassemester3.databinding.ActivityFormAdminBinding
import com.example.p3buassemester3.dataclass.DestinationModel
import com.google.firebase.firestore.FirebaseFirestore

class FormAdminActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val binding = ActivityFormAdminBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)

        setContentView(binding.root)


        val firestore = FirebaseFirestore.getInstance()
        val noteCollectionRef  = firestore.collection("note")

        fun insertNote(note : DestinationModel) {
            noteCollectionRef.add(note)
        }

        with(binding) {
            btnSaveAdd.setOnClickListener {
                insertNote(
                    DestinationModel(
                        destinationName = addNameDestination.text.toString(),
                        destinationDesc = addDesc.text.toString()
                    )
                )
                finish()
            }
        }
    }
}