package com.example.autophonecall;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;

public class SmsReceiver extends BroadcastReceiver {
    private static final String TAG = "SmsReceiver";
    private static final String SMS_RECEIVED = "android.provider.Telephony.SMS_RECEIVED";

    @Override
    public void onReceive(Context context, Intent intent) {

        if(intent.getAction().equals(SMS_RECEIVED)){
            Bundle bundle = intent.getExtras();           //---get the SMS message passed in---

            if (bundle != null){
                //---retrieve the SMS message received---
                try{
                    Object[] pdus = (Object[]) bundle.get("pdus");
                    SmsMessage [] message = new SmsMessage[pdus.length];
                    message[0] = SmsMessage.createFromPdu((byte[])pdus[0]);
                    String sender = message[0].getOriginatingAddress();
                    String msgBody = message[0].getMessageBody();

                    if(MainActivity.getCodeWord().equals(msgBody) && (MainActivity.getSender1Number().equals(sender) ||
                        MainActivity.getSender2Number().equals(sender))) {
                        PhoneCaller phoneCaller = new PhoneCaller();
                        phoneCaller.makePhoneCall(context, MainActivity.getTargetPhone());
                    }

                }catch(Exception e){
                    Log.d(TAG, e.getMessage());
                }
            }
        }
    }
}
