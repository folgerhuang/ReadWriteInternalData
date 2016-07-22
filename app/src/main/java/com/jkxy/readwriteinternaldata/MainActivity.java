package com.jkxy.readwriteinternaldata;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;

public class MainActivity extends AppCompatActivity {

    private Button btnRead;
    private Button btnSave;
    private EditText etContent;
    private TextView tvContent;
    private char[] buffer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnSave = (Button) findViewById(R.id.btnSave);
        btnRead = (Button) findViewById(R.id.btnRead);
        etContent = (EditText) findViewById(R.id.etContent);
        tvContent = (TextView) findViewById(R.id.tvContent);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FileOutputStream out = null;
                try {
                    out = openFileOutput("test", Context.MODE_PRIVATE);
                    OutputStreamWriter outputStreamWriter = new OutputStreamWriter(out, "UTF-8");
                    outputStreamWriter.write(etContent.getText().toString());

                    outputStreamWriter.flush();
                    out.flush();
                    outputStreamWriter.close();
                    out.close();
                    Toast.makeText(getApplicationContext(), "Save message successfully.", Toast.LENGTH_SHORT).show();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        btnRead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    FileInputStream fileInputStream = openFileInput("test");
                    InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
                    buffer = new char[fileInputStream.available()];
                    inputStreamReader.read(buffer);
                    inputStreamReader.close();
                    fileInputStream.close();
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            tvContent.setText(buffer, 0, buffer.length);
                        }
                    });
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private Handler handler = new Handler();
}
