package com.example.test1.ui;

import com.example.test1.R;
import com.example.test1.ui.atv.model.TreeNode;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by admin on 31-03-2016.
 */
public class MyHolder extends TreeNode.BaseNodeViewHolder<MyHolder.IconTreeItem> {
    ImageView img;
    TextView tvValue;

    public MyHolder(Context context) {
        super(context);
    }

    @Override
    public View createNodeView(TreeNode node, IconTreeItem value) {
        final LayoutInflater inflater = LayoutInflater.from(context);
        final View view = inflater.inflate(R.layout.child, null, false);
        img = (ImageView) view.findViewById(R.id.image);
        tvValue = (TextView) view.findViewById(R.id.text);
        img.setImageResource(value.icon);
        tvValue.setText(value.text);
        return view;
    }
    public void toggle(boolean active) {
        img.setImageResource(active ? R.drawable.add_wishlist : R.drawable.inquire_now);
    }

    public static class IconTreeItem {
        public int icon;
        public String text;

        public IconTreeItem(int icon, String text) {

            this.icon = icon;
            this.text = text;
        }
    }
}
