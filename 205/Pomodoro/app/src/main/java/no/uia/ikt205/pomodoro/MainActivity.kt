package no.uia.ikt205.pomodoro

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import no.uia.ikt205.pomodoro.util.millisecondsToDescriptiveTime

class MainActivity : AppCompatActivity() {

    lateinit var timer:CountDownTimer
    lateinit var startButton:Button
    lateinit var countDownBtn30:Button
    lateinit var countDownBtn60:Button
    lateinit var countDownBtn90:Button
    lateinit var countDownBtn120:Button
    lateinit var coutdownDisplay:TextView

    var countdownStart = 5000L
    var countdown30min = 1800000L
    var countdown60min = 3600000L
    var countdown90min = 5400000L
    var countdown120min = 7200000L
    val timeTicks = 1000L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

       startButton = findViewById<Button>(R.id.startCountdownButton)
       startButton.setOnClickListener(){
           startCountDown(it)
       }

        countDownBtn30 = findViewById<Button>(R.id.countdown30)
        countDownBtn30.setOnClickListener(){
            countdownStart = countdown30min
        }

        countDownBtn60 = findViewById<Button>(R.id.countdown60)
        countDownBtn60.setOnClickListener(){
            countdownStart = countdown60min
        }

        countDownBtn90 = findViewById<Button>(R.id.countdown90)
        countDownBtn90.setOnClickListener(){
            countdownStart = countdown90min
        }

        countDownBtn120 = findViewById<Button>(R.id.countdown120)
        countDownBtn120.setOnClickListener(){
            countdownStart = countdown120min
        }

       coutdownDisplay = findViewById<TextView>(R.id.countDownView)

    }

    fun startCountDown(v: View){

        timer = object : CountDownTimer(countdownStart,timeTicks) {
            override fun onFinish() {
                Toast.makeText(this@MainActivity,"Arbeidsøkt er ferdig", Toast.LENGTH_SHORT).show()
                // Bugfix
                // Her kan man disable knappene ved å bruke isEnabled
                startButton.isEnabled = true
                countDownBtn30.isEnabled = true
                countDownBtn60.isEnabled = true
                countDownBtn90.isEnabled = true
                countDownBtn120.isEnabled = true
            }

            override fun onTick(millisUntilFinished: Long) {
               updateCountDownDisplay(millisUntilFinished)
            }
        }

        timer.start()
    }

    fun updateCountDownDisplay(timeInMs:Long){
        coutdownDisplay.text = millisecondsToDescriptiveTime(timeInMs)

        startButton.isEnabled = false
        countDownBtn30.isEnabled = false
        countDownBtn60.isEnabled = false
        countDownBtn90.isEnabled = false
        countDownBtn120.isEnabled = false
    }

}