package net.dirtcraft.plugin.dirtdatabaselib;

import net.dirtcraft.plugin.dirtdatabaselib.Configuration.SQLConfiguration;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.service.sql.SqlService;

import java.io.File;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.SQLException;

public class SQLManager {

    private static SqlService sql = Sponge.getServiceManager().provideUnchecked(SqlService.class);

    public static Connection getConnection(String database, Path directory) {
        try {
            return sql.getDataSource(getURI(database, directory)).getConnection();
        } catch (SQLException exception) {
            exception.printStackTrace();
            return null;
        }
    }

    private static String getURI(String database, Path directory) {

        if (database != null) {
            return "jdbc:mariadb://" + SQLConfiguration.Database.IP +
                    ":" + SQLConfiguration.Database.PORT +
                    "/" + database +
                    "?user=" + SQLConfiguration.Database.USER + "&password=" + SQLConfiguration.Database.PASS;
        }

        if (directory != null) {
            return "jdbc:h2:" + directory.toString() + File.separator + "storage.db";
        }

        DirtDatabaseLib.getLogger().error("Could not connect to database, defaulted to " + DirtDatabaseLib.getContainer().getName() + "'s H2 database!");

        return "jdbc:h2:" + DirtDatabaseLib.getInstance().getDir().toString() + File.separator + "storage.db";
    }

}
