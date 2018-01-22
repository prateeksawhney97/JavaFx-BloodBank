package bloodbank;

import javafx.beans.property.SimpleStringProperty;

public class Blood {

    private final SimpleStringProperty sno;
    private final SimpleStringProperty did;
    private final SimpleStringProperty bgroup;
    private final SimpleStringProperty rdate;
    private final SimpleStringProperty edate;
    private final SimpleStringProperty status;
    public Blood(String sno, String did, String bgroup, String rdate, String edate, String status) {
        this.sno = new SimpleStringProperty(sno);
        this.did = new SimpleStringProperty(did);
        this.bgroup = new SimpleStringProperty(bgroup);
        this.rdate = new SimpleStringProperty(rdate);
        this.edate = new SimpleStringProperty(edate);
        this.status = new SimpleStringProperty(status);
        }

    public String getSNO() {
        return sno.get();
    }

    public void setSNO(String sno) {
        this.sno.set(sno);
    }

    public String getDID() {
        return did.get();
    }

    public void setDID(String did) {
        this.did.set(did);
    }

    public String getBgroup() {
        return bgroup.get();
    }

    public void setBgroup(String bgroup) {
        this.bgroup.set(bgroup);
    }

    public String getRdate() {
        return rdate.get();
    }

    public void setRdate(String rdate) {
        this.rdate.set(rdate);
    }

    public String getEdate() {
        return edate.get();
    }

    public void setEdate(String edate) {
        this.edate.set(edate);
    }

    public String getStatus() {
        return status.get();
    }

    public void setStatus(String status) {
        this.status.set(status);
    }
   
}
