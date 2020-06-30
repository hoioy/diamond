package com.hoioy.jiayin.api;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.text.StrSpliter;
import cn.hutool.core.util.StrUtil;
import com.github.tobato.fastdfs.domain.fdfs.MetaData;
import com.github.tobato.fastdfs.domain.fdfs.StorePath;
import com.github.tobato.fastdfs.domain.fdfs.ThumbImageConfig;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;

@RestController
@RequestMapping("/jiayin/file")
public class FileController {
    protected static Logger LOGGER = LoggerFactory.getLogger(FileController.class);
    @Autowired
    protected FastFileStorageClient storageClient;

    @Autowired
    private ThumbImageConfig thumbImageConfig;

    @PostMapping("/upload")
    public List<String> fileUpload(MultipartHttpServletRequest request , @RequestParam(name = "paths",required = false) String paths) throws IOException {
        List<String> list = new ArrayList();
        Map<String, MultipartFile> map = request.getFileMap();
        if (map.size() > 0) {
            for (Map.Entry<String, MultipartFile> entry : map.entrySet()) {
                MultipartFile multipartFile = entry.getValue();
                Set<MetaData> metaDataSet = createMetaData();
                String suffix = multipartFile.getOriginalFilename().substring(multipartFile.getOriginalFilename().lastIndexOf(".") + 1);
                StorePath path = storageClient.uploadFile(multipartFile.getInputStream(), multipartFile.getSize(), suffix,
                        metaDataSet);
                //删除是为了减少服务器垃圾图片
                storageClient.deleteFile(path.getGroup(), path.getPath());
                String fullPath = path.getFullPath();
                list.add(fullPath);
            };
        }
        if (StrUtil.isNotBlank(paths)) {
            String[] split = paths.split(",");
            for (int i = 0; i < split.length; i++) {
                String s = split[i];
                List<String> groupPath = StrSpliter.split(s, '/', 2, true, true);
                storageClient.deleteFile(groupPath.get(0), groupPath.get(1));
            }
        }
        return list;
    }
    private Set<MetaData> createMetaData() {
        Set<MetaData> metaDataSet = new HashSet<>();
        metaDataSet.add(new MetaData("app", "jiayin"));
        LocalDateTime now = LocalDateTime.now();
        String today= DateUtil.today();
        metaDataSet.add(new MetaData("createDate", today));
        return metaDataSet;
    }

}
