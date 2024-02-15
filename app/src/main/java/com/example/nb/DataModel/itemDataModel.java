package com.example.nb.DataModel;

public class itemDataModel {
    String text;
    int id;
    public itemDataModel(int id,String text){
        this.text=text;
        this.id=id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
