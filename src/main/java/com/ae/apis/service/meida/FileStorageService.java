package com.ae.apis.service.meida;

import com.ae.apis.service.meida.dto.UploadFileResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


public interface FileStorageService {
	UploadFileResponse storeFile(MultipartFile file);
	UploadFileResponse storeImage(MultipartFile file);
	void deleteFile(String link);
	List<?> storeFiles(MultipartFile[] files);
}
