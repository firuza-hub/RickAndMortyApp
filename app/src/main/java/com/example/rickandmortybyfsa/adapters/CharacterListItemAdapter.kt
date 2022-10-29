package com.example.rickandmortybyfsa.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.rickandmortybyfsa.data.remote.models.CharacterDetails
import com.example.rickandmortybyfsa.databinding.CharacterListItemBinding

class CharacterListItemAdapter: RecyclerView.Adapter<CharacterListItemAdapter.CharacterListItemViewHolder>() {

    private var data = listOf<CharacterDetails>()

    class CharacterListItemViewHolder(val binding: CharacterListItemBinding): RecyclerView.ViewHolder(binding.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterListItemViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = CharacterListItemBinding.inflate(layoutInflater,  parent, false)
        return CharacterListItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: CharacterListItemViewHolder, position: Int) {
        val currentItem = data[position]
        holder.binding.character = currentItem
    }

    override fun getItemCount(): Int {
        return data.size
    }

    fun setData(newData:List<CharacterDetails>){
        data = newData
        notifyDataSetChanged()
    }
}