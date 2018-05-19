package com.example.administrator.listenspeak;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.List;

class ListenAdapter extends ArrayAdapter<User> {
    private int resourceId;
    private int defItem;

    public ListenAdapter(@NonNull Context context, int resource, List<User> objects) {
        super(context, resource,objects);
        resourceId = resource;
    }
    public void setDefSelect(int position) {
        this.defItem = position;
        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        User user = getItem(position);
        View view;
        ViewHolder viewHolder;
        if (convertView == null) {
            view = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.Username = view.findViewById(R.id.user_name);
            viewHolder.Title = view.findViewById(R.id.list_title);
            viewHolder.UserImg = view.findViewById(R.id.user_img);
            viewHolder.TitleImg = view.findViewById(R.id.Title_img);
            view.setTag(viewHolder);
        } else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }

        viewHolder.Username.setText(user.getUsername());
        viewHolder.Title.setText(String.valueOf(user.getTitle()));
        viewHolder.UserImg.setImageResource(user.getUserImg());
        viewHolder.TitleImg.setImageResource(user.getTitleImg());
        return view;
    }

}
