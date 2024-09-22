package com.contest.grass.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback(value = false)
class FileControllerTest {

    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    private FileService fileService;

    @Test
    void testFile() throws IOException {
        MultipartFile file = new MockMultipartFile("testfile.png", "testfile.png", "image/png", "test data".getBytes());

        Image storedImage = fileService.storeFile(file);

        System.out.println("Stored image: " + storedImage);

        List<Image> images = imageRepository.findAll();
        images.forEach(img -> System.out.println("Image in DB: " + img));

        assertNotNull(storedImage);
        assertEquals("testfile.png", storedImage.getFilename());
        assertEquals("image/png", storedImage.getFiletype());
        assertTrue(Arrays.equals("test data".getBytes(), storedImage.getFile_data()), "배열의 내용이 일치하지 않습니다.");
    }
}

