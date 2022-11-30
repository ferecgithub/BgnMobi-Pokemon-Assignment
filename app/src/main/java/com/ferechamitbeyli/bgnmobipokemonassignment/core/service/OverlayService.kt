package com.ferechamitbeyli.bgnmobipokemonassignment.core.service

import android.app.Service
import android.content.Context
import android.content.Intent
import android.graphics.PixelFormat
import android.os.Build
import android.os.IBinder
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import coil.load
import com.ferechamitbeyli.bgnmobipokemonassignment.R
import com.ferechamitbeyli.bgnmobipokemonassignment.core.data.model.pokemon_detail.PokemonDetail
import com.ferechamitbeyli.bgnmobipokemonassignment.databinding.LayoutOverlayBinding

/**
 * Created by Ferec Hamitbeyli on 1.12.2022.
 */

class OverlayService : Service() {

    private lateinit var windowManager: WindowManager
    private lateinit var layoutInflater: LayoutInflater
    private lateinit var params: WindowManager.LayoutParams
    private lateinit var bindingOverlayWindow: LayoutOverlayBinding

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        bindingOverlayWindow.imageViewLayoutOverlayPokemonFront.load(intent?.getStringExtra("front_img")) {
            crossfade(true)
            crossfade(1000)
            error(R.drawable.ic_warning)
        }

        bindingOverlayWindow.imageViewLayoutOverlayPokemonBack.load(intent?.getStringExtra("back_img")) {
            crossfade(true)
            crossfade(1000)
            error(R.drawable.ic_warning)
        }

        bindingOverlayWindow.textViewLayoutOverlayPokemonName.text = intent?.getStringExtra("name")

        return super.onStartCommand(intent, flags, startId)
    }

    override fun onCreate() {
        super.onCreate()

        windowManager = getSystemService(Context.WINDOW_SERVICE) as WindowManager

        layoutInflater = this.getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater

        bindingOverlayWindow = LayoutOverlayBinding.inflate(layoutInflater)

        val layoutFlag = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
        } else {
            WindowManager.LayoutParams.TYPE_PHONE
        }

        params = WindowManager.LayoutParams(
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.WRAP_CONTENT,
            layoutFlag,
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
            PixelFormat.RGBA_8888
        )

        bindingOverlayWindow.buttonLayoutErrorCloseWindow.setOnClickListener {
            stopSelf()
        }

        params.gravity = Gravity.CENTER

        windowManager.addView(bindingOverlayWindow.root, params)
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onDestroy() {
        super.onDestroy()
        windowManager.removeView(bindingOverlayWindow.root)
    }
}