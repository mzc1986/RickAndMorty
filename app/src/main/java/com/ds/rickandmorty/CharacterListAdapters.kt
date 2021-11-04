package com.ds.rickandmorty;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.ds.rickandmorty.databinding.ItemCharacterBinding;
import com.ds.rickandmorty.model.Result;

import java.util.ArrayList;
import java.util.List;


public class CharacterListAdapters extends RecyclerView.Adapter<CharacterListAdapters.CharacterViewHolder> {

    private List<Result> result = new ArrayList<>();

    public void addResult(List<Result> result){
        this.result.addAll(result);
        notifyDataSetChanged();
    }

    public void clearAdapter(){
        this.result = new ArrayList<>();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CharacterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemCharacterBinding binding = ItemCharacterBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new CharacterViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull CharacterViewHolder holder, int position) {
        holder.bind(result.get(position));
    }

    @Override
    public int getItemCount() {
        return this.result.size();
    }

    static final class CharacterViewHolder extends RecyclerView.ViewHolder{

        ItemCharacterBinding binding;

        public CharacterViewHolder(ItemCharacterBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(Result result){
            binding.tvRocketName.setText(result.getName());
            binding.tvRocketCostPerLaunch.setText(result.getStatus());

            if (result.getImage() != null) {
                String imageUrl = result.getImage()
                        .replace("http://", "https://");

                Glide.with(binding.getRoot().getContext()).load(imageUrl)
                        .thumbnail(0.5f)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(binding.ivRocket);
            }

            binding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent detailsActivity = new Intent(binding.getRoot().getContext(), CharacterDetailsActivity.class);
                    detailsActivity.putExtra("character", result);
                    binding.getRoot().getContext().startActivity(detailsActivity);
                }
            });
        }
    }

}
