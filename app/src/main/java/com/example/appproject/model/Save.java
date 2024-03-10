package com.example.appproject.model;

import androidx.annotation.OpenForTesting;

public class Save {
    private  String idSave;
    private String id_account_save;
    private String id_comic_save;

    public Save() {
    }

    public Save(String idSave,String id_account_save,String id_comic_save){
        this.idSave=idSave;
        this.id_account_save=id_account_save;
        this.id_comic_save=id_comic_save;
    }
    public String getIdSave() {
        return idSave;
    }

    public void setIdSave(String idSave) {
        this.idSave = idSave;
    }

    public String getId_account_save() {
        return id_account_save;
    }

    public void setId_account_save(String id_account_save) {
        this.id_account_save = id_account_save;
    }

    public String getId_comic_save() {
        return id_comic_save;
    }

    public void setId_comic_save(String id_comic_save) {
        this.id_comic_save = id_comic_save;
    }
}
