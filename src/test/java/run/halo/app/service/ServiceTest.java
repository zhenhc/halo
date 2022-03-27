package run.halo.app.service;

import cn.hutool.json.JSON;
import cn.hutool.json.JSONUtil;
import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import run.halo.app.config.properties.HaloProperties;
import run.halo.app.handler.theme.config.support.ThemeProperty;
import run.halo.app.model.dto.post.BasePostMinimalDTO;
import run.halo.app.model.entity.Post;
import run.halo.app.model.support.ThemeFile;
import run.halo.app.repository.ThemeRepository;
import run.halo.app.service.assembler.PostAssembler;
import run.halo.app.service.impl.BasePostServiceImpl;
import run.halo.app.service.impl.ThemeServiceImpl;
import run.halo.app.theme.ThemeFileScanner;
import run.halo.app.theme.ThemePropertyScanner;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

/**
 * @author : zhenhc
 * @date : 2022-03-26 21:22
 **/
@SpringBootTest
public class ServiceTest {
    @Autowired
    private PostService postService;

    @Autowired
    private PostAssembler postAssembler;

    @Autowired
    private ThemeRepository themeRepository;

    @Autowired
    private HaloProperties properties;
    @Test
    public void Test(){
        List<Post> content = postService.pageLatest(5).getContent();
        System.out.println(JSONUtil.toJsonStr(content));
        List<BasePostMinimalDTO> basePostMinimalDTOS = postAssembler.convertToMinimal(content);
        System.out.println(JSONUtil.toJsonStr(basePostMinimalDTOS));
    }

    @Test
    public void Test1(){
        List<ThemeFile> themeFiles = themeRepository.fetchThemePropertyByThemeId("joe2.0")
            .map(themeProperty -> ThemeFileScanner.INSTANCE.scan(themeProperty.getThemePath()))
            .orElse(Collections.emptyList());
        //System.out.println(JSONUtil.toJsonStr(themeFiles));

        List<ThemeProperty> scan = ThemePropertyScanner.INSTANCE.scan(getThemeRootPath(),
            themeRepository.getActivatedThemeId());
        //System.out.println(JSONUtil.toJsonStr(scan));
    }
    private Path getThemeRootPath() {
        Path path = Paths.get(properties.getWorkDir()).resolve("templates/themes");
        System.out.println(path);
        return path;
    }

}
