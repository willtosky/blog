package cn.crybird.manage.aspect;

import cn.crybird.manage.model.log.RequestLog;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@Aspect
@Component
public class LogAspect {

    @Pointcut("execution(* cn.crybird.manage.controller.*.*.*(..))")
    public void logThree(){}

    @Pointcut("execution(* cn.crybird.manage.controller.*.*(..))")
    public void logTwo(){}

    @Pointcut("logThree() || logTwo()")
    public void log(){}

    @Before("log()")
    public void doBefore(JoinPoint joinPoint){
        RequestLog rLog = new RequestLog();
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        rLog.setUrl(request.getRequestURL().toString());
        rLog.setIp(request.getRemoteAddr());
        rLog.setMethod(joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
        if(joinPoint.getArgs().length > 0){
            StringBuilder sb = new StringBuilder();
            sb.append("{");
            for(Object arg : joinPoint.getArgs()){
                if(arg == null){
                    sb.append("null");
                }else {
                    sb.append(arg.toString());
                }
                sb.append(" , ");
            }
            sb.delete(sb.length() - 3,sb.length());
            sb.append("}");
            rLog.setArgs(sb.toString());
        }
        log.info(rLog.toString());
    }

    @After("log()")
    public void doAfter(){
        log.info("-------------------doAfter-----------------");
    }

    @AfterReturning(returning = "result",pointcut = "log()")
    public void doAfterReturn(Object result){
        log.info("Result : {} ",result);
    }
}
