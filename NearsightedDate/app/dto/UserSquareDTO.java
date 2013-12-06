
package dto;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 用户广场信息。
 */
public class UserSquareDTO {
    public String photo_url = null;

    public String id = null;

    public String brief_intro = null;

    public static List<UserSquareDTO> format(String userSquareDTOs) {
        Gson gson = new Gson();
        // 定义类型解析器
        Type type = new TypeToken<List<UserSquareDTO>>() {
        }.getType();
        List<UserSquareDTO> dtos = gson.fromJson(userSquareDTOs, type);

        return dtos;
    }

}
