import java.util.Vector;

public class Outfit {

    private int outfitID;
    private int userID;
    private String outfitName;
    private Vector<Item> items;

    public Outfit(String outfit_Name, Vector<Item> items) {
        this.outfitName = outfit_Name;
        this.items = items;
    }

    public Outfit(int outfit_ID, int user_ID, String outfit_Name, Vector<Item> items) {
        this.outfitID = outfit_ID;
        this.userID = user_ID;
        this.outfitName = outfit_Name;
        this.items = items;
    }

    public int getOutfit_ID() {
        return outfitID;
    }

    public Vector<Item> getItems() {
        return items;
    }

    public void setItems(Vector<Item> items) {
        this.items = items;
    }

    public String getOutfitName() {
        return outfitName;
    }

    public void setOutfit_Name(String outfit_Name) {
        this.outfitName = outfit_Name;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int user_ID) {
        this.userID = user_ID;
    }

    public void setOutfitID(int outfit_ID) {
        this.outfitID = outfit_ID;
    }

    public void addItem(Item item){
        this.items.add(item);
    }

    public void removeItem(Item item){
        this.items.remove(item);
    }

    public void removeItemById(int itemID){
        if(!this.items.isEmpty()){
            for(int i = 0; i<items.size(); i++){
                if(itemID == items.get(i).getId()){
                    items.remove(i);
                }
            }
        }
        
    }

    public String toString(){
        String headerString= "Outfit "+ this.outfitID + " Name: " + this.outfitName + "Of User: " + this.userID;
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

}