package com.okc.utils;

import com.github.tobato.fastdfs.domain.fdfs.StorePath;
import com.github.tobato.fastdfs.domain.proto.storage.DownloadByteArray;
import com.github.tobato.fastdfs.domain.upload.FastImageFile;
import com.github.tobato.fastdfs.exception.FdfsServerException;
import com.github.tobato.fastdfs.exception.FdfsUnsupportStorePathException;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnails;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

/**
 * fastdfs文件上传工具类
 */
@Component
@Slf4j
@ConfigurationProperties(prefix = "fdfs")
@Data
public class FastDFSClient {

    private FastFileStorageClient storageClient;
    private String absoluteAddress;
    private String tempPath;


    @Autowired
    public FastDFSClient(FastFileStorageClient storageClient) {
        this.storageClient = storageClient;
    }

    /**
     * 上传文件
     *
     * @param file 文件对象
     * @return 文件访问地址
     */
    public String uploadFile(MultipartFile file) throws IOException {
        StorePath storePath = storageClient.uploadFile(
                file.getInputStream(),
                file.getSize(),
                FilenameUtils.getExtension(file.getOriginalFilename()),
                null);
        String photoUrl = getResAccessUrl(storePath);
        // TODO 入库
        return photoUrl;
    }

    /**
     * 图片上传
     *
     * @param file
     * @return
     * @throws IOException
     */
    public String uploadImage(MultipartFile file) throws IOException {

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        Thumbnails.of(file.getInputStream()).scale(1f).outputQuality(0.25f).toOutputStream(outputStream);
        byte[] buff = outputStream.toByteArray();
        ByteArrayInputStream inputStream = new ByteArrayInputStream(buff);
        StorePath storePath = storageClient.uploadImage(new FastImageFile.Builder()
                .withFile(
                        inputStream,
                        buff.length,
                        FilenameUtils.getExtension(file.getOriginalFilename()))
                .build());

        String photoUrl = getResAccessUrl(storePath);
        // TODO 入库
        return photoUrl;
    }

    /**
     * 将一段字符串生成一个文件上传
     *
     * @param buff 文件内容
     */
    public String uploadFile(byte[] buff, String fileExtension) {
        ByteArrayInputStream stream = new ByteArrayInputStream(buff);
        StorePath storePath = storageClient.uploadFile(stream, buff.length, fileExtension, null);
        return getResAccessUrl(storePath);
    }

    /**
     * 封装图片完整URL地址
     *
     * @param storePath 图片地址
     * @return
     */
    private String getResAccessUrl(StorePath storePath) {
        return absoluteAddress + storePath.getFullPath();
    }

    /**
     * 删除文件
     */
    @Transactional(rollbackFor = Exception.class)
    public void deleteFiles(List<String> fileUrlList) {

        try {
            for (String url : fileUrlList) {
                StorePath storePath = StorePath.parseFromUrl(url);
                storageClient.deleteFile(storePath.getGroup(), storePath.getPath());
            }
        } catch (FdfsUnsupportStorePathException | FdfsServerException e) {
            log.warn(e.getMessage());
        }
    }

    /**
     * 删除文件
     */
    @Transactional(rollbackFor = Exception.class)
    public void deleteFile(String fileUrl) {

        try {
            StorePath storePath = StorePath.parseFromUrl(fileUrl);
            storageClient.deleteFile(storePath.getGroup(), storePath.getPath());
            // TODO 删库
        } catch (FdfsUnsupportStorePathException | FdfsServerException e) {
            log.warn(e.getMessage());
        }
    }


    /**
     * 下载文件
     *
     * @param fileUrl 文件url
     * @return
     */
    public byte[] download(String fileUrl) {
        String group = fileUrl.substring(0, fileUrl.indexOf("/"));
        String path = fileUrl.substring(fileUrl.indexOf("/") + 1);
        return storageClient.downloadFile(group, path, new DownloadByteArray());
    }
}