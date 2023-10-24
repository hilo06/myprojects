package cn.itcast.travel.web.servlet;

import cn.itcast.travel.domain.PageBean;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.FavoriteService;
import cn.itcast.travel.service.impl.FavoriteServiceImpl;
import cn.itcast.travel.service.RouteService;
import cn.itcast.travel.service.impl.RouteServiceImpl;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/route/*")
public class RouteServlet extends BaseServlet{
    RouteService routeService = new RouteServiceImpl();
    FavoriteService favoriteService = new FavoriteServiceImpl();
    public void pageQuery(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String currentPageStr = req.getParameter("currentPage");
        String pageSizeStr = req.getParameter("pageSize");
        String cidStr = req.getParameter("cid");
        String rname = req.getParameter("rname");
        if(rname != null && rname.length()>0){
            rname = new String(rname.getBytes("iso-8859-1"),"utf-8");
            if("null".equals(rname)){
                rname=null;
            }
        }
        int currentPage = 0;
        int pageSize = 0;
        int cid = 0;
        if(currentPageStr != null && currentPageStr.length()!=0){
            currentPage = Integer.parseInt(currentPageStr);
        }else {
            currentPage = 1;
        }
        if(pageSizeStr != null && pageSizeStr.length()!=0){
            pageSize= Integer.parseInt(pageSizeStr);
        }else {
            pageSize = 5;
        }
        if(cidStr != null && cidStr.length()!=0 && !"null".equals(cidStr)){
            cid = Integer.parseInt(cidStr);
        }
        PageBean<Route> pb = routeService.pageQuery(cid, currentPage, pageSize, rname);
        writeValue(pb,resp);
    }

    public void findOne(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String rid = req.getParameter("rid");
        Route route = routeService.findOne(rid);
        writeValue(route,resp);
    }
    public void isFavorite(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String rid = req.getParameter("rid");
        User user = (User) req.getSession().getAttribute("user");
        int uid;
        if(user == null){
            uid = 0;
        }else {
            uid = user.getUid();
        }
        boolean flag = favoriteService.isFavorite(rid, uid);
        writeValue(flag,resp);
    }
    public void addFavorite(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String rid = req.getParameter("rid");
        User user = (User) req.getSession().getAttribute("user");
        int uid;
        if(user == null){
            uid = 0;
        }else {
            uid = user.getUid();
        }
        favoriteService.add(rid, uid);
    }
}
