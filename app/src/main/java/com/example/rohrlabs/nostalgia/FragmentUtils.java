package com.example.rohrlabs.nostalgia;

import com.example.rohrlabs.nostalgia.Fragments.GroupMembersFragment;
import com.example.rohrlabs.nostalgia.RecyclerFragments.ChatListFragment;
import com.example.rohrlabs.nostalgia.RecyclerFragments.GroupsItemFragment;
import com.example.rohrlabs.nostalgia.RecyclerFragments.PostItemFragment;

public class FragmentUtils {

    public static PostItemFragment getMainItemFragment () {
        return new PostItemFragment();
    }

    public static ChatListFragment getRecyclerChatFragment () {
        return new ChatListFragment();
    }

    public static GroupMembersFragment getGroupMembersFragment () {
        return new GroupMembersFragment();
    }
    public static com.example.rohrlabs.nostalgia.RecyclerFragments.GroupMembersFragment getGroupMembersList() {
        return new com.example.rohrlabs.nostalgia.RecyclerFragments.GroupMembersFragment();
    }

    public static GroupsItemFragment getGroupsItemFragment () {
        return new GroupsItemFragment();
    }
}
