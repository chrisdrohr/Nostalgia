package com.example.gabekeyner.nostalgia.DialogFragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gabekeyner.nostalgia.Activities.GroupsActivity;
import com.example.gabekeyner.nostalgia.Adapters.UserAdapter;
import com.example.gabekeyner.nostalgia.Firebase.FirebaseUtil;
import com.example.gabekeyner.nostalgia.ObjectClasses.Group;
import com.example.gabekeyner.nostalgia.ObjectClasses.User;
import com.example.gabekeyner.nostalgia.R;
import com.github.florent37.viewanimator.ViewAnimator;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_OK;
import static com.example.gabekeyner.nostalgia.Firebase.FirebaseUtil.getGroupMemberRef;

public class GroupFragment extends DialogFragment {

    private final static int SELECT_PHOTO = 0;
    private String mUsername, mPhotoUrl, mUid, groupName, groupPhoto;
    private Context context;
    private TextView textView;
    private CardView cardView;
    private CircleImageView circleUserImageView;
    private ProgressBar progressBar;
    private ImageButton imageButton;
    private EditText mEditText;
    private DatabaseReference databaseReference;
    private StorageReference mStorageReference;
    private FloatingActionButton fabCancelGroup, fabCreateGroup;
    private FirebaseStorage mFirebaseStorage = FirebaseStorage.getInstance();
    public static String groupKey = "groupKey";
    private ImageView groupBg;
    private Dialog dialog;
    private Uri mMediaUri;
    private Boolean mGroupProcess = false;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        final LayoutInflater inflater = getActivity().getLayoutInflater();
        final View view = inflater.inflate(R.layout.fragment_group, null);
        builder.setView(view);

        cardView = (CardView) view.findViewById(R.id.cardViewprofile);
        imageButton = (ImageButton) view.findViewById(R.id.addGroupImage);
        circleUserImageView = (CircleImageView) view.findViewById(R.id.dialogUserImageView);
        textView = (TextView) view.findViewById(R.id.dialogTextView);
        mPhotoUrl = FirebaseUtil.getUser().getProfilePicture();
        mUsername = FirebaseUtil.getUser().getUserName();
        mUid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        groupBg = (ImageView) view.findViewById(R.id.group_bg);
        mEditText = (EditText) view.findViewById(R.id.groupEditText);
        fabCancelGroup = (FloatingActionButton) view.findViewById(R.id.fabCancelGroup);
        fabCreateGroup = (FloatingActionButton) view.findViewById(R.id.fabCreateGroup);

        mStorageReference = mFirebaseStorage.getReference().child("posts");

        context = getActivity();

        progressBar = (ProgressBar) view.findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.GONE);

//        Glide.with(this)
//                .load()
//                .priority(Priority.IMMEDIATE)
//                .centerCrop()
//                .thumbnail(0.5f)
//                .crossFade()
//                .diskCacheStrategy(DiskCacheStrategy.ALL)
//                .into(groupBg);

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent photoPickerIntent = new Intent(Intent.ACTION_GET_CONTENT);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, SELECT_PHOTO);
            }
        });

        hideEditText();

        fabCreateGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mEditText.getText().toString().matches("") || !mGroupProcess){
                    Toast.makeText(context, "Upload a group photo and name your group to continue", Toast.LENGTH_SHORT).show();
                } else {
                    intent();
                }
//                if (mGroupProcess){
//                    intent();
//                } else {
//                    Toast.makeText(context, "Upload a group photo to begin", Toast.LENGTH_SHORT).show();
//                }
            }
        });
        fabCancelGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
//                dialog.dismiss();
            }
        });
//        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                dialog.dismiss();
//            }
//        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
        return alertDialog;
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
                    groupPhoto = taskSnapshot.getDownloadUrl().toString();
                    progressBar.setVisibility(View.INVISIBLE);
                    mGroupProcess = true;
                    showEditText();
                }
            });
        }
    }

    public void intent () {
        groupName = mEditText.getText().toString();
        databaseReference = FirebaseUtil.getGroupRef();
        DatabaseReference ref = databaseReference.push();
        groupKey = FirebaseUtil.getGroupRef().child(groupKey).toString();
        groupKey = ref.getKey();
        final Group group = new Group(
                mUsername,
                groupName,
                groupPhoto,
                groupKey);
        ref.setValue(group);
        groupKey = ref.getKey();
        User user = new User(
                mUsername,
                mPhotoUrl,
                mUid,
                groupKey,
                null);
        getGroupMemberRef().push().setValue(user);
        Intent groupNameIntent = new Intent(context, UserAdapter.class);
        groupNameIntent.putExtra("groupKey", groupKey);
        context.sendBroadcast(groupNameIntent,"groupKey");

        Intent intent = new Intent(context, GroupsActivity.class);
        intent.putExtra("groupName",mEditText.getText().toString());
        intent.putExtra("groupKey", groupKey);
        intent.putExtra("groupPhoto", groupPhoto);
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
}
