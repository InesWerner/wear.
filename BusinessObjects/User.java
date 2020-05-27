public class User {

    private int ID;
    private String FirstName;
    private String LastName;
    private String NickName;
    private String email;
    private String birthday;
    private Adress adress;

    public User() {
    }

    public User(String firstName, String lastName, String nickName, String email, String birthday, Adress adress) {
        FirstName = firstName;
        LastName = lastName;
        NickName = nickName;
        this.email = email;
        this.birthday = birthday;
        this.adress = adress;
    }

    public User(int iD, String firstName, String lastName, String nickName, String email, String birthday,
    Adress adress) {
        ID = iD;
        FirstName = firstName;
        LastName = lastName;
        NickName = nickName;
        this.email = email;
        this.birthday = birthday;
        this.adress = adress;
}

    public int getID() {
        return ID;
    }

    public void setID(int iD) {
        this.ID = iD;
    }

    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String firstName) {
        this.FirstName = firstName;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String lastName) {
        this.LastName = lastName;
    }

    public String getNickName() {
        return NickName;
    }

    public void setNickName(String nickName) {
        this.NickName = nickName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public Adress getAdress() {
        return adress;
    }

    public void setAdress(Adress adress) {
        this.adress = adress;
    }

    public String toString(){
        return this.ID + "|" + this.FirstName + "|" +  this.LastName + "|" + this.NickName + "|" + this.email + "|" + this.birthday + "|" + this.adress.toString();
   
    }

    




}