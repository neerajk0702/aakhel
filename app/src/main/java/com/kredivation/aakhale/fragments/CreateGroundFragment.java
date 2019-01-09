package com.kredivation.aakhale.fragments;


import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kredivation.aakhale.R;
import com.kredivation.aakhale.adapter.RecycleViewAdapter;
import com.kredivation.aakhale.model.ImageItem;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class CreateGroundFragment extends Fragment {

    View view;

    RecyclerView addImageView;
    private RecycleViewAdapter imageAdapater;


    public CreateGroundFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_create_ground, container, false);
        init();
        getActivity().setTitle("Create Ground");
        return view;
    }

    public void init() {
        addImageView = view.findViewById(R.id.addImageView);

        imageAdapater = new RecycleViewAdapter(getContext(), R.layout.image_item_layout, getData());
        addImageView.setAdapter(imageAdapater);

        setLinearLayoutManager(addImageView);
        addImageView.setNestedScrollingEnabled(false);
        addImageView.setHasFixedSize(false);
    }

    private void setLinearLayoutManager(RecyclerView recyclerView) {
        RecyclerView.LayoutManager LayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(LayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    // Prepare some dummy data for gridview
    private ArrayList<ImageItem> getData() {
        final ArrayList<ImageItem> imageItems = new ArrayList<>();
        TypedArray imgs = getResources().obtainTypedArray(R.array.image_ids);
        for (int i = 0; i < imgs.length(); i++) {
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), imgs.getResourceId(i, -1));
            //imageItems.add(new ImageItem(bitmap, "Image#" + i));
        }
        return imageItems;
    }
}
