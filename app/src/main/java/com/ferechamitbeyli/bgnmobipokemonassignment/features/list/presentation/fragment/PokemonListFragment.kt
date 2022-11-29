package com.ferechamitbeyli.bgnmobipokemonassignment.features.list.presentation.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ferechamitbeyli.bgnmobipokemonassignment.R
import com.ferechamitbeyli.bgnmobipokemonassignment.databinding.FragmentOverlayPermissionBinding
import com.ferechamitbeyli.bgnmobipokemonassignment.databinding.FragmentPokemonListBinding

class PokemonListFragment : Fragment() {
    private var _binding: FragmentPokemonListBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPokemonListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setOnClickListeners()

    }

    private fun setOnClickListeners() {

    }

    override fun onResume() {
        super.onResume()

    }


}