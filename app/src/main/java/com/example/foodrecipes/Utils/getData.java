package com.example.foodrecipes.Utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class getData {
    public static String getData1(){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd- HH:mm:ss");// HH:mm:ss
        //获取当前时间
        Date date = new Date(System.currentTimeMillis());
        return simpleDateFormat.format(date);
    }


//   simpleDateFormat.format(date)

}
