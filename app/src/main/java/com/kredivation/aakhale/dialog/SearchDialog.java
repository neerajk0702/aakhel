package com.kredivation.aakhale.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.kredivation.aakhale.R;
import com.kredivation.aakhale.adapter.ListAdapter;
import com.kredivation.aakhale.utility.ASTUIUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Altametrics Inc.
 */
public class SearchDialog extends Dialog implements View.OnClickListener {

    ListAdapter adapter;
    List<String> arrayList = new ArrayList<>();
    ListView listView;
    SearchView search;

    public SearchDialog(Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.setContentView(R.layout.search_dilog);
//        float radius = ASTUIUtil.getDimension(R.dimen._15dp);
        //  int scrWidth = getContext().getResources().getConfiguration().screenWidthDp;
        //	this.getWindow().setLayout((FNUtil.getDipFromPixel(this.getContext(), scrWidth - 50)), LinearLayout.LayoutParams.WRAP_CONTENT);
        //	ASTUIUtil.setBackgroundRound(this.findViewById(R.id.DialogNLayout), R.color.bg_color, new float[] { radius, radius, radius, radius, radius, radius, radius, radius });
        //ASTUIUtil.setBackgroundRound(this.findViewById(R.id.pop_up_footer), android.R.color.white, new float[] { 0, 0, 0, 0, radius, radius, radius, radius });
        //this.findViewById(R.id.footerDivider).setVisibility(View.GONE);
        this.setCanceledOnTouchOutside(true);
        this.getWindow().setBackgroundDrawableResource(android.R.color.darker_gray);

        listView = findViewById(R.id.listView);
        search = findViewById(R.id.search);
        arrayList.add("Sachin");
        arrayList.add("Neeraj");
        arrayList.add("Narayan");
        arrayList.add("Ram");
        arrayList.add("Shyam");
        arrayList.add("Kapil");
        arrayList.add("Jagu");
        adapter = new ListAdapter(arrayList, getContext());
        listView.setAdapter(adapter);
        search.setActivated(true);
        search.setQueryHint("Type Player Uniq id or Name here");
        search.onActionViewExpanded();
        search.setIconified(false);
        search.clearFocus();

        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                adapter.getFilter().filter(newText);

                return false;
            }
        });
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.search) {
            dismiss();
            actionPerform();
        }
    }

    public void actionPerform() {

    }

}

