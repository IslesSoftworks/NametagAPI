package org.hyphenical.nametagapi;

import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * This event is fired whenever a nametag changes due to: </br> A group node
 * prefix and suffix being set (NametagChangeReason.GROUP_NODE) </br> A prefix /
 * suffix is set through the plugin (NametagChangeReason.SET_PREFIX /
 * NametagChangeReason.SET_SUFFIX) </br> A prefix / suffix is set through the
 * API (NametagChangeReason.CUSTOM) </br>
 */
public class NametagChangeEvent extends Event implements Cancellable {

    private static final HandlerList handlers = new HandlerList();
    private String player;
    private String oldPrefix;
    private String oldSuffix;
    private String newPrefix;
    private String newSuffix;
    private NametagChangeType type;
    private NametagChangeReason reason;
    private boolean cancelled;

    /**
     * You don't need to touch this, this is fired via this plugin. </br> </br>
     * 
     * Creates a new NametagChangeEvent with the given properties.
     * 
     * @param player - The player this event is associated with.
     * @param oldPrefix - The current prefix for the player
     * @param oldSuffix - The current suffix for the player
     * @param newPrefix - The prefix to set for the player
     * @param newSuffix - The suffix to set for the player
     * @param type - The type of nametag change (hard/soft)
     * @param reason - The reason why the nametag is being changed.
     */
    public NametagChangeEvent(String player, String oldPrefix, String oldSuffix, String newPrefix, String newSuffix, NametagChangeType type, NametagChangeReason reason) {
        this.player = player;
        this.oldPrefix = oldPrefix;
        this.oldSuffix = oldSuffix;
        this.newPrefix = newPrefix;
        this.newSuffix = newSuffix;
        this.type = type;
        this.reason = reason;
    }

    /**
     * Sets whether this event should be cancelled or not.
     * 
     * @param cancelled the boolean to set as the cancelled state.
     */
    @Override
    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

    /**
     * Returns whether this event has been cancelled or not
     * 
     * @return true if the event is cancelled, false otherwise.
     */
    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    /**
     * Returns the player's name who is getting their nametag changed.
     * 
     * @return the player's name
     */
    public String getPlayerName() {
        return player;
    }

    /**
     * Returns the prefix that the player currently has.
     * 
     * @return the current prefix string
     */
    public String getCurrentPrefix() {
        return oldPrefix;
    }

    /**
     * Returns the suffix that the player currently has.
     * 
     * @return the current suffix string
     */
    public String getCurrentSuffix() {
        return oldSuffix;
    }

    /**
     * Returns the prefix that is going to be set if this event is not cancelled
     * 
     * @return the prefix to set
     */
    public String getPrefix() {
        return newPrefix;
    }

    /**
     * Returns the suffix that is going to be set if this event is not cancelled
     * 
     * @return the suffix to set
     */
    public String getSuffix() {
        return newSuffix;
    }

    /**
     * Sets the prefix to set if this event is not cancelled
     * 
     * @param prefix the prefix to set
     */
    public void setPrefix(String prefix) {
        newPrefix = prefix;
    }

    /**
     * Sets the suffix to set if this event is not cancelled
     * 
     * @param suffix the prefix to set
     */
    public void setSufix(String suffix) {
        newSuffix = suffix;
    }

    /**
     * Returns the type of nametag change the player will undergo if this event
     * is not cancelled </br></br> NametagChangeType.HARD - The current prefix
     * and suffix is completely overwritten by the new ones. </br></br>
     * NametagChangeType.SOFT - The current prefix and suffix is set only if the
     * new prefix/suffix is not null/empty. Null and empty prefixes and suffixes
     * will not be set.
     * 
     * @return the NametagChangeType associated with this event.
     */
    public NametagChangeType getType() {
        return type;
    }

    /**
     * Returns the reason for the firing of this event. </br></br>
     * 
     * NametagChangeReason.SET_PREFIX - The prefix was set with the /ne command
     * </br></br>
     * 
     * NametagChangeReason.SET_SUFFIX - The suffix was set with the /ne command
     * </br></br>
     * 
     * NametagChangeReason.GROUP_NODE - The group node was set from logging in
     * or resetting the nametag back to the default. </br></br>
     * 
     * NametagChangeReason.CUSTOM - The nametag is being set from outside of
     * NametagEdit.
     * 
     * @return the NametagChangeReason associated with this event.
     */
    public NametagChangeReason getReason() {
        return reason;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    /** Represents the type of change a player's nametag can undergo. */
    public enum NametagChangeType {
        HARD,
        SOFT
    }

    /** Represents the reason or cause for the change of a player's nametag. */
    public enum NametagChangeReason {
        SET_PREFIX,
        SET_SUFFIX,
        CUSTOM
    }

}