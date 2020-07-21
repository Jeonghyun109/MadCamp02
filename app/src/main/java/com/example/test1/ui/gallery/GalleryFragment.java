package com.example.test1.ui.gallery;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.bumptech.glide.Glide;
import com.example.test1.JHJApplication;
import com.example.test1.R;
import com.example.test1.ui.Data.ContactData;
import com.example.test1.ui.Data.ContactResponse;
import com.example.test1.ui.Data.GalleryData;
import com.example.test1.ui.Data.GalleryResponse;
import com.example.test1.ui.RetrofitClient;
import com.example.test1.ui.ServiceApi;
import com.example.test1.ui.contact.ContactAdapter;
import com.example.test1.ui.contact.ContactAdd;
import com.example.test1.ui.contact.ContactFragment;
import com.example.test1.ui.contact.ContactInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GalleryFragment extends Fragment {
    private ArrayList<String> mArrayList = new ArrayList<String>();
    private ImageAdapter mAdapter;
    private Context context;
    private RecyclerView mRecyclerView;
    private GridLayoutManager mLayoutManager;
    private ContentResolver contentResolver;
    private static final int MEDIA_TYPE_IMAGE = 1;
    private static final int REQUEST_TAKE_PHOTO = 2222;
    private Camera mCamera;
    private int id;
    private ServiceApi service;
    private ServiceApi mservice;
    String mCurrentPhotoPath;
    ImageView img;
    Uri imageUri;
    Uri photoURI, albumURI;
    File f;
    Uri real;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getActivity();

        mArrayList = new ArrayList<>();
        mAdapter = new ImageAdapter(context,mArrayList, GalleryFragment.this);

        contentResolver = context.getContentResolver();

        id = ((JHJApplication)this.getActivity().getApplication()).getId();
        showGallery(new GalleryData(id));
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_gallery, container, false);

        mRecyclerView = (RecyclerView) root.findViewById(R.id.gallery);
        mRecyclerView.setHasFixedSize(true);

        int numberOfColumns = 4;
        mLayoutManager = new GridLayoutManager(context,numberOfColumns);

        mRecyclerView.setLayoutManager(mLayoutManager);

        mArrayList = new ArrayList<>();

        mAdapter = new ImageAdapter(context,mArrayList, GalleryFragment.this);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();

        final SwipeRefreshLayout pullToRefresh = root.findViewById(R.id.pullToRefresh2);
        pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                showGallery(new GalleryData(id));
                pullToRefresh.setRefreshing(false);
            }
        });

        root.findViewById(R.id.camera).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                captureCamera();
            }
        });

        showGallery(new GalleryData(id));

        return root;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_TAKE_PHOTO) {
            if (resultCode == Activity.RESULT_OK) {
                try {
                    Log.i("REQUEST_TAKE_PHOTO", "OK");
                    galleryAddPic();


                } catch (Exception e) {
                    Log.e("REQUEST_TAKE_PHOTO", e.toString());
                }
            } else {
                Toast.makeText(context, "사진찍기를 취소하였습니다.", Toast.LENGTH_SHORT).show();
            }
        }
    }
    private void galleryAddPic(){
        Log.i("galleryAddPic", "Call");
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        // 해당 경로에 있는 파일을 객체화(새로 파일을 만든다는 것으로 이해하면 안 됨)
        File f = new File(mCurrentPhotoPath);
        Uri contentUri = Uri.fromFile(f);

        mediaScanIntent.setData(contentUri);
        context.sendBroadcast(mediaScanIntent);

        Uri uri = getImageContentUri(context,f);

        // db로 전달 -> db 업로드 함수 호출

        if (uri != null) {
            //db add + upload
            id = ((JHJApplication)this.getActivity().getApplication()).getId();
//            Log.d("nnnn", String.valueOf(id));
            addGallery(new GalleryData(id, String.valueOf(uri)));
        }
        Toast.makeText(context, "사진이 앨범에 저장되었습니다.", Toast.LENGTH_SHORT).show();
    }

    private void showGallery(GalleryData data) {
//        mArrayList.clear();
//        Log.d("nnnn", String.valueOf(data.getID()));
        service = RetrofitClient.getClient().create(ServiceApi.class);
        service.photo(data).enqueue(new Callback<GalleryResponse>() {
            @Override
            public void onResponse(Call<GalleryResponse> call, Response<GalleryResponse> response) {
                GalleryResponse result = response.body();
                Log.d("nnnn", String.valueOf(result));
                String nj="{\"Gallery\":"+result.getURI()+"}";
                mAdapter = new ImageAdapter(context, mArrayList, GalleryFragment.this);
                mRecyclerView.setAdapter(mAdapter);
                saveURI(nj);
            }

            @Override
            public void onFailure(Call<GalleryResponse> call, Throwable t) {
                Log.d("ㅕㅕㅕㅕㅕㅕㅕㅕ", "이거 아니야");
            }
        });
    }

    private void addGallery(GalleryData data) {
//        Log.d("nnnn", String.valueOf(data.getID()));
        mservice = RetrofitClient.getClient().create(ServiceApi.class);
        mservice.photoAdd(data).enqueue(new Callback<GalleryResponse>() {
            @Override
            public void onResponse(Call<GalleryResponse> call, Response<GalleryResponse> response) {
                GalleryResponse result2 = response.body();
//                Log.d("nnnn", String.valueOf(result2));
                Toast.makeText(getActivity(), result2.getMessage(), Toast.LENGTH_SHORT).show();
                showGallery(new GalleryData(data.getID()));
            }

            @Override
            public void onFailure(Call<GalleryResponse> call, Throwable t) {
                Log.d("ㅕㅕㅕㅕㅕㅕㅕㅕ", "이거 아니야");
            }
        });
    }

    public File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + ".jpg";
        File imageFile = null;
        File storageDir = new File(Environment.getExternalStorageDirectory() + "/Pictures", "JHJ");

        if (!storageDir.exists()) {
            Log.i("mCurrentPhotoPath1", storageDir.toString());
            storageDir.mkdirs();
        }

        imageFile = new File(storageDir, imageFileName);
        mCurrentPhotoPath = imageFile.getAbsolutePath();

        f = imageFile;
        Log.d("사진이다",String.valueOf(imageFile));

        return imageFile;
    }
    private void captureCamera(){
        String state = Environment.getExternalStorageState();
        // 외장 메모리 검사
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

            if (takePictureIntent.resolveActivity(context.getPackageManager()) != null) {
                File photoFile = null;
                try {
                    photoFile = createImageFile();
                } catch (IOException ex) {
                    Log.e("captureCamera Error", ex.toString());
                }
                if (photoFile != null) {
                    // getUriForFile의 두 번째 인자는 Manifest provier의 authorites와 일치해야 함

                    Uri providerURI = FileProvider.getUriForFile(context, context.getPackageName(), photoFile);
                    imageUri = providerURI;

                    // 인텐트에 전달할 때는 FileProvier의 Return값인 content://로만!!, providerURI의 값에 카메라 데이터를 넣어 보냄
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, providerURI);

                    startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
                }
            }
        } else {
            Toast.makeText(context, "저장공간이 접근 불가능한 기기입니다", Toast.LENGTH_SHORT).show();
            return;
        }
    }

    private Camera.PictureCallback mPicture = new Camera.PictureCallback() {
        @Override
        public void onPictureTaken(byte[] data, Camera camera) {
            File pictureFile = null;
            try {
                pictureFile = createImageFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (pictureFile == null){
                Log.d("에러", "Error creating media file, check storage permissions");
                return;
            }
            try {
                FileOutputStream fos = new FileOutputStream(pictureFile);
                fos.write(data);
                fos.close();
            } catch (FileNotFoundException e) {
                Log.d("에러", "File not found: " + e.getMessage());
            } catch (IOException e) {
                Log.d("에러", "Error accessing file: " + e.getMessage());
            }
        }
    };

    public Uri getImageContentUri(Context context, File imageFile) {
        String filePath = imageFile.getAbsolutePath();
        Cursor cursor = context.getContentResolver().query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                new String[] { MediaStore.Images.Media._ID },
                MediaStore.Images.Media.DATA + "=? ",
                new String[] { filePath }, null);

        if (cursor != null && cursor.moveToFirst()) {
            int id = cursor.getInt(cursor.getColumnIndex(MediaStore.MediaColumns._ID));
            cursor.close();
            real = Uri.withAppendedPath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "" + id);
            return real;
        } else {
            if (imageFile.exists()) {
                ContentValues values = new ContentValues();
                values.put(MediaStore.Images.Media.DATA, filePath);
                real = context.getContentResolver().insert(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
                return real;
            } else {
                return null;
            }
        }
    }

    public void saveURI(String json){
        mArrayList.clear();
        try{
            JSONObject jobj=new JSONObject(json);
            JSONArray jarray=jobj.getJSONArray("Gallery");

            Log.d("제이슨",String.valueOf(jarray.length()));

            for(int i=0; i<jarray.length(); i++){
                JSONObject pobj=jarray.getJSONObject(i);
                Log.d("qqqqqqqqqq", pobj.toString());
                String c=pobj.getString("URI");

                mArrayList.add(c);
                Log.d("qqqqqqqqqq", pobj.getString("URI"));
                Log.d("qqqqqqqqqq", String.valueOf(mArrayList.size()));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        mAdapter.notifyDataSetChanged();
    }
}