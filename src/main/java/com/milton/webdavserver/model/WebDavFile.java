package com.milton.webdavserver.model;

public class WebDavFile {
    private Long id;
    private String name;
    private byte[] contents;
    private WebDavFolder parent;

    public WebDavFile(Long id, String name, byte[] contents, WebDavFolder parent) {
        this.id = id;
        this.name = name;
        this.contents = contents;
        this.parent = parent;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte[] getContents() {
        return contents;
    }

    public void setContents(byte[] contents) {
        this.contents = contents;
    }

    public WebDavFolder getParent() {
        return parent;
    }

    public void setParent(WebDavFolder parent) {
        this.parent = parent;
    }


}
