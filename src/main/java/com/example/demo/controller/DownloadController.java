package com.example.demo.controller;

import com.example.demo.service.DownloadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
public class DownloadController {

	@Autowired
	private DownloadService service;

	@RequestMapping(value = "/download", method = RequestMethod.GET)
	@ResponseBody
	public String getAWSS3Folder(@RequestBody List<String> object)  {

		service.downloadFiles(object);

		/*for (S3ObjectSummary objectSummary : summaries) {

			System.out.println(objectSummary.getKey());

			downloadFile(objectSummary.getKey());
		}*/
		return null;
	}

	@GetMapping("/list")
	 public void getFiles(){

			service.getList();
	 }


}




