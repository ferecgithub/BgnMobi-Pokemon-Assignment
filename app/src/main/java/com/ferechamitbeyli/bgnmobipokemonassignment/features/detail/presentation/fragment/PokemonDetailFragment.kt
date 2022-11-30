package com.ferechamitbeyli.bgnmobipokemonassignment.features.detail.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.ferechamitbeyli.bgnmobipokemonassignment.databinding.FragmentPokemonDetailBinding
import com.ferechamitbeyli.bgnmobipokemonassignment.features.detail.presentation.viewmodel.PokemonDetailViewModel

class PokemonDetailFragment : Fragment() {
    private var _binding: FragmentPokemonDetailBinding? = null
    private val binding get() = _binding!!

    private val viewModel: PokemonDetailViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPokemonDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setOnClickListeners()
        setUpUI()
    }

    private fun setOnClickListeners() {

    }

    private fun setUpUI() {

    }
}