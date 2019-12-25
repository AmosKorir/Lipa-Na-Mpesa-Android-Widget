package com.template.lipanampesa

import android.content.Intent
import android.net.Uri
import android.os.Build.VERSION
import android.os.Build.VERSION_CODES
import android.os.Bundle
import android.provider.Settings
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.lipanampesa.FloatingService
import com.lipanampesa.LipaNaMpesa
import com.lipanampesa.LipaNaMpesaConstants
import kotlinx.android.synthetic.main.activity_main.button

class MainActivity : AppCompatActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

  }

  override fun onStart() {
    super.onStart()
    button.setOnClickListener {
      showWidget()
    }
  }

  private fun showWidget() {
    when {
      VERSION.SDK_INT < VERSION_CODES.M -> {
        var intent = Intent(this@MainActivity, FloatingService::class.java)
        var lipaNaMpesa =
          LipaNaMpesa(
            "34545455",
            "0712345678",
            "2000",
            resources.getColor(R.color.colorPrimaryDark)
          )
        intent.putExtra(LipaNaMpesaConstants.LIPANAMPESA, lipaNaMpesa)
        startService(intent)

      }
      Settings.canDrawOverlays(this) -> {
        var intent = Intent(this@MainActivity, FloatingService::class.java)
        var lipaNaMpesa =
          LipaNaMpesa(
            "34545455",
            "0712345678",
            "2000",
            resources.getColor(R.color.colorPrimaryDark)
          )
        intent.putExtra(LipaNaMpesaConstants.LIPANAMPESA, lipaNaMpesa)
        startService(intent)

      }
      else -> {
        askPermission()
        Toast.makeText(
          this,
          "You need System Alert Window Permission to do this",
          Toast.LENGTH_SHORT
        ).show()
      }
    }
  }

  //ask for permission
  private fun askPermission() {
    val intent = Intent(
      Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
      Uri.parse("package:$packageName")
    )
    ActivityCompat.startActivityForResult(
      this,
      intent,
      LipaNaMpesaConstants.SYSTEM_ALERT_WINDOW_PERMISSION,
      null
    )
  }
}


