package com.l.zhuoyaohook;

import android.content.Context;
import android.content.SharedPreferences;

public class SPUtils {
    public static void setSwitch(boolean isSwitch) {
        SharedPreferences sp = App.mContext.getSharedPreferences("config", Context.MODE_WORLD_READABLE);
        sp.edit().putBoolean("isSwitch", isSwitch).commit();
    }

    public static boolean isSwitch() {
        SharedPreferences sp = App.mContext.getSharedPreferences("config", Context.MODE_WORLD_READABLE);
        return sp.getBoolean("isSwitch", false);
    }

    //倍数
    public static void setNumber(int num) {
        SharedPreferences sp = App.mContext.getSharedPreferences("config", Context.MODE_WORLD_READABLE);
        sp.edit().putInt("num", num).commit();
    }

    public static int getNum() {
        SharedPreferences sp = App.mContext.getSharedPreferences("config", Context.MODE_WORLD_READABLE);
        return sp.getInt("num", 2);
    }
}
