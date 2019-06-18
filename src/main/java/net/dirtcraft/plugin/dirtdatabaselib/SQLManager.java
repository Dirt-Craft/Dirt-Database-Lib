package net.dirtcraft.plugin.dirtdatabaselib;

import net.dirtcraft.plugin.dirtdatabaselib.Configuration.SQLConfiguration;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.service.sql.SqlService;

import java.io.File;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class SQLManager {

    private static SqlService sql;

    private final String statement;
    private final String database;
    private final Path directory;

    public SQLManager(String statement, String database, Path directory) {
        this.statement = statement;
        this.database = database;
        this.directory = directory;
    }

    public void load() {
        sql = Sponge.getServiceManager().provideUnchecked(SqlService.class);

        if (statement == null) return;
        try {

            Connection connection = getConnection();
            Statement stmt = connection.createStatement();

            stmt.execute(statement);

            stmt.close();
            connection.close();

        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    public Connection getConnection() {
        try {
            return sql.getDataSource(getURI()).getConnection();
        } catch (SQLException exception) {
            exception.printStackTrace();
            return null;
        }
    }

    private String getURI() {

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
