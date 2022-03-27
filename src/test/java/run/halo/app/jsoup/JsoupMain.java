package run.halo.app.jsoup;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

/**
 * @author justLym
 * @version 1.0.0 2020/4/23 19:44
 **/
public class JsoupMain {
    @Test
    public void test(){
        try {
            Document document = Jsoup.connect("http://www.baidu.com/")
                //.data("wd","我")
                .userAgent("Mozilla")
                .cookie("auth","token")
                .timeout(3000)
                .post();

            Elements elements = document.select("a");

            elements.forEach(element -> {
                System.out.println(element.text());
            });


        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

