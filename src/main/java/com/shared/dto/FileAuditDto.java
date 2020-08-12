package com.shared.dto;

import java.util.Date;

public class FileAuditDto {

    public String id;
    public String name;
    public String type;
    public Date downloadDateTime;
    public String url;
    public Integer sizeInKb;

    public FileAuditDto(String id, String name, String type, Date downloadDateTime, String url, Integer sizeInKb) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.downloadDateTime = downloadDateTime;
        this.url = url;
        this.sizeInKb = sizeInKb;
    }
}
