package controllers;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.jboss.netty.handler.codec.http.HttpMethod;

import controllers.secure.NDPrincipal;
import dto.UserInfoDTO;
import dto.UserSquareDTO;

import play.Logger;
import play.cache.Cache;
import play.libs.URLs;
import play.libs.WS;
import play.libs.WS.HttpResponse;
import play.libs.WS.WSRequest;
import play.mvc.Controller;
import utils.WSUtil;

/**
 * 功能描述：功能实现。
 * <p>
 * 版权所有：优视科技
 * <p>
 * 未经本公司许可，不得以任何方式复制或使用本程序任何部分
 * <p>
 * 
 * @author <a href="mailto:chenwj@ucweb.com">陈玩杰 </a>
 * @version 1.0.0
 * @since 1.0.0 create on: 2013-12-2
 */
public class NearsightedDates extends Controller {
    
    public static void index() {
        Map map = new HashMap();
        map.put("page", 1);
        
        Map result = WSUtil.rawResponse("http://115.29.163.82/user/square",
                HttpMethod.GET, map);
        Logger.info("result:" + result.toString());
        
        if (Boolean.TRUE.equals(result.get("success"))) {
            String rawData = result.get("data").toString();
            List<UserSquareDTO> userSquareDTOs = UserSquareDTO.format(rawData);
            
            if (isConnected()) {
                NDPrincipal principal = (NDPrincipal)Cache.get(session.getId());
                
                render(userSquareDTOs, principal);
            } else {
                render(userSquareDTOs);
            }
        } else {
            error(result.get("msg").toString());
        }
    }
    
    /**
     * 功能描述：获取用户信息。
     * 
     * @param user_id
     * @author <a href="mailto:chenwj@ucweb.com">陈玩杰 </a>
     * @version 1.0.0
     * @since 1.0.0 create on: 2013-12-2
     */
    public static void display(Integer user_id) {
        Logger.info("user_id:" + user_id);
        
        NDPrincipal principal = null;
        if (user_id == null) {
            principal = (NDPrincipal)Cache.get(session.getId());
            
            user_id = Integer.valueOf(principal.getUserId());
        }
        
        if (StringUtils.isBlank(user_id.toString())) {
            error("参数错误");
        }
        
        Map map = new HashMap();
        map.put("user_id", user_id);
        
        Map result = WSUtil.rawResponse("http://115.29.163.82/user/info",
                HttpMethod.GET, map);
        
        if (Boolean.TRUE.equals(result.get("success"))) {
            String rawData = result.get("data").toString();
            UserInfoDTO userInfo = UserInfoDTO.format(rawData);
            
            render(userInfo, principal);
        } else {
            error(result.get("msg").toString());
        }
    }
    
    public static void userInfoRender(UserInfoDTO userInfo) {
        renderTemplate("NearsightedDates/display.html", userInfo);
    }
    
    /**
     * 功能描述：发布相亲。
     * 
     * @author <a href="mailto:chenwj@ucweb.com">陈玩杰 </a>
     * @version 1.0.0
     * @since 1.0.0 create on: 2013-11-9
     */
    public static void create(String user_id) {
        if (isConnected()) {
            renderTemplate("NearsightedDates/new.html");
        } else {
            // TODO try to login
            renderTemplate("NearsightedDates/login.html");
        }
    }
    
    public static void edit() {
        if (isConnected()) {
            renderTemplate("NearsightedDates/edit.html");
        } else {
            // TODO try to login
            renderTemplate("NearsightedDates/login.html");
        }
    }
    
    public static void login(String userName, String password, String backUrl) {
        boolean loginSuccess = true;
        if (loginSuccess) {
            String sessionId = session.getId();
            
            // FIXME for test now,it should get from php webservice
            String portalPrincipal = "{user_id:'14',user_name:'Marks',token:'token'}";
            Cache.add(sessionId, new NDPrincipal(portalPrincipal));
            
            redirect(backUrl);
        } else {
            // FIXME for test now , it should get from php webservice
            error("login fail");
        }
    }
    
