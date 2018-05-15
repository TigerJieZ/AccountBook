package cn.cslg.edu.accountbook;

/**
 * Created by 14446 on 2018/5/15.
 */

public class User {
    String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    String email;
    String gender;
    String tel;

    public User(String name,String email,String gender,String tel){
        setEmail(email);
        setGender(gender);
        setName(name);
        setTel(tel);
    }
}
