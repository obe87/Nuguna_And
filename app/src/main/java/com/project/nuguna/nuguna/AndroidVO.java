package com.project.nuguna.nuguna;

public class AndroidVO {

    private String owner;
    private String title;
    private String shopname;
    private String shopaddr;
    private String shoptel;
    private String shopnumber;
    private String templatename;
    private int version;
    private String domain;
    private int hosting_id;



    public AndroidVO(){};
    public AndroidVO(String owner, String title, String shopname, String shopaddr, String shoptel, String shopnumber,
                     String templatename, int version, String domain, int hosting_id) {
        this.owner = owner;
        this.title = title;
        this.shopname = shopname;
        this.shopaddr = shopaddr;
        this.shoptel = shoptel;
        this.shopnumber = shopnumber;
        this.templatename = templatename;
        this.version = version;
        this.domain = domain;
        this.hosting_id = hosting_id;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getShopname() {
        return shopname;
    }

    public void setShopname(String shopname) {
        this.shopname = shopname;
    }

    public String getShopaddr() {
        return shopaddr;
    }

    public void setShopaddr(String shopaddr) {
        this.shopaddr = shopaddr;
    }

    public String getShoptel() {
        return shoptel;
    }

    public void setShoptel(String shoptel) {
        this.shoptel = shoptel;
    }

    public String getShopnumber() {
        return shopnumber;
    }

    public void setShopnumber(String shopnumber) {
        this.shopnumber = shopnumber;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }


    public String getTemplatename() {
        return templatename;
    }

    public void setTemplatename(String templatename) {
        this.templatename = templatename;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public int getHosting_id() {
        return hosting_id;
    }

    public void setHosting_id(int hosting_id) {
        this.hosting_id = hosting_id;
    }


    @Override
    public String toString() {
        return "MakeHomepageVO [owner=" + owner + ", title=" + title + ", shopname=" + shopname + ", shopaddr=" + shopaddr + ", shoptel=" + shoptel + ", shopnumber=" + shopnumber + ", templatename="
                + templatename + ", version=" + version + ", domain=" + domain + ", hosting_start=" + ", hosting_id=" + hosting_id + "]";
    }
}
