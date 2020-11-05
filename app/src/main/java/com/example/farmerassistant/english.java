package com.example.farmerassistant;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class english extends AppCompatActivity {
    TextView omg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_english);
        omg = findViewById(R.id.hmm);
        Bundle bundle = getIntent().getExtras();
        final String ok = bundle.getString("stud");
        omg.setText(ok);
    }
}