package com.SmartPannel.fileUtil;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


public class fileDownload {
    private Path foundFile;
 public Resource getFileResource(String fileCode) throws IOException {

     Path uploadDir = Paths.get("Files-Upload");
     Files.list(uploadDir).forEach(file ->{
         if(file.getFileName().toString().startsWith(fileCode)){
            foundFile = file;
            return;
         }
     });
     if(foundFile!=null){
         return new UrlResource(foundFile.toUri());
     }
     return null;
 }
}
