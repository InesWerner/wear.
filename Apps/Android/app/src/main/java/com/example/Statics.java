package com.example;

import android.graphics.Color;

public class Statics {

    public final static String DATABASE_CREDENTIALS = "jdbc:jtds:sqlserver://wearsqlserver.database.windows.net:1433;"
            + "databaseName=wearDB;"
            + "user=wearbabo@wearsqlserver;"
            + "password=24wear-8;"
            + "encrypt=true;"
            + "trustServerCertificate=false;"
            + "loginTimeout=30;";


    public final static String DATABASE_DRIVER = "net.sourceforge.jtds.jdbc.Driver";

    public final static int INSIDE_NUMB = 0;
    public final static int OUTSIDE_NUMB = 1;

    public final static String COLOR_EDIT_ACTIVE = "#77CD12";
    public final static String COLOR_EDIT_NOT_ACTIVE = "#2196F3";
    public final static String COLOR_CLICKED = "#2196F3";
    public final static String COLOR_NOT_CLICKED = "#555555";
    public final static int LOCKCOLOR = Color.LTGRAY;

    public final static String SERIZABLE_ID_ITEM = "ItemDetails";
    public final static String SERIZABLE_ID_ITEMMAPPER = "ItemMapper";
    public final static String SERIZABLE_ID_OUTFIT = "OutfitDetails";
    public final static String SERIZABLE_ID_OUTFITMAPPER = "OutfitMapper";
    public static final String SERIZABLE_ID_USERMAPPER = "Usermapper";
    public static final String SERIZABLE_ID_USER = "User";

    public final static String ERROR_MISSING_NAME = "Please fill in a name!";
    public final static String ERROR_MISSING_ITEM = "Please select at least one item!";
    public final static String ERROR_MISSING_NAME_ITEM = "Please fill in a name and choose at least one item!";
    public final static String ERROR_MISSING_TAG_NAME_ITEM = "Please fill in tag and name!";

    public final static String DELETE_QUESTION = "Are you sure?";
    public final static String DELETE_POPUP_BUTTON = "YES";
    public static final String SAVE_BUTTON = "Save";
    public static final String EDIT_BUTTON = "Edit";
    public static final String CLOSE = "Close";

    public final static String UPDATE_ITEM_SUCCESSFUL = "Item updated successful!";
    public static final String DELETE_ITEM_SUCCESSFUL = "Item deleted successful!";
    public final static String DELETE_ITEM_ERROR = "Error deleting item! Try again later";
    public final static String UPDATE_ITEM_ERROR = "Error updating item! Try again later!";
    public final static String CREATE_ITEM_ERROR = "Error creating item! Try again later!";

    public final static String UPDATE_OUTFIT_SUCCESSFUL = "Outfit updated successful!";
    public static final String DELETE_OUTFIT_SUCCESSFUL = "Outfit deleted successful!";
    public final static String UPDATE_OUTFIT_ERROR = "Error updating outfit! Try again later!";
    public final static String DELETE_OUTFIT_ERROR = "Error deleting outfit! Try again later";

    public final static String CREATE_OUTFIT_ERROR = "Error creating outfit! Try again later!";
    public final static String CREATE_OUTFIT_SUCCESSFUL = "Outfit created successful!";

    public static final String SELECT_ITEM = "SELECT ITEMS";

    public static final String NONE = "";
    public static final String ERROR_LOGIN = "E-Mail or password is wrong! Try again.";
    public static final String ERROR_ACCOUNT = "Something went wrong! Try again later.";
    public static final String ERROR_ACCOUNT_VALUES_MISSING = "Please fill in all informations!";
    public static final String ERROR_LOGIN_VALUES_MISSING = "Please fill in all informations!";

    public static final String ERROR_USER_UPDATE = "Upps! Something went wrong. Please tray again later.";
    public static final String UPDATE_USER_SUCCESSFUL = "User updated successful!";
    public static final String DELETE_USER_SUCCESSFUL = "User deleted successful!";
}
