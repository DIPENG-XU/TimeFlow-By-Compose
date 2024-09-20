package com.apollo.timeflow.basesupport

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.apollo.timeflow.utils.logD

open class BaseActivity(
    private val selfName: String,
) : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        "onCreate-->$selfName".logD("MonitorLifecycle")
    }

    override fun onStart() {
        super.onStart()
        "onStart-->$selfName".logD("MonitorLifecycle")
    }

    override fun onRestart() {
        super.onRestart()
        "onRestart-->$selfName".logD("MonitorLifecycle")
    }

    override fun onResume() {
        super.onResume()
        "onResume-->$selfName".logD("MonitorLifecycle")
    }

    override fun onPause() {
        super.onPause()
        "onPause-->$selfName".logD("MonitorLifecycle")
    }

    override fun onStop() {
        super.onStop()
        "onStop-->$selfName".logD("MonitorLifecycle")
    }

    override fun onDestroy() {
        super.onDestroy()
        "onDestroy-->$selfName".logD("MonitorLifecycle")
    }
}