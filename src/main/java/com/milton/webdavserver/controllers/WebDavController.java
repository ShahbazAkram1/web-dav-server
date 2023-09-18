package com.milton.webdavserver.controllers;

import com.milton.webdavserver.database.DatabaseService;
import com.milton.webdavserver.model.WebDavFile;
import com.milton.webdavserver.model.WebDavFolder;
import io.milton.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.*;

@ResourceController
public class WebDavController {
    private static final Logger logger = LoggerFactory.getLogger(WebDavController.class);
    private static final DatabaseService databaseService;

    static {
        try {
            databaseService = new DatabaseService();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Root
    public WebDavController getRoot() {
        return this;
    }

    @ChildrenOf
    public List<WebDavFolder> getWebDavFolders(WebDavController root) throws SQLException {
        List<WebDavFolder> webDavFolders = new ArrayList<>();
        for (WebDavFolder webDavFolder : databaseService.getWebDavFolders()) {
            webDavFolders.add(webDavFolder);
        }
        return webDavFolders;
    }

    @ChildrenOf
    public Collection<WebDavFile> getWebDavFiles(WebDavFolder webDavFolder) throws SQLException {
        Collection<WebDavFile> webDavFiles = new ArrayList<>();
        webDavFiles.addAll(databaseService.getWebDavFiles(webDavFolder));
        return webDavFiles;
    }

    @Get
    public InputStream getChild(WebDavFile webDavFile) throws SQLException {
        return new ByteArrayInputStream(databaseService.getWebDavFileContents(webDavFile));
    }

    @Delete
    public void delete(WebDavFile webDavFile) throws SQLException {
        databaseService.deleteWebDavFile(webDavFile);
    }

    @Name
    public String getWebDavFile(WebDavFile webDavFile) {
        return webDavFile.getName();
    }

    @DisplayName
    public String getDisplayName(WebDavFile webDavFile) {
        return webDavFile.getName();
    }

    @UniqueId
    public String getUniqueId(WebDavFile webDavFile) {
        return webDavFile.getName();
    }
}
