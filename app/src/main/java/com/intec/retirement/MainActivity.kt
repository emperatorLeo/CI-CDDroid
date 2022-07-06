package com.intec.retirement

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.microsoft.appcenter.AppCenter
import com.microsoft.appcenter.analytics.Analytics
import com.microsoft.appcenter.crashes.Crashes

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        AppCenter.start(
            application,
            "53557573-09a0-4f4a-a076-2e123c4b3d88",
            Analytics::class.java,
            Crashes::class.java
        )
    }
}