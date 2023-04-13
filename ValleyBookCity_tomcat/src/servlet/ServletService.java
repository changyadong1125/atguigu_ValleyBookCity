package servlet;

import bean.CommonResult;
import bean.User;
import com.google.gson.Gson;
import org.apache.commons.beanutils.BeanUtils;
import service.ServiceInterface;
import service.userService;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

/**
 * project:atguigu_ValleyBookCity
 * package:${PACKAGE_NAME}
 * class:${NAME}
 *
 * @author: smile
 * @create: 2023/3/22-22:00
 * @Version: v1.0
 * @Description:
 */
@SuppressWarnings("all")
@WebServlet(name = "ServletService", value = "/Servlet")
public class ServletService extends ServletBase {
    private final ServiceInterface userService = new userService();

    /**
     * return:
     * author: smile
     * version: 1.0
     * description:
     */
    protected void toLogin(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Cookie[] cookies = request.getCookies();
        if (cookies.length >= 3) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("username")) {
                    request.setAttribute("username", cookie.getValue());
                }
                if (cookie.getName().equals("password")) {
                    request.setAttribute("password", cookie.getValue());
                }
            }
        }
        this.processTemplate("pages/user/login", request, response);
    }

    /**
     * return:
     * author: smile
     * version: 1.0
     * description:
     */
    protected void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //读取用户输入的信息
        Map<String, String[]> parameterMap = request.getParameterMap();
        User user = new User();
        try {
            BeanUtils.populate(user, parameterMap);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
        User login = userService.login(user);

        if (login != null) {
            //如果注册成功就转发到成功页面
            request.getRequestDispatcher("/Servlet?method=toLoginSuccess").forward(request, response);
        } else {
            request.setAttribute("errorMsg", "用户名或密码错误");
            this.processTemplate("pages/user/login", request, response);
        }
    }

    /**
     * return:
     * author: smile
     * version: 1.0
     * description:
     */
    protected void toLoginSuccess(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String rememberUser = request.getParameter("rememberUser");
        String username = request.getParameter("username");
        User user = userService.selectByUserName(username);
        if ("true".equals(rememberUser)) {
            Cookie username_cookie = new Cookie("username", username);
            Cookie password_cookie = new Cookie("password", request.getParameter("password"));
            username_cookie.setMaxAge(60 * 1024);
            password_cookie.setMaxAge(60 * 1024);
            response.addCookie(username_cookie);
            response.addCookie(password_cookie);
        } else {
            Cookie[] cookies = request.getCookies();
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("username")) {
                    cookie.setMaxAge(0);
                    response.addCookie(cookie);
                } else if (cookie.getName().equals("password")) {
                    cookie.setMaxAge(0);
                    response.addCookie(cookie);
                }
            }
        }
        HttpSession session = request.getSession();
        session.setAttribute("user", user);
        this.processTemplate("pages/user/login_success", request, response);
    }

    /**
     * return:
     * author: smile
     * version: 1.0
     * description:
     */
    protected void toRegister(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setAttribute("err", "  用户名应为6~16位数组和字母组成");
        this.processTemplate("pages/user/regist", request, response);
    }

    /**
     * return:
     * author: smile
     * version: 1.0
     * description:
     */
    protected void register(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取用户的验证码
        String code = request.getParameter("code");
        Object kaptcha_session_key = request.getSession().getAttribute("kaptcha");
        if (code.equals(kaptcha_session_key)) {
            Map<String, String[]> parameterMap = request.getParameterMap();
            User user = new User();
            try {
                BeanUtils.populate(user, parameterMap);
            } catch (IllegalAccessException | InvocationTargetException e) {
                throw new RuntimeException(e);
            }

            userService.register(user);
            request.getRequestDispatcher("/Servlet?method=toRegisterSuccess").forward(request, response);
        } else {
            request.setAttribute("errCode", "验证码错误");
            this.processTemplate("pages/user/regist", request, response);
        }
    }

    /**
     * return:
     * author: smile
     * version: 1.0
     * description:
     */
    protected void toRegisterSuccess(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        String username = request.getParameter("username");
        User user  = userService.selectByUserName(username);
        session.setAttribute("user",user );
        this.processTemplate("pages/user/regist_success", request, response);
    }

    /**
     * return:
     * author: smile
     * version: 1.0
     * description:
     */
    protected void logout(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            HttpSession session = request.getSession();
            session.removeAttribute("user");
            request.getRequestDispatcher("/index.html").forward(request, response);
        } catch (ServletException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * return:
     * author: smile
     * version: 1.0
     * description:
     */
    protected void logoutAndLogin(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            HttpSession session = request.getSession();
            session.invalidate();
            request.getRequestDispatcher("/Servlet?method=toLogin").forward(request, response);
        } catch (ServletException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * return:
     * author: smile
     * version: 1.0
     * description:
     */
    protected void checkUsername(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String username = request.getParameter("username");
        User user = userService.selectByUserName(username);
        try {
            if (user == null) {
                response.getWriter().write(new Gson().toJson(CommonResult.ok().setMessage("√")));
            } else {
                response.getWriter().write(new Gson().toJson(CommonResult.error().setResultData("用户名已存在")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
