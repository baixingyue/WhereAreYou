package com.example.dell.whereareyou;

/**
 * Created by DELL on 2016/10/4.
 */

import java.util.Vector;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.view.SurfaceHolder;

public class Func_Draw {
    private static Vector<Paint> mPaint = new Vector<Paint>();
    public static Integer times = 0;// 防丢搜索次数
    public static float Bei = 200;// 绘制图形时放大倍数

    public static void initPaint() {
        Paint paint0 = new Paint();
        paint0.setAntiAlias(true);
        paint0.setStyle(Style.STROKE);
        paint0.setColor(Color.RED);
        mPaint.add(paint0);
        Paint paint1 = new Paint();
        paint1.setAntiAlias(true);
        paint1.setStyle(Style.STROKE);
        paint1.setColor(Color.GREEN);
        mPaint.add(paint1);
        Paint paint2 = new Paint();
        paint2.setAntiAlias(true);
        paint2.setStyle(Style.STROKE);
        paint2.setColor(Color.BLUE);
        mPaint.add(paint2);
        Paint paint3 = new Paint();
        paint3.setAntiAlias(true);
        paint3.setStyle(Style.STROKE);
        paint3.setColor(Color.YELLOW);
        mPaint.add(paint3);
        Paint paint4 = new Paint();
        paint4.setAntiAlias(true);
        paint4.setStyle(Style.STROKE);
        paint4.setColor(Color.WHITE);
        mPaint.add(paint4);
        Paint paint5 = new Paint();
        paint5.setAntiAlias(true);
        paint5.setStyle(Style.STROKE);
        paint5.setColor(Color.LTGRAY);
        mPaint.add(paint5);
        Paint paint6 = new Paint();
        paint6.setAntiAlias(true);
        paint6.setStyle(Style.STROKE);
        paint6.setColor(Color.CYAN);
        mPaint.add(paint6);
    }

    public static void draw(SurfaceHolder mHolder) {
        Canvas canvas = mHolder.lockCanvas();
        canvas.drawRGB(0, 0, 0);
        for (int i = 0; i < UI_Main.mBTSArrayList.size(); i++) {
            boolean find = false;
            short rssi = 0;
            for (int j = 0; j < UI_Main.mFuncBT.mAddrVector.size(); j++) {
                if (UI_Main.mBTSArrayList.get(i).mAddr
                        .equals(UI_Main.mFuncBT.mAddrVector.get(j))) {
                    find = true;
                    rssi = UI_Main.mFuncBT.mRSSIVector.get(j);
                }
            }
            if (find == false) {
                canvas.drawText(
                        times + ": NOT_FIND "
                                + UI_Main.mBTSArrayList.get(i).mName, 5,
                        i * 10 + 12, mPaint.get(i));
            } else {
                float power = (float) ((Math.abs(rssi) - 59) / (10 * 2.0));
                float dis = (float) Math.pow(10, power);

                canvas.drawText(
                        times + ": FIND " + UI_Main.mBTSArrayList.get(i).mName
                                + " dis: " + new Float(dis).toString()
                                + " rssi: " + rssi, 5, i * 10 + 12,
                        mPaint.get(i));
                canvas.drawCircle(canvas.getWidth() / 2,
                        canvas.getHeight() / 2, Bei * dis, mPaint.get(i));//画圆圈
            }
        }
        times++;
        mHolder.unlockCanvasAndPost(canvas);// 更新屏幕显示内容
        UI_Main.mFuncBT.mRSSIVector.clear();
        UI_Main.mFuncBT.mNameVector.clear();
        UI_Main.mFuncBT.mAddrVector.clear();
    }
}