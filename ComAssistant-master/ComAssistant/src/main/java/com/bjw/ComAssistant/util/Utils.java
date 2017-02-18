package com.bjw.ComAssistant.util;

import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Yocn on 16.11.28.
 */

public class Utils {
    public static boolean isMobileNO(String mobiles) {
        Pattern p = Pattern.compile("^1[0-9]{10}$");
        Matcher m = p.matcher(mobiles);
        System.out.println(m.matches() + "---");
        return m.matches();
    }

    public static String exeData(String source) {
        String regex = "\\+\\d{8}";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(source);
        while (matcher.find()) {
            String s = matcher.group(0);
            String temp = matcher.group(0).substring(1, s.length() - 1);
            int i = Integer.parseInt(temp);
            float f = (float) i / 1000;
            DecimalFormat decimalFormat = new DecimalFormat("0.0");//构造方法的字符格式这里如果小数不足2位,会以0补足.
            String p = decimalFormat.format(f * 2);//format 返回的是字符串
            return p;
        }
        return "0.0";
    }

    public static String MD5(String sourceStr) {
        String result = "";
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(sourceStr.getBytes());
            byte b[] = md.digest();
            int i;
            StringBuffer buf = new StringBuffer("");
            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0)
                    i += 256;
                if (i < 16)
                    buf.append("0");
                buf.append(Integer.toHexString(i));
            }
            result = buf.toString();
            System.out.println("MD5(" + sourceStr + ",32) = " + result);
            System.out.println("MD5(" + sourceStr + ",16) = " + buf.toString().substring(8, 24));
        } catch (NoSuchAlgorithmException e) {
            System.out.println(e);
        }
        return result;
    }


    public static void writeFileToSD(String exception) {
        String sdStatus = Environment.getExternalStorageState();
        String filePath = Environment.getExternalStorageDirectory().getAbsolutePath();
        String room = "";
        if (!sdStatus.equals(Environment.MEDIA_MOUNTED)) {
            return;
        }
        try {
            String fileName = "/exception_1" + ".txt";
            File path = new File(filePath);
            File file = new File(filePath + fileName);
            if (!path.exists()) {
                Log.d("TestFile", "Create the path:" + filePath);
                path.mkdir();
            }
            if (!file.exists()) {
                Log.d("TestFile", "Create the file:" + fileName);
                file.createNewFile();
            }
            FileOutputStream stream = new FileOutputStream(file);
            byte[] buf = exception.getBytes();
            stream.write(buf);
            stream.close();
        } catch (Exception e) {
            Log.e("TestFile", "Error on writeFilToSD.");
            e.printStackTrace();
        }
    }

}
