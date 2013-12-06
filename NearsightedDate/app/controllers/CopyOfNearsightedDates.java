package controllers;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jboss.netty.handler.codec.http.HttpMethod;

import play.Logger;
import play.cache.Cache;
import play.libs.URLs;
import play.libs.WS;
import play.libs.WS.HttpResponse;
import play.libs.WS.WSRequest;
import play.mvc.Controller;
import utils.WSUtil;

public class CopyOfNearsightedDates extends Controller {
    
    public static void index() {
        HttpResponse res = WS.url("http://115.29.163.82/user/square?page=1")
                .get();
        Logger.info("res.getString():" + res.getString());
        
        // HttpResponse res1 =
        // WS.url("http://localhost:7300/test").setHeader("Cookie",
        // "sessionId="+session.getId())
        // .get();
        // Logger.info("test:" + res1.getString());
        
        render();
    }
    
    /**
     * 
     * 功能描述：获取用户信息。
     * 
     * @param user_id
     * @author <a href="mailto:chenwj@ucweb.com">陈玩杰 </a>
     * @version 1.0.0
     * @since 1.0.0 create on: 2013-12-2
     */
    public static void display(Integer user_id) {
        Logger.info("user_id:" + user_id);
        
        Map map = new HashMap();
        map.put("user_id", user_id);
        
        Map result = WSUtil.rawResponse("http://115.29.163.82/user/info",
                HttpMethod.GET, map);
        
        if (Boolean.TRUE.equals(result.get("success"))) {
            render(result.get("data").toString());
        } else {
            renderJSON(result);
        }
    }
    
    /**
     * 
     * 功能描述：发布相亲。
     * 
     * @author <a href="mailto:chenwj@ucweb.com">陈玩杰 </a>
     * @version 1.0.0
     * @since 1.0.0 create on: 2013-11-9
     */
    public static void create(String user_id) {
        Logger.info("session id :" + session.getId());
        
        if (isConnected(user_id)) {
            renderTemplate("NearsightedDates/new.html");
        } else {
            // TODO try to login
        }
        
    }
    
    /**
     * 
     * 功能描述：获取下一页面的照片。
     * 
     * @param page
     * @author <a href="mailto:chenwj@ucweb.com">陈玩杰 </a>
     * @version 1.0.0
     * @since 1.0.0 create on: 2013-11-9
     */
    public static void next(Long page, String sex, String city) {
        Logger.info(" page: " + page);
        
        HttpResponse res = WS.url(
                "http://115.29.163.82/user/square?page=" + page)
                .get();
        
        Logger.info("next:" + res.getString());
        
        if (null != request.cookies.get("sex"))
            Logger.info(" sex: " + request.cookies.get("sex").value);
        if (null != request.cookies.get("city"))
            Logger.info(" city: " + request.cookies.get("city").value);
        
        List<String> images = new ArrayList<String>();
        images.add("public/images/nd/demo/b6_1.png");
        images.add("public/images/nd/demo/ba.jpg");
        images.add("public/images/nd/demo/banner1.jpg");
        images.add("public/images/nd/demo/demo1.jpg");
        images.add("public/images/nd/demo/demo2.jpg");
        images.add("public/images/nd/demo/fuwu1.png");
        images.add("public/images/nd/demo/logo.png");
        images.add("public/images/nd/demo/logo (2).png");
        images.add("public/images/nd/demo/demo2.jpg");
        images.add("public/images/nd/demo/fuwu1.png");
        
        List<Map> data = new ArrayList<Map>();
        if (page > 5)
            renderJSON(data);
        
        for (int index = 0; index < 10; index++) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("href", "display");
            map.put("src", images.get(index));
            map.put("desc", "广州，会做饭，琴棋书画都会");
            
            data.add(map);
        }
        
        renderJSON(data);
        
