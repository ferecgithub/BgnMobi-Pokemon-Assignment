package com.ferechamitbeyli.bgnmobipokemonassignment.core.common.helper

import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import com.ferechamitbeyli.bgnmobipokemonassignment.core.common.util.Destination

object UIHelpers {
    fun handleActionBarVisibility(
        activity: AppCompatActivity,
        currentDestinationLabel: String) {
        when(currentDestinationLabel) {
            Destination.OVERLAY_PERMISSION.label -> activity.supportActionBar?.hide()
            else -> activity.supportActionBar?.show()
        }
    }

    fun setCurrentLabelOntoActionBar(
        activity: AppCompatActivity,
        currentDestinationLabel: String
    ) {
        activity.supportActionBar?.title = currentDestinationLabel
    }
}