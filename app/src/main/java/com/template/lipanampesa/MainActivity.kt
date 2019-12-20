package com.template.lipanampesa

import android.content.Intent
import android.net.Uri.parse
import android.os.Build
import android.os.Build.VERSION
import android.os.Build.VERSION_CODES
import android.os.Bundle
import android.provider.Settings
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.lipanampesa.FloatingService
import com.lipanampesa.LipaNaMpesaConstants
import kotlinx.android.synthetic.main.activity_main.button

class MainActivity : AppCompatActivity() {
  val WINDOW_PERMISSION = 1000
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !Settings.canDrawOverlays(this)) {
      askPermission()
    }
  }

  private fun askPermission() {
    val intent = Intent(
      Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
      parse("package:$packageName")
    )

    startActivityForResult(intent, WINDOW_PERMISSION)
  }

  @RequiresApi(VERSION_CODES.M)
  override fun onStart() {
    super.onStart()
    button.setOnClickListener {
      when {
        VERSION.SDK_INT < VERSION_CODES.M -> {
          var intent = Intent(this@MainActivity, FloatingService::class.java)
          intent.putExtra(LipaNaMpesaConstants.PAYBILL, "2198621")
          intent.putExtra(LipaNaMpesaConstants.ACCOUNT, "0712345678")
          intent.putExtra(LipaNaMpesaConstants.AMOUNT, "2000")
          startService(intent)

        }
        Settings.canDrawOverlays(this) -> {
          var intent = Intent(this@MainActivity, FloatingService::class.java)
          intent.putExtra(LipaNaMpesaConstants.PAYBILL, "2198621")
          intent.putExtra(LipaNaMpesaConstants.ACCOUNT, "0712345678")
          intent.putExtra(LipaNaMpesaConstants.AMOUNT, "2000")
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
  }

}
