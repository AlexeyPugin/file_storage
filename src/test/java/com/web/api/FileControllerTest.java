package com.web.api;

import com.service.FileService;
import com.web.ResponseMessage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class FileControllerTest {

    @InjectMocks
    FileController fileController;

    @Mock
    FileService fileService;

    @Test
    public void fileUploadTest() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        MockMultipartFile file = new MockMultipartFile("filename", "filename.txt", "text/plain", "text".getBytes());
        ResponseEntity<ResponseMessage> responseEntity = fileController.fileUpload(file);

        assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
    }
}