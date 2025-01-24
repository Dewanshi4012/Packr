package com.example.packr

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.ImageView
import android.widget.TextView

class AboutUs : AppCompatActivity() {

    private lateinit var imgYoutube: ImageView
    private lateinit var imgInstagram: ImageView
    private lateinit var imgTwitter: ImageView
    private lateinit var txtEmail: TextView
    private lateinit var txtWebsiteUrl: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about_us)

        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            title = "About Us"
        }

        imgYoutube = findViewById(R.id.Youtube)
        txtEmail = findViewById(R.id.txtEmail)
        txtWebsiteUrl = findViewById(R.id.txtWebsiteUrl)
        imgInstagram = findViewById(R.id.imgInstagram)
        imgTwitter = findViewById(R.id.imgTwitter)

        imgYoutube.setOnClickListener {
            navigateToUrl("http://www.youtube.com")
        }

        txtEmail.setOnClickListener {
            try {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse("mailto:mail4dewanshi@gmail.com"))
                intent.putExtra(Intent.EXTRA_SUBJECT, "From Packwise")
                startActivity(intent)
            } catch (e: ActivityNotFoundException) {
                println(e)
            }
        }

        txtWebsiteUrl.setOnClickListener {
            navigateToUrl("https://github.com/Dewanshi4012")
        }

        imgInstagram.setOnClickListener {
            navigateToUrl("https://www.instagram.com/")
        }

        imgTwitter.setOnClickListener {
            navigateToUrl("https://x.com/i/flow/login")
        }
    }

    private fun navigateToUrl(url: String) {
        val intent = Intent(Intent.ACTION_VIEW).apply {
            addCategory(Intent.CATEGORY_BROWSABLE)
            data = Uri.parse(url)
        }
        startActivity(intent)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}