    /**
     * 功能描述：获取下一页面的照片。
     * 
     * @param page
     * @author <a href="mailto:chenwj@ucweb.com">陈玩杰 </a>
     * @version 1.0.0
     * @since 1.0.0 create on: 2013-11-9
     */
    public static void next(Long page, String sex, String city) {
        Map paramMap = new HashMap();
        paramMap.put("page", page);
        paramMap.put("sex", sex);
        paramMap.put("city", city);
        
        Map result = WSUtil.rawResponse("http://115.29.163.82/user/square",
                HttpMethod.GET,
                paramMap);
        
        if (Boolean.TRUE.equals(result.get("success"))) {
            renderJSON(result.get("data").toString());
        } else {
            renderJSON(result);
        }
    }
    
    /**
     * 功能描述：更新个人信息。
     * 
     * @param userId
     * @author <a href="mailto:chenwj@ucweb.com">陈玩杰 </a>
     * @version 1.0.0
     * @since 1.0.0 create on: 2013-12-2
     */
    public static void update() {
        if (isConnected()) {
            NDPrincipal principal = (NDPrincipal)Cache.get(session.getId());
            
            Map map = new HashMap();
            map.put("user_id", principal.getUserId());
            
            // FIXME ask php webservice to update user info, and it should
            // return the newest user info if success
            Map result = WSUtil.rawResponse("http://115.29.163.82/user/info",
                    HttpMethod.POST, map);
            
            // render newest user info
            String rawData = result.get("data").toString();
            UserInfoDTO userInfo = UserInfoDTO.format(rawData);
            
            display(Integer.valueOf(principal.getUserId()));
        } else {
            // TODO try to login
            renderTemplate("NearsightedDates/login.html");
        }
    }
    
    private static void saveOrUpdate(Map map) {
        
    }
    
    // User_id: 用户id，无id则用cookie中的session
    public static void uploadPicture(String user_id, File picture) {
        // 登录判断
        if (!isConnected()) {
            renderJSON("go to login");
        }
        
        Map paramMap = new HashMap();
        paramMap.put("user_id", user_id);
        paramMap.put("picture", picture);
        
        Map result = WSUtil.rawResponse("http://115.29.163.82/user/upload_pic",
                HttpMethod.POST,
                paramMap);
        
        renderJSON(result);
    }
    
    public static void addUserInfo() {
        Logger.info(params.allSimple().toString());
        
        NDPrincipal principal = (NDPrincipal)Cache.get(session.getId());
        
        Map paramMap = new HashMap();
        paramMap.put("user_id", principal.getUserId());
        paramMap.putAll(params.allSimple());
        
        // 返回最新个人信息，和获取个人信息的接口一样格式
        Map result = WSUtil.rawResponse(
                "http://115.29.163.82/user/add_user_info", HttpMethod.POST,
                paramMap);
        
        Logger.info(result.toString());
        
        // render newest user info
        
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
            renderJSON(result.get("data").toString());
        } else {
            renderJSON(result);
        }
    }
    
    /**
     * 功能描述：是否已登录。
     * 
     * @param userId
     * @return
     * @author <a href="mailto:chenwj@ucweb.com">陈玩杰 </a>
     * @version 1.0.0
     * @since 1.0.0 create on: 2013-12-1
     */
    // TODO
    private static Boolean isConnected() {
        return null != getPrincipal();
    }
    
    private static NDPrincipal getPrincipal() {
        String sessionId = session.getId();
        
        return (NDPrincipal)Cache.get(sessionId);
    }
    
    /**
     * 功能描述：webservice登录后的认证接口。
     * 
     * @param token
     * @throws Throwable
     * @author <a href="mailto:chenwj@ucweb.com">陈玩杰 </a>
     * @version 1.0.0
     * @since 1.0.0 create on: 2013-12-2
     */
    public static void authenticate(String principal) throws Throwable {
        String sessionId = session.getId();
        
        Cache.set(sessionId, new NDPrincipal(principal));
    }
    
}
