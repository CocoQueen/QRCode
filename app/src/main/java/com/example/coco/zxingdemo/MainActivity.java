package com.example.coco.zxingdemo;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    private EditText editText;
    private Button button;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText = findViewById(R.id.ed);
        button = findViewById(R.id.btn);
        imageView = findViewById(R.id.img);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //只生成不保存到本地
                //https://www.jianshu.com/p/3ced3bd973e4
//                Bitmap qrImage = ZXingUtils.createQRImage(editText.getText().toString(), 100, 100);
//                imageView.setImageBitmap(qrImage);
                //https://www.jb51.net/article/127880.htm
                createQRCode();
            }
        });
    }

    private void createQRCode() {
        final String filePath = ZXingUtils.getFileRoot(MainActivity.this) + File.separator
                + "qr_" + System.currentTimeMillis() + ".jpg";
        //二维码图片较大时，生成图片、保存文件的时间可能较长，因此放在新线程中
        new Thread(new Runnable() {
            @Override
            public void run() {
                boolean success = ZXingUtils.createQRImage(editText.getText().toString().trim(), 800, 800,
                        BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher_round),
                        filePath);
                if (success) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            imageView.setImageBitmap(BitmapFactory.decodeFile(filePath));
                        }
                    });
                }
            }
        }).start();
    }
}
