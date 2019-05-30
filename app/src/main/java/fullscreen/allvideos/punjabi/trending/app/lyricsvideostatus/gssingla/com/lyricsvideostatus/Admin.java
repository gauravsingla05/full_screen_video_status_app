package fullscreen.allvideos.punjabi.trending.app.lyricsvideostatus.gssingla.com.lyricsvideostatus;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.media.MediaMetadataRetriever;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.nbsp.materialfilepicker.MaterialFilePicker;
import com.nbsp.materialfilepicker.ui.FilePickerActivity;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.regex.Pattern;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static android.provider.MediaStore.Video.Thumbnails.MINI_KIND;
import static android.provider.MediaStore.Video.Thumbnails.getThumbnail;

public class Admin extends AppCompatActivity {
    ImageView thumbnailimageview;
    Bitmap thumbBitmap,nbit;
    Button video, addQuotes, submit;
    CheckBox trendng;
    String category_name = null;
    EditText title, singer,quotesText;
    public static final int GALLERY_REQUEST = 1;
    public static final int GALLERY_THUMB = 2;
    Uri video_uri,thumb_uri = null;
    Uri thum_urii=null,finalUri;
    StorageReference mStorageRef;
    ProgressDialog progressDialog;
    private android.widget.Spinner spinner1;
    DatabaseReference database;
    File f;
    String content_type, url;
    FirebaseFirestore db,videosDB;
    DatabaseReference new_post;
    StorageReference thumb_path;
    Map<String, Object> videos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(android.graphics.Color.WHITE);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        thumbnailimageview= (ImageView)findViewById(R.id.thumbImg);
        url = "https://whatsapp-status.live/status_videos/upload_video.php";
        video = (Button) findViewById(R.id.video_id);
        addQuotes = (Button)findViewById(R.id.submit_quotes);
        submit = (Button) findViewById(R.id.submit_id);
        title = (EditText) findViewById(R.id.title_id);
        singer = (EditText) findViewById(R.id.singer_id);
        quotesText = (EditText)findViewById(R.id.addQuote_text);
        trendng = (CheckBox) findViewById(R.id.trending_id);
        progressDialog = new ProgressDialog(this);
        spinner1 = (android.widget.Spinner) findViewById(R.id.spinner1);
        spinner1.setPrompt("Category");
        java.util.List<String> list = new java.util.ArrayList<String>();
        list.add("Romantic");
        list.add("Sad");
        list.add("Love");
        list.add("Old");
        list.add("Dosti");
        list.add("Punjabi");
        list.add("Festival");
        list.add("Tv Serials");
        list.add("Others");

        db= FirebaseFirestore.getInstance();
        videosDB = FirebaseFirestore.getInstance();
        android.widget.ArrayAdapter<String> dataAdapter = new android.widget.ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner1.setAdapter(dataAdapter);

        mStorageRef = FirebaseStorage.getInstance().getReference();
        database = FirebaseDatabase.getInstance().getReference().child("videos");

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 100);
                return;
            }
        }


        video.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new MaterialFilePicker()
                        .withActivity(Admin.this)
                        .withRequestCode(10)
                        .withFilter(Pattern.compile(".*\\.mp4$"))
                        .start();

