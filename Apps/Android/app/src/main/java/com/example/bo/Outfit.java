package com.example.bo;
import java.io.Serializable;
import java.util.ArrayList;


public class Outfit implements Serializable {

    private int outfitID;
    private String outfitName;
    private String category;
    private String description;
    private boolean createStatus;
    private ArrayList<Item> items = new ArrayList<>();

    public Outfit(){

    }

    public Outfit(String outfit_Name) {
        this.outfitName = outfit_Name;
    }

    public Outfit(int outfit_ID, String outfit_Name) {
        this.outfitID = outfit_ID;
        this.outfitName = outfit_Name;
    }

    public boolean isEmpty(){
        return items.isEmpty();
    }

    public int getOutfit_ID() {
        return outfitID;
    }

    public ArrayList<Item> getItems() {
        return items;
    }

    public void add(Item item){
        items.add(item);
    }

    public void remove(Item item){
        items.remove(item);
    }

    public int size(){
        return items.size();
    }

    public Item get(int i){
        return items.get(i);
    }

    public ArrayList<Item> getAllItems(){
        return items;
    }


    public String getOutfitName() {
        return outfitName;
    }

    public void setOutfit_Name(String outfit_Name) {
        this.outfitName = outfit_Name;
    }

    public void setOutfitID(int outfit_ID) {
        this.outfitID = outfit_ID;
    }



    public String toString(){
        String headerString= "Outfit "+ this.outfitID + " Name: " + this.outfitName;
        String itemsString = "";
        if(!this.items.isEmpty()){
            for(int i = 0; i<this.items.size();i++){
                itemsString = itemsString + this.items.get(i).toString();
            }
        }else{
            itemsString = "This outfit has no items!";
        }

        return headerString + itemsString;

    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isCreateStatus() {
        return createStatus;
    }

    public void setCreateStatus(boolean createStatus) {
        this.createStatus = createStatus;
    }
}