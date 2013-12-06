
package dto;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

/**
 * 用户照片信息。
 */
public class PhotoDTO {
    public String photo_url = null;

    public String photo_type = null;

    public static List<PhotoDTO> format(String userSquareDTOs) {
        Gson gson = new Gson();
        // 定义类型解析器
        Type type = new TypeToken<List<PhotoDTO>>() {
        }.getType();
        List<PhotoDTO> dtos = gson.fromJson(userSquareDTOs, type);

        
        return dtos;
    }
}
