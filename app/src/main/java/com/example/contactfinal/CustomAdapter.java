package com.example.contactfinal;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomAdapter extends BaseAdapter {
    class ViewHolder {
        TextView name, phone;
        ImageView image;
    }
    ArrayList<Line> al;
    LayoutInflater li;
    CustomAdapter (ArrayList<Line> al, LayoutInflater li) {
        this.al = al;
        this.li = li;
    }
    @Override
    public int getCount() {
        return al.size();
    }

    @Override
    public Object getItem(int position) {
        return al.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Line line = (Line)getItem(position);
        ViewHolder vh = null;
        if(convertView == null) {
            convertView = li.inflate(R.layout.list_layout, null);
            vh = new ViewHolder();
            vh.name = convertView.findViewById(R.id.name);
            vh.phone = convertView.findViewById(R.id.phone);
            vh.image = convertView.findViewById(R.id.image);
            convertView.setTag(vh);
        }
        else vh = (ViewHolder) convertView.getTag();
        vh.name.setText(line.name);
        vh.phone.setText(line.phone);
        vh.image.setImageResource(line.id);
        return convertView;
    }




}
