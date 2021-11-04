package com.ds.rickandmorty

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.ds.rickandmorty.CharacterListAdapters.CharacterViewHolder
import com.ds.rickandmorty.databinding.ItemCharacterBinding
import com.ds.rickandmorty.model.Result
import java.util.*

class CharacterListAdapters : RecyclerView.Adapter<CharacterViewHolder>() {

    private var result: MutableList<Result> = ArrayList()

    fun addResult(result: List<Result>?) {
        this.result.addAll(result!!)
        notifyDataSetChanged()
    }

    fun clearAdapter() {
        result = ArrayList()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        val binding = ItemCharacterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CharacterViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        holder.bind(result[position])
    }

    override fun getItemCount(): Int {
        return result.size
    }

    class CharacterViewHolder(var binding: ItemCharacterBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(result: Result) {
            binding.tvRocketName.text = result.name
            binding.tvRocketCostPerLaunch.text = result.status

            if (result.image != null) {
                val imageUrl = result.image!!
                        .replace("http://", "https://")
                Glide.with(binding.root.context).load(imageUrl)
                        .thumbnail(0.5f)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(binding.ivRocket)
            }

            binding.root.setOnClickListener {
                val detailsActivity = Intent(binding.root.context, CharacterDetailsActivity::class.java)
                detailsActivity.putExtra("character", result)
                binding.root.context.startActivity(detailsActivity)
            }
        }
    }
}