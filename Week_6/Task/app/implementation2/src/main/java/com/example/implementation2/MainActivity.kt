package com.example.implementation2

import android.Manifest.permission.*
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.provider.Settings
import android.view.View
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.implementation2.adapters.ContactAdapter
import com.example.implementation2.databinding.ActivityMainBinding
import com.example.implementation2.models.ContactModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class MainActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private var contacts = ArrayList<ContactModel>()
    private var permissionsToRequest = mutableListOf<String>()

    private lateinit var layout: View
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        layout = binding.mainLayout
        setContentView(view)

        binding.appInfoScreenBtn.setOnClickListener {
            appInfoScreen()
        }

        recyclerView = binding.contactList
        checkPermission()
    }

    private fun checkPermission() {
        permissionsToRequest = mutableListOf()
        if (ActivityCompat.checkSelfPermission(this, READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            permissionsToRequest.add(READ_CONTACTS)
        } else {
            getContacts()
        }

        if (permissionsToRequest.isNotEmpty()) {
            println("request permission")
            ActivityCompat.requestPermissions(this, permissionsToRequest.toTypedArray(), 0)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == 0 && grantResults.isNotEmpty()) {
            for (i in grantResults.indices) {
                if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                    getContacts()
                } else {
                    displayRationalMessage()
                }
            }
        }
    }

    @SuppressLint("Range")
    fun getContacts() {
        contacts = ArrayList()
        val uri = ContactsContract.Contacts.CONTENT_URI
        val sort = ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC"

        val cursor = contentResolver.query(uri, null, null, null, sort)

        if (cursor != null) {
            if (cursor.count > 0) {
                while (cursor.moveToNext()) {
                    val id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID))

                    val name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME))

                    val uriPhone = ContactsContract.CommonDataKinds.Phone.CONTENT_URI

                    val selection = ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " =?"

                    val phoneCursor = contentResolver.query(uriPhone, null, selection, arrayOf(id), null)

                    if (phoneCursor != null) {
                        if (phoneCursor.moveToNext()) {
                            val number = phoneCursor.getString(phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))

                            val contactModel = ContactModel(name, number)
                            contacts.add(contactModel)
                            phoneCursor.close()
                        }
                    }
                }
                cursor.close()

                recyclerView.layoutManager = LinearLayoutManager(this)
                val contactAdapter = ContactAdapter(contacts)
                recyclerView.adapter = contactAdapter
            }
        }
    }

    private fun displayRationalMessage() {
        MaterialAlertDialogBuilder(this)
            .setTitle("Warning")
            .setMessage("This app cannot work well without contact access")
            .setNeutralButton("Cancel") { dialog, _ ->
                // Respond to neutral button press
                dialog.cancel()
                binding.banner.visibility = View.VISIBLE
            }
            .setNegativeButton("Decline") { dialog, _ ->
                // Respond to negative button press
                dialog.dismiss()
                binding.banner.visibility = View.VISIBLE
            }
            .setPositiveButton("Allow") { _, _ ->
                // Respond to positive button press
                appInfoScreen()
            }
            .show()
    }

    private fun appInfoScreen() {
        startActivity(Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
            data = Uri.fromParts("package", packageName, null)
        })
    }

    override fun onResume() {
        super.onResume()
        if (ActivityCompat.checkSelfPermission(this, READ_CONTACTS) == PackageManager.PERMISSION_GRANTED) {
            binding.banner.visibility = View.GONE
            getContacts()
        } else {
            binding.banner.visibility = View.VISIBLE
        }
    }
}