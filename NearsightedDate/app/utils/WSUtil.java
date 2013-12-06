
package utils;

import java.lang.reflect.Type;
import java.util.Map;

import org.jboss.netty.handler.codec.http.HttpMethod;

import play.Logger;
import play.libs.WS;
import play.libs.WS.HttpResponse;
import play.libs.WS.WSRequest;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class WSUtil {

    public static Map rawResponse(String url, HttpMethod method, Map params) {
        try {
            // 设置参数
            WSRequest wsr = WS.url(url);

            for (Object key : params.keySet()) {
                wsr.setParameter((String) key, params.get(key));
            }

            Logger.debug("WSUtil url: " + wsr.url);

            // 向JBPM发起请求
            String reponseText = "";
            HttpResponse response = null;

            if (HttpMethod.PUT.equals(method))
                response = wsr.put();
            else if (HttpMethod.POST.equals(method))
                response = wsr.post();
            else if (HttpMethod.GET.equals(method))
                response = wsr.get();
            else if (HttpMethod.DELETE.equals(method))
                response = wsr.delete();
            else
                return RenderUtil.FailureMap("不支持" + method.toString() + "的请求方式！");

            if (null != response && response.getStatus() == 200) {
                reponseText = response.getString();

                Logger.debug("MEETING成功返回：" + reponseText);
            } else {
                Logger.error("MEETING reponse is NOT OK!!!  " + response.getString());

                return RenderUtil.FailureMap();
            }

            return RenderUtil.SuccessMap(reponseText, "success");

        } catch (Exception e) {
            Logger.error(e, "Error occurs when " + " doing request to MEETING ");
        }

        return RenderUtil.FailureMap();
    }

    private static Map ResultMap(String response) {
        Gson gson = new Gson();
        // 定义类型解析器
        Type type = new TypeToken<Map>() {
        }.getType();
        Map result = gson.fromJson(response, type);

        return result;
    }

}
