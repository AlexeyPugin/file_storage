package com.service;

import com.domain.File;
import com.domain.FileAudit;
import com.repository.FileAuditRepository;
import com.repository.FileRepository;
import com.shared.dto.FileDto;
import com.web.api.FileController;
import javassist.NotFoundException;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

@PropertySource(value = {"classpath:application.properties"})
@Service
public class FileService {

    private static final Integer BYTES_IN_KB = 1024;

    @Value("${upload.path}")
    private String filePathUpload;

    @Autowired
    private FileRepository fileRepository;
    @Autowired
    private FileAuditRepository fileAuditRepository;

    public List<FileDto> getFiles(final Optional<String> fileName, final Optional<Integer> minSizeKb, final Optional<Integer> maxSizeKb) {
        if (fileName.isPresent()) {
            if (minSizeKb.isPresent() && maxSizeKb.isPresent() && minSizeKb.get() <= maxSizeKb.get()) {
                final Optional<File> file = fileRepository.findOneByNameRange(fileName.get(), minSizeKb.get(), maxSizeKb.get());
                if (file.isPresent()) {
                    return new ArrayList<>(Collections.singleton(file.get().toDto()));
                }
            }
            final Optional<File> file = fileRepository.findOneByName(fileName.get());
            if (file.isPresent()) {
                return new ArrayList<>(Collections.singleton(file.get().toDto()));
            }
        }
        if (minSizeKb.isPresent() && maxSizeKb.isPresent() && minSizeKb.get() <= maxSizeKb.get()) {
            final List<File> files = fileRepository.findOneByRange(minSizeKb.get(), maxSizeKb.get());
            return files.stream().map(File::toDto).collect(Collectors.toList());
        }

        return fileRepository.findAll().stream().map(File::toDto).collect(Collectors.toList());
    }

    public Resource getFile(final UUID fileUuid) throws NotFoundException, MalformedURLException {
        final Optional<File> findFile = fileRepository.findById(fileUuid.toString());
        if (findFile.isPresent()) {
            final File file = findFile.get();

            final Path root = Paths.get(filePathUpload);
            final Path filePath = root.resolve(file.getName()).normalize();
            final Resource resource = new UrlResource(filePath.toUri());
            if (resource.exists()) {
                saveFileAudit(file);
                return resource;
            }
        }
        throw new NotFoundException("File " + fileUuid + " not found");
    }

    public void saveFile(final MultipartFile uploadFile) {
        if (!uploadFile.isEmpty() && uploadFile.getOriginalFilename() != null && !"".equals(uploadFile.getOriginalFilename())) {
            try {
                final String uploadFileName = uploadFile.getOriginalFilename();
                final String uploadFileType = uploadFile.getContentType();
                final Integer uploadFileSizeInKb = Math.toIntExact(uploadFile.getSize() / BYTES_IN_KB);

                final byte[] bytes = uploadFile.getBytes();
                final Path path = Paths.get(filePathUpload + uploadFileName);
                final java.io.File folder = new java.io.File(filePathUpload);
                if (!folder.exists()) {
                    folder.mkdir();
                }
                Files.write(path, bytes);

                final String fileUuid = UUID.randomUUID().toString();
                final String url = MvcUriComponentsBuilder
                        .fromMethodName(FileController.class, "getFile", fileUuid).build().toString();
                final File file = new File();
                file.setId(fileUuid);
                file.setName(uploadFileName);
                file.setUploadDateTime(new DateTime().toDate());
                file.setType(uploadFileType);
                file.setUrl(url);
                file.setSizeInKb(uploadFileSizeInKb);

                fileRepository.save(file);
            } catch (Exception e) {
                throw new RuntimeException("Could not upload the file. Error: " + e.getMessage());
            }
        }
    }

    public void updateFile(final MultipartFile uploadFile) {
        if (!uploadFile.isEmpty() && uploadFile.getOriginalFilename() != null && !"".equals(uploadFile.getOriginalFilename())) {
            try {
                final String uploadFileName = uploadFile.getOriginalFilename();

                final Optional<File> findFile = fileRepository.findOneByName(uploadFileName);
                if (findFile.isPresent()) {

                    final byte[] bytes = uploadFile.getBytes();
                    final Path root = Paths.get(filePathUpload);
                    final Path path = root.resolve(uploadFileName).normalize();

                    Files.delete(path);
                    Files.write(path, bytes);

                    final File file = findFile.get();
                    file.setUploadDateTime(new DateTime().toDate());
                    file.setSizeInKb(Math.toIntExact(uploadFile.getSize() / BYTES_IN_KB));

                    fileRepository.save(file);
                }
            } catch (Exception e) {
                throw new RuntimeException("Could not upload the file. Error: " + e.getMessage());
            }
        }
    }

    public void deleteFile(final UUID fileUuid) throws Exception {
        final Optional<File> file = fileRepository.findById(fileUuid.toString());
        if (file.isPresent()) {
            try {
                final Path root = Paths.get(filePathUpload);
                final Path path = root.resolve(file.get().getName()).normalize();
                Files.delete(path);

                fileRepository.delete(file.get());
            } catch (Exception e) {
                throw new Exception();
            }
        } else {
            throw new NotFoundException("File " + fileUuid + " not found");
        }
    }

    private void saveFileAudit(final File file) {
        final FileAudit fileAudit = new FileAudit();
        fileAudit.setId(file.getId());
        fileAudit.setName(file.getName());
        fileAudit.setType(file.getType());
        fileAudit.setDownloadDateTime(new DateTime().toDate());
        fileAudit.setUrl(file.getUrl());
        fileAudit.setSizeInKb(file.getSizeInKb());

        fileAuditRepository.save(fileAudit);
    }
}



