package com.example.administrator.listenspeak;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import java.util.List;

class MyAdapter extends ArrayAdapter<Topic> {
    private int resourceId;
    private int defItem;

    public MyAdapter(@NonNull Context context, int resource, List<Topic> objects) {
        super(context, resource,objects);
        resourceId = resource;
    }
    public void setDefSelect(int position) {
        this.defItem = position;
        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Topic topic = getItem(position);
        View view;
        TopicHolder topicHolder;
        if (convertView == null) {
            view = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);
            topicHolder = new TopicHolder();
            topicHolder.Username = view.findViewById(R.id.username);
            topicHolder.Topic = view.findViewById(R.id.topic);
            topicHolder.UserImg = view.findViewById(R.id.topic_user);
            view.setTag(topicHolder);
        } else {
            view = convertView;
            topicHolder = (TopicHolder) view.getTag();

        }

        topicHolder.Username.setText(topic.getName());
        topicHolder.Topic.setText(String.valueOf(topic.getStudentId()));
        topicHolder.UserImg.setImageResource(topic.getImg());
        return view;
    }
}
