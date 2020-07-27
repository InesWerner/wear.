package com.example.database;
import com.example.bo.Item;
import com.example.bo.Tag;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class ItemMapper implements Serializable {

    private static ItemMapper itemMapper = null;

    protected ItemMapper() {
    }

    public static ItemMapper itemMapper() {
        System.out.println("Item Mapper is created!");
        if (itemMapper == null) {
            itemMapper = new ItemMapper();
        }
        return itemMapper;
    }


    public ArrayList<Item> findAll(){

        Connection con = DBConnection.connection();
        ArrayList <Item> items = new ArrayList<>();

        try{
            PreparedStatement find = con.prepareStatement("SELECT * from dbo.Item ORDER BY  itemID DESC");
            ResultSet rs = find.executeQuery();
            while(rs.next()){
                Item item = new Item();

                item.setId(rs.getInt("itemID"));
                item.setName(rs.getString("name"));
                item.setCategory(rs.getString("category"));
                item.setColor(rs.getString("color"));
                item.setDescription(rs.getString("description"));
                item.setNumberOfUsage(rs.getInt("numberOfUsage"));
                item.setSize(rs.getString("size"));
                item.setStatus(rs.getInt("status"));
                item.setBrand(rs.getString("brand"));
                BigDecimal tagIDbigdec = new BigDecimal(0);
                tagIDbigdec = rs.getBigDecimal("tagID");
                if(tagIDbigdec != null){
                    item.setTagID(tagIDbigdec.longValue());
                }else{
                    item.setTagID(0L);
                }
                items.add(item);
            }

        }catch(Exception e){
            System.out.println("Error ItemMapper: findAll");
            e.printStackTrace();

        }

        return items;
    }


    public Item create(Item i, Tag t) {
        Connection con = DBConnection.connection();
        try {
            PreparedStatement insert = con.prepareStatement("INSERT INTO dbo.Item(name, category, description, numberOfUsage, status, size, color, tagID) VALUES(?,?,?,?,?,?,?,?);");
            insert.setString(1, i.getName());
            insert.setString(2, i.getCategory());
            insert.setString(3, i.getDescription());
            insert.setInt(4, i.getNumberOfUsage());
            insert.setInt(5, i.getStatus());
            insert.setString(6, i.getSize());
            insert.setString(7, i.getColor());
            BigDecimal tagIDbigdec = new BigDecimal(t.getTagID());
            insert.setBigDecimal(8, tagIDbigdec);
            insert.executeUpdate();

            PreparedStatement tagNotFree = con.prepareStatement("UPDATE dbo.Tag SET tagFree = 1 WHERE tagId= "+t.getTagID());
            tagNotFree.executeUpdate();

            PreparedStatement getNewItem = con.prepareStatement("SELECT TOP 1 * FROM dbo.Item ORDER BY itemID DESC;");
            ResultSet rs = getNewItem.executeQuery();
            if (rs.next()) {
                i.setId(rs.getInt("itemID"));
                i.setCreateStatus(true);
            }
        } catch (SQLException e2) {
            e2.printStackTrace();
            i.setCreateStatus(false);
            System.out.println("Create" + i.toString() + t.toString());
        }
        return i;
    }


    public Item update(Item i) {
        Connection con = DBConnection.connection();

        try {
            Statement stmt = con.createStatement();

            String query = "UPDATE dbo.Item SET name= '"+ i.getName() + "' ,category='" + i.getCategory() + "',  description='" + i.getDescription() + "', " + "numberOfUsage=" + i.getNumberOfUsage() + ", " + "status=" + i.getStatus() + ", " + "size='" + i.getSize() + "', " + "color='" + i.getColor() + "',brand='" + i.getBrand() +"' WHERE itemID=" + i.getId();
            System.out.println(query);
            stmt.executeUpdate(query);
            i.setCreateStatus(true);

        } catch (SQLException e2) {
            e2.printStackTrace();
            i.setCreateStatus(false);
        }

        return i;
    }


    public Item delete(Item i) {
        Connection con = DBConnection.connection();

        try {

            PreparedStatement delete = con.prepareStatement("DELETE dbo.Item WHERE itemID=?;");
            delete.setInt(1, i.getId());
            delete.executeUpdate();

            System.out.println("Delete Item: "+ i.toString());

            PreparedStatement setTagFree = con.prepareStatement("UPDATE dbo.Tag SET tagFree = NULL WHERE tagId=?;");
            setTagFree.setLong(1, i.getTagID());
            setTagFree.executeUpdate();

            i.setCreateStatus(false);

            System.out.println("Set Tag free: "+ i.getTagID());

        } catch (SQLException e) {
            e.printStackTrace();
            i.setCreateStatus(true);
        } catch(Exception e){
            e.printStackTrace();
            i.setCreateStatus(true);
        }
        return i;
    }


    public void deleteBy(int itemID) {
        Connection con = DBConnection.connection();

        try {

            PreparedStatement delete = con.prepareStatement("DELETE dbo.Item WHERE itemID=?;");
            delete.setInt(1, itemID);
            delete.executeUpdate();

            System.out.println("Delete Item: "+ itemID);

        } catch (SQLException e) {
            e.printStackTrace();
        } catch(Exception e){
            e.printStackTrace();
        }
    }


}
