package com.domain;

import com.shared.dto.FileAuditDto;
import com.shared.dto.FileDto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "T_FILEAUDIT")
public class FileAudit {

    @Id
    @Column(nullable = false)
    private String id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String type;

    @Column(nullable = false)
    private Date downloadDateTime;

    @Column(nullable = false)
    private String url;

    @Column(nullable = false)
    private Integer sizeInKb;

    public FileAuditDto toDto() {
        return new FileAuditDto(id,
                name,
                type,
                downloadDateTime,
                url,
                sizeInKb);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getDownloadDateTime() {
        return downloadDateTime;
    }

    public void setDownloadDateTime(Date downloadDateTime) {
        this.downloadDateTime = downloadDateTime;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getSizeInKb() {
        return sizeInKb;
    }

    public void setSizeInKb(Integer sizeInKb) {
        this.sizeInKb = sizeInKb;
    }
}
