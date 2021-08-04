package com.guodong.android.widget.edittext;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ListAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatAutoCompleteTextView;

import com.guodongandroid.widget.R;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by guodongAndroid on 2021/8/2.
 */
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

        fixPadding();

        showDropDownDrawable();

        setOnDismissListener(this);
    }

    private void fixPadding() {
        int start = getPaddingStart();
        int end = getPaddingEnd();

        if (start == 0) {
            start = 32;
        }

        if (end == 0) {
            end = 32;
        }

        setPadding(start, 0, end, 0);
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
    public <T extends ListAdapter & Filterable> void setAdapter(T adapter) {
        throw new RuntimeException();
    }

    public <T, VH extends ViewHolder> void setAdapter(Adapter<T, VH> adapter) {
        super.setAdapter(adapter);
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

    public abstract static class Adapter<T, VH extends ViewHolder> extends BaseAdapter implements Filterable {

        private final Object mLock = new Object();

        private final List<T> mObjects;

        private List<T> mOriginalValues;
        private Filter mFilter;

        private boolean mNotifyOnChange = true;

        public Adapter(@NonNull List<T> objects) {
            mObjects = objects;
        }

        public void add(@Nullable T item) {
            synchronized (mLock) {
                if (mOriginalValues != null) {
                    mOriginalValues.add(item);
                } else {
                    mObjects.add(item);
                }
            }

            if (mNotifyOnChange) {
                notifyDataSetChanged();
            }
        }

        public void addAll(@NonNull Collection<? extends T> collection) {
            synchronized (mLock) {
                if (mOriginalValues != null) {
                    mOriginalValues.addAll(collection);
                } else {
                    mObjects.addAll(collection);
                }
            }

            if (mNotifyOnChange) {
                notifyDataSetChanged();
            }
        }

        public void addAll(@NonNull T... items) {
            synchronized (mLock) {
                if (mOriginalValues != null) {
                    Collections.addAll(mOriginalValues, items);
                } else {
                    Collections.addAll(mObjects, items);
                }
            }

            if (mNotifyOnChange) {
                notifyDataSetChanged();
            }
        }

        public void insert(int index, @Nullable T item) {
            synchronized (mLock) {
                if (mOriginalValues != null) {
                    mOriginalValues.add(index, item);
                } else {
                    mObjects.add(index, item);
                }
            }

            if (mNotifyOnChange) {
                notifyDataSetChanged();
            }
        }

        public void remove(@Nullable T item) {
            synchronized (mLock) {
                if (mOriginalValues != null) {
                    mOriginalValues.remove(item);
                } else {
                    mObjects.remove(item);
                }
            }

            if (mNotifyOnChange) {
                notifyDataSetChanged();
            }
        }

        public void remove(int index) {
            synchronized (mLock) {
                if (mOriginalValues != null) {
                    mOriginalValues.remove(index);
                } else {
                    mObjects.remove(index);
                }
            }

            if (mNotifyOnChange) {
                notifyDataSetChanged();
            }
        }

        public void clear() {
            synchronized (mLock) {
                if (mOriginalValues != null) {
                    mOriginalValues.clear();
                } else {
                    mObjects.clear();
                }
            }

            if (mNotifyOnChange) {
                notifyDataSetChanged();
            }
        }

        public void sort(@NonNull Comparator<? super T> comparator) {
            synchronized (mLock) {
                if (mOriginalValues != null) {
                    Collections.sort(mOriginalValues, comparator);
                } else {
                    Collections.sort(mObjects, comparator);
                }
            }

            if (mNotifyOnChange) {
                notifyDataSetChanged();
            }
        }

        @Override
        public int getCount() {
            return mObjects.size();
        }

        @Override
        public T getItem(int position) {
            return mObjects.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            VH holder;
            if (convertView == null) {
                holder = onCreateViewHolder(parent, position);
                convertView = holder.itemView;
                convertView.setTag(holder);
            } else {
                // noinspection uncheked
                holder = (VH) convertView.getTag();
            }

            onBindViewHolder(holder, position);

            return convertView;
        }

        @NonNull
        public abstract VH onCreateViewHolder(@NonNull ViewGroup parent, int position);

        public abstract void onBindViewHolder(@NonNull VH holder, int position);

        public abstract boolean filter(@NonNull T item, @NonNull String constraint);

        @NonNull
        public abstract CharSequence convertResultToString(@NonNull T result);

        @Override
        public Filter getFilter() {
            if (mFilter == null) {
                mFilter = new DropFilter();
            }
            return mFilter;
        }

        @Override
        public void notifyDataSetChanged() {
            super.notifyDataSetChanged();
            mNotifyOnChange = true;
        }

        private class DropFilter extends Filter {

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults results = new FilterResults();

                if (mOriginalValues == null) {
                    synchronized (mLock) {
                        mOriginalValues = new ArrayList<>(mObjects);
                    }
                }

                if (TextUtils.isEmpty(constraint)) {
                    final ArrayList<T> list;
                    synchronized (mLock) {
                        list = new ArrayList<>(mOriginalValues);
                    }
                    results.values = list;
                    results.count = list.size();
                } else {
                    final String constraintString = constraint.toString().toLowerCase();

                    final ArrayList<T> values;
                    synchronized (mLock) {
                       values = new ArrayList<>(mOriginalValues);
                    }

                    int count = values.size();
                    List<T> newValues = new ArrayList<>();

                    for (int i = 0; i < count; i++) {
                        T value = values.get(i);
                        if (Adapter.this.filter(value, constraintString)) {
                            newValues.add(value);
                        }
                    }

                    results.values = newValues;
                    results.count = newValues.size();
                }

                return results;
            }

            @Override
            public CharSequence convertResultToString(Object resultValue) {
                // noinspection unchecked
                return Adapter.this.convertResultToString((T) resultValue);
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                int count = results.count;
                if (count > 0) {
                    mObjects.clear();
                    // noinspection unchecked
                    mObjects.addAll((List<T>) results.values);
                    notifyDataSetChanged();
                }
            }
        }
    }

    public abstract static class ViewHolder {
        protected View itemView;

        public ViewHolder(@NonNull View itemView) {
            this.itemView = itemView;
        }
    }
}
