package com.milton.webdavserver.controllers;

import com.milton.webdavserver.model.File;
import com.milton.webdavserver.model.Folder;
import io.milton.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.*;


@ResourceController
public class HelloWorldController {
    private static Logger logger = LoggerFactory.getLogger(HelloWorldController.class);
    private static HashMap<String, File> fileHashMap = new HashMap<>();

    static {
        byte[] bytes = "Hello World".getBytes(StandardCharsets.UTF_8);
        fileHashMap.put("file1.txt", new File("file1.txt", bytes));
        fileHashMap.put("file2.txt", new File("file2.txt", bytes));
    }


    @Root
    public HelloWorldController getRoot() {
        return this;
    }

    @ChildrenOf
    public List<Folder> getWebDavFolders(HelloWorldController root) {
        List<Folder> webDavFolders = new ArrayList<Folder>();
        webDavFolders.add(new Folder("folder1"));
        webDavFolders.add(new Folder("folder2"));
        return webDavFolders;
    }

    @ChildrenOf
    public Collection<File> getWebDavFiles(Folder webDavFolder) {
        return fileHashMap.values();
    }

    @Get
    public InputStream getChild(File webDavFile) {
        return new ByteArrayInputStream(fileHashMap.get(webDavFile.getName()).getBytes());
    }

    @PutChild
    public void putChild(File parent, String name, byte[] bytes) {
        if (name != null) {
            fileHashMap.put(name, new File(name, bytes));
        } else {
            parent.setBytes(bytes);
            fileHashMap.put(parent.getName(), parent);
        }
    }

    @Delete
    public void delete(File webDavFile) {
        fileHashMap.remove(webDavFile.getName());
    }

    @Name
    public String getWebDavFile(File webDavFile) {
        return webDavFile.getName();
    }

    @DisplayName
    public String getDisplayName(File webDavFile) {
        return webDavFile.getName();
    }

    @UniqueId
    public String getUniqueId(File webDavFile) {
        return webDavFile.getName();
    }

    @ModifiedDate
    public Date getModifiedDate(File webDavFile) {
        return webDavFile.getModifiedDate();
    }

    @CreatedDate
    public Date getCreatedDate(File webDavFile) {
        return webDavFile.getCreatedDate();
    }

}
