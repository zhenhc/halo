package run.halo.app.controller;

import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSONUtil;
import org.junit.jupiter.api.Test;
import redis.clients.jedis.Jedis;
import run.halo.app.constant.RedisConstants;
import run.halo.app.model.params.LoginParam;

/**
 * @author : zhenhc
 * @date : 2022-03-26 18:38
 **/
public class ControllerTest {
    private Jedis jedis;
    private String token;
    private void init(){
        jedis = new Jedis(RedisConstants.IP,RedisConstants.PORT);
        token = jedis.get(RedisConstants.REDIS_TOKEN_KEY).replace("\"","");
    }

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

    @Test
    public void statisticsTest(){
        init();
        String body = HttpRequest.get("http://localhost:8090/api/admin/statistics")
            .header("Admin-Authorization",token)
            .execute()
            .body();
        System.out.println(body);
    }
}
