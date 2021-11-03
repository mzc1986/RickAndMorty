package com.ds.rickandmorty;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.ds.rickandmorty.databinding.ActivityCharDetailsBinding;
import com.ds.rickandmorty.model.Result;

import java.util.Arrays;
import java.util.List;


public class CharacterDetailsActivity extends AppCompatActivity {

    ActivityCharDetailsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityCharDetailsBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        setTitle("Character Details");

        Intent intent = getIntent();
        Result results = (Result) intent.getSerializableExtra("character");

        binding.charDetailsLayout.tvStatus.setText(results.getStatus());
        binding.charDetailsLayout.tvGender.setText(results.getGender());
        binding.charDetailsLayout.tvSpecies.setText(results.getSpecies());
        binding.charDetailsLayout.tvLocation.setText(results.getLocation().getName());
        binding.charDetailsLayout.tvOrigin.setText(results.getOrigin().getName());

        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, results.getEpisode());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        Glide.with(this).load(Uri.parse(results.getImage()))
                .thumbnail(0.5f)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(binding.charDetailsLayout.ivDetailedActivity);
    }

}
