package com.muniaccidente.check_my_pills

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var preferenceManager: PreferenceManager
    private var userText1: String = ""
    private var userText2: String = ""
    private var userText3: String = ""
    private var userText4: String = ""
    private var check1: Int = 0
    private var check2: Int = 0
    private var check3: Int = 0
    private var check4: Int = 0
    private var isUPDATE: Boolean = false
//    private val PILL_CH_ID = "PILL_CHANNEL_ID"
//    private val NotificationID = 14881337

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        createNotificationChannel()
//        showMyNotification()
        preferenceManager = PreferenceManager(this)


        saveData()
        if (!isUPDATE) {
            observeData()
            isUPDATE = true
//            Toast.makeText(this, userText1, Toast.LENGTH_LONG).show()
        }
    }

    private fun observeData() {

        preferenceManager.userDataFlowText1.asLiveData().observe(this, {
            userText1 = it.toString()
            user_text1.setText(it.toString())
//            Toast.makeText(this, userText1, Toast.LENGTH_LONG).show()
//            Toast.makeText(this, it.toString(), Toast.LENGTH_LONG).show()
        })
        preferenceManager.userDataFlowText2.asLiveData().observe(this, {
            userText2 = it.toString()
            user_text2.setText(it.toString())
        })
        preferenceManager.userDataFlowText3.asLiveData().observe(this, {
            userText3 = it.toString()
            user_text3.setText(it.toString())
        })
        preferenceManager.userDataFlowText4.asLiveData().observe(this, {
            userText4 = it.toString()
            user_text4.setText(it.toString())
        })

        preferenceManager.userDataFlowLastUpdate.asLiveData().observe(this, { update ->
            if (update != java.time.LocalDate.now().toString()) {
                check1 = 0
                check2 = 0
                check3 = 0
                check4 = 0
//                save()
            } else {
                preferenceManager.userDataFlowPill1.asLiveData().observe(this, { pill1 ->
                    check1 = pill1.toInt()
                })
                preferenceManager.userDataFlowPill2.asLiveData().observe(this, { pill2 ->
                    check2 = pill2.toInt()
                })
                preferenceManager.userDataFlowPill3.asLiveData().observe(this, { pill3 ->
                    check3 = pill3.toInt()
                })
                preferenceManager.userDataFlowPill4.asLiveData().observe(this, { pill4 ->
                    check4 = pill4.toInt()
                })
            }
            textView.text = check1.toString()
            textView2.text = check2.toString()
            textView3.text = check3.toString()
            textView4.text = check4.toString()
        })
    }


    private fun saveData() {

        button.setOnClickListener {
            check1++
            textView.text = check1.toString()
            save()
        }
        button2.setOnClickListener {
            check2++
            textView2.text = check2.toString()
            save()
        }
        button3.setOnClickListener {
            check3++
            textView3.text = check3.toString()
            save()

        }
        button4.setOnClickListener {
            check4++
            textView4.text = check4.toString()
            save()
        }
//        save()
    }

    private fun save() {

        userText1 = user_text1.text.toString()
        userText2 = user_text2.text.toString()
        userText3 = user_text3.text.toString()
        userText4 = user_text4.text.toString()

        lifecycleScope.launch {
            preferenceManager.storeUser(userText1, userText2, userText3, userText4, check1, check2, check3, check4)
        }
    }

    override fun onDestroy() {
        save()
        super.onDestroy()
    }

    override fun onBackPressed() {
        onDestroy()
        super.onBackPressed()
    }

    override fun onPause() {
        save()
        super.onPause()
    }

//    private fun showMyNotification() {
//        val now = java.time.LocalTime.now().toString().split(".")[0]
////        Toast.makeText(this, now, Toast.LENGTH_LONG).show()
//        if (now == "09:00:00" || now == "12:00:00" || now == "21:00:00") {
//            with(NotificationManagerCompat.from(this)) {
//                // notificationId is a unique int for each notification that you must define
//                notify(NotificationID, builder.build())
//            }
//        }
//    }
//
//    private var builder = NotificationCompat.Builder(this, PILL_CH_ID)
//        .setContentTitle("Пора!")
//        .setContentText("Дед, пей таблетки")
//        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
//
//    private fun createNotificationChannel() {
//        // Create the NotificationChannel, but only on API 26+ because
//        // the NotificationChannel class is new and not in the support library
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            val name = getString(R.string.channel_name)
//            val descriptionText = getString(R.string.channel_description)
//            val importance = NotificationManager.IMPORTANCE_DEFAULT
//            val channel = NotificationChannel(PILL_CH_ID, name, importance).apply {
//                description = descriptionText
//            }
//            // Register the channel with the system
//            val notificationManager: NotificationManager =
//                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
//            notificationManager.createNotificationChannel(channel)
//        }
//    }
}