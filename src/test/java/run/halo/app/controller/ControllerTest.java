package run.halo.app.controller;

import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSONUtil;
import org.junit.jupiter.api.Test;
import run.halo.app.model.params.LoginParam;

/**
 * @author : zhenhc
 * @date : 2022-03-26 18:38
 **/
public class ControllerTest {
    @Test
    public void loginTest(){
        LoginParam loginParam = new LoginParam();
        loginParam.setUsername("zhenhc");
        loginParam.setPassword("zhc19970418");
        String body = HttpRequest.post("http://localhost:8090/api/admin/login")
            .body(JSONUtil.toJsonStr(loginParam))
            .execute()
            .body();
        System.out.println(body);
    }
}
