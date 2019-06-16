package net.dirtcraft.plugin.dirtdatabaselib.Configuration;

import ninja.leaping.configurate.objectmapping.Setting;
import ninja.leaping.configurate.objectmapping.serialize.ConfigSerializable;

@ConfigSerializable
public class SQLConfiguration {
    @Setting(value = "Database")
    private SQLConfiguration.Database database = new SQLConfiguration.Database();

    @ConfigSerializable
    public static class Database {

        @Setting
        public static String USER = "";

        @Setting
        public static String PASS = "";

        @Setting
        public static String IP = "127.0.0.1";

        @Setting
        public static int PORT = 3306;

    }
}
