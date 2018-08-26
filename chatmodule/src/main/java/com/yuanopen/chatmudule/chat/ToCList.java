package com.yuanopen.chatmudule.chat;

import android.content.Context;
import android.content.Intent;

import com.yuanopen.chatmudule.AdressBookActivity;

/**
 * Created by yuanopen on 2018/6/28/028.
 */

public class ToCList {
    private static ToCList toCList;
    private  Context mContext;

    public static ToCList getIntance(Context context){
        if(toCList==null){
            toCList=new ToCList(context);
        }

        return  toCList;
    }

    public ToCList(Context mContext) {
        this.mContext = mContext;
    }

    public void toAddressBook()
    {
        Intent intent=new Intent(mContext, AdressBookActivity.class);
        mContext.startActivity(intent);
    }

}
