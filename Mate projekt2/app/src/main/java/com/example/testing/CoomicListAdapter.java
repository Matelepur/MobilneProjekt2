package com.example.testing;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class CoomicListAdapter  extends ArrayAdapter<Coomics> {

    private Context context;
    private List<Coomics> comicList;

    public CoomicListAdapter(Context context, List<Coomics> comicList) {
        super(context, 0, comicList);
        this.context = context;
        this.comicList = comicList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View itemView = convertView;
        if (itemView == null) {
            itemView = LayoutInflater.from(context).inflate(R.layout.list_item_comic, parent, false);
        }

        // Get the current comic item
        Coomics currentComic = comicList.get(position);

        // Set the comic name
        TextView comicNameTextView = itemView.findViewById(R.id.comicNameTextView);
        comicNameTextView.setText(currentComic.getComicName());

        // Set the publishing house
        TextView publishingHouseTextView = itemView.findViewById(R.id.publishingHouseTextView);
        publishingHouseTextView.setText(currentComic.getPublishingHouse());

        // Set the age demographic
        TextView ageDemographicTextView = itemView.findViewById(R.id.ageDemographicTextView);
        ageDemographicTextView.setText(currentComic.getAgeDemographic());


        return itemView;
    }

    public void setComicList(List<Coomics> comicList) {
        this.comicList = comicList;
    }
}
