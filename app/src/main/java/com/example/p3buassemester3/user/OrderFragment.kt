package com.example.p3buassemester3.user

import android.app.DatePickerDialog
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.p3buassemester3.PrefManager
import com.example.p3buassemester3.R
import com.example.p3buassemester3.databinding.FragmentOrderBinding
import com.example.p3buassemester3.dataclass.NoteOrder
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.delay
import java.text.SimpleDateFormat

class OrderFragment : Fragment(), DatePickerDialog.OnDateSetListener {
    private val binding by lazy {
        FragmentOrderBinding.inflate(layoutInflater)
    }

    private val channelId = "TEST_NOTIF"
    private val notifId = 33

    private var hargaTiketSementara = 0
    private var totalHarga = 0
    private var boolPaket1 = false
    private var boolPaket2 = false
    private var boolPaket3 = false
    private var boolPaket4 = false
    private var boolPaket5 = false
    private var boolPaket6 = false
    private var boolPaket7 = false

    private val firestore by lazy {
        FirebaseFirestore.getInstance()
    }

    private val noteUserOrderCollectionRef by lazy {
        firestore.collection("order")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val notifManager = requireActivity().getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        with(binding) {
            editTextTanggal.setOnClickListener{
                val datePickerDialog = MaterialDatePicker.Builder.datePicker().build()
                datePickerDialog.addOnPositiveButtonClickListener {
                    val formatter = SimpleDateFormat("MM/dd/YYYY")
                    editTextTanggal.setText(formatter.format(datePickerDialog.selection))
                }
                datePickerDialog.show(this@OrderFragment.requireActivity().supportFragmentManager, "date picker")




            }

            val stasiun = resources.getStringArray(R.array.stasiun)
            spinnerAsal.adapter = ArrayAdapter<String> (
                this@OrderFragment.requireActivity(),
                android.R.layout.simple_spinner_item,
                stasiun
            )

            spinnerTujuan.adapter = ArrayAdapter<String> (
                this@OrderFragment.requireActivity(),
                android.R.layout.simple_spinner_item,
                stasiun
            )

            val kelas = resources.getStringArray(R.array.kelas)
            spinnerClass.adapter = ArrayAdapter<String> (
                this@OrderFragment.requireActivity(),
                android.R.layout.simple_spinner_item,
                kelas
            )
            spinnerClass.onItemSelectedListener =
                object : AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(
                        parent: AdapterView<*>?,
                        view: View?,
                        position: Int,
                        id: Long
                    ) {
                        when(position) {
                            0 -> {
                                totalHarga = 0
                                txtHarga2.text = "Rp${totalHarga}"
                            }
                            1 -> {
                                totalHarga = 150000 + hargaTiketSementara
                                txtHarga2.text = "Rp${totalHarga}"
                            }
                            2 -> {
                                totalHarga = 50000 + hargaTiketSementara
                                txtHarga2.text = "Rp${totalHarga}"
                            }
                            3 -> {
                                totalHarga = 0 + hargaTiketSementara
                                txtHarga2.text = "Rp${totalHarga}"
                            }

                        }

                    }

                    override fun onNothingSelected(parent: AdapterView<*>?) {
                        //TODO: Do something

                    }
                }

            //paket
            toggleSwitch.setOnClickListener{
                if (toggleSwitch.isChecked){
                    totalHarga += 30000
                    txtHarga2.text = "Rp${totalHarga}"
                    boolPaket1 = true
                }
                else {
                    totalHarga -= 30000
                    txtHarga2.text = "Rp${totalHarga}"
                    boolPaket1 = false
                }
            }

            toggleSwitch2.setOnClickListener{
                if (toggleSwitch2.isChecked){
                    totalHarga += 25000
                    txtHarga2.text = "Rp${totalHarga}"
                    boolPaket2 = true
                }
                else {
                    totalHarga -= 25000
                    txtHarga2.text = "Rp${totalHarga}"
                    boolPaket2 = false
                }
            }

            toggleSwitch3.setOnClickListener{
                if (toggleSwitch3.isChecked){
                    totalHarga += 10000
                    txtHarga2.text = "Rp${totalHarga}"
                    boolPaket3 = true
                }
                else {
                    totalHarga -= 10000
                    txtHarga2.text = "Rp${totalHarga}"
                    boolPaket3 = false
                }
            }

            toggleSwitch4.setOnClickListener{
                if (toggleSwitch4.isChecked){
                    totalHarga += 55000
                    txtHarga2.text = "Rp${totalHarga}"
                    boolPaket4 = true
                }
                else {
                    totalHarga -= 55000
                    txtHarga2.text = "Rp${totalHarga}"
                    boolPaket4 = false
                }
            }

            toggleSwitch5.setOnClickListener{
                if (toggleSwitch5.isChecked){
                    totalHarga += 35000
                    txtHarga2.text = "Rp${totalHarga}"
                    boolPaket5 = true
                }
                else {
                    totalHarga -= 35000
                    txtHarga2.text = "Rp${totalHarga}"
                    boolPaket5 = false
                }
            }

            toggleSwitch6.setOnClickListener{
                if (toggleSwitch6.isChecked){
                    totalHarga += 30000
                    txtHarga2.text = "Rp${totalHarga}"
                    boolPaket6 = true
                }
                else {
                    totalHarga -= 30000
                    txtHarga2.text = "Rp${totalHarga}"
                    boolPaket6 = false
                }
            }

            toggleSwitch7.setOnClickListener{
                if (toggleSwitch7.isChecked){
                    totalHarga += 15000
                    txtHarga2.text = "Rp${totalHarga}"
                    boolPaket7 = true
                }
                else {
                    totalHarga -= 15000
                    txtHarga2.text = "Rp${totalHarga}"
                    boolPaket7 = false
                }
            }

            btnPesan.setOnClickListener() {

                val flag = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    PendingIntent.FLAG_IMMUTABLE
                }
                else {
                    0
                }

                val intent = Intent (requireActivity(), OrderFragment::class.java)

                val pendingIntent = PendingIntent.getActivity(
                    requireActivity(), 0, intent, flag
                )

                val builder = NotificationCompat.Builder(requireActivity(), channelId).setSmallIcon(R.drawable.baseline_notifications_24)
                    .setContentTitle("Notifku")
                    .setContentText("Pesanan berhasil!!")
                    .setAutoCancel(true)
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                    .setContentIntent(pendingIntent)

                if (Build.VERSION.SDK_INT > Build.VERSION_CODES.O) {
                    val notifChannel = NotificationChannel(channelId, "Notifku",
                        NotificationManager.IMPORTANCE_DEFAULT)
                    with(notifManager) {
                        createNotificationChannel(notifChannel)
                        notify(0, builder.build())
                    }
                }


                notifManager.notify(notifId, builder.build())


                val prefManager = PrefManager.getInstance(requireActivity())
                val uid = prefManager.getUserId()

                val newNoteOrder = NoteOrder(
                    kotaAsal = binding.spinnerAsal.selectedItem.toString(),
                    kotaTujuan = binding.spinnerTujuan.selectedItem.toString(),
                    keberangkatan = binding.editTextTanggal.text.toString(),
                    totalHarga = totalHarga,
                    userId = uid
                )

                noteUserOrderCollectionRef.add(newNoteOrder).addOnSuccessListener {
                    newNoteOrder.id = it.id
                    it.set(newNoteOrder).addOnSuccessListener {
                        Toast.makeText(root.context, "Berhasil memesan", Toast.LENGTH_LONG).show()
                    }
                }

                findNavController().navigateUp()


            }
        }
    }

    override fun onDateSet(
        view: android.widget.DatePicker?,
        year: Int,
        month: Int,
        dayOfMonth: Int
    ) {
        val selectedDate = "$dayOfMonth/${month+1}/$year"
        binding.editTextTanggal.setText(selectedDate)
    }
}