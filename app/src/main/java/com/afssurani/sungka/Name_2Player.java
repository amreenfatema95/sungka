package com.afssurani.sungka;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.EditText;

public class Name_2Player extends Activity {
    public static final String EXTRA_MESSAGE1 = "com.afssurani.sungka.MESSAGE1";
    public static final String EXTRA_MESSAGE2 = "com.afssurani.sungka.MESSAGE2";

    public void startGame(View view){
        Intent intent = new Intent(this, MainActivity.class);
        EditText player1 = (EditText) findViewById(R.id.name_p1);
        EditText player2 = (EditText) findViewById(R.id.name_p2);
        String name1 = player1.getText().toString();
        String name2 = player2.getText().toString();
        intent.putExtra(EXTRA_MESSAGE1, name1);
        intent.putExtra(EXTRA_MESSAGE2, name2);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_name_2_player);
        setTitle("Enter Name for 2 Player");
    }

}
