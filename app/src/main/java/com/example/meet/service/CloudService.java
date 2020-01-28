package com.example.meet.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.example.framework.cloud.CloudManager;
import com.example.framework.entity.Constants;
import com.example.framework.utils.LogUtils;
import com.example.framework.utils.SpUtils;

import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Message;
import okhttp3.internal.Util;

public class CloudService extends Service {
    public CloudService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException ("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate ();
        linkCloudServer ();
    }

    /**
     * 连接云服务
     */
    public void linkCloudServer(){
        String token= SpUtils.getInstance ().getString (Constants.SP_TOKEN,"");
        CloudManager.getInstance ().connect (token);
        CloudManager.getInstance ().setOnReceiveMessageListener (new RongIMClient.OnReceiveMessageListener () {
            @Override
            public boolean onReceived(Message message, int i) {
                LogUtils.i ("message:"+message);
                return false;
            }
        });
    }
}
