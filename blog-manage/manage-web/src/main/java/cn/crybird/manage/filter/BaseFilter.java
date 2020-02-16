package cn.crybird.manage.filter;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Enumeration;

@Slf4j
//@Component
public class BaseFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.info("Init BaseFilter");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest hsr = (HttpServletRequest)servletRequest;
        Enumeration<String> names = servletRequest.getAttributeNames();
        while (names.hasMoreElements()){
            String name = names.nextElement();
            System.out.print(name + " ");
        }
        filterChain.doFilter(servletRequest,servletResponse);
    }

    @Override
    public void destroy() {
        log.info("Destroy BaseFilter");
    }
}
