package com.example.administrator.listenspeak;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.List;

class DiarAdapter extends ArrayAdapter<Diary> {
    private int resourceId;
    private int defItem;

    public DiarAdapter(@NonNull Context context, int resource, List<Diary> objects) {
        super(context, resource,objects);
        resourceId = resource;
    }
    public void setDefSelect(int position) {
        this.defItem = position;
        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Diary diary = getItem(position);
        View view;
        DiaryHolder diaryHolder;
        if (convertView == null) {
            view = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);
            diaryHolder = new DiaryHolder();
            diaryHolder.Month = view.findViewById(R.id.dia_month);
            diaryHolder.Week = view.findViewById(R.id.dia_week);
            diaryHolder.Time = view.findViewById(R.id.dia_time);
            diaryHolder.Title = view.findViewById(R.id.dia_title);
            diaryHolder.TitleImg = view.findViewById(R.id.dia_titImmg);

            view.setTag(diaryHolder);
        } else {
            view = convertView;
            diaryHolder = (DiaryHolder) view.getTag();

        }

        diaryHolder.Month.setText(diary.getMonth());
        diaryHolder.Week.setText(diary.getWeek());
        diaryHolder.Time.setText(diary.getTime());
        diaryHolder.Title.setText(diary.getTitle());
        diaryHolder.TitleImg.setImageResource(diary.getTitleImg());
        return view;
    }
}
