package com.example.kodakanatsumi.intentapplication;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.renderscript.ScriptGroup;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.InputStream;

public class MainActivity extends AppCompatActivity {

    static final int REQUEST_CODE_MAP = 1;
    static final int REQUEST_CODE_MAIL = 2;
    static final int REQUEST_CODE_CAMERA = 3;
    static final int REQUEST_CODE_GALLERY = 4;

    ImageView imageView;
    EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView = (ImageView) findViewById(R.id.imageview);
        editText = (EditText) findViewById(R.id.editText2);
        editText.setText("東京都港区南麻布2-12-3");
    }
    public void map(View v) {

        String location = "geo:0,0?q" + editText.getText().toString();
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(location));
        startActivityForResult(intent, REQUEST_CODE_MAP);
    }
    public void gallery(View v) {

        Intent intent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, REQUEST_CODE_GALLERY);
    }
    public void mail(View v) {

        Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:natsumi-k.2002@docomo.ne.jp"));
        intent.putExtra(Intent.EXTRA_SUBJECT, "題名");
        intent.putExtra(Intent.EXTRA_TEXT, "本文");
        startActivityForResult(intent, REQUEST_CODE_MAIL);
    }
    public void app(View v) {

        PackageManager pm = getPackageManager();//"PackageManager"とはなんぞや？
        Intent intent = pm.getLaunchIntentForPackage("com.android.vending");
        startActivity(intent);
    }
    public void camera(View v) {

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_CODE_CAMERA);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);

        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_CODE_GALLERY) {
                try {

                    InputStream inputStream = getContentResolver().openInputStream(intent.getData());
                    Bitmap bmp = BitmapFactory.decodeStream(inputStream);//Bitmapクラスはイメージビューを使用する時使用する
                    inputStream.close();
                    imageView.setImageBitmap(bmp);

                } catch (Exception e) {

                    Toast.makeText(MainActivity.this, "エラー", Toast.LENGTH_LONG).show();
                }
            }else if (requestCode == REQUEST_CODE_CAMERA){

                Bitmap bpm = (Bitmap) intent.getExtras().get("data");
                imageView.setImageBitmap(bpm);

            }
        }else if (resultCode == RESULT_CANCELED) {
            //キャンセルされたらトーストで表示！
            Toast.makeText(MainActivity.this, "CANCLE", Toast.LENGTH_LONG).show();
        }
    }
}
