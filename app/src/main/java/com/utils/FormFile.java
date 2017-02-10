package com.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

/**
 * 上传文件
 */
public class FormFile {
    /* 上传文件的数据 */
    private byte[] data;
    private InputStream inStream;
    private File file;

    /* 文件名称 */
    private String fileName;

    /* 请求参数名称*/
    private String parameterName;

    /* 内容类型 */
    private String contentType = "application/octet-stream";

    public FormFile(String filName, byte[] data, String parameterName, String contentType) {
        this.data = data;
        this.fileName = filName;
        this.parameterName = parameterName;

        if (contentType != null){
            this.contentType = contentType;
        }
    }

    public FormFile(String filName, File file, String parameterName, String contentType) {
        this.fileName = filName;
        this.parameterName = parameterName;
        this.file = file;

        try {
            this.inStream = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            System.out.println("" + e.toString());
            e.printStackTrace();
        }

        if (contentType != null) {
            this.contentType = contentType;
        }
    }

    public File getFile() {
        return file;
    }

    public InputStream getInStream() {
        return inStream;
    }

    public byte[] getData() {
        return data;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getParameterName() {
        return parameterName;
    }

    public void setParameterName(String parameterName) {
        this.parameterName = parameterName;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }
}