        // render();
    }
    
    public static void update(String userId) {
        Logger.info("session id :" + session.getId());
        
        // TODO
        
        if (isConnected(userId)) {
            renderTemplate("NearsightedDates/update.html");
        } else {
            // TODO try to login
        }
        
        URLs url = new URLs();
        
        display(0);
    }
    
    // User_id: 用户id，无id则用cookie中的session
    public static void uploadPicture(String user_id, File picture) {
        // TODO
        Logger.info("file size:" + picture.length());
        
        List<String> images = new ArrayList<String>();
        images.add("public/images/nd/demo/b6_1.png");
        images.add("public/images/nd/demo/ba.jpg");
        images.add("public/images/nd/demo/banner1.jpg");
        images.add("public/images/nd/demo/demo1.jpg");
        images.add("public/images/nd/demo/demo2.jpg");
        images.add("public/images/nd/demo/fuwu1.png");
        images.add("public/images/nd/demo/logo.png");
        images.add("public/images/nd/demo/logo (2).png");
        images.add("public/images/nd/demo/demo2.jpg");
        images.add("public/images/nd/demo/fuwu1.png");
        
        Integer index = (int)(Math.random() * 10);
        
        Map map = new HashMap();
        map.put("photo_url", images.get(index));
        map.put("success", true);
        renderJSON(map);
    }
    
    public static void addUserInfo() {
        Logger.info(params.allSimple().toString());
        
        String url = "http://115.29.163.82/user/add_user_info?user_id=23";
        HttpMethod method = HttpMethod.POST;
        Map result = WSUtil.rawResponse(url, method, params.allSimple());
        
        Logger.info(result.toString());
        
        Map map = new HashMap();
        map.put("success", true);
        
        // TODO php feedback newest user info
        
        renderJSON(map);
    }
    
    public static void getUserInfo(String user_id) {
        Logger.info("user_id:" + user_id);
        
        Map map = new HashMap();
        map.put("user_id", user_id);
        
        Map result = WSUtil.rawResponse("http://115.29.163.82/user/info",
                HttpMethod.GET, map);
        
        if (Boolean.TRUE.equals(result.get("success"))) {
            render(result.get("data").toString());
        } else {
            renderJSON(result);
        }
        
//        Map map = new HashMap();
//        map.put("success", true);
//        map.put("age", 23);
//        map.put("education", "本科");
//        map.put("income", "10万");
//        map.put("working_place", "广州");
//        map.put("qq", "35678989");
//        map.put("weixin", "凌晨的街灯");
//        map.put("description",
//                "大家好，我是一个憨厚老实，幽默，专注的男孩，现在常住地点在，我平时的业余时间喜欢运动，对于我的另一半，我希望她是一个孝顺的女生……希望在百合网能快点遇到心仪的她。");
//        map.put("birthday", "1985年5月17日");
//        map.put("career", "IT程序员");
//        map.put("has_house", "自己供房");
//        map.put("height", "185cm");
//        map.put("company_type", "企业");
//        map.put("income", "20万");
//        map.put("gender", "男");
//        map.put("hometown", "广州");
//        map.put("constellation", "狮子座");
//        
//        renderJSON(map);
    }
    
    public static void login() {
        render();
    }
    
    public static void logout() {
        render();
    }
    
    /**
     * 
     * 功能描述：webservice登录后的认证接口。
     * 
     * @param token
     * @throws Throwable
     * @author <a href="mailto:chenwj@ucweb.com">陈玩杰 </a>
     * @version 1.0.0
     * @since 1.0.0 create on: 2013-12-2
     */
    public static void authenticate(String token) throws Throwable {
        if (null != Cache.get(token)) {
            index();
        } else {
            
        }
    }
    
    /**
     * 
     * 功能描述：是否已登录。
     * 
     * @param userId
     * @return
     * @author <a href="mailto:chenwj@ucweb.com">陈玩杰 </a>
     * @version 1.0.0
     * @since 1.0.0 create on: 2013-12-1
     */
    // TODO
    private static Boolean isConnected(String userId) {
        // String userKey = "" + userId;
        //
        // return null != Cache.get(userKey);
        
        return true;
    }
    
}