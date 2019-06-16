package net.dirtcraft.plugin.dirtdatabaselib.Configuration;

import com.google.common.reflect.TypeToken;
import ninja.leaping.configurate.ConfigurationOptions;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.loader.ConfigurationLoader;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;

import java.io.IOException;

public class ConfigManager {
    private ConfigurationLoader<CommentedConfigurationNode> loader;
    private ConfigurationOptions options;
    private SQLConfiguration config;

    public ConfigManager(ConfigurationLoader<CommentedConfigurationNode> loader) {
        this.loader = loader;
        options = ConfigurationOptions.defaults().setShouldCopyDefaults(true);
        update();
    }

    private void update() {
        try {
            CommentedConfigurationNode node = loader.load(options);
            SQLConfiguration cfg = node.getValue(TypeToken.of(SQLConfiguration.class), new SQLConfiguration());
            loader.save(node);
            this.config = cfg;
        } catch (IOException | ObjectMappingException exception) {
            exception.printStackTrace();
        }
    }
}
