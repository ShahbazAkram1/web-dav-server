package com.milton.webdavserver.model;

import java.util.ArrayList;
import java.util.List;

public class WebDavFolder {
    private Long id;
    private String name;
    private List<WebDavFolder> childFolders;
    private List<WebDavFile> childFiles;

    public WebDavFolder(Long id, String name) {
        this.id = id;
        this.name = name;
        this.childFolders = new ArrayList<>();
        this.childFiles = new ArrayList<>();
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

    public List<WebDavFolder> getChildFolders() {
        return childFolders;
    }

    public void addChildFolder(WebDavFolder childFolder) {
        this.childFolders.add(childFolder);
    }

    public List<WebDavFile> getChildFiles() {
        return childFiles;
    }

    public void addChildFile(WebDavFile childFile) {
        this.childFiles.add(childFile);
    }
}
