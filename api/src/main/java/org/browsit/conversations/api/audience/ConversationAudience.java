package org.browsit.conversations.api.audience;

import java.util.UUID;

public interface ConversationAudience {

    /**
     * Returns the unique id of the audience.
     * @return The unique id.
     */
    UUID getUniqueId();

    /**
     * Sends a legacy message to the audience.
     * @param message Message to send.
     */
    void sendMessage(String message);

}
