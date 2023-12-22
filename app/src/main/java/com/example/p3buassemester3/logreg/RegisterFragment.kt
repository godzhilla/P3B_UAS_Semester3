package com.example.p3buassemester3.logreg

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.app.DatePickerDialog
import android.content.Intent
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import com.example.p3buassemester3.MainActivity.Companion.viewPager2a
import com.example.p3buassemester3.R
import com.example.p3buassemester3.databinding.FragmentRegisterBinding
import com.example.p3buassemester3.dataclass.User
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat
import java.util.Calendar

class RegisterFragment : Fragment(), DatePickerDialog.OnDateSetListener {
    var auth = FirebaseAuth.getInstance()
    var firestore = FirebaseFirestore.getInstance()

    private val binding by lazy {
        FragmentRegisterBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    companion object {
        const val EXTRA_EMAIL = "extra_email"
        const val EXTRA_USERNAME = "extra_username"
        const val EXTRA_PASSWORD = "extra_password"
        var password = ""
        var email = ""
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val inputUsername = binding.editTextUsername
        val inputPassword = binding.editTextPassword
        val inputEmail = binding.editTextEmail

        with(binding) {

            editTextDob.setOnClickListener{
                calendarDatePicker.visibility = View.VISIBLE
                val datePickerDialog = MaterialDatePicker.Builder.datePicker().build()
                datePickerDialog.addOnPositiveButtonClickListener {
                    val formatter = SimpleDateFormat("MM/dd/YYYY")
                    editTextDob.setText(formatter.format(datePickerDialog.selection))
                }
                datePickerDialog.show(childFragmentManager, "datePicker")
                editTextDob.clearFocus()
            }

            btnRegister.setOnClickListener{
                if (editTextEmail.text.toString().isEmpty()){
                    editTextEmail.error = "Masukkan Email"
                    return@setOnClickListener
                }
                if (editTextUsername.text.toString().isEmpty()){
                    editTextUsername.error = "Masukkan Username"
                    return@setOnClickListener
                }
                if (editTextPassword.text.toString().isEmpty()){
                    editTextPassword.error = "Masukkan Password"
                    return@setOnClickListener
                }
                if (editTextDob.text.toString().isEmpty()){
                    editTextDob.error = "Pilih Tanggal Lahir Anda"
                    return@setOnClickListener
                }

                if (editTextDob.text.isNotEmpty()){
                    //mengambil tahun sekarang
                    val currentYear = Calendar.getInstance().get(Calendar.YEAR)

                    //menghitung umur user
                    val dateOfBirth = editTextDob.text.toString()
                    val dateOfBirthSplit = dateOfBirth.split("/")
                    val yearOfBirth = dateOfBirthSplit[2].toInt()
                    val age = currentYear - yearOfBirth

                    //condition
                    if (age < 14) {
                        Toast.makeText(
                            requireContext(),
                            "Registrasi gagal, anda belum cukup umur.",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    else{
                        //masuk ke login page
                        password = inputPassword.text.toString()
                        email = inputEmail.text.toString()

                        auth.createUserWithEmailAndPassword(email, password).addOnSuccessListener {
                            val user = User(
                                username = editTextUsername.text.toString(),
                                email = editTextEmail.text.toString(),
                                password = editTextPassword.text.toString()
                            )
                            firestore.collection("users").document(auth.currentUser?.uid.toString()).set(user)
                            editTextEmail.setText("")

                            editTextUsername.setText("")

                            editTextPassword.setText("")

                            editTextDob.setText("")

                            viewPager2a.setCurrentItem(1)
                    }
                }


                inputUsername.text.clear()
                inputPassword.text.clear()
                inputEmail.text.clear()
                editTextDob.text.clear()
                }

                with(view){
                    findViewById<CheckBox>(R.id.checkbox1).isChecked = false
                }

                viewPager2a.setCurrentItem(1)
            }

            binding.textViewLogin.setOnClickListener{
                viewPager2a.setCurrentItem(1)
            }

        }
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        val selectedDate = "$dayOfMonth/${month+1}/$year"
        binding.editTextDob.setText(selectedDate)
    }
}