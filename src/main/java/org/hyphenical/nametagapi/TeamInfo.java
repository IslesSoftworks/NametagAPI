package org.hyphenical.nametagapi;

/**
 * Represents a scoreboard team, used in the NametagManager
 * object.
 */
class TeamInfo {

    private String name;
    private String prefix;
    private String suffix;

    TeamInfo(String name) {
        this.name = name;
    }

    void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    String getPrefix() {
        return prefix;
    }

    String getSuffix() {
        return suffix;
    }

    String getName() {
        return name;
    }

}