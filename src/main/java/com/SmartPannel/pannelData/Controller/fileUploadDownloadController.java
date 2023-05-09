package com.SmartPannel.pannelData.Controller;

import com.SmartPannel.fileUtil.fileDownload;
import com.SmartPannel.fileUtil.uploadUtil;
import com.SmartPannel.userData.Model.fileUpload;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.security.RolesAllowed;
import java.io.FileDescriptor;
import java.io.IOException;

@RestController
@RequestMapping
@CrossOrigin("*")
public class fileUploadDownloadController {
    @PostMapping("fileUpload")
    @RolesAllowed("ROLE_ADMIN")
    public ResponseEntity<fileUpload> FileUpload (@RequestParam("file")MultipartFile multipartFile) throws IOException {
        String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
        long size = multipartFile.getSize();
        String fileCode = uploadUtil.saveFile(fileName,multipartFile);
        fileUpload response = new fileUpload();
        response.setFilename(fileName);
        response.setSize(size);
        response.setDownloadUri("/downloadFile/"+fileCode);
        return  new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/downloadFile/{filecode}")
    @RolesAllowed({"ROLE_CUSTOMER", "ROLE_DISTRIBUTOR","ROLE_ADMIN"})
    public
    ResponseEntity<?> download(@PathVariable("filecode") String fileCode){
        fileDownload download = new fileDownload();

        Resource resource = null;
        try{
            resource = download.getFileResource(fileCode);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }if (resource == null){
            return new ResponseEntity<>("File not found",HttpStatus.NOT_FOUND);
        }

        String contentType = "application/octet-stream";
        String headerValue = "attachment; filename=\""+ resource.getFilename()+"\"";
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION,headerValue).body(resource);
    }
}