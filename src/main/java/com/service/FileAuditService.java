package com.service;

import com.domain.FileAudit;
import com.repository.FileAuditRepository;
import com.shared.dto.FileAuditDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FileAuditService {

    @Autowired
    private FileAuditRepository fileAuditRepository;

    public List<FileAuditDto> getFileAuditNames() {
        final List<FileAudit> files = fileAuditRepository.findAll();
        return files.stream().map(FileAudit::toDto).collect(Collectors.toList());
    }
}



