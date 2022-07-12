package com.intec.retirement

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.intec.retirement.databinding.ActivityMainBinding
import com.microsoft.appcenter.AppCenter
import com.microsoft.appcenter.analytics.Analytics
import com.microsoft.appcenter.crashes.Crashes
import java.lang.Exception
import kotlin.math.pow

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        AppCenter.start(
            application,
            "c260b5fe-661c-44d9-9554-579bc9742da3",
            Analytics::class.java,
            Crashes::class.java
        )

        binding.calculateButton.setOnClickListener {
            try {


                val interestRate = binding.interestEditText.text.toString().toFloat()
                val currentAge = binding.ageEditText.text.toString().toInt()
                val retirementAge = binding.retirementEditText.text.toString().toInt()
                val monthly = binding.monthlySavingsEditText.text.toString().toFloat()
                val current = binding.currentEditText.text.toString().toFloat()

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

                val futureSavings = calculateRetirement(interestRate, current, monthly, (retirementAge - currentAge)*12)

                binding.resultTextView.text = "At the current rate of $interestRate%, saving \$$monthly a month you will have \$${String.format("%f, futureSavings")} by $retirementAge."
            } catch (ex: Exception) {
                Analytics.trackEvent(ex.message)
            }
        }
    }

    fun calculateRetirement(interestRate: Float, currentSavings: Float, monthly: Float, numMonths: Int): Float {
        var futureSavings = currentSavings * (1+(interestRate/100/12)).pow(numMonths)

        for (i in 1..numMonths) {
            futureSavings += monthly * (1+(interestRate/100/12)).pow(i)
        }

        return  futureSavings
    }
}
