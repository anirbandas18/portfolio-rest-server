package com.teenthofabud.portfolio.controller.test;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.cadence.upload.model.FileModel;
import com.cadence.upload.model.FileUploadMetadataModel;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.teenthofabud.portfolio.controller.FreelancerController;
import com.teenthofabud.portfolio.service.FreelancerService;

@RunWith(SpringRunner.class)
@WebMvcTest(FreelancerController.class)
public class FreelancerControllerTest {

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	public FreelancerService freelancerService;
	
	public void test( ) {
		List<FileModel> filesInBaseDir = fileModelDataset();
		FileUploadMetadataModel uploadMetadata = new FileUploadMetadataModel();
		uploadMetadata.setFilesInBaseDir(filesInBaseDir);
		uploadMetadata.setFileChunkSize(testModel.getFileChunkSize());
		String jsonString = objectMapper.writeValueAsString(uploadMetadata);
		when(uploadService.getCompletelyUploadedFiles(testModel.getBaseDir())).thenReturn(filesInBaseDir);
		mockMvc.perform(get("/upload/metadata/" + testModel.getBaseDir())).andDo(print()).andExpect(status().isOk())
				.andExpect(content().json(jsonString));
	}
}
