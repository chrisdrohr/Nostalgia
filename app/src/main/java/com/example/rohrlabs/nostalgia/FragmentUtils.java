package com.example.rohrlabs.nostalgia;

import com.example.rohrlabs.nostalgia.RecyclerFragments.GroupsItemFragment;
import com.example.rohrlabs.nostalgia.RecyclerFragments.MainItemFragment;
import com.example.rohrlabs.nostalgia.RecyclerFragments.RecyclerChatFragment;

public class FragmentUtils {

    public static MainItemFragment getMainItemFragment () {
        return new MainItemFragment();
    }

    public static RecyclerChatFragment getRecyclerChatFragment () {
        return new RecyclerChatFragment();
    }

    public static GroupsItemFragment getGroupsItemFragment () {
        return new GroupsItemFragment();
    }
}
