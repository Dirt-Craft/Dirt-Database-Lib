package net.dirtcraft.plugin.dirtdatabaselib;

import com.google.inject.Inject;
import net.dirtcraft.plugin.dirtdatabaselib.Configuration.ConfigManager;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.loader.ConfigurationLoader;
import org.slf4j.Logger;
import org.spongepowered.api.config.ConfigDir;
import org.spongepowered.api.config.DefaultConfig;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.Order;
import org.spongepowered.api.event.game.state.GamePreInitializationEvent;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.plugin.PluginContainer;

import java.nio.file.Path;
import java.sql.Connection;

@Plugin(
        id = "dirt-database-lib",
        name = "Dirt Database Lib",
        description = "DirtCraft's database management plugin",
        authors = {
                "juliann"
        }
)
public class DirtDatabaseLib {

    @Inject
    @ConfigDir(sharedRoot = false)
    private Path dir;

    @Inject
    @DefaultConfig(sharedRoot = false)
    private ConfigurationLoader<CommentedConfigurationNode> loader;
    private ConfigManager configManager;

    @Inject
    private Logger logger;
    @Inject
    private PluginContainer container;

    private static DirtDatabaseLib instance;
    private static SQLManager sqlManager;

    @Listener (order = Order.FIRST)
    public void onPreInit(GamePreInitializationEvent event) {
        instance = this;
        this.configManager = new ConfigManager(loader);
    }

    public static void loadSQL(String statement, String database, Path directory) {
        sqlManager = new SQLManager(statement, database, directory);
        sqlManager.load();
    }

    public static Connection getConnection() {
        return sqlManager.getConnection();
    }

    public Path getDir() {
        return dir;
    }

    public static DirtDatabaseLib getInstance() {
        return instance;
    }

    public static Logger getLogger() {
        return instance.logger;
    }

    public static PluginContainer getContainer() {
        return instance.container;
    }
}
