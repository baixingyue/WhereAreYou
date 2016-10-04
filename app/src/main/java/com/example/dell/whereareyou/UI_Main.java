package com.example.dell.whereareyou;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.SurfaceHolder;
 import android.view.SurfaceHolder.Callback;
 import android.view.SurfaceView;
 import android.view.View;
 import android.view.View.OnClickListener;
 import android.widget.Button;

 public class UI_Main extends Activity implements Callback {
           public Func_Draw mFuncDraw;
             public SurfaceHolder mHolder;
             public static Func_BT mFuncBT;
             // 防丢设备
                    public static ArrayList<My_BTS> mBTSArrayList = new ArrayList<My_BTS>();

                    // 消息句柄(线程里无法进行界面更新，
                 // 所以要把消息从线程里发送出来在消息句柄里进行处理)
                    public Handler myHandler = new Handler() {
                     @Override
                     public void handleMessage(Message msg) {
                          if (msg.what == 0x01) {
                                    Func_Draw.draw(mHolder);
                                }
                             mFuncBT.doDiscovery();
                         }
                };

                    @Override
            protected void onCreate(Bundle savedInstanceState) {
                     super.onCreate(savedInstanceState);
                     setContentView(R.layout.ui_main);

                     Func_Draw.initPaint();

                     SurfaceView mSurface = (SurfaceView) findViewById(R.id.surfaceView);
                     mHolder = mSurface.getHolder();
                     mHolder.addCallback(this);

                     mFuncBT = new Func_BT(this, myHandler);

                     Button mButton1 = (Button) findViewById(R.id.button_start);
                     mButton1.setOnClickListener(new OnClickListener() {
                             @Override
                             public void onClick(View v) {
                                     Func_Draw.times = 0;
                                     mFuncBT.openBT();
                                 }
                        });

                     Button mButton2 = (Button) findViewById(R.id.button_add);
                     mButton2.setOnClickListener(new OnClickListener() {
                             @Override
                             public void onClick(View v) {
                                     startActivity(new Intent(UI_Main.this, UI_List.class));
                               }
                         });
                 }

                     @Override
           public void surfaceCreated(SurfaceHolder holder) {
                     // TODO Auto-generated method stub

                 }

                     @Override
             public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
                   // TODO Auto-generated method stub

               }

                     @Override
             public void surfaceDestroyed(SurfaceHolder holder) {
                     // TODO Auto-generated method stub

                 }
     }