package com.hoioy.diamond.sys.service;

import com.hoioy.diamond.common.domain.TDFDomain;
import com.hoioy.diamond.common.exception.BaseException;
import com.hoioy.diamond.common.service.IBaseService;
import com.hoioy.diamond.sys.dto.FileStorageDTO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface IFileStorageService<D extends TDFDomain> extends IBaseService<FileStorageDTO, D> {
    String saveFile(MultipartFile file, String relativePath, String replacedFileName) throws BaseException, IOException;
}
