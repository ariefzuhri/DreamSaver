package com.ppab1.dreamsaver.utils;

import android.text.TextUtils;
import android.widget.EditText;

public class EditTextUtils {
    // Mengecek apakah string bernilai null
    public static boolean isNull(String text){
        return TextUtils.isEmpty(text);
    }

    // Mendapatkan string tanpa spasi berlebihan
    public static String getFixText(EditText editText){
        return (editText.getText().toString().trim()).replaceAll("\\s+", " ");
    }

    // Mengecek apakah karakter yang diinput melebih batas karakter
    public static boolean isMaxChar(EditText editText, int maxChar){
        return editText.getText().toString().trim().length() > maxChar;
    }
}
