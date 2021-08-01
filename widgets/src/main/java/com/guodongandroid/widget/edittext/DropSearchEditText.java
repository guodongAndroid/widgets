package com.guodongandroid.widget.edittext;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.AutoCompleteTextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatAutoCompleteTextView;

import com.guodongandroid.widget.R;

public class DropSearchEditText extends AppCompatAutoCompleteTextView
 implements AutoCompleteTextView.OnDismissListener {

    private Drawable mDropDownDrawable;
    private Drawable mRiseUpDrawable;

    private int mDrawableWidth;
    private int mDrawableHeight;

    public DropSearchEditText(@NonNull Context context) {
        this(context, null);
    }

    public DropSearchEditText(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, android.R.attr.autoCompleteTextViewStyle);
    }

    public DropSearchEditText(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init(context, attrs, defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.DropSearchEditText, defStyleAttr, 0);

        mDropDownDrawable = ta.getDrawable(R.styleable.DropSearchEditText_dropDownDrawable);
        mRiseUpDrawable = ta.getDrawable(R.styleable.DropSearchEditText_riseUpDrawable);

        mDrawableWidth = ta.getDimensionPixelSize(R.styleable.DropSearchEditText_dropDrawableWidth, 15);
        mDrawableHeight = ta.getDimensionPixelSize(R.styleable.DropSearchEditText_dropDrawableHeight, 15);

        ta.recycle();
        
        showDropDownDrawable();
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int masked = event.getActionMasked();
        if (masked == MotionEvent.ACTION_UP) {
            Drawable drawable = getCompoundDrawablesRelative()[2];
            if (drawable != null) {
                if (event.getX() > getWidth() - drawable.getBounds().width()) {
                    showDropDown();
                    return true;
                }
            }
        }
        return super.onTouchEvent(event);
    }

    @Override
    public void showDropDown() {
        super.showDropDown();
        showRiseUpDrawable();
    }

    @Override
    public void onDismiss() {
        showDropDownDrawable();
    }

    private void showDropDownDrawable() {
        showDrawable(mDropDownDrawable);
    }

    private void showRiseUpDrawable() {
        showDrawable(mRiseUpDrawable);
    }

    private void showDrawable(Drawable drawable) {
        if (drawable == null) {
            return;
        }

        drawable.setBounds(0, 0, mDrawableWidth, mDrawableHeight);
        setCompoundDrawablesRelative(null, null, drawable, null);
    }
}
