package org.browsit.conversations.bukkit;

import org.browsit.conversations.api.Conversations;
import org.browsit.conversations.api.provider.ConversationsProvider;
import org.browsit.conversations.impl.audience.ConversationAudienceImpl;
import org.browsit.conversations.impl.provider.ConversationsProviderImpl;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * @author Illusion
 * created on 2/16/2023
 * <p>
 * Bukkit wrapper for {@link Conversations}.
 */
public class BukkitConversations {

    private static boolean initialized;

    /**
     * Initializes the Conversations API.
     */
    public static void init(JavaPlugin plugin) {
        if (initialized) throw new IllegalStateException("Conversations(Bukkit) API already initialized");
        ConversationsProvider provider = ConversationsProviderImpl.create(userId -> new ConversationAudienceImpl(userId, message -> {
            Player player = plugin.getServer().getPlayer(userId);
            if (player != null) {
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
            }
        }));
        Conversations.init(provider);
        new BukkitConversationsForwarder().register(plugin);
        initialized = true;
    }

    /**
     * Cleans up the Conversations API.
     */
    public static void cleanUp() {
        if (!initialized) throw new IllegalStateException("Conversations(Bukkit) API not initialized");
        Conversations.cleanUp();
    }
}
