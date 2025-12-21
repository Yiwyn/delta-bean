package tmpl;

import domain.User;
import fun.lifepoem.tool.deltabean.annotation.Diff;
import fun.lifepoem.tool.deltabean.annotation.VirtualDiff;
import fun.lifepoem.tool.deltabean.interfaceable.BaseDiffTmpl;
import fun.lifepoem.tool.deltabean.interfaceable.event.VirtualField;
import fun.lifepoem.tool.deltabean.interfaceable.event.OnFieldDiffTransEvent;

import java.util.HashMap;
import java.util.Map;

/**
 * @className: UserTmpl
 * @author: Yiwyn
 * @date: 2025/12/14 00:28
 * @Version: 1.0
 * @description: 用户比较模版
 */
public class UserDiffTmpl extends BaseDiffTmpl<User> {

    @Diff
    String username;


    Map<Integer, String> genderMap = new HashMap<Integer, String>() {{
        put(0, "男");
        put(1, "女");
    }};

    @Diff
    @VirtualDiff
    VirtualField<User> phone = object -> "手机号：" + object.getPhone();

    @Diff(diffFieldDesc = "性别")
    Integer gender;


    @Override
    public void addFieldDiffEvent(Map<String, OnFieldDiffTransEvent<User>> fieldOnFieldDiffEventMap) {
        fieldOnFieldDiffEventMap.put("gender", data -> genderMap.get(data.getGender()));

        fieldOnFieldDiffEventMap.put("phone", data -> data.getPhone() + "变更记录");
    }
}
