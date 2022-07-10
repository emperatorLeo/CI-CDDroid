package com.intec.retirement

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.intec.retirement.databinding.ActivityMainBinding
import com.microsoft.appcenter.AppCenter
import com.microsoft.appcenter.analytics.Analytics
import com.microsoft.appcenter.crashes.Crashes
import java.lang.Exception

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        AppCenter.start(
            application,
            "53557573-09a0-4f4a-a076-2e123c4b3d88",
            Analytics::class.java,
            Crashes::class.java
        )

        binding.calculateButton.setOnClickListener {
            try {


                val interestRate = binding.interestEditText.text.toString().toFloat()
                val currentAge = binding.ageEditText.text.toString().toInt()
                val retirementAge = binding.retirementEditText.text.toString().toInt()
                val monthly = binding.monthlySavingsEditText.text.toString().toInt()
                val current = binding.currentEditText.text.toString().toInt()

                val properties: HashMap<String, String> = HashMap()
                properties["interest_rate"] = interestRate.toString()
                properties["current_age"] = currentAge.toString()
                properties["retirement_age"] = retirementAge.toString()
                properties["monthly_savings"] = monthly.toString()
                properties["current_savings"] = current.toString()


                if (interestRate <= 0) {
                    Analytics.trackEvent("Wrong_interest_rate", properties)
                }

                if (retirementAge <= currentAge) {
                    Analytics.trackEvent("Wrong_age", properties)
                }

                binding.resultTextView.text = "At the current rate of $interestRate%, saving\$$monthly a month"
            } catch (ex: Exception) {
                Analytics.trackEvent(ex.message)
            }
        }
    }
}