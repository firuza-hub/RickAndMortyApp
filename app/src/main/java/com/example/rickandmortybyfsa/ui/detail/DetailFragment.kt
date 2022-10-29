package com.example.rickandmortybyfsa.ui.detail

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.rickandmortybyfsa.R
import com.example.rickandmortybyfsa.databinding.FragmentDetailBinding

class DetailFragment : Fragment() {

    private lateinit var binding:FragmentDetailBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val character = DetailFragmentArgs.fromBundle(requireArguments()).data

        binding= DataBindingUtil.inflate(inflater ,R.layout.fragment_detail, container, false)
        binding.character = character
        return binding.root
    }


}