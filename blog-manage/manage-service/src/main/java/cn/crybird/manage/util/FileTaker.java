package cn.crybird.manage.util;

import cn.crybird.manage.config.PathConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

@Component
@Slf4j
public class FileTaker{

    @Value("${path.md}")
    private String MD_DIR;

    public String takeMdFile(String mdPath) throws IOException {
        StringBuilder sb = new StringBuilder();
        FileReader reader = null;
        BufferedReader bufferedReader = null;
        try{
            String dirPath = MD_DIR;//"/root/blog/md";//pathConfig.getMd();
            if(!dirPath.endsWith("/") && !dirPath.endsWith("\\")){
                dirPath += "/";
            }
            File file = new File(dirPath + mdPath);
            if(!file.exists()){
                throw new IOException();
            }

            reader = new FileReader(file);
            bufferedReader = new BufferedReader(reader);
            String line;
            while ((line = bufferedReader.readLine()) != null){
                sb.append(line);
                sb.append("\n");
            }
        }catch (IOException e){
            throw e;
        }finally {
            try {
                if(bufferedReader != null)
                    bufferedReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if(reader != null)
                    reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }

}
