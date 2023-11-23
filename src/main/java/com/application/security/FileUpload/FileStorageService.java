package com.application.security.FileUpload;

import com.application.security.FileUpload.Exception.FileStorageException;
import com.application.security.FileUpload.Exception.MyFileNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class FileStorageService {
    private final Path fileStorageLocation;

    @Autowired
    public FileStorageService(FileStorageProperties fileStorageProperties) {
        this.fileStorageLocation = Paths.get(fileStorageProperties.getUploadDir()).toAbsolutePath().normalize();

        try {
            Files.createDirectories(this.fileStorageLocation);

        }catch(Exception ex) {
            throw new FileStorageException("Could not create the directory to upload");
        }
    }

    //	function to store the file
    public String storeFile(MultipartFile file,String imgName,String uploadDir) {
        try {
            Path uplaodPath= Paths.get(this.fileStorageLocation.toString()+"\\"+uploadDir);
            if(!Files.exists(uplaodPath)){
                Files.createDirectories(uplaodPath);
            }

            Path targetLocation = uplaodPath.resolve(imgName);
            Files.copy(file.getInputStream(), targetLocation,StandardCopyOption.REPLACE_EXISTING);

            return imgName;
        }catch(IOException ex) {
            throw new FileStorageException("Could not store file"+imgName + ". Please try again!",ex);
        }
    }


    //	function to load the file
    public Resource loadFileAsResource(String fileName,String fileDir) {
        try {
            Path filePath=Paths.get(this.fileStorageLocation.toString()+"\\"+fileDir).resolve(fileName).normalize();

            Resource resource = new UrlResource(filePath.toUri());

            if(resource.exists()) {
                return resource;
            }
            if(!resource.exists()){
                Path noPicFilePath=Paths.get(this.fileStorageLocation.toString()+"\\"+fileDir).resolve("0.jpg").normalize();
                return new UrlResource(noPicFilePath.toUri());
            }
            else {
                throw new MyFileNotFoundException("File not found " + fileName);
            }
        }catch(MalformedURLException ex) {
            throw new MyFileNotFoundException("File not found " + fileName);
        }
    }

    public List<String> getMultipleFiles(String fileDir){
        File folder= new File(this.fileStorageLocation.toString()+"\\"+fileDir);
        File[] files=folder.listFiles();

        List<String> list=new ArrayList<>();
        for(File file:files){
            if(file.isFile()){
                list.add(file.getName());
            }
        }
        return list;
    }

    public boolean deleteDetailImage(String fileDir) {
        Path path = FileSystems.getDefault().getPath(this.fileStorageLocation.toString()+"\\"+fileDir);
        try {
            Files.deleteIfExists(path);
            return true;
        } catch (IOException x) {
            System.err.println(x);
        }
        return false;
    }
}
