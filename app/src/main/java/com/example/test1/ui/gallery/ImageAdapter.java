package com.example.test1.ui.gallery;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import com.example.test1.R;

import java.io.File;
import java.util.ArrayList;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImageViewHolder> {
    private Context context;

    private ArrayList<String> mList = new ArrayList<String>();
    public int position;

    public int getPosition() {
        return position;
    }
    public void setPosition() {
        this.position = position;
    }

    public ImageAdapter(Context context,ArrayList<String> list) {
        this.context = context;
        this.mList.clear();
        this.mList = list;
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