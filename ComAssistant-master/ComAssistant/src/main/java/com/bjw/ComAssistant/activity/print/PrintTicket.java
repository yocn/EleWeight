package com.bjw.ComAssistant.activity.print;

import java.io.UnsupportedEncodingException;

/**
 * Created by winsock on 15/9/23.
 * s
 */
public class PrintTicket {

    BluetoothService mService;

    public PrintTicket(BluetoothService mService) {
        this.mService = mService;
    }

    public void stopprint() {
        mService.write(new byte[]{0x10, 0x04, 0x07});
    }

    public void printGoodsInfo(String shopName, String goods_name, String quantity, String time) {
        // int i = 87;
        byte[] sends;
        // 设置页模式 1b 4c
        sends = new byte[2];
        sends[0] = 0x1b;
        sends[1] = 0x4c;
        mService.write(sends);
        // 设置页面大小 1b 57 00 00 00 00 80 01 30 01
        sends = new byte[10];
        sends[0] = 0x1b;
        sends[1] = 0x57;
        sends[2] = 0x00;
        sends[3] = 0x00;
        sends[4] = 0x00;
        sends[5] = 0x00;
        sends[6] = (byte) 0x80;
        sends[7] = 0x01;
        sends[8] = 0x30;
        sends[9] = 0x01;
        mService.write(sends);
        // 设置纵向打印
        sends = new byte[3];
        sends[0] = 0x1b;
        sends[1] = 0x54;
        sends[2] = 0x00;
        mService.write(sends);
        // 设置2倍字体
        sends = new byte[3];
        sends[0] = 0x1b;
        sends[1] = 0x46;
        sends[2] = 0x03;
        mService.write(sends);
        printtable1();

        // 设置2倍字体
        sends = new byte[3];
        sends[0] = 0x1b;
        sends[1] = 0x46;
        sends[2] = 0x04;
        mService.write(sends);

        // 横坐标
        sends = new byte[4];
        sends[0] = 0x1b;
        sends[1] = 0x24;
        sends[2] = (byte) 0x00;
        sends[3] = 0x00;
        mService.write(sends);
        // 纵坐标
        sends = new byte[4];
        sends[0] = 0x1d;
        sends[1] = 0x24;
        sends[2] = (byte) 0x30;
        sends[3] = 0x00;
        mService.write(sends);
        // 设置字体大小
        sends = new byte[3];
        sends[0] = 0x1d;
        sends[1] = 0x21;
        sends[2] = 0x11;
        mService.write(sends);
        try {
            mService.write(shopName.getBytes("GBK"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        // 横坐标
        sends = new byte[4];
        sends[0] = 0x1b;
        sends[1] = 0x24;
        sends[2] = (byte) 0x00;
        sends[3] = 0x00;
        mService.write(sends);
        // 纵坐标
        sends = new byte[4];
        sends[0] = 0x1d;
        sends[1] = 0x24;
        sends[2] = (byte) 0x75;
        sends[3] = 0x00;
        mService.write(sends);
        // 设置基础字体大小
        sends = new byte[3];
        sends[0] = 0x1d;
        sends[1] = 0x21;
        sends[2] = 0x11;
        mService.write(sends);
        // 设置行间距
        sends = new byte[3];
        sends[0] = 0x1b;
        sends[1] = 0x33;
        sends[2] = 0x20;
        mService.write(sends);
        try {
            /**商品：姜干*/
            mService.write(goods_name.getBytes("GBK"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        // 横坐标
        sends = new byte[4];
        sends[0] = 0x1b;
        sends[1] = 0x24;
        sends[2] = (byte) 0x00;
        sends[3] = 0x00;
        mService.write(sends);
        // 纵坐标
        sends = new byte[4];
        sends[0] = 0x1d;
        sends[1] = 0x24;
        sends[2] = (byte) 0xc0;
        sends[3] = 0x00;
        mService.write(sends);
        // 设置三倍字体大小
        sends = new byte[3];
        sends[0] = 0x1d;
        sends[1] = 0x21;
        sends[2] = 0x11;
        mService.write(sends);
        try {
            /**数量：30件*/
            mService.write(quantity.getBytes("GBK"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        // 横坐标
        sends = new byte[4];
        sends[0] = 0x1b;
        sends[1] = 0x24;
        sends[2] = (byte) 0x00;
        sends[3] = 0x00;
        mService.write(sends);
        // 纵坐标
        sends = new byte[4];
        sends[0] = 0x1d;
        sends[1] = 0x24;
        sends[2] = (byte) 0xe5;
        sends[3] = 0x00;
        mService.write(sends);
        // 设置三倍字体大小
        sends = new byte[3];
        sends[0] = 0x1d;
        sends[1] = 0x21;
        sends[2] = 0x11;
        mService.write(sends);
        try {
            /**2017-09-12 15:35*/
            mService.write(time.getBytes("GBK"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        mService.write(new byte[]{0x0c, 0x16, 0x08});
//        Log.d("printer", "print " + i + " end");
    }

    public void printtable1() {
        try {
            mService.write(new byte[]{0x1d, 0x21, 0x11});
            mService.write(new byte[]{0x1b, 0x24, 0x00, 0x00});
            mService.write(new byte[]{0x1d, 0x24, 0x18, 0x00});
//            mService.write("┌─────────┐".getBytes("GBK"));
            mService.write(new byte[]{0x1b, 0x24, 0x00, 0x00});
            mService.write(new byte[]{0x1d, 0x24, 0x40, 0x00});
//            mService.write("│            │    │".getBytes("GBK"));
            mService.write(new byte[]{0x1b, 0x24, 0x00, 0x00});
            mService.write(new byte[]{0x1d, 0x24, 0x60, 0x00});
            mService.write("───────".getBytes("GBK"));
            mService.write(new byte[]{0x1b, 0x24, 0x00, 0x00});
            mService.write(new byte[]{0x1d, 0x24, (byte) 0x80, 0x00});
//            mService.write("│                  │".getBytes("GBK"));
            mService.write(new byte[]{0x1b, 0x24, 0x00, 0x00});
            mService.write(new byte[]{0x1d, 0x24, (byte) 0xa0, 0x00});
//            mService.write("│                  │".getBytes("GBK"));
            mService.write(new byte[]{0x1b, 0x24, 0x00, 0x00});
            mService.write(new byte[]{0x1d, 0x24, (byte) 0xc4, 0x00});
//            mService.write("├─────────┤".getBytes("GBK"));
            mService.write(new byte[]{0x1b, 0x24, 0x00, 0x00});
            mService.write(new byte[]{0x1d, 0x24, (byte) 0xe8, 0x00});
//            mService.write("│                  │".getBytes("GBK"));
            mService.write(new byte[]{0x1b, 0x24, 0x00, 0x00});
            mService.write(new byte[]{0x1d, 0x24, (byte) 0x04, 0x01});
//            mService.write("├─────────┤".getBytes("GBK"));
            mService.write(new byte[]{0x1b, 0x24, 0x00, 0x00});
            mService.write(new byte[]{0x1d, 0x24, (byte) 0x30, 0x01});
//            mService.write("│                  │".getBytes("GBK"));
            mService.write(new byte[]{0x1b, 0x24, 0x00, 0x00});
            mService.write(new byte[]{0x1d, 0x24, (byte) 0x44, 0x01});
//            mService.write("│                  │".getBytes("GBK"));
            mService.write(new byte[]{0x1b, 0x24, 0x00, 0x00});
            mService.write(new byte[]{0x1d, 0x24, (byte) 0x64, 0x01});
//            mService.write("└─────────┘".getBytes("GBK"));
            mService.write(new byte[]{0x1d, 0x21, 0x00});
            mService.write(new byte[]{0x1b, 0x24, 0x30, 0x00});
            mService.write(new byte[]{0x1d, 0x24, (byte) 0x70, 0x01});
//            mService.write(time.getBytes("GBK"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

}
