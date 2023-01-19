package com.example.matchashop.BroadcastReceivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;

import com.example.matchashop.MainActivity;
import com.example.matchashop.R;

public class SoundBroadcastReceivers extends BroadcastReceiver {



    @Override
    public void onReceive(Context context, Intent intent)
    {
        if (intent.getAction().equals(MainActivity.CLICK_SOUND)) {
            MediaPlayer mp = MediaPlayer.create(context, R.raw.click);
            mp.start();
        }
        else if (intent.getAction().equals(MainActivity.CHECKOUT_SOUND)) {
            MediaPlayer mp = MediaPlayer.create(context, R.raw.checkout);
            mp.start();
        }
        else if (intent.getAction().equals(MainActivity.COUPON_SOUND)) {
            MediaPlayer mp = MediaPlayer.create(context, R.raw.coupon);
            mp.start();
        }
    }

}
