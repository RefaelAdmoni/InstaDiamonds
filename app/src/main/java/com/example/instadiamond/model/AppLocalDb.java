package com.example.instadiamond.model;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.instadiamond.MyApplication;

@Database(entities = {Product.class}, version = 1)
abstract class AppLocalDbRepository extends RoomDatabase {
    public abstract ProductDao productDao();
}

public class AppLocalDb {
    static public AppLocalDbRepository db =
            Room.databaseBuilder(MyApplication.context,
                    AppLocalDbRepository.class,
                    "productsDB.db")
                    .fallbackToDestructiveMigration()
                    .build();
}
