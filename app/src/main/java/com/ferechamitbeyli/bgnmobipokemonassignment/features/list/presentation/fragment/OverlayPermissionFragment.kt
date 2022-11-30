package com.ferechamitbeyli.bgnmobipokemonassignment.features.list.presentation.fragment

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.ferechamitbeyli.bgnmobipokemonassignment.R
import com.ferechamitbeyli.bgnmobipokemonassignment.core.common.helper.UIHelpers.handleActionBarVisibility
import com.ferechamitbeyli.bgnmobipokemonassignment.databinding.FragmentOverlayPermissionBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OverlayPermissionFragment : Fragment() {
    private var _binding: FragmentOverlayPermissionBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOverlayPermissionBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        checkIfHasOverlayPermission()

        requireActivity().findViewById<Toolbar>(R.id.toolbar_main).isVisible = false

        setOnClickListeners()
    }

    private fun setOnClickListeners() {
        binding.buttonOverlayPermission.setOnClickListener {
            checkIfHasOverlayPermission {
                launchSettingsIntentForOverlayPermission()
            }
        }
    }

    private val overlayPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            decideNavigationOnSettingsIntent(it.resultCode)
        }

    private fun checkIfHasOverlayPermission(action: (() -> Unit)? = null) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) navigateToPokemonListFragment()
        else {
            if (!Settings.canDrawOverlays(requireContext())) {
                action?.invoke()
            } else {
                navigateToPokemonListFragment()
            }
        }
    }

    private fun launchSettingsIntentForOverlayPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val intent = Intent(
                Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                Uri.parse("package:${requireActivity().packageName}")
            )
            overlayPermissionLauncher.launch(intent)
        }
    }

    private fun decideNavigationOnSettingsIntent(resultCode: Int) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) navigateToPokemonListFragment()
        else {
            if (resultCode != 0) return
            if (Settings.canDrawOverlays(requireContext())) {
                navigateToPokemonListFragment()
            } else {
                findNavController().navigateUp()
            }
        }
    }

    private fun navigateToPokemonListFragment() {
        if (findNavController().currentDestination?.id == R.id.overlayPermissionFragment) {
            findNavController().navigate(R.id.action_overlayPermissionFragment_to_pokemonListFragment)
        }
    }
}