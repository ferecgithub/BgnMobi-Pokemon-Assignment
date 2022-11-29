package com.ferechamitbeyli.bgnmobipokemonassignment.core.common

import android.Manifest.permission.SYSTEM_ALERT_WINDOW
import android.content.Context
import androidx.fragment.app.Fragment
import com.ferechamitbeyli.bgnmobipokemonassignment.R
import com.ferechamitbeyli.bgnmobipokemonassignment.core.common.util.UiText
import com.vmadalin.easypermissions.EasyPermissions

object PermissionManager {
    private const val PERMISSION_OVERLAY = 1

    /** Overlay Permission functions **/
    fun hasOverlayPermission(context: Context) =
        EasyPermissions.hasPermissions(
            context,
            SYSTEM_ALERT_WINDOW
        )

    fun requestOverlayPermission(fragment: Fragment) {
        EasyPermissions.requestPermissions(
            fragment,
            UiText.StringResource(R.string.overlayPermissionWarning)
                .asString(fragment.requireContext()).toString(),
            PERMISSION_OVERLAY,
            SYSTEM_ALERT_WINDOW
        )
    }

}