package com.ferechamitbeyli.bgnmobipokemonassignment.features.list.presentation.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.ferechamitbeyli.bgnmobipokemonassignment.R
import com.ferechamitbeyli.bgnmobipokemonassignment.core.common.PermissionManager
import com.ferechamitbeyli.bgnmobipokemonassignment.core.common.PermissionManager.requestOverlayPermission
import com.ferechamitbeyli.bgnmobipokemonassignment.databinding.FragmentOverlayPermissionBinding
import com.google.android.material.snackbar.Snackbar
import com.vmadalin.easypermissions.EasyPermissions
import com.vmadalin.easypermissions.dialogs.SettingsDialog

class OverlayPermissionFragment : Fragment(), EasyPermissions.PermissionCallbacks {
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

        setOnClickListeners()

    }

    private fun setOnClickListeners() {

    }

    override fun onResume() {
        super.onResume()

        checkIfHasOverlayPermission()
    }

    private fun checkIfHasOverlayPermission() {
        if (!PermissionManager.hasOverlayPermission(requireContext())) {
            requestOverlayPermission(this)
        } else {
            navigateToPokemonListFragment()
        }
    }



    private fun navigateToPokemonListFragment() {
        if (findNavController().currentDestination?.id == R.id.overlayPermissionFragment) {
            findNavController().navigate(R.id.action_overlayPermissionFragment_to_pokemonListFragment)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }

    override fun onPermissionsDenied(requestCode: Int, perms: List<String>) {
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            SettingsDialog.Builder(requireActivity()).build().show()
        } else {
            requestOverlayPermission(this)
        }
    }

    override fun onPermissionsGranted(requestCode: Int, perms: List<String>) {
        Snackbar.make(binding.root, getString(R.string.overlayPermissionGrantedMessage), Snackbar.LENGTH_SHORT).show()
    }

}