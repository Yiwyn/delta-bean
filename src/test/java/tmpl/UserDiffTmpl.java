package tmpl;

import domain.User;
import fun.lifepoem.tool.deltabean.annotation.Diff;
import fun.lifepoem.tool.deltabean.annotation.IgnoreDiff;
import fun.lifepoem.tool.deltabean.interfaceable.BaseDiffTmpl;
import fun.lifepoem.tool.deltabean.interfaceable.event.OnFieldDiffEvent;

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


    String username;

    @IgnoreDiff
    String phone;

    @Diff(diffFieldDesc = "性别")
    Integer gender;


    Map<Integer, String> genderMap = new HashMap<Integer, String>() {{
        put(0, "男");
        put(1, "女");
    }};


    @Override
    public void addFieldDiffEvent(Map<String, OnFieldDiffEvent> fieldOnFieldDiffEventMap) {
        fieldOnFieldDiffEventMap.put("gender", item -> {
            item.setNewValueTrans(genderMap.get((Integer) item.getNewValue()));
            item.setOldValueTrans(genderMap.get((Integer) item.getOldValue()));
        });
    }
}
