package com.vericarte.catalog.custom;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.vericarte.catalog.R;
import com.vericarte.catalog.entitie.Aplication;

import java.util.List;

/**
 * Created by raulgomez on 17/01/16.
 */
public class CustomListAdapter extends BaseAdapter {

    private LayoutInflater mInflater;
    List<Aplication> list;

    public CustomListAdapter(List<Aplication> list, Context context){
        mInflater = LayoutInflater.from(context);

        this.list=list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return (long) list.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        try
        {
            final ViewHolder holder;
            if(convertView == null)
            {
                convertView = this.mInflater.inflate(R.layout.custom_list, parent, false);
                holder = new ViewHolder();
                holder.image = ((ImageView)convertView.findViewById(R.id.imageView1));
                holder.heading = ((TextView)convertView.findViewById(R.id.headingText));
                holder.subHeading = ((TextView)convertView.findViewById(R.id.subHeadingText));
                convertView.setTag(holder);
            }
            else
            {
                holder = (ViewHolder)convertView.getTag();
            }
            holder.image.setImageBitmap(getImage(list.get(position)));
            holder.heading.setText(list.get(position).getName());
            holder.subHeading.setText(list.get(position).getPrice());
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }

        return convertView;
    }

    private static class ViewHolder
    {
        public ImageView image;
        public TextView heading;
        public TextView subHeading;
    }

    private String[] getNames(List<Aplication> list){
        String[] response = new String[list.size()];
        for (int i=0; i<list.size(); i++) {
            response[i]= list.get(i).getName();
        }
        return response;
    }
    private String[] getPrice(List<Aplication> list){
        String[] response = new String[list.size()];
        for (int i=0; i<list.size(); i++) {
            response[i]= list.get(i).getPrice();
        }
        return response;
    }
    private Bitmap[] getImage(List<Aplication> list){
        Bitmap[] response = new Bitmap[list.size()];
        for (int i=0; i<list.size(); i++) {
            Bitmap bmp= BitmapFactory.decodeByteArray(list.get(i).getImage(), 0, list.get(i).getImage().length);
            response[i]= bmp;
        }
        return response;
    }
    private Bitmap getImage(Aplication aplication){
        Bitmap response;

        Bitmap bmp = BitmapFactory.decodeByteArray(aplication.getImage(), 0, aplication.getImage().length);
        response= bmp;

        return response;
    }
}

