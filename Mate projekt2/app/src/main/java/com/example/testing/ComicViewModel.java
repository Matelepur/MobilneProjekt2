package com.example.testing;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class ComicViewModel extends AndroidViewModel {
    private ComicRepository comicRepository;

    public ComicViewModel(@NonNull Application application) {
        super(application);
        comicRepository = new ComicRepository(application);
    }


    public List<Coomics> getAllComics() {
        return comicRepository.getAllComics();
    }

    public void insertComic(Coomics comic) {
        comicRepository.insertComic(comic);
    }

    public void updateComic(Coomics comic) {
        comicRepository.updateComic(comic);
    }
}
