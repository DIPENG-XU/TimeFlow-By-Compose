package com.apollo.timeflow.basesupport

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.apollo.timeflow.utils.logd

open class BaseActivity(
    private val selfName: String,
) : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        "onCreate-->$selfName".logd("MonitorLifecycle")
    }

    override fun onStart() {
        super.onStart()
        "onStart-->$selfName".logd("MonitorLifecycle")
    }

    override fun onRestart() {
        super.onRestart()
        "onRestart-->$selfName".logd("MonitorLifecycle")
    }

    override fun onResume() {
        super.onResume()
        "onResume-->$selfName".logd("MonitorLifecycle")
    }

    override fun onPause() {
        super.onPause()
        "onPause-->$selfName".logd("MonitorLifecycle")
    }

    override fun onStop() {
        super.onStop()
        "onStop-->$selfName".logd("MonitorLifecycle")
    }

    override fun onDestroy() {
        super.onDestroy()
        "onDestroy-->$selfName".logd("MonitorLifecycle")
    }
}