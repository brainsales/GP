package com.brainsales.gameport;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class ReviewActivity extends AppCompatActivity {

    private String selectedPath = new String();
    private static final int GALLERY_REQUEST = 1;
    private static final int SELECT_VIDEO = 3;
    private ImageButton mSelectImage;
    private Button mChooseButton;
    private Uri mImageUri = null;
    private Uri mVideoUri = null;
    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);

        mSelectImage = (ImageButton) findViewById(R.id.thumbnail);
        mChooseButton = (Button) findViewById(R.id.choose_vedio);
        mTextView = (TextView) findViewById(R.id.choose_path);


        mSelectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent, GALLERY_REQUEST);
            }
        });

        mChooseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent vediointent = new Intent(Intent.ACTION_GET_CONTENT);
                vediointent.setType("video/*");
                startActivityForResult(vediointent, SELECT_VIDEO);
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GALLERY_REQUEST && resultCode == RESULT_OK) {

            mImageUri = data.getData();

            mSelectImage.setImageURI(mImageUri);

        } else if (requestCode == SELECT_VIDEO && resultCode == RESULT_OK) {

            mVideoUri = data.getData();
            selectedPath = getFileName(mVideoUri);
            mTextView.setText(selectedPath);
        }
    }

    
    public String getFileName(Uri uri) {
        String result = null;
        if (uri.getScheme().equals("content")) {
            Cursor cursor = getContentResolver().query(uri, null, null, null, null);
            try {
                if (cursor != null && cursor.moveToFirst()) {
                    result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                }
            } finally {
                cursor.close();
            }
        }
        if (result == null) {
            result = uri.getPath();
            int cut = result.lastIndexOf('/');
            if (cut != -1) {
                result = result.substring(cut + 1);
            }
        }
        return result;
    }
}
