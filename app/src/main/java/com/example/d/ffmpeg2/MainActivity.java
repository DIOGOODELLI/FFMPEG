package com.example.d.ffmpeg2;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.github.hiteshsondhi88.libffmpeg.ExecuteBinaryResponseHandler;
import com.github.hiteshsondhi88.libffmpeg.FFmpeg;
import com.github.hiteshsondhi88.libffmpeg.FFmpegExecuteResponseHandler;
import com.github.hiteshsondhi88.libffmpeg.FFmpegLoadBinaryResponseHandler;
import com.github.hiteshsondhi88.libffmpeg.LoadBinaryResponseHandler;
import com.github.hiteshsondhi88.libffmpeg.exceptions.FFmpegCommandAlreadyRunningException;
import com.github.hiteshsondhi88.libffmpeg.exceptions.FFmpegNotSupportedException;


public class MainActivity extends Activity
{
    Button enviar;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Permissao.requestPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
        Permissao.requestPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        Toast.makeText(MainActivity.this, "Criou", Toast.LENGTH_LONG).show();
        setContentView(R.layout.activity_main);
        loadFFMpegBinary();

        final TextView mTextView = (TextView) findViewById(R.id.command);
        mTextView.setText("-i /storage/emulated/0/ssmultitrackPlus/ggfg/s_test2.mp3 -ar 44100 -ac 1 /storage/emulated/0/ssmultitrackPlus/ggfg/test2.wav");

        final TextView mTextView2 = (TextView) findViewById(R.id.raiz);
        mTextView2.setText("-i /storage/emulated/0/ssmultitrackPlus/ggfg/s_test.wav -vn -ar 44100 -ac 2 -ab 192k -f mp3 /storage/emulated/0/ssmultitrackPlus/ggfg/s_test2.mp3");

        //ffmpeg -i /storage/emulated/0/test.mp3 -ar 8000 -ac 1 /storage/emulated/0/test.wav
        //ffmpeg -i input.wav -vn -ar 44100 -ac 2 -ab 192k -f mp3 output.mp3

        enviar = (Button) findViewById(R.id.btn);
        enviar.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v)
              {
                  TextView auxiliar  = (TextView) findViewById(R.id.command);
                  String cmd = auxiliar.getText().toString();

                  Toast.makeText(MainActivity.this, cmd, Toast.LENGTH_LONG).show();

                  String[] command = cmd.split(" ");
                  if (command.length != 0) {
                      executar(command);
                  } else {
                      Toast.makeText(MainActivity.this, "Comando não informado!", Toast.LENGTH_LONG).show();
                  }
              }
          }
        );
    }


    private void loadFFMpegBinary()
    {
        try {
            FFmpeg.getInstance(MainActivity.this).loadBinary(new FFmpegLoadBinaryResponseHandler() {
                public void onStart() {
                    Toast.makeText(MainActivity.this, "onStart", Toast.LENGTH_LONG).show();
                }

                public void onSuccess() {
                    Toast.makeText(MainActivity.this, "onSuccess", Toast.LENGTH_LONG).show();
                }

                public void onFailure() {
                    Toast.makeText(MainActivity.this, "onFailure", Toast.LENGTH_LONG).show();
                }

                public void onFinish() {
                    Toast.makeText(MainActivity.this, "onFinish", Toast.LENGTH_LONG).show();
                }
            });
        } catch (Exception e) {
            Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
            //loaded = false;
            //callback.onFailure(var3);
        }
    }



    public void executar(final String[] command)
    {
        try {

            FFmpeg.getInstance(MainActivity.this).execute(command, new FFmpegExecuteResponseHandler() {
                @Override
                public void onSuccess(String message) {
                    Toast.makeText(MainActivity.this,"suc:"+message, Toast.LENGTH_LONG).show();
                }
                @Override
                public void onProgress(String message) {
                    Toast.makeText(MainActivity.this, "Prog:"+message, Toast.LENGTH_LONG).show();

                }

                @Override
                public void onFailure(String message) {
                    Toast.makeText(MainActivity.this, "Fail:"+message, Toast.LENGTH_LONG).show();
                }

                @Override
                public void onStart() {
                    Toast.makeText(MainActivity.this, "Start", Toast.LENGTH_LONG).show();
                }

                @Override
                public void onFinish() {
                    Toast.makeText(MainActivity.this, "onFinish", Toast.LENGTH_LONG).show();

                }
            });

        } catch (FFmpegCommandAlreadyRunningException e) {

            Toast.makeText(MainActivity.this, "Errowww" + e.getMessage(), Toast.LENGTH_LONG).show();
            // do nothing for now
        }

    }
}
