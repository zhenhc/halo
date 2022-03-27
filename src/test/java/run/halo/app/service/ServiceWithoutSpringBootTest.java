package run.halo.app.service;

import org.junit.jupiter.api.Test;
import run.halo.app.model.support.HaloConst;
import run.halo.app.service.impl.BasePostServiceImpl;
import run.halo.app.utils.HaloUtils;

/**
 * @author : zhenhc
 * @date : 2022-03-26 22:22
 **/
public class ServiceWithoutSpringBootTest {

    @Test
    public void test(){
        String htmlContent = "<ul> <li><a href=\"https://blog.csdn" +
            ".net/weixin_41624318/article/details/108712319\" " +
            "target=\"_blank\">idea2020配置gradle并导入Spring源码</a></li> <li><a href=\"https://mp" +
            ".weixin.qq.com/s/uk95_ls3MtrAbaDtEBotdA\" " +
            "target=\"_blank\">代码量减少90%，Java程序员必会的工具库</a></li> <li><a href=\"https://segmentfault" +
            ".com/a/1190000038999784\" target=\"_blank\">Vue3.0 + Vite 初体验（一）项目配置</a></li> <li><a" +
            " href=\"https://mp.weixin.qq.com/s/MrYbq0CCzuJhOQhJ8urNHw\" " +
            "target=\"_blank\">如何写出优雅的开源项目文档</a></li> </ul>";
        long l = BasePostServiceImpl.htmlFormatWordCount(htmlContent);
        System.out.println(l);

        String s = HaloUtils.cleanHtmlTag(htmlContent);
        System.out.println(s);
        System.out.println(s.length());
    }

}
