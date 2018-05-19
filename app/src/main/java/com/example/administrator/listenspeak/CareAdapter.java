package com.example.administrator.listenspeak;

import android.content.Context;
import android.media.Image;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.List;

class CareAdapter extends ArrayAdapter<ImageUser> {
    private int resourceId;
    private int defItem;

    public CareAdapter(@NonNull Context context, int resource, List<ImageUser> objects) {
        super(context, resource,objects);
        resourceId = resource;
    }
    public void setDefSelect(int position) {
        this.defItem = position;
        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageUser user = getItem(position);
        View view;
        UserImgHolder userImgHolder;
        if (convertView == null) {
            view = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);
            userImgHolder = new UserImgHolder();
            userImgHolder.UserImg = view.findViewById(R.id.care_img);

            view.setTag(userImgHolder);
        } else {
            view = convertView;
            userImgHolder = (UserImgHolder) view.getTag();

        }

        userImgHolder.UserImg.setImageResource(user.userImag());
        return view;
    }
}
