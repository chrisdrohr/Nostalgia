package com.example.rohrlabs.nostalgia.DialogFragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.rohrlabs.nostalgia.Activities.GroupsActivity;
import com.example.rohrlabs.nostalgia.Firebase.FirebaseUtil;
import com.example.rohrlabs.nostalgia.ObjectClasses.Group;
import com.example.rohrlabs.nostalgia.ObjectClasses.User;
import com.example.rohrlabs.nostalgia.R;
import com.github.florent37.viewanimator.ViewAnimator;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import static android.app.Activity.RESULT_OK;
import static com.example.rohrlabs.nostalgia.Firebase.FirebaseUtil.getGroupRef;
import static com.example.rohrlabs.nostalgia.Firebase.FirebaseUtil.getMemberGroupRef;

public class GroupFragment extends DialogFragment {

    private final static int SELECT_PHOTO = 0;
    private String mUsername, mPhotoUrl, mUid;
    private Context context;
    private ProgressBar progressBar;
    private ImageButton imageButton;
    private EditText mEditText;
    private DatabaseReference databaseReference, ref;
    private StorageReference mStorageReference;
    private FloatingActionButton fabCancelGroup, fabCreateGroup;
    private FirebaseStorage mFirebaseStorage = FirebaseStorage.getInstance();
    public static String mGroupKey, mGroupName, mGroupPhoto, mGroupTimestamp, mGroupCreator;
    private ImageView groupBg;
    private Uri mMediaUri;
    private Boolean mGroupProcess = false;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        final LayoutInflater inflater = getActivity().getLayoutInflater();
        final View view = inflater.inflate(R.layout.fragment_group, null);
        builder.setView(view);

//        cardView = (CardView) view.findViewById(R.id.cardViewprofile);
        imageButton = (ImageButton) view.findViewById(R.id.addGroupImage);

//        circleUserImageView = (CircleImageView) view.findViewById(R.id.dialogUserImageView);
//        textView = (TextView) view.findViewById(R.id.dialogTextView);
        mPhotoUrl = FirebaseUtil.getUser().getProfilePicture();
        mUsername = FirebaseUtil.getUser().getUserName();
        mUid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        groupBg = (ImageView) view.findViewById(R.id.group_bg);
        mEditText = (EditText) view.findViewById(R.id.groupEditText);
        fabCancelGroup = (FloatingActionButton) view.findViewById(R.id.fabCancelGroup);
        fabCreateGroup = (FloatingActionButton) view.findViewById(R.id.fabCreateGroup);
        mStorageReference = mFirebaseStorage.getReference().child("groups");

        context = getActivity();

        progressBar = (ProgressBar) view.findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.GONE);

//        imageButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent photoPickerIntent = new Intent(Intent.ACTION_GET_CONTENT);
//                photoPickerIntent.setType("image/*");
//                startActivityForResult(photoPickerIntent, SELECT_PHOTO);
//            }
//        });

        hideEditText();
        pickPhoto();

        fabCreateGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mEditText.getText().toString().matches("") || !mGroupProcess){
                    Toast.makeText(context, "Upload a group photo and name your group to continue", Toast.LENGTH_SHORT).show();
                } else {
                    getKey();
                }
            }
        });
        fabCancelGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
        return alertDialog;
    }

    public void pickPhoto() {
        Intent photoPickerIntent = new Intent(Intent.ACTION_GET_CONTENT);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, SELECT_PHOTO);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SELECT_PHOTO && resultCode == RESULT_OK && data != null) {
            mMediaUri = data.getData();
            groupBg.setImageURI(mMediaUri);
            progressBar.setVisibility(View.VISIBLE);
            imageButton.setVisibility(View.INVISIBLE);
            final StorageReference photoRef = mStorageReference.child(mMediaUri.getLastPathSegment());
            photoRef.putFile(mMediaUri).addOnSuccessListener((Activity) context, new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Toast.makeText(context, "Group photo uploaded!", Toast.LENGTH_SHORT).show();
                    mGroupPhoto = taskSnapshot.getDownloadUrl().toString();
                    progressBar.setVisibility(View.INVISIBLE);
                    mGroupProcess = true;
                    showEditText();
                }
            });
        }
    }

    public void getKey () {
//        databaseReference = getGroupRef();
        databaseReference = getMemberGroupRef();
        ref = databaseReference.push();
        mGroupKey = ref.getKey();
        intent();
    }

    public void intent () {
        mGroupCreator = FirebaseUtil.getUserName();
        mGroupName = mEditText.getText().toString();
        final Group group = new Group(
                mGroupCreator,
                mGroupName,
                mGroupPhoto,
                mGroupKey);
        ref.setValue(group);

        mGroupKey = ref.getKey();

        User user = new User(
                mUsername,
                mPhotoUrl,
                mUid,
                mGroupKey,
                null);
        getGroupRef().child(mGroupKey).setValue(group);
        getGroupRef().child(mGroupKey).child("members").child(mUid).setValue(user);
//        getBaseRef().child("members").child(mUid).child("groups").child(mGroupKey).setValue("true");
//        ref.child("members").child(mUid).setValue("true");
//        ref.child("members").child(mUid).setValue(user);
//        Intent groupNameIntent = new Intent(context, UserAdapter.class);
//        groupNameIntent.putExtra("groupName",mEditText.getText().toString());
//        groupNameIntent.putExtra("mGroupKey", mGroupKey);
//        groupNameIntent.putExtra("groupPhoto", groupPhoto);
//        context.sendBroadcast(groupNameIntent,"mGroupKey");

        Intent intent = new Intent(context, GroupsActivity.class);
        intent.putExtra("groupName",mEditText.getText().toString());
        intent.putExtra("mGroupKey", mGroupKey);
        intent.putExtra("groupPhoto", mGroupPhoto);
        context.startActivity(intent);
    }
    public void showEditText () {
        ViewAnimator.animate(mEditText)
                .slideBottom()
                .duration(500)
                .start();
    }
    public void hideEditText () {
        ViewAnimator.animate(mEditText)
                .fadeOut()
                .duration(500)
                .start();
    }

    @Override
    public void dismiss() {
        super.dismiss();
//        getGroupRef().child(mGroupKey).removeValue();
    }

    public void show(FragmentManager fragmentManager, String s) {
        fragmentManager.beginTransaction().commit();
    }
}
