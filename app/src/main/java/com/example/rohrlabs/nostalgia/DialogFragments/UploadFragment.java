package com.example.rohrlabs.nostalgia.DialogFragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.rohrlabs.nostalgia.Adapters.GroupsAdapter;
import com.example.rohrlabs.nostalgia.Firebase.FirebaseUtil;
import com.example.rohrlabs.nostalgia.ObjectClasses.Post;
import com.example.rohrlabs.nostalgia.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static android.app.Activity.RESULT_OK;

public class UploadFragment extends android.support.v4.app.DialogFragment {

    private final static int SELECT_PHOTO = 0;
    private String mUsername, mPhotoUrl, mUid, groupName, groupPhoto, postPhoto;
    private Context context;
    private ProgressBar progressBar;
    private EditText mEditText;
    private StorageReference mStorageReference;
    private FirebaseStorage mFirebaseStorage = FirebaseStorage.getInstance();
    private FloatingActionButton mFabUpload, mFabCancel;
    public static String mGroupKey = "mGroupKey";
    private ImageView uploadImage;
    private Uri mMediaUri;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        final LayoutInflater inflater = getActivity().getLayoutInflater();
        final View view = inflater.inflate(R.layout.fragment_upload, null);
        builder.setView(view);

        Intent photoPickerIntent = new Intent(Intent.ACTION_GET_CONTENT);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, SELECT_PHOTO);

        mGroupKey = GroupsAdapter.mGroupKey;
        mPhotoUrl = FirebaseUtil.getUser().getProfilePicture();
        mUsername = FirebaseUtil.getUser().getUserName();
        mUid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        uploadImage = (ImageView) view.findViewById(R.id.uploadImage);
        mEditText = (EditText) view.findViewById(R.id.uploadEditText);
        mFabCancel = (FloatingActionButton) view.findViewById(R.id.fabCancelUpload);
        mFabUpload = (FloatingActionButton) view.findViewById(R.id.fabUpload);
        postPhoto = mEditText.getText().toString();
        mStorageReference = mFirebaseStorage.getReference().child("posts");
        context = getActivity();
        progressBar = (ProgressBar) view.findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.GONE);

        mFabUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
                Toast.makeText(context, "Uploading photo...", Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.VISIBLE);
                final StorageReference photoRef = mStorageReference.child(mMediaUri.getLastPathSegment());
                SimpleDateFormat time = new SimpleDateFormat("ddMMyyyy", Locale.US);
                final String mTimestamp = time.format(new Date());
                photoRef.putFile(mMediaUri).addOnSuccessListener((Activity) context, new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Post post = new Post(
                                taskSnapshot.getDownloadUrl().toString(),
                                mEditText.getText().toString(),
                                mUsername,
                                mTimestamp,
                                mUid);
                        FirebaseUtil.getBaseRef().child("groups").child(mGroupKey).child("posts").push().setValue(post);
                        Toast.makeText(context, "Photo uploaded!", Toast.LENGTH_SHORT).show();
                        postPhoto = taskSnapshot.getDownloadUrl().toString();
                        progressBar.setVisibility(View.INVISIBLE);
                    }
                });
            }
        });
        mFabCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
        return alertDialog;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SELECT_PHOTO && resultCode == RESULT_OK && data != null) {
            mMediaUri = data.getData();
            uploadImage.setImageURI(mMediaUri);
        } else {
            getDialog().dismiss();
        }
    }

    @Override
    public void dismiss() {
        super.dismiss();

    }



}
