package com.yuanopen.chatmudule.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yuanopen.chatmudule.R;


/**
 * Created by Administrator on 2017/4/2.
 */

public class userItem extends LinearLayout {

    private ImageView mIconView;
    private TextView mKeyView;
    private TextView mValueView;


    public userItem(Context context) {
        super(context);
        init();
    }

    public userItem(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public userItem(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.view_user_info, this
                , true);
        findAllViews();
    }

    private void findAllViews() {
        mIconView=(ImageView)findViewById(R.id.profile_icon);
        mKeyView = (TextView) findViewById(R.id.profile_key);
        mValueView=(TextView) findViewById(R.id.profile_value);

    }

    public void set(int iconResId, String key) {
        mIconView.setImageResource(iconResId);
        mKeyView.setText(key);
    }

    public void set(int iconResId, String key,String value) {
        mIconView.setImageResource(iconResId);
        mKeyView.setText(key);
        mValueView.setText(value);
    }

    public void set(String value) {
        mValueView.setText(value);
    }

    public void updateValue(String value) {
        mValueView.setText(value);
    }

    public String getValue() {
        return mValueView.getText().toString();
    }

}
