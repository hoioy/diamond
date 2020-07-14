package com.hoioy.diamond.sys.service.impl;

import com.hoioy.diamond.common.base.BaseServiceImpl;
import com.hoioy.diamond.common.dto.PageDTO;
import com.hoioy.diamond.common.exception.BaseException;
import com.hoioy.diamond.sys.domain.FileStorage;
import com.hoioy.diamond.sys.dto.FileStorageDTO;
import com.hoioy.diamond.sys.exception.SysException;
import com.hoioy.diamond.sys.mapper.FileStorageMapper;
import com.hoioy.diamond.sys.service.IFileStorageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

@Service
public class FileStorageServiceImpl extends BaseServiceImpl<FileStorageMapper, FileStorage, FileStorageDTO> implements IFileStorageService<FileStorage> {

    @Value("${base.sys.file-storage.root-path}")
    public String fileStorageRootPath;

    @Override
    public String getFileStorageRootPath() {
        return fileStorageRootPath;
    }

    @Override
    public FileStorageDTO saveFile(MultipartFile file, String relativePath, String replacedFileName) throws BaseException, IOException {
        if (file == null || file.isEmpty()) {
            throw new SysException("文件不可以为null");
        }
        String filename = StringUtils.cleanPath(file.getOriginalFilename());
        if (filename.contains("..")) {
            // TODO 安全性校验 zhaozhao
            throw new SysException("无法在当前目录之外存储具有相对路径的文件 " + filename);
        }
        //1. 存储文件到服务器
        // 获取文件的扩展名
        String extensionName = filename.substring(filename.lastIndexOf(".") + 1);
        Path path = Paths.get(fileStorageRootPath + relativePath);
        Files.createDirectories(path);
        Files.copy(file.getInputStream(), path.resolve(replacedFileName + "." + extensionName), StandardCopyOption.REPLACE_EXISTING);
        //2. 文件元数据存储到数据库
        FileStorageDTO dto = new FileStorageDTO();
        dto.setFileName(replacedFileName);
        dto.setExtension(extensionName);
        dto.setRelativePath(relativePath);
        return super.create(dto);
    }

    @Override
    public boolean removeById(String id) throws BaseException {
        FileStorageDTO dtoOptional = findById(id);
        if (dtoOptional != null) {
            FileStorageDTO dto = dtoOptional;
            Path path = Paths.get(fileStorageRootPath + dto.getRelativePath()).resolve(dto.getFileName() + "." + dto.getExtension());
            try {
                FileSystemUtils.deleteRecursively(path);
                return super.removeById(id);
            } catch (IOException e) {
                e.printStackTrace();
                log.error(e.getLocalizedMessage());
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean removeByIds(List<String> ids) throws BaseException {
        ids.forEach(id -> {
            removeById(id);
        });
        return true;
    }

    @Override
    public PageDTO getPage(PageDTO pageDTO) {
        return null;
    }
}
