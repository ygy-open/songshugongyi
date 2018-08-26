package com.yuanopen.chatmudule.add;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.yuanopen.chatmudule.R;

/**
 * Created by yuanopen on 2018/7/25/025.
 */

public class AddBaseAcivity extends AppCompatActivity{
    public TextView searchTitle;
    public EditText etSearchContent;
    public Button btnSearch;
    public ListView listModel;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_friend_or_group);
        init();
    }

    private void init() {
        searchTitle=findViewById(R.id.search_title);
        etSearchContent=findViewById(R.id.input_content);
        btnSearch=findViewById(R.id.btn_search);
        listModel=findViewById(R.id.listview_search);

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Search();
            }
        });

        initData();
    }
    public void initData(){};
    public void Search(){};

    public void Back(View v){
        finish();
    }
}