//                Intent galleryIntentvideo = new Intent(Intent.ACTION_GET_CONTENT);
//                galleryIntentvideo.setType("video/*");
//                startActivityForResult(galleryIntentvideo,GALLERY_REQUEST);


            }
        });



        addQuotes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String quotString = quotesText.getText().toString();

                Map<String, Object> user = new HashMap<>();
                user.put("quotes", quotString);
                db.collection("quotes")
                        .add(user)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                Toast.makeText(getApplicationContext(),"Quote Added",Toast.LENGTH_SHORT).show();
                                quotesText.getText().clear();

                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {

                            }
                        });

            }
        });










        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                upload_data();
                //       startposting();
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 10 && resultCode == RESULT_OK) {

            f = new File(data.getStringExtra(FilePickerActivity.RESULT_FILE_PATH));
            content_type = getMimeType(f.getPath());

            try {
                thumbBitmap = ThumbnailUtils.createVideoThumbnail(f.getPath(),MediaStore.Video.Thumbnails.FULL_SCREEN_KIND);
                thumbnailimageview.setImageBitmap(thumbBitmap);
                thum_urii = getImageUri(getApplicationContext(),thumbBitmap);


            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }


            Toast.makeText(getApplicationContext(), ""+thum_urii, Toast.LENGTH_SHORT).show();
        }


        else if(requestCode==GALLERY_THUMB && resultCode==RESULT_OK){

            // thumb_uri = data.getData();



        }


    }


    private String getMimeType(String path) {

        String extension = MimeTypeMap.getFileExtensionFromUrl(path);

        return MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
    }


    public void startposting()  {

        final String saveTitle = title.getText().toString().trim();
        final String saveSinger = singer.getText().toString().toLowerCase().trim();

        thumb_path = mStorageRef.child("thumbs").child(removeExtension(f.getName()));



        thumb_path.putFile(thumb_uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                Toast.makeText(getApplicationContext(), "Thumb Uploadeded", Toast.LENGTH_LONG).show();

                thumb_path.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        // Got the download URL for 'users/me/profile.png'


                        new_post.child("thumb").setValue(uri.toString());
                        Toast.makeText(getApplicationContext(), "GOT URL"+uri, Toast.LENGTH_LONG).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        // Handle any errors
                    }
                });
                category_name = (String) spinner1.getSelectedItem();
                String trimedcategory = category_name.toLowerCase().trim();

                String video = getString(R.string.ip)+"videos/";
                String video_download = video+ f.getName();

                new_post = database.push();
                new_post.child("title").setValue(saveTitle);
                new_post.child("video").setValue(video_download);
                new_post.child("category").setValue(trimedcategory);
                new_post.child("singer").setValue(saveSinger);


                if (trendng.isChecked()) {
                    new_post.child("trending").setValue("1");

                } else {
                    new_post.child("trending").setValue("0");


                }













            }


        });







    }


    private void upload_data() {
//
        progressDialog.setMessage("Uploading...");
        progressDialog.show();

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {


                String file_path = f.getAbsolutePath();
                OkHttpClient client = new OkHttpClient();
                RequestBody file_body = RequestBody.create(MediaType.parse(content_type), f);

                RequestBody request_body = new MultipartBody.Builder()
                        .setType(MultipartBody.FORM)
                        .addFormDataPart("type", content_type)
                        .addFormDataPart("name", "name")
                        .addFormDataPart("uploaded_file", file_path.substring(file_path.lastIndexOf("/") + 1), file_body)
                        .build();


                Request request = new Request.Builder()
                        .url(url)
                        .post(request_body)
                        .build();

                // Toast.makeText(Add_recipe.this, "hhh", Toast.LENGTH_LONG).show();
                try {
                    Response response = client.newCall(request).execute();

                    if (!response.isSuccessful()) {
                        throw new IOException("Error : " + response);
                    }
                    progressDialog.dismiss();
                    startActivityFromMainThread();


                } catch (IOException e) {
                    Log.d("error123", e.getMessage());

                }


            }
        });

        t.start();

    }

    public void startActivityFromMainThread() {

        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(new Runnable() {
            @Override
            public void run() {

                Toast.makeText(getApplicationContext(), "video Uploaded", Toast.LENGTH_LONG).show();
                startposting();



            }
        });
    }

    private Uri getImageUri(Context context, Bitmap inImage) {

        String path = MediaStore.Images.Media.insertImage(context.getContentResolver(), inImage, null, null);
        return Uri.parse(path);
    }


    public static String removeExtension(String fileName) {

        if (fileName.indexOf(".") > 0) {
            return fileName.substring(0, fileName.lastIndexOf("."));

        } else {
            return fileName;

        }


    }

    @SuppressLint("NewApi")
    public static Bitmap retriveVideoFrameFromVideo(String p_videoPath)
            throws Throwable
    {
        Bitmap m_bitmap = null;
        MediaMetadataRetriever m_mediaMetadataRetriever = null;
        try
        {
            m_mediaMetadataRetriever = new MediaMetadataRetriever();
            m_mediaMetadataRetriever.setDataSource(p_videoPath);
            m_bitmap = m_mediaMetadataRetriever.getFrameAtTime();
        }
        catch (Exception m_e)
        {
            throw new Throwable(
                    "Exception in retriveVideoFrameFromVideo(String p_videoPath)"
                            + m_e.getMessage());
        }
        finally
        {
            if (m_mediaMetadataRetriever != null)
            {
                m_mediaMetadataRetriever.release();
            }
        }
        return m_bitmap;
    }


}

