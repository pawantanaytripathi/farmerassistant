package com.example.farmerassistant;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity2 extends AppCompatActivity {
    Button hindi;
    Button english;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Bundle bundle = getIntent().getExtras();
        hindi = findViewById(R.id.hindi);
        english = findViewById(R.id.english);

//Extract the data…
        final String ok = bundle.getString("stuff");
        hindi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent io = new Intent(MainActivity2.this, hindi.class);


                Bundle bun = new Bundle();

//Add your data to bundle
                bun.putString("stu", ok);

//Add the bundle to the intent
                io.putExtras(bun);

//Fire that second activity
                startActivity(io);
            }
        });

        english.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(MainActivity2.this, english.class);


//Create the bundle
                Bundle bund = new Bundle();

//Add your data to bundle
                bund.putString("stud", ok);

//Add the bundle to the intent
                in.putExtras(bund);

//Fire that second activity
                startActivity(in);

            }
        });
    }
}