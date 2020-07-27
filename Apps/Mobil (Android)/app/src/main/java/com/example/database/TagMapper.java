package com.example.database;

import com.example.bo.Item;
import com.example.bo.Tag;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Vector;

public class TagMapper {


    private static TagMapper tagMapper = null;

    protected TagMapper() {
    }

    public static TagMapper tagMapper() {
        System.out.println("Tag Mapper is created!");
        if (tagMapper == null) {
            tagMapper = new TagMapper();
        }
        return tagMapper;
    }

    public Tag findBy(long tagID){
        Connection con = DBConnection.connection();
        Tag tag = new Tag();

        try{
            PreparedStatement find = con.prepareStatement("SELECT * from dbo.Tag WHERE tagId="+tagID);
            ResultSet rs = find.executeQuery();
            if(rs.next()){
                BigDecimal tagIDbigdec = new BigDecimal(0);
                tagIDbigdec = rs.getBigDecimal("tagId");
                tag.setTagID(tagIDbigdec.longValue());
                tag.setTagFree(rs.getInt("tagFree"));
                tag.setTagKey(rs.getString("tagKey"));
            }

        }catch(Exception e){
            System.out.println("Error TagMapper: findAll");
            e.printStackTrace();
        }
        return tag;


    }


    public ArrayList<Tag> findAll(){
        Connection con = DBConnection.connection();
        ArrayList<Tag> tags = new ArrayList<>();

        try{
            PreparedStatement find = con.prepareStatement("SELECT * from dbo.Tag ORDER BY  orderID DESC");
            ResultSet rs = find.executeQuery();
            while(rs.next()){
                Tag tag = new Tag();

                BigDecimal tagIDbigdec = new BigDecimal(0);
                tagIDbigdec = rs.getBigDecimal("tagId");
                tag.setTagID(tagIDbigdec.longValue());
                tag.setTagFree(rs.getInt("tagFree"));
                tag.setTagKey(rs.getString("tagKey"));
                tags.add(tag);
            }

        }catch(Exception e){
            System.out.println("Error TagMapper: findAll");
            e.printStackTrace();
        }
        return tags;
    }


    public ArrayList<Tag> findAllFreeTags(){
        Connection con = DBConnection.connection();
        ArrayList<Tag> tags = new ArrayList<>();

        try{
            PreparedStatement find = con.prepareStatement("SELECT * from dbo.Tag WHERE tagFree IS NULL ORDER BY orderID DESC");
            ResultSet rs = find.executeQuery();
            while(rs.next()){
                Tag tag = new Tag();

                BigDecimal tagIDbigdec = new BigDecimal(0);
                tagIDbigdec = rs.getBigDecimal("tagId");
                tag.setTagID(tagIDbigdec.longValue());
                tag.setTagFree(rs.getInt("tagFree"));
                tag.setTagKey(rs.getString("tagKey"));
                tags.add(tag);
            }

        }catch(Exception e){
            System.out.println("Error TagMapper: findAll");
            e.printStackTrace();
        }
        return tags;
    }






}
