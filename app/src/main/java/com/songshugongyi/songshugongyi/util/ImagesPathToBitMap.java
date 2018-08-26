package com.songshugongyi.songshugongyi.util;


import com.yuanopen.commenmodule.bean.Image;
import com.yuanopen.commenmodule.utils.getCurrentTime;
import com.yuanopen.commenmodule.utils.getUUID;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yuanopen on 2018/7/19/019.
 */

public class ImagesPathToBitMap {

    //将Image转化为BitMap
    public static  List<Image> getImages(String model_id, List<String> imagePath){
        List<Image> images=new ArrayList<>();
        for (String path:imagePath
                ) {
             Image image=new Image();
            image.setImage_id(getUUID.getUUID());
            //用户项目id和图片id作为图片名称
            image.setImage_url(model_id+"_"+image.getImage_id());
            image.setModel_id(model_id);
            image.setCreate_time(getCurrentTime.getTime());
            image.setUpdate_time(getCurrentTime.getTime());
            images.add(image);
        }

        return images;
    }
}
