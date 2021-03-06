package com.brainsales.gameport;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.media.Image;
import android.net.Uri;
import android.provider.OpenableColumns;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.brainsales.gameport.utils.Gameport;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class ReviewActivity extends AppCompatActivity {

    private String selectedPath = new String();
    private static final int GALLERY_REQUEST = 2;
    private static final int GALLERY_REQUEST_card = 1;
    private static final int SELECT_VIDEO = 3;
    private ImageButton mSelectImage;
    private ImageButton mSelectCard;
    private DatabaseReference mDatabase;
    private Button mChooseButton;
    private Button mAnnounceVideo;
    private EditText mDescription;
    private Uri mImageUri = null;
    private Uri mCardImage = null;
    private Uri mVideoUri = null;
    private Uri finalUriImage = null;
    private Uri finalUriCard = null;
    private ProgressDialog mProgress;
    private TextView mTextView;
    private StorageReference mStorage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);

        mDatabase = FirebaseDatabase.getInstance().getReference().child("Reviews");
        mStorage = FirebaseStorage.getInstance().getReference();
        mSelectImage = (ImageButton) findViewById(R.id.thumbnail);
        mSelectCard = (ImageButton) findViewById(R.id.user_cardimage);
        mChooseButton = (Button) findViewById(R.id.choose_vedio);
        mTextView = (TextView) findViewById(R.id.choose_path);
        mDescription = (EditText) findViewById(R.id.description_text);
        mAnnounceVideo = (Button) findViewById(R.id.apply_button);
        mProgress = new ProgressDialog(this);

        mSelectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent, GALLERY_REQUEST);
            }
        });

        mSelectCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent, GALLERY_REQUEST_card);
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

        mAnnounceVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startAnnounceVideo();

            }
        });
    }

    private void startAnnounceVideo() {

        mProgress.setMessage("Posting to Square");

        final String VideoDescription = mDescription.getText().toString().trim();

        if (!TextUtils.isEmpty(VideoDescription) && mVideoUri != null && mImageUri != null && mCardImage != null) {

            mProgress.show();

            StorageReference filepath_Images = mStorage.child("Thumbnail_Images").child(mImageUri.getLastPathSegment());
            filepath_Images.putFile(mImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    @SuppressWarnings("VisibleForTests") Uri downloadUrl = taskSnapshot.getDownloadUrl();

                    finalUriImage = downloadUrl;

                }
            });


            StorageReference filepath_Card = mStorage.child("Card_Image").child(mCardImage.getLastPathSegment());
            filepath_Card.putFile(mCardImage).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    @SuppressWarnings("VisibleForTests") Uri downloadUrl = taskSnapshot.getDownloadUrl();

                    finalUriCard = downloadUrl;

                }
            });


            StorageReference filepath_Video = mStorage.child("video_review").child(mVideoUri.getLastPathSegment());
            filepath_Video.putFile(mVideoUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    @SuppressWarnings("VisibleForTests") Uri downloadUrl = taskSnapshot.getDownloadUrl();

                    DatabaseReference newPost = mDatabase.push();
                    newPost.child("Description").setValue(VideoDescription);
                    newPost.child("Thumbnail_Images").setValue(finalUriImage.toString());
                    newPost.child("uid").setValue(FirebaseAuth.getInstance().getCurrentUser().getUid());
                    newPost.child("video_review").setValue(downloadUrl.toString());
                    newPost.child("Card_Image").setValue(finalUriCard.toString());

                    mProgress.dismiss();

                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                }
            });

        } else {
            Toast.makeText(getApplicationContext(), "Fill Up All Info", Toast.LENGTH_LONG).show();
            return;
        }
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

        } else if (requestCode == GALLERY_REQUEST_card && resultCode == RESULT_OK) {

            mCardImage = data.getData();
            mSelectCard.setImageURI(mCardImage);
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
