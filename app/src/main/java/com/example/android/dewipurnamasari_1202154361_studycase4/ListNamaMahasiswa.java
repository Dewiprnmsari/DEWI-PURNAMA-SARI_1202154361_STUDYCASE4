package com.example.android.dewipurnamasari_1202154361_studycase4;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;

import java.util.ArrayList;

public class ListNamaMahasiswa extends AppCompatActivity {

    private ListView mListNamaMahasiswa;
    private Button mStartAsyncTask;
    private ProgressBar mProgressBar;
    private String[] ArrayMahasiswa = {
            "Dewi", "Rizka", "Astrid", "Ina", "Silvia", "Jafar", "Farrel", "Ilham", "Defa", "Fakhri"};
    private AddItemToListView mAddItemToListView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_nama_mahasiswa);

        mListNamaMahasiswa = findViewById(R.id.ListNamaMahasiswa);
        mProgressBar = findViewById(R.id.progressBar);
        mStartAsyncTask = findViewById(R.id.buttonMulai);

        mListNamaMahasiswa.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, new ArrayList<String>()));

        mStartAsyncTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAddItemToListView = new AddItemToListView();
                mAddItemToListView.execute();
            }
        });
    }

    //<Void, String, Void> merupakan parameter yang diberikan ke class AsyncTask
    public class AddItemToListView extends AsyncTask<Void, String, Void> {

        private ArrayAdapter<String> mAdapter;
        private int counter = 1;
        ProgressDialog mProgressDialog = new ProgressDialog(ListNamaMahasiswa.this);

        @Override
        protected void onPreExecute() { //method yang dijalankan sebelum task dimulai
            mAdapter = (ArrayAdapter<String>) mListNamaMahasiswa.getAdapter(); //casting suggestion

            //isi progress dialog
            mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            mProgressDialog.setTitle("Loading Data");
            mProgressDialog.setCancelable(false);
            mProgressDialog.setMessage("Please Wait..");
            mProgressDialog.setProgress(0);
            //ini digunakan saat menekan tombol cancel progress
            mProgressDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel Progress", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    mAddItemToListView.cancel(true);
                    mProgressBar.setVisibility(View.VISIBLE);
                    dialog.dismiss();
                }
            });
            mProgressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... voids) { //mengimplemntasikan kode untuk mengeksekusi pekerjaan yang akan dilakukan
            for (String item : ArrayMahasiswa) {
                publishProgress(item);
                try {
                    Thread.sleep(500); //untuk memberikan delay selama 500ms
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (isCancelled()) {
                    mAddItemToListView.cancel(true);
                }
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(String... values) { //untuk update progress
            mAdapter.add(values[0]);

            Integer current_status = (int) ((counter / (float) ArrayMahasiswa.length) * 100);
            mProgressBar.setProgress(current_status);
            mProgressDialog.setProgress(current_status);;
            mProgressDialog.setMessage(String.valueOf(current_status + "%"));
            counter++;
        }

        @Override
        protected void onPostExecute(Void aVoid) { //method ini digunakan untuk mengupdate UI ketika proses doInBackground telah selesai
            mProgressBar.setVisibility(View.GONE); //untuk menyembunyikan progress bar

            //untuk menghilangkan progress dialog
            mProgressDialog.dismiss();
            mListNamaMahasiswa.setVisibility(View.VISIBLE);
        }
    }
}
