package com.kkb.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 字符编码过滤器
 */
/*@WebFilter(urlPatterns = "/*",filterName = "encoderFilter",initParams = {
    @WebInitParam(name = "charset",value = "utf-8")
})*/
public class EncoderFilter extends HttpFilter {

    private String charSet;

    public void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
        //如果是post请求，设置字符编码
        if(req.getMethod().equals("post")) {
            req.setCharacterEncoding(charSet);
        }
        res.setContentType("text/html;charset="+charSet);
        chain.doFilter(req,res);
    }

    @Override
    public void destroy() {

    }

    @Override
    public void init(FilterConfig config) throws ServletException {
        charSet = config.getInitParameter("charset");
    }
}
