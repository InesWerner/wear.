package com.example.database;
import com.example.bo.Item;
import com.example.bo.ItemOutfitConnection;
import com.example.bo.Outfit;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class OutfitMapper implements Serializable {


    private static OutfitMapper outfitMapper = null;

    protected OutfitMapper() {
    }

    public static OutfitMapper outfitMapper() {
        System.out.println("Outfit Mapper is created!");
        if (outfitMapper == null) {
            outfitMapper = new OutfitMapper();
        }
        return outfitMapper;
    }

    public ArrayList<Outfit> findAll(){
        Connection con = DBConnection.connection();
        ArrayList <Outfit> outfits = new ArrayList<>();
        ArrayList<Item> items = new ArrayList<>();
        ArrayList<ItemOutfitConnection> iOConnections = new ArrayList<>();

        try{
            PreparedStatement findOutfits = con.prepareStatement("SELECT * from dbo.Outfit ORDER BY  outfitID DESC");
            ResultSet rs = findOutfits.executeQuery();
            while(rs.next()){
                Outfit outfit = new Outfit();
                outfit.setOutfitID(rs.getInt("outfitID"));
                outfit.setOutfit_Name(rs.getString("name"));
                outfit.setCategory(rs.getString("category"));
                outfit.setDescription(rs.getString("description"));
                outfits.add(outfit);
            }
            PreparedStatement findItemOutfitConnections = con.prepareStatement("SELECT * from dbo.ItemOutfitConnection");
            ResultSet itemOutfitConnections = findItemOutfitConnections.executeQuery();
            while(itemOutfitConnections.next()){
                ItemOutfitConnection iOConnection = new ItemOutfitConnection();
                iOConnection.setItemOutfitID(itemOutfitConnections.getInt("itemOutfitID"));
                iOConnection.setItemID(itemOutfitConnections.getInt("itemID"));
                iOConnection.setOutfitID(itemOutfitConnections.getInt("outfitID"));
                iOConnections.add(iOConnection);
            }


                PreparedStatement findItems = con.prepareStatement("SELECT * from dbo.Item ORDER BY itemID DESC");
                ResultSet allItems = findItems.executeQuery();
                while(allItems.next()){
                    Item item = new Item();
                    item.setId(allItems.getInt("itemID"));
                    item.setName(allItems.getString("name"));
                    item.setCategory(allItems.getString("category"));
                    item.setColor(allItems.getString("color"));
                    item.setDescription(allItems.getString("description"));
                    item.setNumberOfUsage(allItems.getInt("numberOfUsage"));
                    item.setSize(allItems.getString("size"));
                    item.setStatus(allItems.getInt("status"));
                    item.setBrand(allItems.getString("brand"));
                    BigDecimal tagIDbigdec = new BigDecimal(0);
                    tagIDbigdec = allItems.getBigDecimal("tagID");
                    if(tagIDbigdec != null){
                        item.setTagID(tagIDbigdec.longValue());
                    }else{
                        item.setTagID(0L);
                    }
                    items.add(item);
            }
            outfits = insertItemsToOutfit(outfits,items,iOConnections);

        }catch(Exception e){
            System.out.println("Error OutfitMapper: findAll");
            e.printStackTrace();
        }
        return outfits;
    }



    public Outfit findBy(int outfitID){
        Connection con = DBConnection.connection();
        Outfit outfit = new Outfit();
        try{
            PreparedStatement find = con.prepareStatement("SELECT * from dbo.Outfit WHERE outfitID=?");
            find.setInt(1,outfitID);
            ResultSet rs = find.executeQuery();
            while(rs.next()){
                outfit.setOutfitID(rs.getInt("outfitID"));
                outfit.setOutfit_Name(rs.getString("name"));
                outfit.setCategory(rs.getString("category"));
                outfit.setDescription(rs.getString("description"));
            }
            System.out.println("Outfit found:"+ outfit.toString());
        }catch(Exception e){
            System.out.println("Error OutfitMapper: findBy");
            e.printStackTrace();
        }
        return outfit;
    }


    public Outfit create(Outfit outfit){
        Connection con = DBConnection.connection();
        try {
            //INSERT OUTFIT
            PreparedStatement insert = con.prepareStatement("INSERT INTO dbo.Outfit(name, category, description) VALUES(?,?,?);");
            insert.setString(1, outfit.getOutfitName());
            insert.setString(2, outfit.getCategory());
            insert.setString(3, outfit.getDescription());
            insert.executeUpdate();
            System.out.println("Start Outfit creation: " + outfit.getOutfitName());

            //RETURN CURRENT NEW OUTFIT TO GET OUTFIT ID
            PreparedStatement getNewOutfit = con.prepareStatement("SELECT TOP 1* FROM dbo.Outfit ORDER BY outfitID DESC;");
            ResultSet rs = getNewOutfit.executeQuery();
            if (rs.next()) {
               outfit.setOutfitID(rs.getInt("outfitID"));
               outfit.setOutfit_Name(rs.getString("name"));
               outfit.setCategory(rs.getString("category"));
               outfit.setDescription(rs.getString("description"));
            }
            System.out.println("Outfit successful created!: " + outfit.getOutfit_ID());
            //CREATE OUTFIT-ITEM-CONNECTION
            for(int i = 0; i<outfit.size();i++){
                PreparedStatement insertItemConnection = con.prepareStatement("INSERT dbo.ItemOutfitConnection(outfitID, itemID) VALUES(?,?);");
                insertItemConnection.setInt(1,outfit.getOutfit_ID());
                insertItemConnection.setInt(2,outfit.get(i).getId());
                insertItemConnection.executeUpdate();
            }

            outfit.setCreateStatus(true);
        } catch (SQLException e2) {
            e2.printStackTrace();
            outfit.setCreateStatus(false);
        }
        return outfit;
    }


    public Outfit delete(Outfit outfit){
        Connection con = DBConnection.connection();
        try {
            PreparedStatement deleteItemConnections = con.prepareStatement("DELETE dbo.ItemOutfitConnection WHERE outfitID =?;");
            deleteItemConnections.setInt(1,outfit.getOutfit_ID());
            deleteItemConnections.executeUpdate();

            PreparedStatement delete = con.prepareStatement("DELETE dbo.Outfit WHERE outfitID=?;");
            delete.setInt(1, outfit.getOutfit_ID());
            delete.executeUpdate();
            System.out.println("Delete outfit successful: "+ outfit.getOutfit_ID());
            outfit.setCreateStatus(false);

        } catch (SQLException e) {
            outfit.setCreateStatus(true);
            e.printStackTrace();
        }
        return outfit;
    }


    public void deleteBy(int outfitID){
        Connection con = DBConnection.connection();
        try {
            PreparedStatement deleteItemConnections = con.prepareStatement("DELETE dbo.ItemOutfitConnection WHERE outfitID =?;");
            deleteItemConnections.setInt(1,outfitID);
            deleteItemConnections.executeUpdate();

            PreparedStatement delete = con.prepareStatement("DELETE dbo.Outfit WHERE outfitID=?;");
            delete.setInt(1, outfitID);
            delete.executeUpdate();
            System.out.println("Delete outfit successful: "+ outfitID);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    private ArrayList<Outfit> insertItemsToOutfit(ArrayList<Outfit> outfits,
                                                  ArrayList<Item> items,
                                                  ArrayList<ItemOutfitConnection> iOConnections) {

        for(int i = 0; i<outfits.size();i++){
            for(int j = 0; j<iOConnections.size();j++){
                for(int k = 0; k<items.size();k++){
                    if(outfits.get(i).getOutfit_ID()==iOConnections.get(j).getOutfitID() &&
                    items.get(k).getId()==iOConnections.get(j).getItemID()){
                        outfits.get(i).add(items.get(k));
                    }

                }
            }

        }
        return outfits;
    }

    public Outfit update(Outfit outfit) {

        Connection con = DBConnection.connection();

        try {
            Statement stmt = con.createStatement();

            String query = "UPDATE dbo.Outfit SET name='"+ outfit.getOutfitName()+ "' , category='" + outfit.getCategory() + "', description='" + outfit.getDescription() +"' WHERE outfitID=" + outfit.getOutfit_ID();
            System.out.println(query);
            stmt.executeUpdate(query);
            outfit.setCreateStatus(true);

            PreparedStatement deleteItemOutfitConnection = con.prepareStatement("DELETE dbo.ItemOutfitConnection WHERE outfitID =?;");
            System.out.println(deleteItemOutfitConnection);
            deleteItemOutfitConnection.setInt(1,outfit.getOutfit_ID());
            deleteItemOutfitConnection.executeUpdate();

            for(int i = 0; i<outfit.size(); i++){
                PreparedStatement insert = con.prepareStatement("INSERT INTO dbo.ItemOutfitConnection(outfitID, itemID) VALUES(?,?);");
                System.out.println(insert);
                insert.setInt(1, outfit.getOutfit_ID());
                insert.setInt(2, outfit.get(i).getId());
                insert.executeUpdate();
            }


        } catch (SQLException e2) {
            e2.printStackTrace();
            outfit.setCreateStatus(false);
        }

        return outfit;
    }
}
