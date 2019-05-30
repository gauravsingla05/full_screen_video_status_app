package fullscreen.allvideos.punjabi.trending.app.lyricsvideostatus.gssingla.com.lyricsvideostatus.category;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import fullscreen.allvideos.punjabi.trending.app.lyricsvideostatus.gssingla.com.lyricsvideostatus.Admin;
import fullscreen.allvideos.punjabi.trending.app.lyricsvideostatus.gssingla.com.lyricsvideostatus.Full_screen;
import fullscreen.allvideos.punjabi.trending.app.lyricsvideostatus.gssingla.com.lyricsvideostatus.R;
import fullscreen.allvideos.punjabi.trending.app.lyricsvideostatus.gssingla.com.lyricsvideostatus.Video_detail;
import fullscreen.allvideos.punjabi.trending.app.lyricsvideostatus.gssingla.com.lyricsvideostatus.tabs.Tab1;

public class Category_name extends AppCompatActivity {
    Toolbar toolbar;
    DatabaseReference mDatabse;
    RecyclerView recyclerView;
    Context ctx;
    ProgressDialog mProgressDialog;
    public static final String VIDEO_URL_KEY="key";
    public static final String VIDEO_TITLE_KEY="title_key";
    public static final String CAT_KEY = "cat_key";
    public static final String SINGER_KEY = "singer_key";
    String gettingcatdata,current_cat="love";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_name);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(android.graphics.Color.WHITE);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Status Mafia");

        toolbar.inflateMenu(R.menu.menu);

        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {

            @Override
            public boolean onMenuItemClick(MenuItem item) {

                if(item.getItemId()==R.id.admin)
                {

                    Intent intent = new Intent(Category_name.this,Admin.class);

                    startActivity(intent);


                }


                return true;
            }
        });


        Intent getIn = getIntent();
        gettingcatdata = getIn.getStringExtra(CAT_KEY);

        //Toast.makeText(getApplicationContext(),gettingcatdata,Toast.LENGTH_LONG).show();




        recyclerView = (RecyclerView)findViewById(R.id.single_cat_list);
        recyclerView.setHasFixedSize(true);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(),2);

        recyclerView.setLayoutManager(gridLayoutManager);
        mDatabse = FirebaseDatabase.getInstance().getReference().child("videos");

        mProgressDialog = new ProgressDialog(getApplicationContext());

        if (gettingcatdata.equals("0")){

           current_cat = "romantic";

        }
        else if(gettingcatdata.equals("1")){

           current_cat="sad";


        }
        else if(gettingcatdata.equals("2")){


           current_cat="love";


        }

        else if(gettingcatdata.equals("3")){

           current_cat="old";



        }
        else if(gettingcatdata.equals("4")){

           current_cat="dosti";




        }
        else if(gettingcatdata.equals("5")){

           current_cat= "punjabi";



        }
        else if(gettingcatdata.equals("6")){

           current_cat= "festival";



        }
        else if(gettingcatdata.equals("7")){
           current_cat=  "tv serials";




        }
        else if(gettingcatdata.equals("8")){

            current_cat = "others";


        }

        Query categoryQuery = mDatabse.orderByChild("category").startAt(current_cat).endAt(current_cat+"\uf8ff");





















        FirebaseRecyclerAdapter<Video_detail, Tab1.VideoviewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Video_detail, Tab1.VideoviewHolder>(

                Video_detail.class,
                R.layout.single_video,
                Tab1.VideoviewHolder.class,
                categoryQuery


        ) {
            @Override
            protected void populateViewHolder(Tab1.VideoviewHolder viewHolder, final Video_detail model, final int position) {



                // Toast.makeText(getActivity(),"MILGYA"+model.getSinger(),Toast.LENGTH_LONG).show();
                viewHolder.setTitle(model.getTitle());
                viewHolder.setVideo(model.getThumb(), getApplicationContext());





                viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        //Toast.makeText(getContext(),""+model.getVideo(),Toast.LENGTH_LONG).show();


                        Intent intent = new Intent(getApplicationContext(),Full_screen.class);
                        intent.putExtra(VIDEO_URL_KEY,model.getVideo());
                        intent.putExtra(VIDEO_TITLE_KEY,model.getTitle());

                        startActivity(intent);






                    }
                });

            }

        };


        recyclerView.setAdapter(firebaseRecyclerAdapter);





















    }

























}
