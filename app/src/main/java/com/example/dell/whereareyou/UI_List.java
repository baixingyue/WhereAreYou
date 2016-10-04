package com.example.dell.whereareyou;

/**
 * Created by DELL on 2016/10/4.
 */

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

public class UI_List extends Activity {

    private ArrayList<String> Items;
    private ListView myListView;
    private ArrayAdapter<String> aa;
    private boolean getDIV;

    @SuppressLint("InlinedApi")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ui_list);

        UI_Main.mFuncBT.setFunc_BT(this, myHandler);

        // 获取listview并设置为多选
        myListView = (ListView) findViewById(R.id.listView1);
        myListView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        myListView.setTextFilterEnabled(true);
        // 设置listview数组并绑定
        Items = new ArrayList<String>();
        aa = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_checked, Items);
        myListView.setAdapter(aa);

        // 获取OK按钮，并遍历选择的设备，返回主Activity
        Button myButton1 = (Button) findViewById(R.id.button_ok);
        myButton1.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                int num = 0;
                for (int i = 0; i < myListView.getCount(); i++) {
                    if (myListView.isItemChecked(i)) {
                        String item = myListView.getItemAtPosition(i)
                                .toString();
                        String name = item.substring(0, item.indexOf("\n"));
                        String addr = item.substring(item.indexOf("\n") + 1);
                        Log.i("UI_LIST", name + " " + addr);
                        UI_Main.mBTSArrayList
                                .add(num++, new My_BTS(name, addr));
                    }
                }
                Intent mIntent = new Intent(UI_List.this, UI_Main.class);
                mIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(mIntent);
            }
        });
        // 获取Search按钮，设置监听事件
        Button myButton2 = (Button) findViewById(R.id.button_search);
        myButton2.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                getDIV = false;
                UI_Main.mFuncBT.openBT();
                final ProgressDialog dialog = ProgressDialog.show(UI_List.this,
                        "搜索蓝牙设备", "稍等一下~", true);
                new Thread(new Runnable() {
                    public void run() {
                        while (getDIV == false) ;
                        dialog.dismiss();
                    }
                }).start();
            }
        });
    }

    // 消息句柄(线程里无法进行界面更新，
    // 所以要把消息从线程里发送出来在消息句柄里进行处理)
    public Handler myHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 0x01) {
                Items.clear();
                for (int i = 0; i < UI_Main.mFuncBT.mNameVector.size(); i++) {
                    Items.add(i, UI_Main.mFuncBT.mNameVector.get(i) + '\n'
                            + UI_Main.mFuncBT.mAddrVector.get(i));
                    aa.notifyDataSetChanged();// 通知数据变化
                }
                getDIV = true;
                UI_Main.mFuncBT.mRSSIVector.clear();
                UI_Main.mFuncBT.mNameVector.clear();
                UI_Main.mFuncBT.mAddrVector.clear();
            }
        }
    };
}