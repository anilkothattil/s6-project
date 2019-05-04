package com.example.expensetracker;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;
import android.os.HandlerThread;
import android.os.Handler;
public class SMSReceiver extends BroadcastReceiver {


    @Override
    public void onReceive(final Context context, Intent intent) {

        Log.i("SMS", "smsReceiver: SMS Received");

        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            Log.i("SMS", "smsReceiver : Reading Bundle");

            Object[] pdus = (Object[])bundle.get("pdus");
            SmsMessage sms = SmsMessage.createFromPdu((byte[])pdus[0]);
            final String sender  = sms.getOriginatingAddress();
            final String msg = sms.getMessageBody();
            Toast.makeText(context, "Message from "+sender+" : "+msg, Toast.LENGTH_LONG).show();
            Log.i("SMS", "smsBody : "+sms.getMessageBody());
            HandlerThread handlerThread =  new HandlerThread("database_helper");
            handlerThread.start();
            Handler handler =  new Handler(handlerThread.getLooper());
            handler.post(new Runnable() {
                @Override
                public void run() {
                    DatabaseHandler myDb = new DatabaseHandler(context);
                    if(myDb.dbcheck(sender)) {
                        Toast.makeText(context, "Message recorded!", Toast.LENGTH_SHORT).show();
                        myDb.insertExpense(sender + " : " + msg);
                    }
                }
            });
        }
    }
}
