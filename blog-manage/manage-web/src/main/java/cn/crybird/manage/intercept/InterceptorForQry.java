package cn.crybird.manage.intercept;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Map;
import java.util.Properties;

//@Component
//拦截Executor类的query方法
@Intercepts({ @Signature(type = Executor.class, method = "query", args = { MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class }) })
public class InterceptorForQry implements Interceptor {

    @SuppressWarnings({ "rawtypes", "unchecked" })
    public Object intercept(Invocation invocation) throws Throwable {
        Object result = invocation.proceed(); //执行请求方法，并将所得结果保存到result中
        if (result instanceof ArrayList) {
            ArrayList resultList = (ArrayList) result;
            for (int i = 0; i < resultList.size(); i++) {
                if (resultList.get(i) instanceof Map) {
                    Map resultMap = (Map) resultList.get(i);
                    resultMap.put("CERT_NO", "这个是加密结果"); //取出相应的字段进行加密
                }
            }
        }
        return result;
    }

    public Object plugin(Object target) {
        System.out.println("this is the proceed ===>>" + target);
        return Plugin.wrap(target, this);
    }

    public void setProperties(Properties arg0) {
        System.out.println("this is the properties ===>>" + arg0);
    }
}
