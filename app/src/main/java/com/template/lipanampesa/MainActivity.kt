package com.template.lipanampesa

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.lipanampesa.FloatingService
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
      var intent = Intent(this@MainActivity, FloatingService::class.java)
      intent.putExtra(LipaNaMpesaConstants.PAYBILL, "2198621")
      intent.putExtra(LipaNaMpesaConstants.ACCOUNT, "0712345678")
      intent.putExtra(LipaNaMpesaConstants.AMOUNT, "2000")
      startService(intent)
    }
  }
}


