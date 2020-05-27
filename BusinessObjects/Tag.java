public class Tag {

    private int tagID;
    private boolean tagStatus;

    public Tag() {
    }
    public Tag(int tagID, boolean tagStatus) {
        this.tagID = tagID;
        this.tagStatus = tagStatus;
    }

    public int getTagID() {
        return tagID;
    }

    public boolean isTagStatus() {
        return tagStatus;
    }

    public void setTagStatus(boolean tagStatus) {
        this.tagStatus = tagStatus;
    }

    public void setTagID(int tagID) {
        this.tagID = tagID;
    }

    public String toString(){
        return this.tagID + " | " + this.tagStatus;
    }

 




    

}