
package dto;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * 用户信息。
 */
public class UserInfoDTO {

    public String name = "相亲汇";

    public String description = null;

    public String qq = null;

    public String weixin = null;

    public String age = null;

    public String education = null;

    public String has_house = null;

    public String career = null;

    public String hometown = null;

    public String height = null;

    public String company_type = null;

    public String income = null;

    public String working_place = null;

    public String id = null;

    public String gender = null;

    public String brief_intro = null;

    public String status = null;

    public List<PhotoDTO> photo_url = new ArrayList<PhotoDTO>();

    public static UserInfoDTO format(String userInfoDTO) {
        Gson gson = new Gson();
        // 定义类型解析器
        Type type = new TypeToken<UserInfoDTO>() {
        }.getType();
        UserInfoDTO dto = gson.fromJson(userInfoDTO, type);
        return dto;
    }
}
