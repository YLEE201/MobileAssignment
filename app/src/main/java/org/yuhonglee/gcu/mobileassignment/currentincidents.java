package org.yuhonglee.gcu.mobileassignment;
//Name.Yu Hong Lee
//StD No.S1620580

public class currentincidents {
    private String title = "";
    private String description = "";
    private String StrGeo = "";
    private String PubDate = "";


    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getStrGeo() {
        return StrGeo;
    }
    public void setStrGeo(String StrGeo) {
        this.StrGeo = StrGeo;
    }
    public String getPubDate() {
        return PubDate;
    }
    public void setPubDate(String PubDate) {
        this.PubDate = PubDate;
    }

    @Override
    public String toString() {
        return "Current Incident= "+title + "\n" + "Description= " + description + "\n" + "Location= " + StrGeo + "\n" + "Published= " + PubDate;
    }
}
