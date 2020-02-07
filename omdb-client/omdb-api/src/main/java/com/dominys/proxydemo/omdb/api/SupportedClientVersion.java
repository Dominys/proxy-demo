package com.dominys.proxydemo.omdb.api;

/**
 * @author rbezpalko
 * @since 27.10.17.
 */
public enum SupportedClientVersion {

    VERSION_1(Versions.VERSION_1),
    VERSION_2(Versions.VERSION_2);

    private final String version;

    SupportedClientVersion(String version) {
        this.version = version;
    }

    @Override
    public String toString() {
        return this.version;
    }

    public class Versions{
        static final String VERSION_1 = "VERSION_1";
        static final String VERSION_2 = "VERSION_2";

        private Versions() {
            throw new IllegalAccessError("Constants holder class");
        }
    }
}
