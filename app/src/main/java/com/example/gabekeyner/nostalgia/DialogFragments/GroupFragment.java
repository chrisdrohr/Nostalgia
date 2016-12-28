package com.example.gabekeyner.nostalgia.DialogFragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.gabekeyner.nostalgia.Activities.GroupsActivity;
import com.example.gabekeyner.nostalgia.Adapters.UserAdapter;
import com.example.gabekeyner.nostalgia.Firebase.FirebaseUtil;
import com.example.gabekeyner.nostalgia.ObjectClasses.Group;
import com.example.gabekeyner.nostalgia.ObjectClasses.User;
import com.example.gabekeyner.nostalgia.R;
import com.github.florent37.viewanimator.ViewAnimator;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.sloop.fonts.FontsManager;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.example.gabekeyner.nostalgia.Firebase.FirebaseUtil.getGroupMemberRef;

public class GroupFragment extends DialogFragment {

    private String mUsername, mPhotoUrl, mUid, groupName, groupId;
    private Context context;
    private TextView textView;
    private CardView cardView;
    private CircleImageView circleUserImageView;
    private EditText mEditText;
    private DatabaseReference databaseReference;
    public static String groupKey = "groupKey";


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        final LayoutInflater inflater = getActivity().getLayoutInflater();
        final View view = inflater.inflate(R.layout.fragment_group, null);
        builder.setView(view);

        cardView = (CardView) view.findViewById(R.id.cardViewprofile);
        circleUserImageView = (CircleImageView) view.findViewById(R.id.dialogUserImageView);
        textView = (TextView) view.findViewById(R.id.dialogTextView);
        mPhotoUrl = FirebaseUtil.getUser().getProfilePicture();
        mUsername = FirebaseUtil.getUser().getUserName();
        mUid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        context = getActivity();

        Glide.with(this)
                .load(mPhotoUrl)
                .priority(Priority.IMMEDIATE)
                .thumbnail(0.5f)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(circleUserImageView);
        textView.setText(mUsername);
        FontsManager.changeFonts(textView);

        ViewAnimator.animate(cardView)
                .zoomIn()
                .duration(200)
                .andAnimate(circleUserImageView, textView, mEditText)
                .alpha(0,0)
                .thenAnimate(circleUserImageView)
                .alpha(0,1)
                .bounceIn()
                .duration(200)
                .thenAnimate(textView)
                .slideBottom()
                .duration(100)
                .thenAnimate(mEditText)
                .slideBottom()
                .descelerate()
                .duration(150)
                .start();

        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mEditText = (EditText) view.findViewById(R.id.groupEditText);
                groupName = mEditText.getText().toString();

                databaseReference = FirebaseUtil.getGroupRef();
                DatabaseReference ref = databaseReference.push();
                groupKey = FirebaseUtil.getGroupRef().child(groupKey).toString();
                groupKey = ref.getKey();
                final Group group = new Group(
                        mUsername,
                        groupName,
                        mPhotoUrl,
                        groupKey);
//                getGroupRef().push().setValue(group);
                ref.setValue(group);
                groupKey = ref.getKey();
//                ref.push().child("groupId").setValue(groupKey);

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
                context.startActivity(intent);
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
        return alertDialog;
    }
}
