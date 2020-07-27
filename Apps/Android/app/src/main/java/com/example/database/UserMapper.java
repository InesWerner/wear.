package com.example.database;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import com.example.bo.User;
import java.io.ByteArrayOutputStream;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class UserMapper implements Serializable {


    private static UserMapper userMapper = null;

    protected UserMapper() {
    }

    public static UserMapper userMapper() {
        System.out.println("User Mapper is created!");
        if (userMapper == null) {
            userMapper = new UserMapper();
        }
        return userMapper;
    }


    public User login(String username, String password){
        Connection con = DBConnection.connection();
        User user = new User();

        try{
            PreparedStatement find = con.prepareStatement("SELECT * from dbo.[User] WHERE username= ? AND password = ?");
            find.setString(1, username);
            find.setString(2, password);
            ResultSet rs = find.executeQuery();
            if(rs.next()){
                user.setUserID(rs.getInt("userID"));
                user.setUsername(rs.getString("username"));
                user.setName(rs.getString("name"));
                user.setMail(rs.getString("mail"));
                user.setPassword(rs.getString("password"));
              /*   byte[] bytes = Base64.decode(rs.getBytes("profilImage"), Base64.DEFAULT);
                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes,0, bytes.length);
                user.setProfilImage(bitmap);*/
                user.setLoginStatus(true);
            }
        }catch(Exception e){
            e.printStackTrace();
            user.setLoginStatus(false);
        }

        return user;
    }

    public User create(String name, String nickname, String mail, String password){
        Connection con = DBConnection.connection();
        User user = new User();
        try {
            PreparedStatement insert = con.prepareStatement("INSERT INTO dbo.[User](username, name, mail, password) VALUES(?,?,?,?);");
            insert.setString(1, nickname);
            insert.setString(2, name);
            insert.setString(3, mail);
            insert.setString(4, password);
            insert.executeUpdate();

            PreparedStatement getNewItem = con.prepareStatement("SELECT TOP 1 * FROM dbo.[User] ORDER BY userID DESC;");
            ResultSet rs = getNewItem.executeQuery();
            if (rs.next()) {
                user.setUserID(rs.getInt("userID"));
                user.setUsername(rs.getString("username"));
                user.setName(rs.getString("name"));
                user.setMail(rs.getString("mail"));
                user.setPassword(rs.getString("password"));
             /*   byte[] bytes = Base64.decode(rs.getBytes("profilImage"), Base64.DEFAULT);
                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes,0, bytes.length);
                user.setProfilImage(bitmap);*/
                user.setCreateStatus(true);
            }

        }catch (Exception e){
            e.printStackTrace();
            user.setCreateStatus(false);
        }


        return user;
    }



    public User update(User user){
        Connection con = DBConnection.connection();
        try {
            Statement stmt = con.createStatement();

            String query = "UPDATE dbo.[User] SET name= '"+ user.getName() + "' ,username='" + user.getUsername() + "',  mail='" + user.getMail() + "', " + "password='" + user.getPassword() + "' WHERE userID=" + user.getUserID();
            System.out.println(query);
            stmt.executeUpdate(query);
            user.setCreateStatus(true);

        } catch (SQLException e2) {
            e2.printStackTrace();
            user.setCreateStatus(false);
        }


        return user;
    }

    public User delete(User user){
        Connection con = DBConnection.connection();
        try {
            Statement stmt = con.createStatement();
            String query = "DELETE dbo.[User] WHERE userID=" + user.getUserID();
            System.out.println(query);
            stmt.executeUpdate(query);
            user.setCreateStatus(false);

        } catch (SQLException e2) {
            e2.printStackTrace();
            user.setCreateStatus(true);
        }


        return user;
    }


    public User saveProfilPicture(User user){
        Connection con = DBConnection.connection();
        try {

            Bitmap captureImage = user.getProfilImage();
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            captureImage.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
            byte[] bytes = byteArrayOutputStream.toByteArray();
            //byte[] bytes1 = Base64.encode(bytes, Base64.DEFAULT);
           // System.out.println("Write: " + bytes1.toString());

            PreparedStatement update = con.prepareStatement("UPDATE dbo.[User] SET profilImage = (CONVERT(VARBINARY(MAX), '"+ bytes +"')) WHERE userID = " + user.getUserID()+";");
            System.out.println(update.toString());
            update.executeUpdate();
            user.setCreateStatus(true);

        } catch (SQLException e2) {
            e2.printStackTrace();
            user.setCreateStatus(false);
        }


        return user;
    }

    public User findProfilPicture(User user)  {
        Connection con = DBConnection.connection();
        try{
            PreparedStatement get = con.prepareStatement("SELECT  profilImage FROM dbo.[User] WHERE userID =" + user.getUserID() +";");
            ResultSet rs = get.executeQuery();
            if (rs.next()) {
                byte[] bytes = Base64.decode(rs.getString("profilImage"), Base64.DEFAULT);
                System.out.println("Return: " + rs.getString("profilImage"));
                System.out.println("Bytes decocde: " + bytes.toString());

                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes,0, bytes.length);
                System.out.println("Bitmap: " +bitmap.toString());


                user.setProfilImage(bitmap);
            }

        }catch (Exception e){
            e.printStackTrace();
        }


        return user;
    }

}
