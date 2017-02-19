package com.example.rohrlabs.nostalgia.Fragments;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.example.rohrlabs.nostalgia.Adapters.GroupsAdapter;
import com.example.rohrlabs.nostalgia.DialogFragments.GroupFragment;
import com.example.rohrlabs.nostalgia.DialogFragments.UploadFragment;
import com.example.rohrlabs.nostalgia.R;

public class PostFragment extends android.app.Fragment implements View.OnClickListener{

    private static final String TAG = "PostFragment";
    private FloatingActionButton mFabPhoto, mFabGroup, mFabVideo;
    private ImageView mImageViewGroupBg;
    public static String groupPhoto;
    private RelativeLayout mFabLayout;

    public PostFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.layout_post_tab, container, false);
        mFabGroup = (FloatingActionButton) view.findViewById(R.id.fabGroup);
        mFabPhoto = (FloatingActionButton) view.findViewById(R.id.fabPhoto);
        mFabVideo = (FloatingActionButton) view.findViewById(R.id.fabVideo);
        mImageViewGroupBg = (ImageView) view.findViewById(R.id.imageViewGroupBg);
        mFabLayout = (RelativeLayout) view.findViewById(R.id.fabLayout);
        groupPhoto = GroupsAdapter.groupPhoto;

        mFabGroup.setOnClickListener(this);
        mFabPhoto.setOnClickListener(this);
        mFabVideo.setOnClickListener(this);

        if (groupPhoto != null) {
            Glide.with(getActivity())
                    .load(groupPhoto)
                    .centerCrop()
                    .into(mImageViewGroupBg);
        } else {

        }
        return view;
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.fabGroup:
                openGroupsActivity();
                break;
            case R.id.fabPhoto:
                openUploadFragment();
                break;
            case R.id.fabVideo:
                break;
            default:
        }
    }

    public void openGroupsActivity () {
        GroupFragment groupFragment = new GroupFragment();
        groupFragment.show(getFragmentManager(), "Group Fragment");
    }

    public void openUploadFragment () {
        UploadFragment uploadFragment = new UploadFragment();
        uploadFragment.show(getFragmentManager(),"Upload Fragment");
//        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
//        UploadFragment uploadFragment = new UploadFragment();
//        uploadFragment.show(fragmentManager, "Upload Fragment");

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
