package com.web.api;

import com.service.FileAuditService;
import com.shared.dto.FileAuditDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/api")
public class FileAuditController {

    @Autowired
    private FileAuditService fileAuditService;

    @GetMapping(value = "/fileAuditNames")
    private ResponseEntity<List<String>> getFileAuditNames() {
        final List<FileAuditDto> fileAuditDtos = fileAuditService.getFileAuditNames();
        final List<String> fileNames = fileAuditDtos.stream().map(t -> t.name).collect(Collectors.toList());
        return new ResponseEntity<>(fileNames, HttpStatus.OK);
    }
}