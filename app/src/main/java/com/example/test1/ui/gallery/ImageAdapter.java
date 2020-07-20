package com.example.test1.ui.gallery;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import com.example.test1.R;
import com.example.test1.ui.Data.ContactData;
import com.example.test1.ui.Data.ContactResponse;
import com.example.test1.ui.Data.GalleryData;
import com.example.test1.ui.Data.GalleryResponse;
import com.example.test1.ui.RetrofitClient;
import com.example.test1.ui.ServiceApi;

import java.io.File;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImageViewHolder> {
    private Context context;

    private ArrayList<String> mList = new ArrayList<String>();
    public int position;
    private Fragment fragment;
    private ServiceApi service;

    public int getPosition() {
        return position;
    }
    public void setPosition() {
        this.position = position;
    }

    public ImageAdapter(Context context, ArrayList<String> list, Fragment fragment) {
        this.context = context;
        this.mList.clear();
        this.mList = list;
        this.fragment=fragment;
    }
    public class ImageViewHolder extends RecyclerView.ViewHolder {
        protected ImageView imageView;

        public ImageViewHolder(View view) {
            super(view);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();

                    if (pos != RecyclerView.NO_POSITION) {
                        Intent intent = new Intent(context, PopupImg.class);
                        intent.putExtra("url",mList.get(pos));
                        context.startActivity(intent);
                    }
                }
            });
            view.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    AlertDialog.Builder del=new AlertDialog.Builder(fragment.getActivity());
                    del.setTitle("진짜진짜로");
                    del.setMessage("삭제할꾸야? 힝ㅠ");

                    del.setNegativeButton("웅><", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            int pos = getAdapterPosition();
                            if (pos != RecyclerView.NO_POSITION) {
                                service = RetrofitClient.getClient().create(ServiceApi.class);
                                GalleryData data=new GalleryData(mList.get(pos));
                                Log.d("mmmmmm",mList.get(pos));
                                service.photoDelete(data).enqueue(new Callback<GalleryResponse>() {
                                    @Override
                                    public void onResponse(Call<GalleryResponse> call, Response<GalleryResponse> response) {
                                        notifyDataSetChanged();
                                    }

                                    @Override
                                    public void onFailure(Call<GalleryResponse> call, Throwable t) {
                                        Log.d("ㅕㅕㅕㅕㅕㅕㅕㅕ", "이거 아니야");
                                    }
                                });
                                Toast.makeText(fragment.getActivity().getBaseContext(), "잘 살아라 흥.", Toast.LENGTH_SHORT).show();
                            }
                            notifyDataSetChanged();
                        }
                    });
                    del.setPositiveButton("아뇨.", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    });
                    del.create().show();

                    return false;
                }
            });

            this.imageView = (ImageView) view.findViewById(R.id.i_am_image);
        }
    }

    @Override
    public ImageViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        View view = LayoutInflater.from(context)
                .inflate(R.layout.gallery_layout, viewGroup, false);

        ImageViewHolder viewHolder = new ImageViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder viewholder, int position) {
        String data = mList.get(position);
        ImageView imageView = (ImageView) viewholder.imageView.findViewById(R.id.i_am_image);
        Glide.with(context).load(data).into(imageView);

    }

    @Override
    public int getItemCount() {
        return (null != mList ? mList.size() : 0);
    }
}