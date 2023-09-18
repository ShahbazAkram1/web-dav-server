package com.milton.webdavserver.model;

import io.milton.annotations.*;

import java.util.Date;


public class Folder {
    private final String name;
    private Date createdDate;

    public Folder(String name) {
        this.name = name;
    }

    @Name
    public String getName() {
        return name;
    }

    @UniqueId
    public String getUniqueId() {
        return name;
    }

    @CreatedDate
    public Date getCreatedDate() {
        return createdDate;
    }
}
