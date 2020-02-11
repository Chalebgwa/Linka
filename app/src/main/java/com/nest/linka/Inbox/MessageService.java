package com.nest.linka.Inbox;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 * Created by black on 2/20/17.
 */

public class MessageService extends Service{

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
