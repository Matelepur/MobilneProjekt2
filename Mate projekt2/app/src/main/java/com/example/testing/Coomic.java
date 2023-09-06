package com.example.testing;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Coomic extends AppCompatActivity {

    private EditText comicNameEditText;
    private EditText publishingHouseEditText;
    private Spinner ageDemographicSpinner;
    private ImageView imageView;
    private int position;
    private Coomics comic;
    private String imagePath;
    private ActivityResultLauncher<Uri> cameraLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.coomic);

        comicNameEditText = findViewById(R.id.comicNameEditText);
        publishingHouseEditText = findViewById(R.id.publishingHouseEditText);
        ageDemographicSpinner = findViewById(R.id.ageDemographicSpinner);
        imageView = findViewById(R.id.comicImageView);

        Button confirmButton = findViewById(R.id.confirmButton);
        Button cancelButton = findViewById(R.id.cancelButton);

        Intent intent = getIntent();
        position = intent.getIntExtra("position", -1);
        if (position != -1) {
            comic = MainActivity.comicList.get(position);
            comicNameEditText.setText(comic.getComicName());
            publishingHouseEditText.setText(comic.getPublishingHouse());
            setSpinnerSelection(ageDemographicSpinner, comic.getAgeDemographic());
            loadImage(comic.getImagePath());
            confirmButton.setText("Update");
        } else {
            comic = null;
            confirmButton.setText("Add Comic");
        }

        // register camera activity for later use
        cameraLauncher = registerForActivityResult(new ActivityResultContracts.TakePicture(),
                (result) -> {
                    if (result) {
                        if (imagePath != null) {
                            loadImage(imagePath);
                        }
                    }
                });

        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String comicName = comicNameEditText.getText().toString();
                String publishingHouse = publishingHouseEditText.getText().toString();
                String ageDemographic = ageDemographicSpinner.getSelectedItem().toString();

                if (position != -1) {
                    comic.setComicName(comicName);
                    comic.setPublishingHouse(publishingHouse);
                    comic.setAgeDemographic(ageDemographic);
                    Intent intent = new Intent();
                    intent.putExtra("position", position);
                    intent.putExtra("comicName", comicName);
                    intent.putExtra("publishingHouse", publishingHouse);
                    intent.putExtra("ageDemographic", ageDemographic);
                    intent.putExtra("imagePath", imagePath);

                    setResult(RESULT_OK, intent);
                } else {
                    // Check if the comic already exists in the list
                    for (Coomics existingComic : MainActivity.comicList) {
                        if (existingComic.getComicName().equals(comicName)
                                && existingComic.getPublishingHouse().equals(publishingHouse)
                                && existingComic.getAgeDemographic().equals(ageDemographic)) {
                            // Comic already exists, show a message or perform appropriate action
                            // You can display a Toast message or show an AlertDialog
                            // Example: Toast.makeText(Coomic.this, "Comic already exists", Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }

                    Coomics newComic = new Coomics(comicName, publishingHouse, ageDemographic,imagePath);

                    Intent intent = new Intent();
                    intent.putExtra("comicName", comicName);
                    intent.putExtra("publishingHouse", publishingHouse);
                    intent.putExtra("ageDemographic", ageDemographic);
                    intent.putExtra("imagePath", imagePath);
                    setResult(RESULT_OK, intent);
                }

                finish();
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_CANCELED);
                finish();
            }
        });

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                captureImage();
            }
        });
    }

    private void loadImage(String path) {
        Glide.with(getApplicationContext()).load(path).apply(new RequestOptions()
                        .override(200, 200))  // set width and height of image in image view
                .into(imageView);
    }

    private void setSpinnerSelection(Spinner spinner, String value) {
        for (int i = 0; i < spinner.getAdapter().getCount(); i++) {
            if (spinner.getAdapter().getItem(i).toString().equalsIgnoreCase(value)) {
                spinner.setSelection(i);
                break;
            }
        }
    }

    private void captureImage() {
        // create temp file to store image to
        File imageTempFile = null;
        try {
            File imagesDir = MainActivity.getInstance().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            imageTempFile = File.createTempFile(timeStamp, ".jpg", imagesDir);
            imagePath = imageTempFile.getPath();
        } catch (IOException e) {

            // TODO show error toast

            e.printStackTrace();
        }

        // start camera activity
        if (imageTempFile != null) {
            Uri tempFileUri = FileProvider.getUriForFile(MainActivity.getInstance(), "lepur.coomic.fileprovider", imageTempFile);
            cameraLauncher.launch(tempFileUri);
        }
    }
}
