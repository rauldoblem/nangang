package com.taiji.emp.base.util;

import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class FileUtil {

    private static Random randomNum = new Random(System.currentTimeMillis() % 10000);

    /**
     * 检测文件名是否合法
     *
     * @param text
     * @return
     */
    public static boolean isValidName(String text) {
        Pattern pattern = Pattern.compile("# Match a valid Windows filename (unspecified file system).          \n"
                        + "^                                # Anchor to start of string.        \n"
                        + "(?!                              # Assert filename is not: CON, PRN, \n"
                        + "  (?:                            # AUX, NUL, COM1, COM2, COM3, COM4, \n"
                        + "    CON|PRN|AUX|NUL|             # COM5, COM6, COM7, COM8, COM9,     \n"
                        + "    COM[1-9]|LPT[1-9]            # LPT1, LPT2, LPT3, LPT4, LPT5,     \n"
                        + "  )                              # LPT6, LPT7, LPT8, and LPT9...     \n"
                        + "  (?:\\.[^.]*)?                  # followed by optional extension    \n"
                        + "  $                              # and end of string                 \n"
                        + ")                                # End negative lookahead assertion. \n"
                        + "[^<>:\"/\\\\|?*\\x00-\\x1F]*     # Zero or more valid filename chars.\n"
                        + "[^<>:\"/\\\\|?*\\x00-\\x1F\\ .]  # Last char is not a space or dot.  \n"
                        + "$                                # Anchor to end of string.            ",
                Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE | Pattern.COMMENTS);
        Matcher matcher = pattern.matcher(text);
        boolean isMatch = matcher.matches();
        return isMatch;
    }

    /**
     * 根据文件路径取得文件名
     *
     * @param filePath
     * @return
     */
    public static String getFileName(String filePath) {

        if (filePath == null) {
            return null;
        }

        int lastPosBackslash = -1;
        int lastPosSlash = -1;
        String ret = null;

        if ((lastPosSlash = filePath.lastIndexOf("/")) > 0) {
            ret = filePath.substring(lastPosSlash + 1);
        } else if ((lastPosBackslash = filePath.lastIndexOf("\\")) > 0) {
            ret = filePath.substring(lastPosBackslash + 1);
        } else {
            ret = filePath;
        }

        return ret;
    }

    /**
     * 根据文件名取得文件扩展名
     *
     * @param fileName
     * @return
     */
    public static String getFileExtension(String fileName) {

        if (fileName == null) {
            return null;
        }

        int lastPosDot = -1;
        String ret = null;

        if ((lastPosDot = fileName.lastIndexOf(".")) > 0) {
            ret = fileName.substring(lastPosDot + 1);
        }

        return ret;
    }

    /**
     * 生成随机文件名
     *
     * @param fileExt
     * @return
     */
    public static String generateRandomFileName(String fileExt) {

        String ret = System.currentTimeMillis() + "_" + randomNum.nextInt();

        if (fileExt != null && fileExt.length() != 0) {
            ret += "." + fileExt;
        }

        return ret;
    }

}
