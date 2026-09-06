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

    private static final String PLUGIN_ASSET_PREFIX = "/plugins/love-page/assets/images/";

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
            .defaultIfEmpty(new CoupleSetting(null, null, null, null, null, null, null, null))
            .flatMap(couple -> {
                var model = new HashMap<String, Object>();
                model.put("couple", couple);
                model.put("leftAvatar", imageOrDefault(couple.left_avatar(), "default-avatar.png"));
                model.put("rightAvatar", imageOrDefault(couple.right_avatar(), "default-avatar.png"));
                model.put("coverImage", imageOrDefault(couple.cover_image(), "default-cover.webp"));
                model.put(ModelConst.TEMPLATE_ID, "plugin:love-page:love");
                return templateNameResolver.resolveTemplateNameOrDefault(request.exchange(), "love")
                    .flatMap(templateName -> ServerResponse.ok().render(templateName, model));
            });
    }

    private String imageOrDefault(String configuredImage, String defaultImage) {
        return configuredImage == null || configuredImage.isBlank()
            ? PLUGIN_ASSET_PREFIX + defaultImage
            : configuredImage;
    }
}
