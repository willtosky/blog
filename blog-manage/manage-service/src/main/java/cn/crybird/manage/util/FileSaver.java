package cn.crybird.manage.util;

import cn.crybird.manage.config.PathConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

@Component
@Slf4j
public class FileSaver {

    @Value("${path.md}")
    private String MD_DIR;

    public void saveMdFile(String fileName,String content) throws IOException {
        FileWriter writer = null;
        BufferedWriter bufferedWriter = null;

        try{
            String dirPath = MD_DIR;//"/root/blog/md";//pathConfig.getMd();
            File dir = new File(dirPath);
            if(!dir.exists()){
                dir.mkdirs();
            }
            if(!dirPath.endsWith("\\") && !dirPath.endsWith("/")){
                dirPath += "/";
            }
            File file = new File(dirPath + fileName);
            if(!file.exists()){
                file.createNewFile();
            }

            writer = new FileWriter(file);
            bufferedWriter = new BufferedWriter(writer);

            bufferedWriter.write(content);
            bufferedWriter.flush();

        } catch (IOException e) {
            log.warn("文件保存失败："+fileName);
            throw new IOException();
        } finally {
            try {
                if(bufferedWriter != null)
                    bufferedWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if(writer != null)
                    writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
