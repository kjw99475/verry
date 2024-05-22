package org.fullstack.verry.utils;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.URLDecoder;
import java.nio.file.Files;
import java.util.UUID;

@Log4j2
public class FileUploadUtil {
    public static HttpServletRequest getRequest() {
        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        return attr.getRequest();
    }
    public static String uploadFolder = "";


    public static String saveFile(MultipartFile multipartFile, String path) {
        String org_file_name = multipartFile.getOriginalFilename();
        String ext = org_file_name.substring(org_file_name.lastIndexOf("."), org_file_name.length());

        UUID uuid = UUID.randomUUID();
        String[] uuids = uuid.toString().split("-");
        String newName = uuids[0];

        File save_file = new File(uploadFolder + path + "\\" + newName + ext);

        if(!save_file.exists()) {
            save_file.mkdir();
        }

        try {
            multipartFile.transferTo(save_file);
        } catch(IllegalStateException e) {
            e.printStackTrace();
        } catch(Exception e) {
            e.printStackTrace();
        }

        return newName + ext;
    }

    public static void deleteFile(String save_file_name, String path) {
        try {
            File file = new File(uploadFolder + path + "\\" + URLDecoder.decode(save_file_name, "UTF-8"));

            file.delete();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void download(HttpServletRequest req, HttpServletResponse resp, String orgFileName, String saveFileName, String path) {

        try {

            // 파일을 찾아 입력 스트림 생성
            File file = new File(uploadFolder+path+"\\", saveFileName);
            InputStream is = new FileInputStream(file);

            // 한글 파일명 깨짐 방지
            String client = req.getHeader("User-Agent");
            if(client.indexOf("WOW64") != -1) {
                orgFileName = new String(orgFileName.getBytes("UTF-8"), "ISO-8859-1");
            } else {
                orgFileName = new String(orgFileName.getBytes("KSC5601"), "ISO-8859-1");
            }


            // 파일 다운로드용 응답 헤더 설정
            resp.reset();
            resp.setContentType("application/octet-stream");
            resp.setHeader("Content-Disposition", "attachment; filename=\"" + orgFileName + "\"");
            resp.setHeader("Content-Length", "" + file.length());

//        out.clear();

            // response 객체로부터 새로운 출력 스트림 생성
            OutputStream os = resp.getOutputStream();

            // 출력 스트림에 파일 내용 출력
            byte b[] = new byte[(int)file.length()];
            int readBuffer = 0;
            while((readBuffer = is.read(b)) > 0) {
                os.write(b, 0, readBuffer);
            }

            // 입/출력 스트림 close
            is.close();
            os.close();

        } catch(FileNotFoundException e) {
            System.out.println("다운로드 중 파일을 찾을 수 없음");
            e.printStackTrace();
        } catch(Exception e) {
            System.out.println("다운로드 중 예외 발생");
            e.printStackTrace();
        }
    }
}
