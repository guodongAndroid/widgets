package com.guodongandroid.widgets.app

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import com.guodongandroid.widgets.app.databinding.ActivityMarqueeTextViewBinding

/**
 * Created by guodongAndroid on 2021/8/4.
 */
class MarqueeTextViewActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityMarqueeTextViewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityMarqueeTextViewBinding.inflate(LayoutInflater.from(this))
        setContentView(mBinding.root)

        mBinding.btnMarquee.setOnClickListener {
            mBinding.btnMarquee.isSelected = !mBinding.btnMarquee.isSelected
            val selected: Boolean = mBinding.btnMarquee.isSelected
            if (selected) {
                mBinding.btnMarquee.text = "停止滚动"
            } else {
                mBinding.btnMarquee.text = "开始滚动"
            }

            mBinding.androidxHorizontal.isMarquee = selected
            mBinding.androidxVertical.isMarquee = selected
        }
    }
}