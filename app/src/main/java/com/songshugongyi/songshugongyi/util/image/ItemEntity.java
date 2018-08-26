package com.songshugongyi.songshugongyi.util.image;

import java.util.ArrayList;

/**
 * Created by yuanopen on 2018/2/12/012.
 */

public class ItemEntity {

    private ArrayList<String> imageUrls; // 九宫格图片的URL集合

    public ItemEntity(ArrayList<String> imageUrls) {
        super();
        this.imageUrls = imageUrls;
    }

    public ArrayList<String> getImageUrls() {
        return imageUrls;
    }

    public void setImageUrls(ArrayList<String> imageUrls) {
        this.imageUrls = imageUrls;
    }
}
