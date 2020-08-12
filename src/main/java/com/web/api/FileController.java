package com.web.api;

import com.service.FileService;
import com.shared.dto.FileDto;
import com.web.ResponseMessage;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.MalformedURLException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping(path = "/api")
public class FileController {

    @Autowired
    private FileService fileService;

    @GetMapping(value = "/files")
    private ResponseEntity<List<FileDto>> getFiles(@RequestParam(value = "fileName") final Optional<String> fileName,
                                                   @RequestParam(value = "minSizeKb") final Optional<Integer> minSizeKb,
                                                   @RequestParam(value ="maxSizeKb") final Optional<Integer> maxSizeKb) {
        final List<FileDto> fileDtos = fileService.getFiles(fileName, minSizeKb, maxSizeKb);
        return new ResponseEntity<>(fileDtos, HttpStatus.OK);
    }

    @GetMapping(value = "/files/{fileUuid}")
    private ResponseEntity<Resource> getFile(@PathVariable UUID fileUuid) throws NotFoundException, MalformedURLException {
        final Resource file = fileService.getFile(fileUuid);
        if(file.exists()) {
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
                    .body(file);
        } else {
            throw new NotFoundException("File " + fileUuid + " not found");
        }
    }

    @PutMapping(value="/files/upload")
    public ResponseEntity<ResponseMessage> fileUpload(@RequestParam("file") MultipartFile file){
        String message;
        try {
            fileService.saveFile(file);
            message = "Uploaded the file successfully: " + file.getOriginalFilename();
            return ResponseEntity.status(HttpStatus.OK).body(null);
        } catch (Exception e) {
            message = file.getOriginalFilename() + " file has not been uploaded. Exception: " + e.getMessage();
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
        }
    }

    @PostMapping(value="/files/upload")
    public ResponseEntity<ResponseMessage> fileUpdate(@RequestParam("file") MultipartFile file){
        String message;
        try {
            fileService.updateFile(file);
            message = file.getOriginalFilename() + " file has been successfully updated";
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
        } catch (Exception e) {
            message = file.getOriginalFilename() + " file has not been uploaded. Exception: " + e.getMessage();
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
        }
    }

    @DeleteMapping(value = "/files/{fileUuid}")
    public ResponseEntity<ResponseMessage> deleteFile(@PathVariable UUID fileUuid) {
        String message;
        try {
            fileService.deleteFile(fileUuid);
            message = "File was deleted successfully: " + fileUuid;
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
        } catch (Exception e) {
            message = fileUuid + " file has not been deleted. Exception: " + e.getMessage();
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
        }
    }
}