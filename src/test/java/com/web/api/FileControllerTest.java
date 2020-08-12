package com.web.api;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMultipartHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.request.RequestPostProcessor;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ContextConfiguration()
@WebMvcTest()
@RunWith(SpringJUnit4ClassRunner.class)
public class FileControllerTest {

    @Autowired
    MockMvc mockMvc;

    //doesn't work
    @Test
    public void fileUploadTest() throws Exception {

        MockMultipartFile file = new MockMultipartFile("filename", "filename.txt", "text/plain", "text".getBytes());

        MockMultipartHttpServletRequestBuilder builder = MockMvcRequestBuilders.multipart("/api/files/upload");
        builder.with(new RequestPostProcessor() {
            @Override
            public MockHttpServletRequest postProcessRequest(MockHttpServletRequest request) {
                request.setMethod("PUT");
                return request;
            }
        });
        mockMvc.perform(builder
                .file(file))
                .andExpect(status().is(200));
    }
}