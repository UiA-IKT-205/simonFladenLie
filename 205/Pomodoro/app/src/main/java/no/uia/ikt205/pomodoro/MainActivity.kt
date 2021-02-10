package no.uia.ikt205.pomodoro

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.*
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import no.uia.ikt205.pomodoro.util.millisecondsToDescriptiveTime
import no.uia.ikt205.pomodoro.util.minutesToMilliSeconds

class MainActivity : AppCompatActivity() {

    lateinit var timer:CountDownTimer
    lateinit var startButton:Button
    lateinit var countdownViewWork:TextView
    lateinit var countdownViewPause:TextView
    lateinit var workSessionsIntervals:EditText

    var currentDisplayedWorkTime = 0L
    var currentDisplayedPauseTime = 0L
    val timeTicks = 1000L
    var workSession = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        startButton = findViewById<Button>(R.id.startCountdownButton)
        startButton.setOnClickListener(){
            startWorkCountDown(it)
        }

        countdownViewWork = findViewById<TextView>(R.id.countdownView)
        countdownViewPause = findViewById<TextView>(R.id.countdownViewPause)


        val setWorkingSeekBar = findViewById<SeekBar>(R.id.setWorkingTimeSeekbar)
        setWorkingSeekBar?.setOnSeekBarChangeListener(object :
                SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seek: SeekBar, progress: Int, fromUser: Boolean) {
                val workSeekbarChanges = minutesToMilliSeconds(progress.toLong())
                countdownViewWork.text = millisecondsToDescriptiveTime(workSeekbarChanges)
                currentDisplayedWorkTime = minutesToMilliSeconds(progress.toLong())
            }

            override fun onStartTrackingTouch(seek: SeekBar) {
            }

            override fun onStopTrackingTouch(seek: SeekBar) {
                Toast.makeText(this@MainActivity,
                        "Press START to begin a working period of: \n                        " +
                                "" + seek.progress + " minutes",

                        Toast.LENGTH_SHORT).show()
            }
        })

        val setPauseTimeSeekBar = findViewById<SeekBar>(R.id.setPauseTimeSeekbar)
        setPauseTimeSeekBar?.setOnSeekBarChangeListener(object :
                SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seek: SeekBar, progress: Int, fromUser: Boolean) {
                val pauseSeekbarChanges = minutesToMilliSeconds(progress.toLong())
                countdownViewPause.text = millisecondsToDescriptiveTime(pauseSeekbarChanges)
                currentDisplayedPauseTime = minutesToMilliSeconds(progress.toLong())
            }

            override fun onStartTrackingTouch(seek: SeekBar) {
            }

            override fun onStopTrackingTouch(seek: SeekBar) {
                Toast.makeText(this@MainActivity,
                        "Press START to begin a session with: \n                 " +
                                "" + seek.progress + " minutes brakes",

                        Toast.LENGTH_SHORT).show()
            }
        })

        workSessionsIntervals = findViewById<EditText>(R.id.workSessionsIntervals)
        workSessionsIntervals.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                readWorkSessions()
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int,
                                           after: Int) {
            }

            override fun afterTextChanged(s: Editable) {
            }
        })
    }

    fun startWorkCountDown(v: View){
        timer = object : CountDownTimer(currentDisplayedWorkTime,timeTicks) {
            override fun onFinish() {
                Toast.makeText(this@MainActivity,"The session is over, enjoy a break", Toast.LENGTH_SHORT).show()
                startPauseCountdown()
                val setPauseTimeSeekBar = findViewById<SeekBar>(R.id.setPauseTimeSeekbar)
                val setWorkingSeekBar = findViewById<SeekBar>(R.id.setWorkingTimeSeekbar)
                val workSessionsIntervals = findViewById<EditText>(R.id.workSessionsIntervals)

                startButton.isEnabled = true
                workSessionsIntervals.isEnabled = true
                setPauseTimeSeekBar.isEnabled = true
                setWorkingSeekBar.isEnabled = true

            }
            override fun onTick(millisUntilFinished: Long) {
               updateWorkCountDownDisplay(millisUntilFinished)
            }
        }
        timer.start()
    }

    fun startPauseCountdown(){
        val startButton = findViewById<Button>(R.id.startCountdownButton)

        timer = object : CountDownTimer(currentDisplayedPauseTime,timeTicks) {
            override fun onFinish() {
                Toast.makeText(this@MainActivity,"The break is over, start working", Toast.LENGTH_SHORT).show()
                checkIntervalNumber()
                val setPauseTimeSeekBar = findViewById<SeekBar>(R.id.setPauseTimeSeekbar)
                val setWorkingSeekBar = findViewById<SeekBar>(R.id.setWorkingTimeSeekbar)
                val workSessionsIntervals = findViewById<EditText>(R.id.workSessionsIntervals)

                startButton.isEnabled = true
                workSessionsIntervals.isEnabled = true
                setPauseTimeSeekBar.isEnabled = true
                setWorkingSeekBar.isEnabled = true
            }

            override fun onTick(millisUntilFinished: Long) {
                updatePauseCountDownDisplay(millisUntilFinished)
            }
        }
        timer.start()

    }

    fun updatePauseCountDownDisplay(timeInMs:Long){
        countdownViewPause.text = millisecondsToDescriptiveTime(timeInMs)
        val setPauseTimeSeekBar = findViewById<SeekBar>(R.id.setPauseTimeSeekbar)
        val setWorkingSeekBar = findViewById<SeekBar>(R.id.setWorkingTimeSeekbar)
        val workSessionsIntervals = findViewById<EditText>(R.id.workSessionsIntervals)

        workSessionsIntervals.isEnabled = false
        startButton.isEnabled = false
        setPauseTimeSeekBar.isEnabled = false
        setWorkingSeekBar.isEnabled = false
    }

    fun updateWorkCountDownDisplay(timeInMs:Long){
        countdownViewWork.text = millisecondsToDescriptiveTime(timeInMs)
        val setWorkingSeekBar = findViewById<SeekBar>(R.id.setWorkingTimeSeekbar)
        val setPauseTimeSeekBar = findViewById<SeekBar>(R.id.setPauseTimeSeekbar)
        val workSessionsIntervals = findViewById<EditText>(R.id.workSessionsIntervals)

        workSessionsIntervals.isEnabled = false
        startButton.isEnabled = false
        setWorkingSeekBar.isEnabled = false
        setPauseTimeSeekBar.isEnabled = false
    }

    fun readWorkSessions() {
        val interval = workSessionsIntervals.text.toString()

        if (interval == ""){
            return
        }

        workSession = interval.toInt()
    }

    fun checkIntervalNumber() {
        val workSessionsIntervals = findViewById<EditText>(R.id.workSessionsIntervals)

        if (workSession > 1) {
            workSession -= 1
            workSessionsIntervals.setText(workSession.toString())
            startWorkCountDown(startButton)
        }
    }
}