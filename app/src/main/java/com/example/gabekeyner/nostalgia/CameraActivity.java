package com.example.gabekeyner.nostalgia;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.github.florent37.viewanimator.ViewAnimator;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CameraActivity extends AppCompatActivity implements View.OnClickListener {

    //Activity Intents for Button declarations
    public static final String ACTIVITY_INTENTION = "Activity Intention";
    public static final String TAKE_PHOTO = "Take Photo";
    public static final String GALLERY_PICKER = "Gallery Picker";
    public static final String VIDEO_SHOOTER = "Video Shooter";
    public static final String GALLERY_VIDEO_PICKER = "Gallery Video Picker";

    //Request Codes for Media Capturing/Gallery
    public static final int REQUEST_TAKE_PHOTO = 0;
    public static final int REQUEST_TAKE_VIDEO = 1;
    public static final int REQUEST_PICK_PHOTO = 2;
    public static final int REQUEST_PICK_VIDEO = 3;
    public static final int MEDIA_TYPE_IMAGE = 4;
    public static final int MEDIA_TYPE_VIDEO = 5;

    private static final String TAG = CameraActivity.class.getCanonicalName();

    private String filepath;
    private EditText mTitle;
    private FloatingActionButton mUploadFab;
    private ProgressBar progressBar;
    private ImageView mImageView;
    private Date date;
    private Uri mMediaUri;
    private String mUsername;
    private long mTimestamp;
    private String mUid;
    private Context context;

    //FireBase
    private StorageReference mStorageReference;
    private DatabaseReference mDatabaseReference;
    private FirebaseStorage mFirebaseStorage = FirebaseStorage.getInstance();
    private DatabaseReference mPhotosReference = FirebaseDatabase.getInstance().getReference().child("posts");

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        mTitle = (EditText) findViewById(R.id.editText);
        mImageView = (ImageView) findViewById(R.id.cameraImageView);
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.GONE);
        mUploadFab = (FloatingActionButton) findViewById(R.id.uploadFab);
        mUploadFab.setOnClickListener(this);

        mStorageReference = mFirebaseStorage.getReference().child("posts");

        //check flag variable
        Bundle extras = getIntent().getExtras();
        //handles the intent passed from the Main Activity using String ACITVITY_TRANSITION
        if (extras.containsKey(ACTIVITY_INTENTION)) {
            //TAKE PHOTO
            if (extras.getString(ACTIVITY_INTENTION).equals(TAKE_PHOTO)) {
                Intent takePhotoIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                mMediaUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);
                if (takePhotoIntent.resolveActivity(getPackageManager()) !=null) {
                takePhotoIntent.putExtra(MediaStore.EXTRA_OUTPUT, mMediaUri);
                    startActivityForResult(takePhotoIntent, REQUEST_TAKE_PHOTO);
                }

                //PICK FROM GALLERY
            } else if (extras.getString(ACTIVITY_INTENTION).equals(GALLERY_PICKER)) {
                Intent pickPhotoIntent = new Intent(Intent.ACTION_GET_CONTENT);
                pickPhotoIntent.setType("image/*");
                startActivityForResult(pickPhotoIntent, REQUEST_PICK_PHOTO);

                //TAKE VIDEO
            } else if (extras.getString(ACTIVITY_INTENTION).equals(VIDEO_SHOOTER)) {
                Intent takeVideoIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
//                mMediaUri = getOutputMediaFileUri(MEDIA_TYPE_VIDEO);
                takeVideoIntent.putExtra(MediaStore.EXTRA_OUTPUT, mMediaUri);
                takeVideoIntent.putExtra(MediaStore.EXTRA_DURATION_LIMIT, 15);
                startActivityForResult(takeVideoIntent, REQUEST_TAKE_VIDEO);

                //PICK FROM VIDEO GALLERY
            } else if (extras.getString(ACTIVITY_INTENTION).equals(GALLERY_VIDEO_PICKER)) {
                Intent pickVideoIntent = new Intent(Intent.ACTION_GET_CONTENT);
                pickVideoIntent.setType("video/*");
                startActivityForResult(pickVideoIntent, REQUEST_PICK_VIDEO);
            }
        }
    }

    public Uri getOutputMediaFileUri(int mediaType) {
        //check for external storage
        if (isExternalStorageAvailable()) {
            // get the URI
            File mediaStorageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
            String fileName = "";
            String fileType = "";
            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());

            if (mediaType == MEDIA_TYPE_IMAGE) {
                fileName = "IMG_" + FirebaseUtil.getUser().getUserName() + timeStamp;
                fileType = ".jpg";
            } else if (mediaType == MEDIA_TYPE_VIDEO) {
                fileName = "VID_" + timeStamp;
                fileType = ".mp4";
            } else {
                return null;
            }
//            try {
//
////                 Uri photoURI = FileProvider.getUriForFile(CameraActivity.this, BuildConfig.APPLICATION_ID + ".provider", createImageUri());
////                filepath = mediaFile.getAbsolutePath();
////                Log.i(TAG, "File: " + Uri.fromFile(mediaFile));
////
////                return Uri.fromFile(mediaFile);
////            } catch (IOException e) {
////                Log.e(TAG, "Error creating file: " + mediaStorageDir.getAbsolutePath() + fileName + fileType);
//            }
        }
        // something went wrong
        return null;
    }

    private Uri createImageUri() {
        ContentResolver contentResolver = getContentResolver();
        ContentValues contentValues = new ContentValues();
        contentValues.put(MediaStore.Images.Media.TITLE,mTimestamp);
        return contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
    }

    private boolean isExternalStorageAvailable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_TAKE_PHOTO) {
                //CREATES FILE FOR THE IMAGE
                Uri imageUri = Uri.parse(filepath);
                File file = new File(imageUri.getPath());
//                Bundle extras = data.getExtras();
//                Bitmap imageBitmap = (Bitmap) extras.get("data");
//                    mImageView.setImageBitmap(imageBitmap);

            } else if (requestCode == REQUEST_PICK_PHOTO) {
                if (data != null) {
                    mMediaUri = data.getData();
                    mImageView.setImageURI(mMediaUri);
                }


            } else if (requestCode == REQUEST_TAKE_VIDEO) {

                //CREATES FILE FOR THE VIDEO
                File file = new File(filepath);
                if (file.exists()) {
                    Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
                    mImageView.setImageBitmap(bitmap);
                }

            } else if (resultCode == REQUEST_PICK_VIDEO) {
                if (data != null) {
                    mMediaUri = data.getData();
                    mImageView.setImageURI(mMediaUri);
                }
            }
        } else if (resultCode != RESULT_CANCELED) {
            Intent intent = new Intent(this, MainActivity.class);
            Toast.makeText(this, "Sorry, there was an error!", Toast.LENGTH_LONG).show();
            startActivity(intent);

        }else if (data == null) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public void onClick(View v) {
        InputMethodManager inputManager = (InputMethodManager) getSystemService(this.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        ViewAnimator.animate(mTitle)
                .fadeOut()
                .duration(100)
                .andAnimate(mUploadFab)
                .bounce()
                .duration(200)
                .thenAnimate(mUploadFab)
                .newsPaper()
                .duration(4000)
                .start();

        progressBar.setVisibility(View.VISIBLE);
        mUploadFab.setEnabled(false);
        final StorageReference photoRef = mStorageReference.child(mMediaUri.getLastPathSegment());
        mUsername = FirebaseUtil.getUser().getUserName();
        mUid = FirebaseUtil.getUid();
        SimpleDateFormat time = new SimpleDateFormat("ddMMyyyy");
        final String mTimestamp = time.format(new Date());
        photoRef.putFile(mMediaUri).addOnSuccessListener(this, new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Post post = new Post(
                        taskSnapshot.getDownloadUrl().toString(),
                        mTitle.getText().toString(),
                        mUsername,
                        mTimestamp,
                        mUid);
                mPhotosReference.push().setValue(post);
                progressBar.setVisibility(View.GONE);
                mUploadFab.setEnabled(true);
                Toast.makeText(CameraActivity.this, "Memory Uploaded!", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(CameraActivity.this, MainActivity.class));
                finish();
            }
        });
    }
}







