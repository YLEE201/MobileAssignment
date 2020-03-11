package org.yuhonglee.gcu.mobileassignment.ui.main;
//Name.Yu Hong Lee
//StD No.S1620580

public class currentincidents {
    private String title = "";
    private String description = "";
    private String link;
    private String StrGeo = "";
    private String PubDate = "";
    private boolean expand = false;

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
    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
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
    public boolean getExpand() {
        if (expand == false) {
            return expand = false;
        } else {
            return expand = true;
        }
    }

    public void setExpand(boolean expand) {
        this.expand = expand;
    }

    @Override
    public String toString() {
        String s = "";
        if (getExpand() == true) {
            s = title + "\n" + "Description= " + description + "\n" + "Link= = " + link + "\n"+ "Location= " + StrGeo + "\n" + "Published= " + PubDate;
        } else if (getExpand() == false) {
            s = title;
        }
        return s;

    }
}
