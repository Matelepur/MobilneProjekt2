package com.example.testing;
import android.content.Intent;
import android.os.Bundle;
import android.os.Debug;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static List<Coomics> comicList;
    private CoomicListAdapter comicListAdapter;
    private static final int REQUEST_ADD_COMIC = 1;
    private static final int REQUEST_EDIT_COMIC = 2;
    private ComicViewModel comicViewModel;
    private static MainActivity instance;

    @Override
    protected void onResume() {
        super.onResume();

        reloadData();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        instance = this;

        comicList = new ArrayList<>();
        comicListAdapter = new CoomicListAdapter(this, comicList);

        ListView comicListView = findViewById(R.id.comicsListView);
        comicListView.setAdapter(comicListAdapter);

        comicListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, Coomic.class);
                intent.putExtra("position", position);
                startActivityForResult(intent, REQUEST_EDIT_COMIC);
            }
        });

        Button addComicButton = findViewById(R.id.addComicButton);
        addComicButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Coomic.class);
                startActivityForResult(intent, REQUEST_ADD_COMIC);
            }
        });

        // Initialize the ViewModel
        comicViewModel = new ViewModelProvider(this).get(ComicViewModel.class);

        // Fetch and display the comic data
        reloadData();
    }

    // Fetch and display the comic data
    private void reloadData() {
        comicList.clear();
        comicList.addAll(comicViewModel.getAllComics());
        comicListAdapter.setComicList(comicList);
        comicListAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_ADD_COMIC) {
                String comicName = data.getStringExtra("comicName");
                String publishingHouse = data.getStringExtra("publishingHouse");
                String ageDemographic = data.getStringExtra("ageDemographic");
                String imagePath = data.getStringExtra("imagePath"); // Replace this with the actual image path
                Coomics newComic = new Coomics(comicName, publishingHouse, ageDemographic, imagePath);
                comicViewModel.insertComic(newComic); // Insert the new comic using the ViewModel (you need to implement this method in the ViewModel)
            } else if (requestCode == REQUEST_EDIT_COMIC) {
                int position = data.getIntExtra("position", -1);
                if (position != -1) {
                    String comicName = data.getStringExtra("comicName");
                    String publishingHouse = data.getStringExtra("publishingHouse");
                    String ageDemographic = data.getStringExtra("ageDemographic");
                    String imagePath = data.getStringExtra("imagePath"); // Replace this with the actual image path
                    Coomics editedComic = comicList.get(position);
                    editedComic.setComicName(comicName);
                    editedComic.setPublishingHouse(publishingHouse);
                    editedComic.setAgeDemographic(ageDemographic);
                    // image path is going to be null if the image was not modified so DO NOT save it otherwise it will remove the current image and replace it with a blank
                    if (imagePath != null) {
                        editedComic.setImagePath(imagePath); // Postavi putanju slike u uređeni strip
                    }
                    comicViewModel.updateComic(editedComic); // Update the edited comic using the ViewModel (you need to implement this method in the ViewModel)
                }
            }
        }

        reloadData();
    }


    public static MainActivity getInstance() {
        return instance;
    }
}
