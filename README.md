# Lipa-Na-Mpesa-Android-Widget
**Floating Lipa na Mpesa Widget**
[ ![Download](https://api.bintray.com/packages/skyways/LipaNaMpesa/LipaNaMpesa/images/download.svg?version=0.1.0) ](https://bintray.com/skyways/LipaNaMpesa/LipaNaMpesa/0.1.0/link)

This a simple android library, that help create a floating widget with mpesa payment details or any payment gateway that has the same payment format.
This offers great user experience when making payment manually. The user should not gram the payment details, the widget float in all the screens and the can click and copy the details they need.

**How to use**

Gradle: 
```java
 implementation 'com.lipanampesa:LipaNaMpesa:0.1.0'
 ```
 Ask for permission to show a floating widget.
 
 ```kotlin
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
  ```
  Show the widget.
    [a Full example](https://github.com/skyways/Lipa-Na-Mpesa-Android-Widget/blob/master/app/src/main/java/com/template/lipanampesa/MainActivity.kt)
       
        
  ```kotlin
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
   ```
  
        
        
        
        
