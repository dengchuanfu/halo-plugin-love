package run.halo.lovepage;

import org.springframework.stereotype.Component;
import run.halo.app.plugin.BasePlugin;
import run.halo.app.plugin.PluginContext;

/**
 * 情侣主页插件的生命周期组件。
 */
@Component
public class LovePagePlugin extends BasePlugin {

    public LovePagePlugin(PluginContext pluginContext) {
        super(pluginContext);
    }
}
