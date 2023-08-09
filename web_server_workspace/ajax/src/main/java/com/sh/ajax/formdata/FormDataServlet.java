package com.sh.ajax.formdata;

import com.oreilly.servlet.MultipartRequest;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "FormDataServlet", value = "/formdata")
public class FormDataServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("name");
        System.out.println(name);

        response.setContentType("text/plain; charset=utf-8");
        response.getWriter().append("name=" + name);
    }

    /**
     * enctype: multipart/form-data 요청시
     * HttpServletRequest가 아닌 MultipartRequest객체로 처리해야 한다.
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");

        // System.out.println(request.getParameter("name")); // null


        // saveDirectory, encoding 필수
        MultipartRequest multipartRequest = new MultipartRequest(request, getServletContext().getRealPath("/upload"), "utf-8");
        String name = multipartRequest.getParameter("name");
        String profile = multipartRequest.getFilesystemName("profile");
        System.out.println(name);
        System.out.println(profile);

        response.setContentType("text/plain; charset=utf-8");
        response.getWriter()
                .append("name=" + name)
                .append(", ")
                .append("profile=" + profile);
    }
}
