package fullscreen.allvideos.punjabi.trending.app.lyricsvideostatus.gssingla.com.lyricsvideostatus.quotes;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.FirebaseFirestore;

import fullscreen.allvideos.punjabi.trending.app.lyricsvideostatus.gssingla.com.lyricsvideostatus.R;


public class Add_quotes extends AppCompatActivity {
    FirebaseFirestore db;
    private FirestoreRecyclerAdapter<Quotes_detail, QuotesviewHolder> fireStoreadapter;
    RecyclerView recyclerView;

    @Override
    protected void onStart() {
        super.onStart();
        fireStoreadapter.startListening();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_quotes);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(android.graphics.Color.WHITE);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerQuotesview);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(linearLayoutManager);


        Query query = FirebaseFirestore.getInstance().collection("quotes");
        FirestoreRecyclerOptions<Quotes_detail> option = new FirestoreRecyclerOptions.Builder<Quotes_detail>()
                .setQuery(query, Quotes_detail.class)
                .build();


        fireStoreadapter = new FirestoreRecyclerAdapter<Quotes_detail, QuotesviewHolder>(option) {
            @Override
            protected void onBindViewHolder(@NonNull QuotesviewHolder holder, int position, @NonNull final Quotes_detail model) {
                holder.setquotes(model.getQuotes());
                RelativeLayout share = holder.mView.findViewById(R.id.sharecapbtn);
                RelativeLayout copy = holder.mView.findViewById(R.id.copycapbtn);

                share.setOnClickListener(new View.OnClickListener() {
                     @Override
                     public void onClick(View v) {
                         Intent sendIntent = new Intent();
                         sendIntent.setAction(Intent.ACTION_SEND);
                         sendIntent.putExtra(Intent.EXTRA_TEXT, model.getQuotes());
                         sendIntent.setType("text/plain");
                         startActivity(sendIntent);


                     }
                 });

              copy.setOnClickListener(new View.OnClickListener() {
                  @Override
                  public void onClick(View v) {
                      ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                      ClipData clip = ClipData.newPlainText("quote", model.getQuotes());
                      clipboard.setPrimaryClip(clip);
                      Toast.makeText(Add_quotes.this, "Copied", Toast.LENGTH_SHORT).show();

                  }



              });





            }

            @NonNull
            @Override
            public QuotesviewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

                View view = LayoutInflater.from(viewGroup.getContext())
                        .inflate(R.layout.single_quote_view, viewGroup, false);






               return new QuotesviewHolder(view);
            }



        };
        recyclerView.setAdapter(fireStoreadapter);



    }


    private class QuotesviewHolder extends RecyclerView.ViewHolder  {
        View mView;



        public QuotesviewHolder(@NonNull View itemView) {
            super(itemView);
            mView=itemView;



            }

        public void  setquotes(String q){


            TextView textquote = (TextView)mView.findViewById(R.id.captionText);


              textquote.setText(q);

        }







    }

}
    
    
    


