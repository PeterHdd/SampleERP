package model;

public class Users {

    private String firstName,lastName,userName,password;


    public Users(String firstName,String lastName,String userName,String password){
        this.firstName=firstName;
        this.lastName=lastName;
        this.userName=userName;
        this.password=password;
    }



    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
}
