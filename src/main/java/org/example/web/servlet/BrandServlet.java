package org.example.web.servlet;

import com.alibaba.fastjson.JSON;
import org.example.pojo.Brand;
import org.example.pojo.PageBean;
import org.example.service.BrandService;
import org.example.service.impl.BrandServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

@WebServlet("/brand/*")
public class BrandServlet extends BaseServlet{
    private final BrandService brandService = new BrandServiceImpl();

    public void selectAll(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 1.调用service查询
        List<Brand> brands = brandService.selectAll();

        // 2.转为json
        String jsonString = JSON.toJSONString(brands);

        // 3.写数据
        resp.setContentType("text/json;charset=utf-8");
        resp.getWriter().write(jsonString);

    }


    public void add(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //1. 接受品牌数据
        BufferedReader br = req.getReader();
        String params = br.readLine();//json字符串

        //转为Brand对象
        Brand brand = JSON.parseObject(params, Brand.class);

        //2. 调用service添加
        brandService.add(brand);

        //3. 相应成功的标识
        resp.getWriter().write("success");
    }

    /**
     * 批量删除
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    public void deleteByIds(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //1. 接受数据  [1,2,3]
        BufferedReader br = req.getReader();
        String params = br.readLine();

        //转为 int[]
        int[] ids = JSON.parseObject(params, int[].class);

        //2. 调用service添加
        brandService.deleteByIds(ids);

        //3. 相应成功的标识
        resp.getWriter().write("success");
    }

    public void selectByPage(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 1.接受 当前页码 和 每页展示条数    url?currentPage=1&pageSize=5
        String _currentPage = req.getParameter("currentPage");
        String _pageSize = req.getParameter("pageSize");

        int currentPage = Integer.parseInt(_currentPage);
        int pageSize = Integer.parseInt(_pageSize);

        // 2.调用service查询
        PageBean<Brand> pageBean = brandService.selectByPage(currentPage, pageSize);

        // 2.转为json
        String jsonString = JSON.toJSONString(pageBean);

        // 3.写数据
        resp.setContentType("text/json;charset=utf-8");
        resp.getWriter().write(jsonString);

    }

}
