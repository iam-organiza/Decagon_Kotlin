package com.example.implementationtwo

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity() {
    private lateinit var imageBtn: Button
    private lateinit var videoBtn: Button
    private lateinit var pdfBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        imageBtn = findViewById(R.id.imageBtn)
        videoBtn = findViewById(R.id.videoBtn)
        pdfBtn = findViewById(R.id.pdfBtn)

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            imageBtn.isEnabled = false
            videoBtn.isEnabled = false
            pdfBtn.isEnabled = false
            ActivityCompat.requestPermissions(this, arrayOf<String>(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE), 0);
        } else {
            imageBtn.isEnabled = true
            videoBtn.isEnabled = true
            pdfBtn.isEnabled = true
        }


        imageBtn.setOnClickListener {
            _getIntent(it)
        }

        videoBtn.setOnClickListener {
            _getIntent(it)
        }

        pdfBtn.setOnClickListener {
            _getIntent(it)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 0) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED
                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                imageBtn.isEnabled = true
                videoBtn.isEnabled = true
                pdfBtn.isEnabled = true
            }
        }
    }

    private fun _getIntent(view: View) {
        when (view.id) {
            R.id.imageBtn -> {
                val intent = Intent(this, ImageActivity::class.java)
                startActivity(intent)
            }
            R.id.videoBtn -> {
                val intent = Intent(this, VideoActivity::class.java)
                startActivity(intent)
            }
            R.id.pdfBtn -> {
                val intent = Intent(this, PdfActivity::class.java)
                startActivity(intent)
            }
        }
    }
}