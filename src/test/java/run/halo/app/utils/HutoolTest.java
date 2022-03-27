package run.halo.app.utils;

import cn.hutool.core.lang.Console;
import cn.hutool.core.util.ReUtil;
import cn.hutool.http.HttpUtil;
import org.junit.jupiter.api.Test;
import java.util.List;

/**
 * @author : zhenhc
 * @date : 2022-03-27 00:05
 **/
public class HutoolTest {
    @Test
    public void test(){
        //请求列表页
        String listContent = HttpUtil.get("https://www.oschina.net/action/ajax/get_more_news_list?newsType=&p=2");
        //使用正则获取所有标题
        List<String>
            titles = ReUtil.findAll("<span class=\"text-ellipsis\">(.*?)</span>", listContent, 1);
        for (String title : titles) {
            //打印标题
            Console.log(title);
        }
    }
}
