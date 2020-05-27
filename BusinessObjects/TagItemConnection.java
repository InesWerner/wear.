public class TagItemConnection {

    private int tagID;
    private int itemID;


    public TagItemConnection(int tagID, int itemId) {
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


    
}