package com.example.testing;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;
import java.util.Objects;

public class ComicRepository {
    private ComicDao comicDao;

    public ComicRepository(Application application) {
        ComicDatabase database = ComicDatabase.getInstance(application);
        comicDao = database.comicDao();
    }

    public List<Coomics> getAllComics() {
        return comicDao.getAllComics();
    }

    public void insertComic(Coomics comic) {
        comicDao.insert(comic);

    }

    public void updateComic(Coomics comic) {
            comicDao.update(comic);
    }
}

