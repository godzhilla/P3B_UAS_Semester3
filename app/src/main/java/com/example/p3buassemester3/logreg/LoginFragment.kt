package com.example.p3buassemester3.logreg

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.p3buassemester3.MainActivity
import com.example.p3buassemester3.MainActivity2
import com.example.p3buassemester3.PrefManager
import com.example.p3buassemester3.R
import com.example.p3buassemester3.admin.AdminActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore


class LoginFragment : Fragment() {
    var firestore = FirebaseFirestore.getInstance()
    private lateinit var prefManager: PrefManager
    private lateinit var inputEmail : EditText
    private lateinit var inputPassword : EditText

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_login, container, false)
        val auth = FirebaseAuth.getInstance()

        inputEmail = view.findViewById<EditText>(R.id.edit_text_email)
        inputPassword = view.findViewById<EditText>(R.id.edit_text_password)

        view.findViewById<View>(R.id.btn_login).setOnClickListener() {
            if (inputEmail.text.toString().isEmpty()) {
                view.findViewById<EditText>(R.id.edit_text_email).error = "Masukkan username"
                return@setOnClickListener
            }

            else if (inputPassword.text.toString().isEmpty()) {
                view.findViewById<EditText>(R.id.edit_text_password).error = "Masukkan password"
                return@setOnClickListener
            }

            else {
                auth.signInWithEmailAndPassword(inputEmail.text.toString(), inputPassword.text.toString()).addOnSuccessListener {
                    firestore.collection("users").document(auth.currentUser?.uid.toString()).get().addOnSuccessListener {
                        dataUser ->

                        prefManager = PrefManager.getInstance(requireActivity())
                        // TODO : simpan user id

                        prefManager.saveUserId(dataUser.id)
                        if (view.findViewById<CheckBox>(R.id.checkbox2).isChecked) {
                            prefManager.saveEmail(inputEmail.text.toString())
                            prefManager.savePassword(dataUser.id)
                            prefManager.setLoggedIn(true)
                        }

                        if ( dataUser.getString("role") == "admin") {
                            val intent = Intent(requireActivity(), AdminActivity::class.java)

                            inputEmail.text.clear()
                            inputPassword.text.clear()

                            with(view){
                                findViewById<CheckBox>(R.id.checkbox2).isChecked = false
                            }

                            startActivity(intent)
                        }
                        else {
                            val intent = Intent(requireActivity(), MainActivity2::class.java)

                            inputEmail.text.clear()
                            inputPassword.text.clear()

                            with(view){
                                findViewById<CheckBox>(R.id.checkbox2).isChecked = false
                            }

                            startActivity(intent)
                        }
                    }


                }
            }
        }

        fun isValidUserId() : Boolean {
            val email = prefManager.getEmail()
            val password = prefManager.getPassword()
            val userId = prefManager.getUserId()

            return email == inputEmail.text.toString() && password == inputPassword.text.toString()


        }

        with(view){

            findViewById<View>(R.id.text_view_register).setOnClickListener() {
                MainActivity.viewPager2a.setCurrentItem(0)
            }
        }

        return view
    }

}