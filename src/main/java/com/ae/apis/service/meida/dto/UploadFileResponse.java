package com.ae.apis.service.meida.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UploadFileResponse {
	private String fileName;
	private String fileDownloadUri;
	private String fileType;
	private Long size;
}
