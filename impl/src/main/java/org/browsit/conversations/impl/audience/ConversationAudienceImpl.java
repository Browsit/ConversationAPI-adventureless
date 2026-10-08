package org.browsit.conversations.impl.audience;

import java.util.UUID;
import java.util.function.Consumer;
import org.browsit.conversations.api.audience.ConversationAudience;

public class ConversationAudienceImpl implements ConversationAudience {

    private final UUID uniqueId;
    private final Consumer<String> messageConsumer;

    public ConversationAudienceImpl(UUID uniqueId, Consumer<String> messageConsumer) {
        this.uniqueId = uniqueId;
        this.messageConsumer = messageConsumer;
    }

    @Override
    public UUID getUniqueId() {
        return this.uniqueId;
    }

    @Override
    public void sendMessage(String message) {
        if (message == null) {
            return;
        }

        this.messageConsumer.accept(message);
    }
}
