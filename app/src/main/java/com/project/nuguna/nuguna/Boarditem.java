package com.project.nuguna.nuguna;

/**
 * Created by kwon on 2017-01-06.
 */


public class Boarditem {
    private int wbno;
    private String email;
    private String writer;
    private String content;
    private String inputdate;
    private String wbtype;
    private String wbpass;
    private String templatename;
    private int version;


    public Boarditem(){}
    public Boarditem(int wbno, String email, String writer, String content, String inputdate, String wbtype,
                        String wbpass, String templatename, int version) {
        this.wbno = wbno;
        this.email = email;
        this.writer = writer;
        this.content = content;
        this.inputdate = inputdate;
        this.wbtype = wbtype;
        this.wbpass = wbpass;
        this.templatename = templatename;
        this.version = version;
    }


    public int getWbno() {
        return wbno;
    }


    public void setWbno(int wbno) {
        this.wbno = wbno;
    }


    public String getEmail() {
        return email;
    }


    public void setEmail(String email) {
        this.email = email;
    }


    public String getWriter() {
        return writer;
    }


    public void setWriter(String writer) {
        this.writer = writer;
    }


    public String getContent() {
        return content;
    }


    public void setContent(String content) {
        this.content = content;
    }


    public String getInputdate() {
        return inputdate;
    }


    public void setInputdate(String inputdate) {
        this.inputdate = inputdate;
    }


    public String getWbtype() {
        return wbtype;
    }


    public void setWbtype(String wbtype) {
        this.wbtype = wbtype;
    }


    public String getWbpass() {
        return wbpass;
    }


    public void setWbpass(String wbpass) {
        this.wbpass = wbpass;
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


    @Override
    public String toString() {
        return "Widget_board [wbno=" + wbno + ", email=" + email + ", writer=" + writer + ", content=" + content
                + ", inputdate=" + inputdate + ", wbtype=" + wbtype + ", wbpass=" + wbpass + ", templatename="
                + templatename + ", version=" + version + "]";
    }





}