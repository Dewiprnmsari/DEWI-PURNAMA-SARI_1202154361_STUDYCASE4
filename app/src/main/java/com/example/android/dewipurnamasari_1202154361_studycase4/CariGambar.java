package com.example.android.dewipurnamasari_1202154361_studycase4;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import java.io.InputStream;
import java.net.URL;


public class CariGambar extends AppCompatActivity {

    private EditText mLink;
    private Button mCari;
    private ImageView mTampilGambar;
    private ProgressDialog mProgressDialog;


    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cari_gambar);

        mLink = findViewById(R.id.Link);
        mCari = findViewById(R.id.Cari);
        mTampilGambar = findViewById(R.id.tampilGambar);
    }

    public void klikCari (View view) { loadImageInit(); }

    private void loadImageInit(){
        String ImgUrl = mLink.getText().toString();
        new loadImage().execute(ImgUrl); //AsyncTask mencari gambar di internet
    }

    public class loadImage extends AsyncTask<String, Void, Bitmap> {

        @Override
        protected void onPreExecute() { //method ini dipanggil sebelum task dikerjakan
            super.onPreExecute();

            mProgressDialog = new ProgressDialog (CariGambar.this); //membuat progress dialog

            mProgressDialog.setMessage("Loading..."); //isi message progress dialog
            mProgressDialog.show(); //menampilkan progress dialog
        }

        //params : parameter tipe data seperti String untuk link URL yang ingin dilakukan request
        @Override
        protected Bitmap doInBackground(String... params) {
            Bitmap bitmap = null;
            try {
                URL url = new URL(params[0]); //mendownload gambar dari url
                //mengkonversikan gambar ke bitmap (decode to bitmap)
                bitmap = BitmapFactory.decodeStream((InputStream)url.getContent());
            } catch (Exception e){
                e.printStackTrace();
            }
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            mTampilGambar.setImageBitmap(bitmap); //menampung gambar ke imageview dan menampilkannya
            mProgressDialog.dismiss(); //menghilangkan progress dialog
        }
    }
}
