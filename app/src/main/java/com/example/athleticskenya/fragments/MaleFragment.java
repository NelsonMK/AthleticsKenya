package com.example.athleticskenya.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.athleticskenya.R;
import com.example.athleticskenya.URLS;
import com.example.athleticskenya.Utils.DividerItemDecoration;
import com.example.athleticskenya.adapters.AwardsAdapter;
import com.example.athleticskenya.getterClasses.Awards;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MaleFragment extends Fragment {

    private Context context;
    private RecyclerView recyclerView;

    private AwardsAdapter awardsAdapter;
    private List<Awards> awardsList;

    public MaleFragment(){

    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        return inflater.inflate(R.layout.male, container, false);
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.context = getContext();

        recyclerView = view.findViewById(R.id.maleRecycler);
        recyclerView.setHasFixedSize(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(context, LinearLayoutManager.VERTICAL, 16));

        awardsList = new ArrayList<>();

        loadServerAwards();

    }

    private void loadServerAwards() {

        StringRequest stringRequest = new StringRequest(Request.Method.GET, URLS.URL_MALE_AWARDS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {

                            JSONArray array = new JSONArray(response);

                            for (int i = 0; i < array.length(); i++) {


                                JSONObject awards = array.getJSONObject(i);

                                awardsList.add(new Awards(
                                        awards.getString("athlete_f_name"),
                                        awards.getString("athlete_l_name"),
                                        awards.getString("athlete_race"),
                                        awards.getString("position"),
                                        awards.getString("medal"),
                                        awards.getString("image")
                                ));
                            }

                            awardsAdapter = new AwardsAdapter(context, awardsList);
                            recyclerView.setAdapter(awardsAdapter);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });

        Volley.newRequestQueue(context).add(stringRequest);
    }
}
