package cn.crybird.manage.listener;

import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

//@Component
public class SessionListener implements HttpSessionListener {

    @Override
    public void sessionCreated(HttpSessionEvent se) {
        String sessionId = se.getSession().getId();
        System.out.println("创建session：" + sessionId);
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        String sessionId = se.getSession().getId();
        System.out.println("销毁session："+ sessionId);
    }
}
