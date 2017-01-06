package com.example.gabekeyner.nostalgia;

import android.content.Context;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.example.gabekeyner.nostalgia.Activities.CameraActivity;

import java.util.Date;

public class GalleryFragment {
    //Activity Intents for Button declarations
    public static final String ACTIVITY_INTENTION = "Activity Intention";
    public static final String GALLERY_PICKER = "Gallery Picker";
    public static final String GALLERY_VIDEO_PICKER = "Gallery Video Picker";

    //Request Codes for Media Capturing/Gallery
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


}
