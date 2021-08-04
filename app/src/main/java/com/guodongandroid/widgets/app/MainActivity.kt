package com.guodongandroid.widgets.app

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.guodongandroid.widgets.app.databinding.ActivityMainBinding

/**
 * Created by guodongAndroid on 2021/8/2.
 */
class MainActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityMainBinding.inflate(LayoutInflater.from(this))
        setContentView(mBinding.root)
    }

    fun onClick(view: View) {
        when (view.id) {
            R.id.btn_drop_search -> {
                startActivity(DropSearchEditTextActivity::class.java)
            }
            R.id.btn_marquee -> {
                startActivity(MarqueeTextViewActivity::class.java)
            }
            else -> {
            }
        }
    }

    private fun <T : Activity> startActivity(clz: Class<T>) {
        startActivity(Intent(this, clz))
    }
}