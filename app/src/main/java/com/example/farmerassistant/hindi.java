package com.example.farmerassistant;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class hindi extends AppCompatActivity {
    TextView omg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hindi);
        omg = findViewById(R.id.hmm);

        Bundle bundle = getIntent().getExtras();
        final String ok = bundle.getString("stu");
        omg.setText(ok);
    }
}