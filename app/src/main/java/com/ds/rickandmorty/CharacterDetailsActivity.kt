package com.ds.rickandmorty

import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.ds.rickandmorty.databinding.ActivityCharDetailsBinding
import com.ds.rickandmorty.model.Result

class CharacterDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCharDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCharDetailsBinding.inflate(layoutInflater)

        val view: View = binding.root
        setContentView(view)

        title = "Character Details"

        val intent = intent
        var results = intent.getSerializableExtra("character") as Result

        binding.charDetailsLayout.tvStatus.text = results.status
        binding.charDetailsLayout.tvGender.text = results.gender
        binding.charDetailsLayout.tvSpecies.text = results.species
        binding.charDetailsLayout.tvLocation.text = results.location?.name
        binding.charDetailsLayout.tvOrigin.text = results.origin?.name

        val spinner = findViewById<View>(R.id.spinner) as Spinner
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, results.episode!!)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter

        Glide.with(this).load(Uri.parse(results.image))
                .thumbnail(0.5f)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(binding.charDetailsLayout.ivDetailedActivity)
    }
}