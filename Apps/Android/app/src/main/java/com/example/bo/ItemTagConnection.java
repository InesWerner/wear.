package com.example.bo;

public class ItemTagConnection {

    private int tagItemID;
    private int tagID;
    private int itemID;
    private int DELETED;

    public ItemTagConnection(){

    }


    public ItemTagConnection(int tagItemID, int tagID, int itemId) {
        this.tagItemID = tagItemID;
        this.tagID = tagID;
        this.itemID = itemId;
    }

    public int getTagID() {
        return tagID;
    }

    public int getItemID() {
        return itemID;
    }

    public void setItem_Id(int itemId) {
        this.itemID = itemId;
    }

    public void setTag_ID(int tagID) {
        this.tagID = tagID;
    }

    public String toString(){
        return this.tagID + " | " + this.itemID;
    }


    public int getDELETED() {
        return DELETED;
    }

    public void setDELETED(int DELETED) {
        this.DELETED = DELETED;
    }

    public int getTagItemID() {
        return tagItemID;
    }

    public void setTagItemID(int tagItemID) {
        this.tagItemID = tagItemID;
    }
}