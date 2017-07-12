package com.brainsales.gameport;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.brainsales.gameport.utils.Gameport;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SettingsActivity extends AppCompatActivity {
    public SettingsActivity() {}

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private ProgressDialog mProgress;
    private StorageReference mStorage;
    private Uri mImageUri = null;
    private static final int GALLERY_REQUEST = 1;

    @BindView(R.id.profile_image) ImageButton _userImage;
    @BindView(R.id.user_Name) EditText _userName;
    @BindView(R.id.bank_Name) EditText _bankName;
    @BindView(R.id.account_Text) EditText _accountText;
    @BindView(R.id.phone_Text) EditText _phoneText;
    @BindView(R.id.user_Apply) Button _userApply;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        ButterKnife.bind(this);

        mAuth = FirebaseAuth.getInstance();
        mStorage = FirebaseStorage.getInstance().getReference();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Poters");
        mProgress = new ProgressDialog(this);

        _userImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent, GALLERY_REQUEST);
            }
        });

        _userApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register();
            }
        });
    }

    private void register() {

        mProgress.setMessage("업데이트 중 ...");

        final String userName = _userName.getText().toString().trim();
        final String bankName = _bankName.getText().toString().trim();
        final String accountText = _accountText.getText().toString().trim();
        final String phoneText = _phoneText.getText().toString().trim();

        if (!TextUtils.isEmpty(userName) && !TextUtils.isEmpty(bankName) && !TextUtils.isEmpty(accountText) && !TextUtils.isEmpty(phoneText) && mImageUri != null) {

            mProgress.show();
            StorageReference filepath = mStorage.child("User_Images").child(mImageUri.getLastPathSegment());

            filepath.putFile(mImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    @SuppressWarnings("VisibleForTests") Uri downloadUrl = taskSnapshot.getDownloadUrl();

                    DatabaseReference newPost = mDatabase.push();
                    newPost.child("username").setValue(userName);
                    newPost.child("bankname").setValue(bankName);
                    newPost.child("accountText").setValue(accountText);
                    newPost.child("phoneText").setValue(phoneText);
                    newPost.child("image").setValue(downloadUrl.toString());

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
            _userImage.setImageURI(mImageUri);

        }
    }

}
