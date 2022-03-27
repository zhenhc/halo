package run.halo.app.controller;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.lang3.StringUtils;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import run.halo.app.model.dto.AttachmentDTO;


import cn.hutool.http.HttpRequest;

import cn.hutool.json.JSONUtil;

import org.junit.jupiter.api.Test;
import redis.clients.jedis.Jedis;
import run.halo.app.constant.HttpHeaders;
import run.halo.app.constant.RedisConstants;
import run.halo.app.model.dto.TagDTO;
import run.halo.app.model.enums.AttachmentType;
import run.halo.app.model.params.AttachmentQuery;
import run.halo.app.model.params.LoginParam;
import run.halo.app.model.params.PostQuery;
import run.halo.app.model.params.TagParam;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

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

    /**
     * 登录
     */
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

    /**
     * 数据统计（文章，评论，阅读量，建立天数）
     */
    @Test
    public void statisticsTest(){
        init();
        String body = HttpRequest.get("http://localhost:8090/api/admin/statistics")
            .header("Admin-Authorization",token)
            .execute()
            .body();
        System.out.println(body);
    }

    /**
     * 最近文章
     */
    @Test
    public void latestTest(){
        init();
        String body = HttpRequest.get("http://localhost:8090/api/admin/posts/latest")
            .form("top",2)
            .header(HttpHeaders.ADMIN_AUTHORIZATION, token)
            .execute()
            .body();
        System.out.println(body);
    }

    /**
     * 查找主题文件
     */
    @Test
    public void filesTest(){
        init();
        String body = HttpRequest.get("http://localhost:8090/api/admin/themes/joe2.0/files")
            .header(HttpHeaders.ADMIN_AUTHORIZATION,token)
            .execute()
            .body();
        System.out.println(body);
    }

    /**
     * 查询附件
     */
    @Test
    public void attachmentsTest(){
        init();
        AttachmentQuery attachmentQuery = new AttachmentQuery();
        attachmentQuery.setAttachmentType(AttachmentType.LOCAL);
        attachmentQuery.setMediaType("image/jpeg");
        String body = HttpRequest.get("http://localhost:8090/api/admin/attachments")
            .header(HttpHeaders.ADMIN_AUTHORIZATION,token)
            .form("page",0)
            .form("size",18)
            .form("mediaType","image/jpeg")
            .form("attachmentType","LOCAL")
            .execute()
            .body();
        System.out.println(body);
    }

    /**
     * 创建标签
     */
    @Test
    public void tagsTest(){
        init();
        TagParam tagParam = new TagParam();
        tagParam.setName("docker");
        tagParam.setColor("#1109a3");
        String body = HttpRequest.post("http://localhost:8090/api/admin/tags")
            .header(HttpHeaders.ADMIN_AUTHORIZATION,token)
            .body(JSONUtil.toJsonStr(tagParam))
            .execute()
            .body();
        System.out.println(body);
    }

    /**
     * 标签列表
     */
    @Test
    public void listTest(){
        init();
        String body = HttpRequest.get("http://localhost:8090/api/admin/tags")
            .header(HttpHeaders.ADMIN_AUTHORIZATION,token)
            .form("more",true)
            .execute()
            .body();
        System.out.println(body);
    }

    /**
     * 查询所有文章
     */
    @Test
    public void postsTest(){
        init();
        String body = HttpRequest.get("http://localhost:8090/api/admin/posts")
            .header(HttpHeaders.ADMIN_AUTHORIZATION,token)
            .execute()
            .body();
        System.out.println(body);
    }

    @Test
    public void test(){
        FileItem fileItem =
            createFileItem("E:\\OneDrive\\图片\\Saved Pictures\\freestocks-8a95EVm0ovQ-unsplash.jpg");
        MultipartFile multipartFile = new CommonsMultipartFile(fileItem);
        AttachmentDTO attachmentDTO = new AttachmentDTO();
        attachmentDTO.setSize(multipartFile.getSize());
        System.out.println(attachmentDTO);
    }

    public FileItem createFileItem(String filePath) {
        FileItemFactory factory = new DiskFileItemFactory(16, null);
        String[] split = StringUtils.split(filePath,"\\");
        String name = StringUtils.split(split[split.length - 1], ".")[0];
        FileItem item = factory.createItem(name, "text/plain", true, name);
        File newfile = new File(filePath);
        int bytesRead = 0;
        byte[] buffer = new byte[8192];
        try {
            FileInputStream fis = new FileInputStream(newfile);
            OutputStream os = item.getOutputStream();
            while ((bytesRead = fis.read(buffer, 0, 8192)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
            os.close();
            fis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return item;
    }
}
