package com.example.autophonecall;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.TargetApi;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private static int ACTION_MANAGE_OVERLAY_PERMISSION_REQUEST_CODE = 5469;
    private static final String CALL_TO = "callTo";
    private static final String SMS_SENDER1 = "smsSender1";
    private static final String SMS_SENDER2 = "smsSender2";

    static TextView callTo = null;
    static TextView smsSender1 = null;
    static TextView smsSender2 = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        checkPermission();

        //Get handler to the views
        callTo = findViewById(R.id.callToTextBox);
        smsSender1 = findViewById(R.id.smsFromTextBox1);
        smsSender2 = findViewById(R.id.smsFromTextBox2);

        String targetPhone = readFromPreferences(CALL_TO);
        if(targetPhone != null)
        {
            callTo.setText(targetPhone);
        }

        String smsSource1 = readFromPreferences(SMS_SENDER1);
        if(smsSource1 != null)
        {
            smsSender1.setText(smsSource1);
        }

        String smsSource2 = readFromPreferences(SMS_SENDER2);
        if(smsSource2 != null)
        {
            smsSender2.setText(smsSource2);
        }
        callTo.setOnEditorActionListener(
                (v, actionId, event) -> {
                    if (actionId == EditorInfo.IME_ACTION_NEXT ||
                            actionId == EditorInfo.IME_ACTION_SEARCH ||
                            actionId == EditorInfo.IME_ACTION_DONE ||
                            event != null &&
                                    event.getAction() == KeyEvent.ACTION_DOWN &&
                                    event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
                        if (event == null || !event.isShiftPressed()) {
                            storeToPreferences(CALL_TO, v.getText().toString());
                            Toast.makeText(this, "Target phone number successfully saved", Toast.LENGTH_LONG).show();

                            return true; // consume.
                        }
                    }
                    return false; // pass on to other listeners.
                }
        );

        smsSender1.setOnEditorActionListener(
                (v, actionId, event) -> {
                    if (actionId == EditorInfo.IME_ACTION_NEXT ||
                            actionId == EditorInfo.IME_ACTION_SEARCH ||
                            actionId == EditorInfo.IME_ACTION_DONE ||
                            event != null &&
                                    event.getAction() == KeyEvent.ACTION_DOWN &&
                                    event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
                        if (event == null || !event.isShiftPressed()) {
                            storeToPreferences(SMS_SENDER1, v.getText().toString());
                            Toast.makeText(this, "Source phone #1 successfully saved", Toast.LENGTH_LONG).show();

                            return true; // consume.
                        }
                    }
                    return false; // pass on to other listeners.
                }
        );

        smsSender2.setOnEditorActionListener(
                (v, actionId, event) -> {
                    if (actionId == EditorInfo.IME_ACTION_SEARCH ||
                            actionId == EditorInfo.IME_ACTION_DONE ||
                            event != null &&
                                    event.getAction() == KeyEvent.ACTION_DOWN &&
                                    event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
                        if (event == null || !event.isShiftPressed()) {
                            storeToPreferences(SMS_SENDER2, v.getText().toString());
                            Toast.makeText(this, "Source phone #2 successfully saved", Toast.LENGTH_LONG).show();

                            return true; // consume.
                        }
                    }
                    return false; // pass on to other listeners.
                }
        );

    }

    private void checkPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!Settings.canDrawOverlays(this)) {
                Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                        Uri.parse("package:" + getPackageName()));
                startActivityForResult(intent, ACTION_MANAGE_OVERLAY_PERMISSION_REQUEST_CODE);
            }
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ACTION_MANAGE_OVERLAY_PERMISSION_REQUEST_CODE) {
            if (!Settings.canDrawOverlays(this)) {
                // You don't have permissions
                checkPermission();
            } else {
                // Do as per your logic
            }

        }
    }

    //Get the phone number of the caller
    static String getTargetPhone()
    {
        return callTo.getText().toString();
    }
    //Get the phone number of the caller
    static String getSender1Number()
    {
        return smsSender1.getText().toString();
    }

    //Get the phone number of the caller
    static String getSender2Number()
    {
        return smsSender2.getText().toString();
    }

    void storeToPreferences(String key, String value)
    {
        SharedPreferences.Editor editor = getPreferences(MODE_PRIVATE).edit();
        editor.putString(key, value);
        editor.apply();
    }

    String readFromPreferences(String key)
    {
        SharedPreferences prefs = getPreferences(MODE_PRIVATE);
        String restoredText = prefs.getString(key, null);

        return restoredText;
    }

}