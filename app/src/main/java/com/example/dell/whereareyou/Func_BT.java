package com.example.dell.whereareyou;

/**
 * Created by DELL on 2016/10/4.
 */

import java.util.Vector;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

public class Func_BT {
    private BluetoothAdapter mBtAdapter;// 蓝牙适配器
    private static final int ENABLE_BLUETOOTH = 1;
    // 分别用于存储设备名地址名称和RSSI的向量
    public Vector<String> mNameVector;
    public Vector<String> mAddrVector;
    public Vector<Short> mRSSIVector;

    private Handler myHandler;
    private Activity activity;

    public Func_BT(Activity activity, Handler myHandler) {
        this.myHandler = myHandler;
        this.activity = activity;

        mNameVector = new Vector<String>();// 向量
        mAddrVector = new Vector<String>();
        mRSSIVector = new Vector<Short>();

        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        activity.registerReceiver(mReceiver, filter);
        filter = new IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        activity.registerReceiver(mReceiver, filter);
        activity.registerReceiver(mReceiver, filter);

        mBtAdapter = BluetoothAdapter.getDefaultAdapter();
    }

    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                BluetoothDevice device = intent
                        .getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                short rssi = intent.getExtras().getShort(
                        BluetoothDevice.EXTRA_RSSI);
                mNameVector.add(device.getName());
                mAddrVector.add(device.getAddress());
                mRSSIVector.add(rssi);
            } else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED
                    .equals(action)) {
                                    /*if (mNameVector.size() != 0) {
                         Message msg = new Message();// 消息
                         msg.what = 0x01;// 消息类别
                         myHandler.sendMessage(msg);
                     }*/
            }
        }
    };

    public void doDiscovery() {
        if (mBtAdapter.isDiscovering()) {
            mBtAdapter.cancelDiscovery();
        }
        mBtAdapter.startDiscovery();
        new TimeLimitThread().start();
    }

    public void openBT() {
        // 如果没有打开则打开
        if (!mBtAdapter.isEnabled()) {
            Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            activity.startActivityForResult(intent, ENABLE_BLUETOOTH);
        } else {
            doDiscovery();
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == ENABLE_BLUETOOTH) {
            if (resultCode == Activity.RESULT_OK) {
                doDiscovery();
            }
        }
    }

    public void setHandler(Handler myHandler) {
        this.myHandler = myHandler;
    }

    public void setFunc_BT(Activity activity, Handler myHandler) {
        this.myHandler = myHandler;
        this.activity = activity;
    }

    class TimeLimitThread extends Thread {
        public void run() {
            try {
                sleep(3000);
                if (mBtAdapter.isDiscovering()) {
                    mBtAdapter.cancelDiscovery();
                }
                Message msg = new Message();// 消息
                msg.what = 0x01;// 消息类别
                myHandler.sendMessage(msg);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}