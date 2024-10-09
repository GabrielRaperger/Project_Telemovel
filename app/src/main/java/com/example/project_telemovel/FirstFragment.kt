package com.example.project_telemovel

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.telephony.SmsManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment

class FirstFragment : Fragment() {

    private lateinit var editTextPhoneNumber: EditText
    private lateinit var editTextMessage: EditText
    private lateinit var buttonSend: Button
    private lateinit var textViewStatus: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_first, container, false)

        editTextPhoneNumber = view.findViewById(R.id.editTextPhoneNumber)
        editTextMessage = view.findViewById(R.id.editTextMessage)
        buttonSend = view.findViewById(R.id.buttonSend)
        textViewStatus = view.findViewById(R.id.textViewStatus)

        buttonSend.setOnClickListener {
            sendSms()
        }

        return view
    }

    private fun sendSms() {
        val phoneNumber = editTextPhoneNumber.text.toString()
        val message = editTextMessage.text.toString()

        if (phoneNumber.isNotEmpty() && message.isNotEmpty()) {
            if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(arrayOf(Manifest.permission.SEND_SMS), 1)
            } else {
                val smsManager = SmsManager.getDefault()
                smsManager.sendTextMessage(phoneNumber, null, message, null, null)
                textViewStatus.text = "Mensagem enviada!"
            }
        } else {
            textViewStatus.text = "Por favor, preencha todos os campos."
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 1 && grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            sendSms()
        } else {
            textViewStatus.text = "Permissão para enviar SMS negada."
        }
    }
}
