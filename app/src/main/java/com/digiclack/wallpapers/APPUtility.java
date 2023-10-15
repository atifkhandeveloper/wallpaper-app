package com.digiclack.wallpapers;

import android.os.Environment;

import java.io.File;

public class APPUtility {

    final static public String Account_name = "Universal Tech Apps";
    final static public String PrivacyPolicy = "https://privacypolicycftech.blogspot.com/2022/01/privacy-policy.html";
	
	 public static File getAppDir() {
        return Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
    }
}

