package com.vericarte.catalog.custom;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.vericarte.catalog.R;
import com.vericarte.catalog.entitie.Category;

import java.util.List;

/**
 * Created by raul.gomez on 19/01/2016.
 */
public class CategoryAdapter extends BaseAdapter {

    private LayoutInflater mInflater;
    List<Category> list;

    public CategoryAdapter(List<Category> list, Context context){
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
                convertView = this.mInflater.inflate(R.layout.category_detail, parent, false);
                holder = new ViewHolder();
                holder.heading = ((TextView)convertView.findViewById(R.id.textViewTitleCategory));
                convertView.setTag(holder);
            }
            else
            {
                holder = (ViewHolder)convertView.getTag();
            }
            holder.heading.setText(list.get(position).getName());
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }

        return convertView;
    }

    private static class ViewHolder
    {
        public TextView heading;
    }

    private String[] getNames(List<Category> list){
        String[] response = new String[list.size()];
        for (int i=0; i<list.size(); i++) {
            response[i]= list.get(i).getName();
        }
        return response;
    }

}