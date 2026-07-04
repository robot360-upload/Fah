package com.sunmiscunsafe;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends Activity {
    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        TextView textView = new TextView(this);
        if (isVip.isVip()) {
            textView.setText("Vip User");
        } else {
            textView.setText("Not User");
        }
        setContentView(textView);
    }
}
