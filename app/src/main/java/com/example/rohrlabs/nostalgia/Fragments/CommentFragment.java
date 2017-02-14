package com.example.rohrlabs.nostalgia.Fragments;

import android.app.Fragment;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.CardView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.dragankrstic.autotypetextview.AutoTypeTextView;
import com.example.rohrlabs.nostalgia.Firebase.FirebaseUtil;
import com.example.rohrlabs.nostalgia.ObjectClasses.Comment;
import com.example.rohrlabs.nostalgia.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

import java.text.SimpleDateFormat;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;

public class CommentFragment extends Fragment implements View.OnClickListener{

    private CircleImageView userImageView;
    private String mPhotoUrl, mUsername, mUid, commentPath, mCommentKey;
    private TextView textView;
    private CardView cardView;
    private ImageView commentBg;
    private DatabaseReference databaseReference;
    private AutoTypeTextView autoTypeTextView;
    private EditText mEditText;
    private String mPostKey;
    public static String post_image;
    private FloatingActionButton mSendFab;

    public CommentFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPhotoUrl = FirebaseUtil.getUser().getProfilePicture();
        mUsername = FirebaseUtil.getUser().getUserName();
        mUid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        mPostKey = getActivity().getIntent().getExtras().getString("post_key");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_comment, container, false);
        mSendFab = (FloatingActionButton) view.findViewById(R.id.fabSend);
        mEditText = (EditText) view.findViewById(R.id.commentEditText);
        mSendFab.setOnClickListener(this);
        mEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().trim().length() > 0) {
                    mSendFab.setEnabled(true);
                } else {
                    mSendFab.setEnabled(false);
                }
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        return view;
    }

    @Override
    public void onClick(View view) {
        commentPath = FirebaseUtil.getBaseRef().child("posts").getKey();
        SimpleDateFormat time = new SimpleDateFormat("MM/dd-hh:mm");
        final String mCurrentTimestamp = time.format(new Date());
        databaseReference = FirebaseUtil.getCommentsRef().child(mPostKey);
        DatabaseReference ref = databaseReference.push();
        mCommentKey = ref.getKey();
        Comment comment = new
                Comment(mEditText.getText().toString(),
                mUsername,
                mPhotoUrl,
                mPostKey,
                mCurrentTimestamp,
                mUid,
                mCommentKey
        );
        ref.setValue(comment);
        mEditText.setText("");


//        ((DetailActivity)getActivity()).postComment();
    }


//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
//    }

//    @Override
//    public void onDetach() {
//        super.onDetach();
//        mListener = null;
//    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
