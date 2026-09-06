package run.halo.lovepage;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

import java.util.HashMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import run.halo.app.plugin.ReactiveSettingFetcher;
import run.halo.app.theme.TemplateNameResolver;
import run.halo.app.theme.router.ModelConst;

@Configuration(proxyBeanMethods = false)
public class LovePageRouter {

    private static final String PLUGIN_ASSET_PREFIX = "/plugins/PluginLove/assets/images/";

    private final TemplateNameResolver templateNameResolver;
    private final ReactiveSettingFetcher settingFetcher;

    public LovePageRouter(TemplateNameResolver templateNameResolver, ReactiveSettingFetcher settingFetcher) {
        this.templateNameResolver = templateNameResolver;
        this.settingFetcher = settingFetcher;
    }

    @Bean
    RouterFunction<ServerResponse> lovePageRouterFunction() {
        return route(GET("/love"), this::renderLovePage);
    }

    private Mono<ServerResponse> renderLovePage(ServerRequest request) {
        return settingFetcher.fetch(CoupleSetting.GROUP, CoupleSetting.class)
            .defaultIfEmpty(new CoupleSetting(
                null, null, null, null, null, null, null, null, null, null, null, null, null, null
            ))
            .flatMap(couple -> {
                var model = new HashMap<String, Object>();
                model.put("couple", couple);
                model.put("leftAvatar", avatarOrDefault(
                    couple.left_qq(), couple.left_avatar_url(), couple.left_avatar(),
                    CoupleSetting.DEFAULT_LEFT_AVATAR
                ));
                model.put("rightAvatar", avatarOrDefault(
                    couple.right_qq(), couple.right_avatar_url(), couple.right_avatar(),
                    CoupleSetting.DEFAULT_RIGHT_AVATAR
                ));
                model.put("leftProfileUrl", couple.left_profile_url());
                model.put("rightProfileUrl", couple.right_profile_url());
                model.put("coverImage", imageOrDefault(couple.cover_image(), "default-cover.webp"));
                model.put(ModelConst.TEMPLATE_ID, "plugin:PluginLove:love");
                return templateNameResolver.resolveTemplateNameOrDefault(request.exchange(), "love")
                    .flatMap(templateName -> ServerResponse.ok().render(templateName, model));
            });
    }

    private String imageOrDefault(String configuredImage, String defaultImage) {
        return configuredImage == null || configuredImage.isBlank()
            ? PLUGIN_ASSET_PREFIX + defaultImage
            : configuredImage;
    }

    private String avatarOrDefault(
        String qq, String avatarUrl, String uploadedAvatar, String defaultAvatar
    ) {
        if (qq != null && qq.matches("\\d{5,12}")) {
            return "https://q1.qlogo.cn/g?b=qq&nk=" + qq + "&s=640";
        }
        if (avatarUrl != null && !avatarUrl.isBlank()) {
            return avatarUrl;
        }
        return uploadedAvatar == null || uploadedAvatar.isBlank() ? defaultAvatar : uploadedAvatar;
    }
}
