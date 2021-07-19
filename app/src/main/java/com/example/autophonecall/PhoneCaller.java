package com.example.autophonecall;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;

import androidx.core.app.ActivityCompat;

public class PhoneCaller {

    void makePhoneCall(Context context, String number) {
        Intent intent = new Intent(Intent.ACTION_CALL);
        //Needed for start new activity from BroadcastReceiver callback
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        intent.setData(Uri.parse("tel:" + number));
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
        }

        context.startActivity(intent);
    }
}
