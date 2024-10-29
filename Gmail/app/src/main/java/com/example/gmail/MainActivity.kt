package com.example.gmail

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.gmail.adapter.*
import androidx.recyclerview.widget.LinearLayoutManager

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val emails = listOf(
            Email("Edurila.com", "E", "Learn Web Designing", "Are you looking to learn Web Design?", "12:34 PM"),
            Email("Chris Abad", "C", "Campaign Monitor", "Help make Campaign Monitor better!", "11:22 AM"),
            Email("Tuto.com", "T", "Photoshop, SEO, Blender", "8h de formation gratuite", "11:04 AM"),
            Email("support", "S", "SAS OVH", "Suivi de vos services", "10:26 AM"),
            Email("Matt from Ionic", "M", "New Ionic Creator", "The new Ionic Creator is here!", "10:10 AM")
        )

        val emailAdapter = EmailAdapter(emails)
        recyclerView.adapter = emailAdapter
    }
}

