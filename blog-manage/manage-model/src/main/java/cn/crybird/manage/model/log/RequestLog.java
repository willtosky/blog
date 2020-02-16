package cn.crybird.manage.model.log;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class RequestLog extends GlobalLog{

    private String url;
    private String ip;

    @Override
    public String toString() {
        return "RequestLog{" +
                "url='" + url + '\'' +
                ", ip='" + ip + '\'' +
                ", method='" + getMethod() + '\'' +
                ", args='" + getArgs() + '\'' +
                '}';
    }
}
