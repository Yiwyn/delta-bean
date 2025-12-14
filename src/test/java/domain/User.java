package domain;

/**
 * @className: User
 * @author: Yiwyn
 * @date: 2025/12/13 23:34
 * @Version: 1.0
 * @description: 测试用户类
 */
public class User {


    private String username;

    private String phone;

    private Integer gender;


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }
}
