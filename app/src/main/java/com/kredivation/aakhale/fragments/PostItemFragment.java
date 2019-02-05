package com.kredivation.aakhale.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kredivation.aakhale.R;
import com.kredivation.aakhale.adapter.MyPostItemRecyclerViewAdapter;
import com.kredivation.aakhale.fragments.dummy.DummyContent;

import java.util.ArrayList;
import java.util.List;

public class PostItemFragment extends Fragment {

    private StaggeredGridLayoutManager gaggeredGridLayoutManager;

    public PostItemFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static PostItemFragment newInstance(int columnCount) {
        PostItemFragment fragment = new PostItemFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_postitem_list, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);

        gaggeredGridLayoutManager = new StaggeredGridLayoutManager(2, 1);
        recyclerView.setLayoutManager(gaggeredGridLayoutManager);

        List<DummyContent> gaggeredList = getListItemData();
        MyPostItemRecyclerViewAdapter rcAdapter = new MyPostItemRecyclerViewAdapter(gaggeredList);
        recyclerView.setAdapter(rcAdapter);

        return view;
    }

    private List<DummyContent> getListItemData() {
        List<DummyContent> listViewItems = new ArrayList<DummyContent>();
        listViewItems.add(new DummyContent("The difference between the number of views on a post, video or an article is: Articles – Someone has clicked on and opened your article in their browser or on the LinkedIn mobile app. Note: Clicking into and viewing your own article also counts towards the number of views for that article.", R.drawable.p1));
        listViewItems.add(new DummyContent("The difference between the number of views on a post, video or an article is: Articles – Someone has clicked on and opened your article in their browser or on the LinkedIn mobile app. Note: Clicking into and viewing your own article also counts towards the number of views for that article.", R.drawable.p2));
        listViewItems.add(new DummyContent("Alkyne", R.drawable.p3));
        listViewItems.add(new DummyContent("The difference between the number of views on a post, video or an article is: Articles – Someone has clicked on and opened your article in their browser or on the LinkedIn mobile app. Note: Clicking into and viewing your own article also counts towards the number of views for that article.", R.drawable.p4));
        listViewItems.add(new DummyContent("Amide", R.drawable.pager1));
        listViewItems.add(new DummyContent("Amino Acid", R.drawable.pager2));
        listViewItems.add(new DummyContent("The difference between the number of views on a post, video or an article is: Articles – Someone has clicked on and opened your article in their browser or on the LinkedIn mobile app. Note: Clicking into and viewing your own article also counts towards the number of views for that article.", R.drawable.pager3));

        return listViewItems;
    }
}
