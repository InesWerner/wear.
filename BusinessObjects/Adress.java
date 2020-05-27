public class Adress {

    private int userID;
    private String country;
    private String city;
    private String plz;
    private String street;
    private String streetnumber;



    public Adress() {
    }

    public Adress(String country, String city, String plz, String street, String streetnumber) {
        this.country = country;
        this.city = city;
        this.plz = plz;
        this.street = street;
        this.streetnumber = streetnumber;
    }
    

    public Adress(int userID, String country, String city, String plz, String street, String streetnumber) {
        this.userID = userID;
        this.country = country;
        this.city = city;
        this.plz = plz;
        this.street = street;
        this.streetnumber = streetnumber;
    }

    public int getUserID() {
        return userID;
    }

    public String getStreetnumber() {
        return streetnumber;
    }

    public void setStreetnumber(String streetnumber) {
        this.streetnumber = streetnumber;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getPlz() {
        return plz;
    }

    public void setPlz(String plz) {
        this.plz = plz;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }


    public String toString(){
        return this.userID + "|" + this.country + "|" + this.city + "|" + this.plz + "|" + this.street + "|" + this.streetnumber;
    }

  


}