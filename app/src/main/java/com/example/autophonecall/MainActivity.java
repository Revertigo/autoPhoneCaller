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

    static TextView callTo = null;
    static TextView smsSender = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        checkPermission();

        //Get handler to the views
        callTo = findViewById(R.id.callToTextBox);
        smsSender = findViewById(R.id.smsFromTextBox);

        String targetPhone = readFromPreferences("callTo");
        if(targetPhone != null)
        {
            callTo.setText(targetPhone);
        }

        String smsSource = readFromPreferences("smsSender");
        if(smsSource != null)
        {
            smsSender.setText(smsSource);
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
                            storeToPreferences("callTo", v.getText().toString());
                            Toast.makeText(this, "Target phone number successfully saved", Toast.LENGTH_LONG).show();

                            return true; // consume.
                        }
                    }
                    return false; // pass on to other listeners.
                }
        );

        smsSender.setOnEditorActionListener(
                (v, actionId, event) -> {
                    if (actionId == EditorInfo.IME_ACTION_SEARCH ||
                            actionId == EditorInfo.IME_ACTION_DONE ||
                            event != null &&
                                    event.getAction() == KeyEvent.ACTION_DOWN &&
                                    event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
                        if (event == null || !event.isShiftPressed()) {
                            storeToPreferences("smsSender", v.getText().toString());
                            Toast.makeText(this, "Source phone number successfully saved", Toast.LENGTH_LONG).show();

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
    static String getSenderNumber()
    {
        return smsSender.getText().toString();
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