package com.SmartPannel.pannelData.Controller;

import com.SmartPannel.fileUtil.uploadUtil;
import com.SmartPannel.userData.Model.fileUpload;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.security.RolesAllowed;
import java.io.IOException;

@RestController
public class fileUploadController {
    @PostMapping("fileUpload")
    @RolesAllowed("ROLE_ADMIN")
    public ResponseEntity<fileUpload> FileUpload (@RequestParam("file")MultipartFile multipartFile) throws IOException {
        String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
        long size = multipartFile.getSize();
        uploadUtil.saveFile(fileName,multipartFile);
        fileUpload response = new fileUpload();
        response.setFilename(fileName);
        response.setSize(size);
        response.setDownloadUri("/downloadFile");
        return  new ResponseEntity<>(response, HttpStatus.OK);
    }
}
