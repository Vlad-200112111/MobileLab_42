package com.example.mobilelab_42;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;

public class CitizenshipByIdActivity extends AppCompatActivity {

    private DataBaseHelper mDBHelper;
    private SQLiteDatabase mDb;

    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_citizenship_by_id);


        TextView fullname = findViewById(R.id.fullname);
        TextView pasport = findViewById(R.id.pasport);
        TextView snils = findViewById(R.id.snils);
        TextView inn = findViewById(R.id.inn);
        ImageView imageView = findViewById(R.id.avatar);

        mDBHelper = new DataBaseHelper(this);

        try {
            mDBHelper.updateDataBase();
        } catch (IOException mIOException) {
            throw new Error("UnableToUpdateDatabase");
        }

        try {
            mDb = mDBHelper.getWritableDatabase();
        } catch (SQLException mSQLException) {
            throw mSQLException;
        }

        Intent intent = getIntent();

        String id_sitizenship = intent.getStringExtra("pos");

        Cursor data = mDb.rawQuery("SELECT * FROM citizenship WHERE id = " + id_sitizenship + "", null);

        if (data.moveToNext()) {
            byte[] image = data.getBlob(7);
            Bitmap bmp = BitmapFactory.decodeByteArray(image, 0, image.length);
            imageView.setImageBitmap(bmp);
            fullname.setText(data.getString(1) + " " + data.getString(2) + " " + data.getString(3));
            pasport.setText(data.getString(4));
            snils.setText(data.getString(5));
            inn.setText(data.getString(6));
        }
    }
}