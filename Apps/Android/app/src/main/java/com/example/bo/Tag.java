package com.example.bo;

import java.math.BigInteger;

public class Tag {

    private long tagID;
    private int tagFree;
    private String tagKey;

    public Tag() {
    }
    public Tag(long tagID, int tagFree, String tagKey) {
        this.tagID = tagID;
        this.tagFree = tagFree;
        this.tagKey = tagKey;
    }

    public long getTagID() {
        return tagID;
    }

    public void setTagID(long tagID) {
        this.tagID = tagID;
    }

    public void setTagFree(int tagFree){
        this.tagFree = tagFree;
    }

    public int getTagFree(){
        return tagFree;
    }

    public void setTagKey(String tagKey){
        this.tagKey = tagKey;
    }

    public String getTagKey() {
        return tagKey;
    }

    public String toString(){
        return this.tagKey;
    }


}