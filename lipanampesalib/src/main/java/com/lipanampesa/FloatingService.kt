package com.lipanampesa

import android.app.Service
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.graphics.PixelFormat
import android.os.IBinder
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.WindowManager
import android.view.WindowManager.LayoutParams
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isVisible
import kotlin.math.roundToInt

class FloatingService : Service(), View.OnClickListener {
  lateinit var windowManager: WindowManager
  lateinit var floatingBanner: View
  lateinit var topLabel: View
  lateinit var topExpand: View
  lateinit var collapseView: View
  lateinit var minimizeButton: View
  lateinit var accountView: View
  lateinit var paybillView: View
  lateinit var amountView: View
  lateinit var accountTextView: TextView
  lateinit var payBillTextView: TextView
  lateinit var amountTextView: TextView
  lateinit var params: LayoutParams
  private var initialX = 0
  private var initialY = 0
  private var initialTouchX = 0f
  private var initialTouchY = 0f

  override fun onBind(intent: Intent): IBinder? {
    return null
  }

  override fun onClick(v: View?) {
  }

  override fun onStart(intent: Intent?, startId: Int) {
    super.onStart(intent, startId)
    val extras = intent!!.extras
    if (extras == null) {
      Log.d("Service", "lipa na mpesa param are empty")
      throw (Throwable("Params required"))
    } else {
      val amount = extras[LipaNaMpesaConstants.AMOUNT] as String?
      val account = extras[LipaNaMpesaConstants.ACCOUNT] as String?
      val payBill = extras[LipaNaMpesaConstants.PAYBILL] as String?
      amountTextView.text = amount
      accountTextView.text = account
      payBillTextView.text = payBill
    }
  }

  override fun onCreate() {
    super.onCreate()
    //bind ui
    floatingBanner = LayoutInflater.from(this).inflate(R.layout.floating_banner, null)
    collapseView = floatingBanner.findViewById(R.id.collapseView)
    topLabel = floatingBanner.findViewById(R.id.topCollapsible)
    minimizeButton = floatingBanner.findViewById(R.id.minimizeButtton)
    topExpand = floatingBanner.findViewById(R.id.topExpand)
    accountView = floatingBanner.findViewById(R.id.accountView)
    paybillView = floatingBanner.findViewById(R.id.payBillView)
    payBillTextView = floatingBanner.findViewById(R.id.payBillNumber)
    accountTextView = floatingBanner.findViewById(R.id.accountTxt)
    amountView = floatingBanner.findViewById(R.id.amountView)
    amountTextView = floatingBanner.findViewById(R.id.amount)

    //layout params
    val params = LayoutParams(
      LayoutParams.WRAP_CONTENT,
      LayoutParams.WRAP_CONTENT,
      LayoutParams.TYPE_APPLICATION_OVERLAY,
      LayoutParams.FLAG_NOT_FOCUSABLE,
      PixelFormat.TRANSLUCENT
    )
    params.gravity = Gravity.TOP or Gravity.RIGHT
    windowManager = getSystemService(Context.WINDOW_SERVICE) as WindowManager
    windowManager.addView(floatingBanner, params)

    topLabel.setOnClickListener {
      collapseExpand()
    }

    minimizeButton.setOnClickListener {
      collapseLayout()
    }
    topExpand.setOnClickListener {
      collapseExpand()
    }
    //copy data

    paybillView.setOnClickListener {
      copyText(payBillTextView)
    }
    accountView.setOnClickListener {
      copyText(accountTextView)
    }
    amountView.setOnClickListener {
      copyText(amountTextView)
    }

    topLabel.setOnTouchListener(object : View.OnTouchListener {
      override fun onTouch(v: View?, event: MotionEvent): Boolean {
        when (event.action) {
          MotionEvent.ACTION_DOWN -> {
            initialX = params.x
            initialY = params.y
            initialTouchX = event.rawX
            initialTouchY = event.rawY
            return true
          }
          MotionEvent.ACTION_UP -> {

            return true
          }
          MotionEvent.ACTION_MOVE -> {
            //this code is helping the widget to move around the screen with fingers
            params.x = (initialX + (event.rawX - initialTouchX)).roundToInt()
            params.y = (initialY + (event.rawY - initialTouchY)).roundToInt()
            windowManager.updateViewLayout(floatingBanner, params)
            return true
          }
        }
        return false
      }
    })
  }

  fun collapseLayout() {
    topExpand.visibility = View.VISIBLE
    collapseView.visibility = View.GONE
  }

  fun expandLayout() {
    collapseView.visibility = View.VISIBLE
    topExpand.visibility = View.GONE

  }

  fun copyText(view: View?) {
    lateinit var clipboardManager: ClipboardManager

    val text: String = (view as TextView).text.toString()
    if (text.isNotEmpty()) {
      clipboardManager =
        getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
      val clipData = ClipData.newPlainText("lipaKey", text)
      clipboardManager.setPrimaryClip(clipData)
      Toast.makeText(applicationContext, "Copied", Toast.LENGTH_SHORT).show()
    } else {
      Toast.makeText(applicationContext, "No text to be copied", Toast.LENGTH_SHORT).show()
    }
  }

  fun collapseExpand() {
    if (collapseView.isVisible) {
      collapseLayout()
    } else {
      expandLayout()
    }
  }

  override fun onDestroy() {
    super.onDestroy()
    windowManager.removeView(floatingBanner)
  }
}
