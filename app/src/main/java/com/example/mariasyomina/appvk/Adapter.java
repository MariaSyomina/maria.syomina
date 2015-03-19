package com.example.mariasyomina.appvk;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by mariasyomina on 09.03.15.
 */
public class Adapter extends BaseAdapter {
    Context ctx;
    ArrayList<MyFriend> objects;

    public Adapter(Context context, ArrayList<MyFriend> friends) {
        ctx = context;
        objects = friends;
    }


    @Override
    public int getCount() {
        return objects.size();
    }

    @Override
    public MyFriend getItem(int position) {
        return objects.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.item, parent, false);
        }

        TextView textViewFirstName = (TextView) view.findViewById(R.id.tfname);
        TextView textViewLastName = (TextView) view.findViewById(R.id.tlname);
        ImageView imageView = (ImageView) view.findViewById(R.id.photo);

        MyFriend current = getItem(position);

        textViewFirstName.setText(current.getFirstName());
        textViewLastName.setText(current.getLastName());

        Picasso.with(ctx).load(current.getPhoto()).into(imageView);

        return view;
    }
}
