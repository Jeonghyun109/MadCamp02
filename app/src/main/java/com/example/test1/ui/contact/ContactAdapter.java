package com.example.test1.ui.contact;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.test1.R;
import java.util.ArrayList;


public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ContactViewHolder> {

    private ArrayList<ContactInfo> mList;
    private Context context;

    public ContactAdapter(Context context, ArrayList<ContactInfo> list) {
        this.context = context;
        this.mList = list;
    }
    /* CustomViewHolder constructed with textViews */
    public class ContactViewHolder extends RecyclerView.ViewHolder {
        protected TextView name;
        protected TextView phnumber;

        public ContactViewHolder(View view) {
            super(view);

//            view.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    int pos = getAdapterPosition();
//                    if (pos != RecyclerView.NO_POSITION) {
//                        Intent intent = new Intent(context, ContactActivity.class);
//                        intent.putExtra("name",mList.get(pos).getName());
//                        intent.putExtra("phnumber",mList.get(pos).getPhNumber());
//                        intent.putExtra("email",mList.get(pos).getEmail());
//                        intent.putExtra("note",mList.get(pos).getNote());
//                        context.startActivity(intent);
//                    }
//                }
//            });

            this.name = view.findViewById(R.id.list_name);
            this.phnumber = view.findViewById(R.id.list_number);
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
        viewholder.phnumber.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);

        viewholder.name.setGravity(Gravity.LEFT);
        viewholder.phnumber.setGravity(Gravity.LEFT);

        Log.v("이름",data.getName());

        viewholder.name.setText(data.getName());
        viewholder.phnumber.setText(data.getPhNumber());
    }

    @Override
    public int getItemCount() {
        return (null != mList ? mList.size() : 0);
    }

}