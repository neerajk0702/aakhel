package com.kredivation.aakhale.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.kredivation.aakhale.R;
import com.kredivation.aakhale.adapter.ListAdapter;
import com.kredivation.aakhale.components.ASTProgressBar;
import com.kredivation.aakhale.framework.IAsyncWorkCompletedCallback;
import com.kredivation.aakhale.framework.ServiceCaller;
import com.kredivation.aakhale.model.ContentData;
import com.kredivation.aakhale.utility.ASTUIUtil;
import com.kredivation.aakhale.utility.Contants;
import com.kredivation.aakhale.utility.Utility;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * @author AST Inc.
 */
public class SearchDialog extends Dialog implements View.OnClickListener {

    ListAdapter adapter;
    List<String> arrayList = new ArrayList<>();
    ListView listView;
    SearchView search;
    String unique_id;

    public SearchDialog(Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.setContentView(R.layout.search_dilog);
        this.setCanceledOnTouchOutside(true);
        this.getWindow().setBackgroundDrawableResource(android.R.color.darker_gray);
        listView = findViewById(R.id.listView);
        search = findViewById(R.id.search);
        arrayList.add("A");
        arrayList.add("B");
        arrayList.add("C");
        arrayList.add("D");
        adapter = new ListAdapter(arrayList, getContext());
        listView.setAdapter(adapter);
        search.setActivated(true);
        search.setQueryHint("Type Teame Uniq id or Name here");
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
                unique_id = newText;
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


    private void getTeamApi() {
        if (ASTUIUtil.isOnline(getContext())) {
            final ASTProgressBar dotDialog = new ASTProgressBar(getContext());
            dotDialog.show();
            ServiceCaller serviceCaller = new ServiceCaller(getContext());
            final String url = Contants.BASE_URL + Contants.teamCreate;
            JSONObject object = new JSONObject();
            try {
                object.put("unique_id", unique_id);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            serviceCaller.CallCommanGetServiceMethod(url, object, "saveCreateMatchData", new IAsyncWorkCompletedCallback() {
                @Override
                public void onDone(String result, boolean isComplete) {
                    if (isComplete) {
                        parseCrateTeameData(result);
                    } else {
                        showToast(Contants.Error);
                    }
                    if (dotDialog.isShowing()) {
                        dotDialog.dismiss();
                    }
                }
            });
        } else {
            showToast(Contants.OFFLINE_MESSAGE);
        }

    }

    private void showToast(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
    }


    private void parseCrateTeameData(String result) {
        if (result != null) {
            try {
                JSONObject jsonRootObject = new JSONObject(result);
                String jsonStatus = jsonRootObject.optString("Status").toString();
                if (jsonStatus.equals("success")) {
                    JSONObject object = jsonRootObject.optJSONObject("data");
                    String userName = object.optString("name").toString();
                    Toast.makeText(getContext(), "Match added successfully", Toast.LENGTH_LONG).show();


                } else {
                }

            } catch (JSONException e) {
                // TODO Auto-generated catch block
                //
            }
        }

    }


}

