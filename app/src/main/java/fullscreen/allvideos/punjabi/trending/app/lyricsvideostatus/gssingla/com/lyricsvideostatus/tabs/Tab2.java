package fullscreen.allvideos.punjabi.trending.app.lyricsvideostatus.gssingla.com.lyricsvideostatus.tabs;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Arrays;

import fullscreen.allvideos.punjabi.trending.app.lyricsvideostatus.gssingla.com.lyricsvideostatus.quotes.Add_quotes;
import fullscreen.allvideos.punjabi.trending.app.lyricsvideostatus.gssingla.com.lyricsvideostatus.category.CategoryAdapter;
import fullscreen.allvideos.punjabi.trending.app.lyricsvideostatus.gssingla.com.lyricsvideostatus.category.Category_data;
import fullscreen.allvideos.punjabi.trending.app.lyricsvideostatus.gssingla.com.lyricsvideostatus.HLVAdapter;
import fullscreen.allvideos.punjabi.trending.app.lyricsvideostatus.gssingla.com.lyricsvideostatus.R;

public class Tab2 extends Fragment {
    FloatingActionButton quotesBtn;
    DatabaseReference mDatabse;
    RecyclerView mRecyclerView,cateRecyclerview;
    RecyclerView.LayoutManager mLayoutManager;
    RecyclerView.Adapter mAdapter;
    ArrayList<String> alName;
    ArrayList<Integer> alImage;
    String catNames[] = {
            "Romantic",
            "Sad",
            "Love",
            "Old",
            "Dosti",
            "Punjabi",
            "Festival",
            "Tv Serials",
            "Others"
    };
    String cat_image_urls[] = {
            "http://whatsapp-status.live/wp-content/uploads/2019/01/romantic.jpg",
            "http://whatsapp-status.live/wp-content/uploads/2019/01/sad.jpg",
            "http://whatsapp-status.live/wp-content/uploads/2019/01/love.jpg",
            "http://whatsapp-status.live/wp-content/uploads/2019/01/old.jpg",
            "http://whatsapp-status.live/wp-content/uploads/2019/01/dosti.jpg",
            "http://whatsapp-status.live/wp-content/uploads/2019/01/punjabi.jpg",
            "http://whatsapp-status.live/wp-content/uploads/2019/01/festival.jpg",
            "http://whatsapp-status.live/wp-content/uploads/2019/01/tv.png",
            "http://whatsapp-status.live/wp-content/uploads/2019/01/others.jpg"
    };
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //Returning the layout file after inflating
        //Change R.layout.Tab1 in you classes


        View v = inflater.inflate(R.layout.tab2, container, false);
        alName = new ArrayList<>(Arrays.asList("Arijit", "Armaan Malik", "Neha Kakkar", "Shreya Ghoshal", "Atif Aslam", "Palak Muchhal", "Sonu Nigam"));
        alImage = new ArrayList<>(Arrays.asList(R.drawable.arijit, R.drawable.armaan, R.drawable.neha, R.drawable.shreya, R.drawable.atif, R.drawable.palak, R.drawable.sonu));





        // Calling the RecyclerView
        mRecyclerView = (RecyclerView)v.findViewById(R.id.horizontal_recycler_view);
        quotesBtn = (FloatingActionButton) v.findViewById(R.id.quotesBtn);
        mRecyclerView.setHasFixedSize(true);
        cateRecyclerview = (RecyclerView)v.findViewById(R.id.vertical_recyclerview);
        cateRecyclerview.setHasFixedSize(true);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(),2);
        cateRecyclerview.setLayoutManager(gridLayoutManager);

        mDatabse = FirebaseDatabase.getInstance().getReference().child("videos");
        // The number of Columns
        mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);

        ArrayList<Category_data> catname  = prepareData();
        CategoryAdapter adapter = new CategoryAdapter(getActivity(),catname);


        mAdapter = new HLVAdapter(getActivity(), alName, alImage);
        mRecyclerView.setAdapter(mAdapter);

        cateRecyclerview.setAdapter(adapter);

        AdView mAdView = v.findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);


       quotesBtn.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               startActivity(new Intent(getActivity(),Add_quotes.class));
           }
       });


        return v;
    }

    private ArrayList<Category_data> prepareData(){

        ArrayList<Category_data> android_version = new ArrayList<>();
        for(int i=0;i<catNames.length;i++){
            Category_data category_data = new Category_data();
            category_data.setCateName(catNames[i]);
            category_data.setCate_image_url(cat_image_urls[i]);
            android_version.add(category_data);
        }
        return android_version;
    }
}