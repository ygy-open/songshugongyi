package com.songshugongyi.songshugongyi.user;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.songshugongyi.songshugongyi.R;

/**
 * Created by Administrator on 2017/4/2.
 */

public class userEdit extends LinearLayout {

    private ImageView mIconView;
    private TextView mKeyView;
    private TextView mValueView;
    private TextView mNews;
    private ImageView mRightArrowView;

    public userEdit(Context context) {
        super(context);
        init();
    }

    public userEdit(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public userEdit(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.view_user_edit, this
                , true);
        findAllViews();
    }

    private void findAllViews() {
        mIconView = (ImageView) findViewById(R.id.profile_icon);
        mKeyView = (TextView) findViewById(R.id.profile_key);
        mValueView=(TextView) findViewById(R.id.profile_value);
        mRightArrowView = (ImageView) findViewById(R.id.right_arrow);
        mNews=(TextView)findViewById(R.id.profile_news);
    }

    public void set(int iconResId, String key) {
        mIconView.setImageResource(iconResId);
        mKeyView.setText(key);
    }
    public void showNews(int i){
        if(i==0)
        mNews.setVisibility(INVISIBLE);
        else
            mNews.setVisibility(VISIBLE);
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

    protected void disableEdit() {
        mRightArrowView.setVisibility(GONE);
    }
}
