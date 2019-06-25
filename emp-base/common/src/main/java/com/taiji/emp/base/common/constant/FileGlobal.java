package com.taiji.emp.base.common.constant;

import java.util.HashMap;
import java.util.Map;

public class FileGlobal {

    public static final String DOC_PIC_TYPE = "picture";  //图片类型
    public static final String DOC_AUDIO_TYPE = "audio"; //音频类型
    public static final String DOC_VIDEO_TYPE = "video"; //视频类型
    public static final String DOC_WORD_TYPE = "word"; //文档类型
    public static final String DOC_OTHERS_TYPE = "others"; //其他类型

    public static final Map<String,String> DOC_TYPE_MAP = new HashMap<String,String>();
    static {

        //图片类型
        DOC_TYPE_MAP.put("bmp",DOC_PIC_TYPE);
        DOC_TYPE_MAP.put("jpg",DOC_PIC_TYPE);
        DOC_TYPE_MAP.put("png",DOC_PIC_TYPE);
        DOC_TYPE_MAP.put("jpeg",DOC_PIC_TYPE);
        DOC_TYPE_MAP.put("tiff",DOC_PIC_TYPE);
        DOC_TYPE_MAP.put("gif",DOC_PIC_TYPE);
        DOC_TYPE_MAP.put("pcx",DOC_PIC_TYPE);
        DOC_TYPE_MAP.put("tga",DOC_PIC_TYPE);
        DOC_TYPE_MAP.put("exif",DOC_PIC_TYPE);
        DOC_TYPE_MAP.put("fpx",DOC_PIC_TYPE);
        DOC_TYPE_MAP.put("svg",DOC_PIC_TYPE);
        DOC_TYPE_MAP.put("psd",DOC_PIC_TYPE);
        DOC_TYPE_MAP.put("cdr",DOC_PIC_TYPE);
        DOC_TYPE_MAP.put("pcd",DOC_PIC_TYPE);
        DOC_TYPE_MAP.put("dxf",DOC_PIC_TYPE);
        DOC_TYPE_MAP.put("ufo",DOC_PIC_TYPE);
        DOC_TYPE_MAP.put("eps",DOC_PIC_TYPE);
        DOC_TYPE_MAP.put("ai",DOC_PIC_TYPE);
        DOC_TYPE_MAP.put("raw",DOC_PIC_TYPE);
        DOC_TYPE_MAP.put("wmf",DOC_PIC_TYPE);

        //音频类型
        DOC_TYPE_MAP.put("mp3",DOC_AUDIO_TYPE);
        DOC_TYPE_MAP.put("wma",DOC_AUDIO_TYPE);
        DOC_TYPE_MAP.put("wav",DOC_AUDIO_TYPE);
        DOC_TYPE_MAP.put("mod",DOC_AUDIO_TYPE);
        DOC_TYPE_MAP.put("ra",DOC_AUDIO_TYPE);
        DOC_TYPE_MAP.put("cd",DOC_AUDIO_TYPE);
        DOC_TYPE_MAP.put("md",DOC_AUDIO_TYPE);
//        DOC_TYPE_MAP.put("asf",DOC_AUDIO_TYPE);
        DOC_TYPE_MAP.put("aac",DOC_AUDIO_TYPE);
        DOC_TYPE_MAP.put("vqf",DOC_AUDIO_TYPE);
        DOC_TYPE_MAP.put("ape",DOC_AUDIO_TYPE);
        DOC_TYPE_MAP.put("mid",DOC_AUDIO_TYPE);
        DOC_TYPE_MAP.put("ogg",DOC_AUDIO_TYPE);
        DOC_TYPE_MAP.put("m4a",DOC_AUDIO_TYPE);

        //视频类型
        DOC_TYPE_MAP.put("mp4",DOC_VIDEO_TYPE);
        DOC_TYPE_MAP.put("avi",DOC_VIDEO_TYPE);
        DOC_TYPE_MAP.put("mov",DOC_VIDEO_TYPE);
        DOC_TYPE_MAP.put("wmv",DOC_VIDEO_TYPE);
//        DOC_TYPE_MAP.put("asf",DOC_VIDEO_TYPE);
        DOC_TYPE_MAP.put("navi",DOC_VIDEO_TYPE);
        DOC_TYPE_MAP.put("3gp",DOC_VIDEO_TYPE);
        DOC_TYPE_MAP.put("mkv",DOC_VIDEO_TYPE);
        DOC_TYPE_MAP.put("f4v",DOC_VIDEO_TYPE);
        DOC_TYPE_MAP.put("rmvb",DOC_VIDEO_TYPE);
        DOC_TYPE_MAP.put("webm",DOC_VIDEO_TYPE);

        //文档类型
        DOC_TYPE_MAP.put("txt",DOC_WORD_TYPE);
        DOC_TYPE_MAP.put("doc",DOC_WORD_TYPE);
        DOC_TYPE_MAP.put("docx",DOC_WORD_TYPE);
        DOC_TYPE_MAP.put("xls",DOC_WORD_TYPE);
        DOC_TYPE_MAP.put("htm",DOC_WORD_TYPE);
        DOC_TYPE_MAP.put("html",DOC_WORD_TYPE);
        DOC_TYPE_MAP.put("jsp",DOC_WORD_TYPE);
        DOC_TYPE_MAP.put("rtf",DOC_WORD_TYPE);
        DOC_TYPE_MAP.put("wpd",DOC_WORD_TYPE);
        DOC_TYPE_MAP.put("pdf",DOC_WORD_TYPE);
        DOC_TYPE_MAP.put("ppt",DOC_WORD_TYPE);

        //以上都没有则为 其他类型
    }
}
