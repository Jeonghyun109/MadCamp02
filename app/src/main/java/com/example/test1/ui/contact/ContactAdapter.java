package com.example.test1.ui.contact;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.test1.MainActivity;
import com.example.test1.R;
import com.example.test1.ui.Data.ContactData;
import com.example.test1.ui.Data.ContactResponse;
import com.example.test1.ui.RetrofitClient;
import com.example.test1.ui.ServiceApi;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ContactViewHolder> {

    private ArrayList<ContactInfo> mList;
    private Context context;
    private Fragment fragment;
    private ServiceApi service;

    public ContactAdapter(Context context, ArrayList<ContactInfo> list, Fragment fragment) {
        this.context = context;
        this.mList = list;
        this.fragment=fragment;
    }
    /* CustomViewHolder constructed with textViews */
    public class ContactViewHolder extends RecyclerView.ViewHolder {
        protected TextView name;
        protected TextView email;

        public ContactViewHolder(View view) {
            super(view);

            view.setOnLongClickListener(new View.OnLongClickListener() {

                @Override
                public boolean onLongClick(View v) {
                    AlertDialog.Builder del=new AlertDialog.Builder(fragment.getActivity());
                    del.setTitle("진짜진짜로");
                    del.setMessage("절연할꾸야? 힝ㅠ");

                    del.setNegativeButton("웅><", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            int pos = getAdapterPosition();
                            if (pos != RecyclerView.NO_POSITION) {
                                service = RetrofitClient.getClient().create(ServiceApi.class);
                                ContactData data=new ContactData(MainActivity.id, mList.get(pos).getName());
                                service.contactDelete(data).enqueue(new Callback<ContactResponse>() {
                                    @Override
                                    public void onResponse(Call<ContactResponse> call, Response<ContactResponse> response) {
                                        notifyDataSetChanged();
                                    }

                                    @Override
                                    public void onFailure(Call<ContactResponse> call, Throwable t) {
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

            view.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    if (pos != RecyclerView.NO_POSITION) {
                        Intent intent = new Intent(context, VisitHomepage.class);
                        intent.putExtra("name",mList.get(pos).getName());
                        context.startActivity(intent);
                    }
                }
            });


            this.name = view.findViewById(R.id.list_name);
            this.email = view.findViewById(R.id.list_email);
        }
    }



    /* Called when RecyclerView needs a new RecyclerView.ViewHolder of the given type
     * to represent an item. */
    @Override
    public ContactViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.contact_list, viewGroup, false);

        ContactViewHolder viewHolder = new ContactViewHolder(view);

        return viewHolder;
    }

    /* Called when notifyItemChanged */
    @Override
    public void onBindViewHolder(@NonNull ContactViewHolder viewholder, int position) {
        ContactInfo data = mList.get(position);
        viewholder.name.setTextSize(TypedValue.COMPLEX_UNIT_SP, 25);
        viewholder.email.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);

        viewholder.name.setGravity(Gravity.LEFT);
        viewholder.email.setGravity(Gravity.LEFT);

        viewholder.name.setText(data.getName());
        viewholder.email.setText(data.getEmail());
    }

    @Override
    public int getItemCount() {
        return (null != mList ? mList.size() : 0);
    }

}