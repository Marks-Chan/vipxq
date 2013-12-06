package controllers.secure;

import java.io.Serializable;
import java.security.Principal;
import java.util.regex.Pattern;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class NDPrincipal implements Principal, Serializable {
    
    private static JsonParser jsonParser = new JsonParser();
    
    private static Pattern pattern = Pattern.compile("(.+?)_(.+)");
    
    private String userName = null;
    
    private String userId = null;
    
    private String token = null;
    
    private NDPrincipal() {
        super();
    }
    
    public NDPrincipal(String portalPrincipal) {
        this();
        
        JsonObject jsonObject = jsonParser.parse(portalPrincipal)
                .getAsJsonObject();
        
        userId = jsonObject.get("user_id").getAsString();
        userName = jsonObject.get("user_name").getAsString();
        token = jsonObject.get("token").getAsString();
    }
    
    @Override
    public String toString() {
        return (new Gson()).toJson(this);
    }
    
 //   @Override
    public String getName() {
        return userName;
    }
    
    public String getUserName() {
        return userName;
    }
    
    public String getUserId() {
        return userId;
    }
    
    public String getToken() {
        return token;
    }
    
}
