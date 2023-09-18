package com.milton.webdavserver.database;

import com.milton.webdavserver.model.WebDavFile;
import com.milton.webdavserver.model.WebDavFolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.*;

public class DatabaseService {
    private Connection connection;

    private static final Logger logger = LoggerFactory.getLogger(DatabaseService.class);

    public DatabaseService() throws SQLException {
        this.connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/webdav_server", "root", "Dell0312");
        logger.info("Database connected successfully!");
    }

    public List<WebDavFolder> getWebDavFolders() throws SQLException {
        List<WebDavFolder> webDavFolders = new ArrayList<>();

        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM webdav_folders");

        while (resultSet.next()) {
            webDavFolders.add(new WebDavFolder(resultSet.getLong("id"), resultSet.getString("name")));
        }

        statement.close();
        resultSet.close();

        return webDavFolders;
    }

    public Collection<WebDavFile> getWebDavFiles(WebDavFolder webDavFolder) throws SQLException {
        Collection<WebDavFile> webDavFiles = new ArrayList<>();

        PreparedStatement statement = connection.prepareStatement("SELECT * FROM webdav_files WHERE folder_id = ?");
        statement.setLong(1, webDavFolder.getId());

        ResultSet resultSet = statement.executeQuery();

        while (resultSet.next()) {
            webDavFiles.add(new WebDavFile(resultSet.getLong("id"), resultSet.getString("name"), resultSet.getBytes("contents"), webDavFolder));
        }

        statement.close();
        resultSet.close();

        return webDavFiles;
    }

    public byte[] getWebDavFileContents(WebDavFile webDavFile) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("SELECT contents FROM webdav_files WHERE id = ?");
        statement.setLong(1, webDavFile.getId());

        ResultSet resultSet = statement.executeQuery();

        if (resultSet.next()) {
            return resultSet.getBytes("contents");
        } else {
            return null;
        }
    }

    public void createWebDavFile(WebDavFolder parent, String name, byte[] bytes) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("INSERT INTO webdav_files (folder_id, name, contents) VALUES (?, ?, ?)");
        statement.setLong(1, parent.getId());
        statement.setString(2, name);
        statement.setBytes(3, bytes);

        statement.executeUpdate();

        statement.close();
    }

    public void updateWebDavFile(WebDavFile webDavFile, byte[] bytes) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("UPDATE webdav_files SET contents = ? WHERE id = ?");
        statement.setBytes(1, bytes);
        statement.setLong(2, webDavFile.getId());

        statement.executeUpdate();

        statement.close();
    }



    public void deleteWebDavFile(WebDavFile webDavFile) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("DELETE FROM webdav_files WHERE id = ?");
        statement.setLong(1, webDavFile.getId());

        statement.executeUpdate();

        statement.close();
    }

    public void close() throws SQLException {
        connection.close();
    }
}
