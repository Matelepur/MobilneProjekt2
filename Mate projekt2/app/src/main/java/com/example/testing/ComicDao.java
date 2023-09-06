package com.example.testing;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface ComicDao {
    @Insert
    void insert(Coomics comic);

    @Update
    void update(Coomics comic);

    @Delete
    void delete(Coomics comic);

    @Query("SELECT * FROM comics")
    List<Coomics> getAllComics();

    // You can add other query methods here based on your needs
    // For example, to get a specific comic by ID:
    @Query("SELECT * FROM comics WHERE id = :comicId")
    Coomics getComicById(int comicId);
}
