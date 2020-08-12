package com.shared.dto;

import java.util.Date;

public class FileDto {

    public String id;
    public String name;
    public String type;
    public Date uploadDateTime;
    public String url;
    public Integer sizeInKb;

    public FileDto(String id, String name, String type, Date uploadDateTime, String url, Integer sizeInKb) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.uploadDateTime = uploadDateTime;
        this.url = url;
        this.sizeInKb = sizeInKb;
    }
}
