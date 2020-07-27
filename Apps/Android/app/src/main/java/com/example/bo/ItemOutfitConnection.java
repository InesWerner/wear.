package com.example.bo;

public class ItemOutfitConnection {

    int itemOutfitID;
    int outfitID;
    int itemID;


    public ItemOutfitConnection(){

    }

    public ItemOutfitConnection(int itemOutfitID, int outfitID, int itemID){
        this.itemOutfitID =itemOutfitID;
        this.outfitID = outfitID;
        this.itemID = itemID;

    }

    public int getItemOutfitID() {
        return itemOutfitID;
    }

    public void setItemOutfitID(int itemOutfitID) {
        this.itemOutfitID = itemOutfitID;
    }

    public int getOutfitID() {
        return outfitID;
    }

    public void setOutfitID(int outfitID) {
        this.outfitID = outfitID;
    }

    public int getItemID() {
        return itemID;
    }

    public void setItemID(int itemID) {
        this.itemID = itemID;
    }

    public String toString(){
        return "ItemOutfitConnection: "+this.itemOutfitID + ", " + this.itemID + ", " + this.outfitID;
    }

}
