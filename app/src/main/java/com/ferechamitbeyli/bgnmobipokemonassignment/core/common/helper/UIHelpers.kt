package com.ferechamitbeyli.bgnmobipokemonassignment.core.common.helper

import androidx.appcompat.app.AppCompatActivity
import com.ferechamitbeyli.bgnmobipokemonassignment.core.common.util.Destination

object UIHelpers {
    fun handleActionBarVisibility(
        activity: AppCompatActivity,
        currentDestination: String) {
        when(currentDestination) {
            Destination.OVERLAY_PERMISSION.label -> activity.supportActionBar?.hide()
            else -> activity.supportActionBar?.show()
        }

    }
}