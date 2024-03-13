package com.example.appproject.model;

import androidx.annotation.OpenForTesting;

import java.io.Serializable;

public class Save implements Serializable {
    private  int idSave;
    private int id_account_save;
    private int id_comic_save;

    public Save() {
    }

    public Save(int idSave, int id_account_save, int id_comic_save) {
        this.idSave = idSave;
        this.id_account_save = id_account_save;
        this.id_comic_save = id_comic_save;
    }

    public int getIdSave() {
        return idSave;
    }

    public void setIdSave(int idSave) {
        this.idSave = idSave;
    }

    public int getId_account_save() {
        return id_account_save;
    }

    public void setId_account_save(int id_account_save) {
        this.id_account_save = id_account_save;
    }

    public int getId_comic_save() {
        return id_comic_save;
    }

    public void setId_comic_save(int id_comic_save) {
        this.id_comic_save = id_comic_save;
    }
}
