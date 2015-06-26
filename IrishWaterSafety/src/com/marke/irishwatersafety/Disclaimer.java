package com.marke.irishwatersafety;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;

public class Disclaimer extends Activity {
    ImageView appImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.disclaimer_layout);

        appImage = (ImageView)findViewById(R.id.app_banner);
    }
}

