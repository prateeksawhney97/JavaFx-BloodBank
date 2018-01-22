package bloodbank;

import javafx.beans.property.SimpleStringProperty;

public class Donors {

    private final SimpleStringProperty DID;
    private final SimpleStringProperty name;
    private final SimpleStringProperty gender;
    private final SimpleStringProperty DOB;
    private final SimpleStringProperty contactno;
    private final SimpleStringProperty emailid;
    private final SimpleStringProperty address;
    private final SimpleStringProperty city;
    public Donors(String DID, String name, String gender, String DOB, String contactno, String emailid, String address, String city) {
        this.DID = new SimpleStringProperty(DID);
        this.name = new SimpleStringProperty(name);
        this.gender = new SimpleStringProperty(gender);
        this.DOB = new SimpleStringProperty(DOB);
        this.contactno = new SimpleStringProperty(contactno);
        this.emailid = new SimpleStringProperty(emailid);
        this.address = new SimpleStringProperty(address);
        this.city = new SimpleStringProperty(city);
        }

    public String getDID() {
        return DID.get();
    }

    public void setDID(String DID) {
        this.DID.set(DID);
    }

    public String getName() {
        return name.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getGender() {
        return gender.get();
    }

    public void setGender(String gender) {
        this.gender.set(gender);
    }

    public String getDOB() {
        return DOB.get();
    }

    public void setDOB(String DOB) {
        this.DOB.set(DOB);
    }

    public String getContactno() {
        return contactno.get();
    }

    public void setContactno(String contactno) {
        this.contactno.set(contactno);
    }

    public String getEmailid() {
        return emailid.get();
    }

    public void setEmailid(String emailid) {
        this.emailid.set(emailid);
    }
    public String getAddress(){
        return address.get();
    }
    public void setAddress(String address){
        this.address.set(address);
    }
    public String getCity(){
        return city.get();
    }
    public void setCity(String city){
        this.city.set(city);
    }

}
