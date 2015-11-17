package com.afssurani.sungka;

import android.os.Bundle;
import android.app.Activity;

public class Instructions extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructions);
        getActionBar().setDisplayHomeAsUpEnabled(true);
    }

}
