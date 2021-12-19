package com.example.demo.service;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class DownloadServiceImpl implements DownloadService {

    @Autowired
    private AmazonS3 amazonS3;

    @Value("${aws.s3.bucket}")
    private String s3bucket;
    @Value("${app.destination-dir}")
    private String path;

    @Override
    public void downloadFiles(List<String> files) {
        try {

            files.forEach((fileName -> {
                try {
                    filestoBinary(fileName);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }));


        } catch (AmazonServiceException e) {

            System.out.println(e.getErrorMessage());

        }
        System.out.println("Done!");
    }


    public void filestoBinary(String fileName) throws IOException {
        boolean exists = amazonS3.doesObjectExist(s3bucket, fileName);
        if (exists) {
            S3Object o = amazonS3.getObject(s3bucket, fileName);

            S3ObjectInputStream s3is = o.getObjectContent();

            File dir = new File(path + "/Altair-Fatura-Aliss");

            dir.mkdirs();
            FileOutputStream fos = null;
            try {
                fos = new FileOutputStream(new File(dir, fileName));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            /*String content cannot be directly written into
             * a file. It needs to be converted into bytes
             */

            byte[] read_buf = new byte[1024];
            int read_len = 0;
            while (true) {

                if (!((read_len = s3is.read(read_buf)) > 0)) break;

                fos.write(read_buf, 0, read_len);
            }
            s3is.close();
            fos.close();

        } else {
            System.out.println("Object \"" + s3bucket + "/" + fileName + "\" does not exist!");

        }
    }
    public void getList(){

        List<String> results = new ArrayList<String>();


        File[] files = new File("C:\\Users\\aysen\\Desktop\\Altair-Fatura-Alis").listFiles();

        //If this pathname does not denote a directory, then listFiles() returns null.

        for(File file : files)
        {
            if (file.isFile()) {
                System.out.println(file.getName());
                results.add(file.getName());
            }
        }


    }


}
