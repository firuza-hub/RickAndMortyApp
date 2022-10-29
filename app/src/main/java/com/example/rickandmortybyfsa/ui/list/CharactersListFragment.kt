package com.example.rickandmortybyfsa.ui.list

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.rickandmortybyfsa.R
import com.example.rickandmortybyfsa.adapters.CharacterListItemAdapter
import com.example.rickandmortybyfsa.data.remote.CharacterApiStatus
import com.example.rickandmortybyfsa.databinding.FragmentCharactersListBinding

class CharactersListFragment : Fragment() {

    private val viewModel: CharactersListViewModel by lazy {
        ViewModelProvider(this, CharactersListViewModel.Factory(requireActivity().application))[CharactersListViewModel::class.java]
    }
    private lateinit var adapter: CharacterListItemAdapter
    private lateinit var binding: FragmentCharactersListBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_characters_list, container, false)

        adapter = CharacterListItemAdapter()
        binding.rvCharacters.adapter = adapter

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.characterList.observe(requireActivity(), Observer {
            adapter.setData(it.results)
            binding.tvPage.text = it.info.count.toString()
        })
        viewModel.status.observe(requireActivity(), Observer {
            if(it == CharacterApiStatus.LOADING) binding.pbListLoader.visibility =  View.VISIBLE
            else   binding.pbListLoader.visibility = View.INVISIBLE
        })
    }

}