package j2ee_project.service;

import jakarta.servlet.http.Part;

public class FileService {
    public static String getFileExtension(Part part) {
        String fileName = null;
        final String partHeader = part.getHeader("content-disposition");
        for (String content : partHeader.split(";")) {
            if (content.trim().startsWith("filename")) {
                fileName = content.substring(content.indexOf('=') + 1).trim().replace("\"", "");
                break;
            }
        }

        if(fileName == null) {
            return "";
        }

        int dotIndex = fileName.lastIndexOf(".");
        if (dotIndex > 0) {
            return fileName.substring(dotIndex + 1);
        }
        return "";
    }

    public static boolean isImage(Part part) {
        return part.getContentType().startsWith("image/");
    }
}
