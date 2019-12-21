package com.template.lipanampesa

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
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
    askPermission()

  }

  override fun onStart() {
    super.onStart()
    button.setOnClickListener {
      var intent = Intent(this@MainActivity, FloatingService::class.java)
      var lipaNaMpesa = LipaNaMpesa("34545455", "0712345678", "2000")
      intent.putExtra(LipaNaMpesaConstants.LIPANAMPESA, lipaNaMpesa)
      startService(intent)
    }
  }
  //ask for permission
  private fun askPermission(): Unit {
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


