package com.example.week_5_task

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.example.week_5_task.functions.isValidEmail
import com.example.week_5_task.functions.isValidGender
import com.example.week_5_task.functions.isValidName
import com.example.week_5_task.functions.isValidPhone
import com.google.android.material.textfield.TextInputLayout

class MainActivity : AppCompatActivity() {
    private lateinit var name: String
    private lateinit var phone: String
    private lateinit var email: String
    private lateinit var gender: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val genderView = findViewById<AutoCompleteTextView>(R.id.gender)
        val _gender = resources.getStringArray(R.array.gender)
        val _adapter = ArrayAdapter(this, R.layout.spinner_list, _gender)
        genderView.setAdapter(_adapter)

        val button: Button = findViewById(R.id.submitter)
        button.setOnClickListener {
            register()
        }

        nameFocusListener()
        phoneFocusListener()
        emailFocusListener()
        genderFocusListener()
    }

    private fun reportNameValidity(view: EditText) {
        val usernameTextInputLayout = findViewById<TextInputLayout>(R.id.usernameTextInputLayout)
        if (!view.text.toString().isValidName()) {
            usernameTextInputLayout.helperText = "Invalid name (name must have a minimum length of 3 and must contain only letters)"
        } else {
            usernameTextInputLayout.helperText = ""
        }
    }

    private fun nameFocusListener() {
        val nameView = findViewById<EditText>(R.id.username)
        nameView.setOnFocusChangeListener { _, focused ->
            if (!focused) {
                reportNameValidity(nameView)
            }
        }
    }

    private fun reportPhoneValidity(view: EditText) {
        val phoneTextInputLayout = findViewById<TextInputLayout>(R.id.phoneTextInputLayout)
        if (!view.text.toString().isValidPhone()) {
            phoneTextInputLayout.helperText = "Invalid phone (number must have a minimum length of 11 digits and must contain only numbers)"
        } else {
            phoneTextInputLayout.helperText = ""
        }
    }

    private fun phoneFocusListener() {
        val phoneView = findViewById<EditText>(R.id.phone)
        phoneView.setOnFocusChangeListener { _, focused ->
            if (!focused) {
                reportPhoneValidity(phoneView)
            }
        }
    }

    private fun reportEmailValidity(view: EditText) {
        val emailTextInputLayout = findViewById<TextInputLayout>(R.id.emailTextInputLayout)
        if (!view.text.toString().isValidEmail()) {
            emailTextInputLayout.helperText = "Invalid Email (format: example@domain.com)"
        } else {
            emailTextInputLayout.helperText = ""
        }
    }

    private fun emailFocusListener() {
        val emailView = findViewById<EditText>(R.id.email)
        emailView.setOnFocusChangeListener { _, focused ->
            if (!focused) {
                reportEmailValidity(emailView)
            }
        }
    }

    private fun reportGenderValidity(view: EditText) {
        val genderInputLayout = findViewById<TextInputLayout>(R.id.genderInputLayout)
        if (!view.text.toString().isValidGender()) {
            genderInputLayout.helperText = "Invalid gender (Male or Female)"
        } else {
            genderInputLayout.helperText = ""
        }
    }

    private fun genderFocusListener() {
        val genderView = findViewById<EditText>(R.id.gender)
        genderView.setOnFocusChangeListener { _, focused ->
            if (!focused) {
                reportGenderValidity(genderView)
            }
        }
    }

    private fun register() {
        name = findViewById<EditText>(R.id.username).text.toString()
        phone = findViewById<EditText>(R.id.phone).text.toString()
        email = findViewById<EditText>(R.id.email).text.toString()
        gender = findViewById<EditText>(R.id.gender).text.toString()

        if (name.isValidName() && phone.isValidPhone() && email.isValidEmail() && gender.isValidGender()) {

            val intent = Intent(this, DisplayActivity::class.java).also {
                it.putExtra("name", name)
                it.putExtra("phone", phone)
                it.putExtra("email", email)
                it.putExtra("gender", gender)
                startActivity(it)
            }

        } else {
            reportGenderValidity(findViewById(R.id.gender))
            reportEmailValidity(findViewById(R.id.email))
            reportPhoneValidity(findViewById(R.id.phone))
            reportNameValidity(findViewById(R.id.username))
        }
    }

}