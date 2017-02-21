package com.example.rohrlabs.nostalgia.Fragments;

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
import com.example.rohrlabs.nostalgia.Adapters.GroupsAdapter;
import com.example.rohrlabs.nostalgia.Firebase.FirebaseUtil;
import com.example.rohrlabs.nostalgia.FragmentUtils;
import com.example.rohrlabs.nostalgia.ObjectClasses.Chat;
import com.example.rohrlabs.nostalgia.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

import java.text.SimpleDateFormat;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;

public class ChatFragment extends android.app.Fragment implements View.OnClickListener{

    private static final String TAG = "ChatFragment";
    private CircleImageView userImageView;
    private String mPhotoUrl, mUsername, mUid, chatPath, mChatKey;
    private TextView textView;
    private CardView cardView;
    private ImageView commentBg;
    private DatabaseReference databaseReference;
    private AutoTypeTextView autoTypeTextView;
    private EditText mEditText;
    public static String mGroupKey, mGroupPhoto;
    private FloatingActionButton mSendFab;

    public ChatFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPhotoUrl = FirebaseUtil.getUser().getProfilePicture();
        mUsername = FirebaseUtil.getUser().getUserName();
        mUid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        mGroupKey = GroupsAdapter.mGroupKey;
        mGroupPhoto = GroupsAdapter.groupPhoto;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.layout_chat_tab, container, false);
        mSendFab = (FloatingActionButton) view.findViewById(R.id.fabChatSend);
        mEditText = (EditText) view.findViewById(R.id.chatEditText);
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

        getChildRecyclerChatFragment();
        return view;
    }

    @Override
    public void onClick(View view) {
        chatPath = FirebaseUtil.getChatRef().getKey();
        SimpleDateFormat time = new SimpleDateFormat("MM/dd-hh:mm");
        final String mCurrentTimestamp = time.format(new Date());
        databaseReference = FirebaseUtil.getChatRef();
        DatabaseReference ref = databaseReference.push();
        mChatKey = ref.getKey();
        Chat chat = new
                Chat(mEditText.getText().toString(),
                mUsername,
                mPhotoUrl,
                mGroupKey,
                mCurrentTimestamp,
                mUid,
                mChatKey
        );
        ref.setValue(chat);
        mEditText.setText("");
    }

    void getChildRecyclerChatFragment () {
        getChildFragmentManager()
                .beginTransaction()
                .add(R.id.containerRecyclerChatFragment, FragmentUtils.getRecyclerChatFragment())
                .commit();
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
//        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
//        Fragment fragment = (fragmentManager.findFragmentByTag(TAG));
//        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//        fragmentTransaction.remove(fragment).commit();
    }
}
