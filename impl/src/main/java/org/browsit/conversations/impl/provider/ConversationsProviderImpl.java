package org.browsit.conversations.impl.provider;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import org.browsit.conversations.api.audience.ConversationAudience;
import org.browsit.conversations.api.data.Conversation;
import org.browsit.conversations.api.provider.ConversationsProvider;
import org.browsit.conversations.impl.data.ConversationImpl;

public class ConversationsProviderImpl implements ConversationsProvider {

    private final List<ConversationImpl> conversations = new CopyOnWriteArrayList<>();
    private final Function<UUID, ConversationAudience> audienceFactory;
    private final ScheduledExecutorService conversationsExecutor;

    private ConversationsProviderImpl(Function<UUID, ConversationAudience> audienceFactory) {
        this.audienceFactory = audienceFactory;
        this.conversationsExecutor = Executors.newSingleThreadScheduledExecutor();
        this.conversationsExecutor.scheduleAtFixedRate(() -> {
            try {
                this.conversations.forEach(ConversationImpl::tick);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, 0L, 1L, TimeUnit.MILLISECONDS);
    }

    public static ConversationsProviderImpl create(Function<UUID, ConversationAudience> audienceFactory) {
        return new ConversationsProviderImpl(audienceFactory);
    }

    @Override
    public void cleanUp() {
        try {
            if (!this.conversationsExecutor.awaitTermination(5000, TimeUnit.MILLISECONDS)) {
                this.conversationsExecutor.shutdownNow();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        this.conversations.clear();
    }

    @Override
    public Conversation createConversation(UUID userId) {
        ConversationAudience audience = this.audienceFactory.apply(userId);
        ConversationImpl conversation = new ConversationImpl(this, audience);
        this.conversations.add(conversation);
        return conversation;
    }

    @Override
    public Optional<Conversation> getConversationOf(UUID userId) {
        return this.conversations.stream()
            .filter(conversation -> conversation.inConversation(userId))
            .findFirst()
            .map(conversation -> conversation);
    }

    public void endInternal(ConversationImpl conversation) {
        this.conversations.remove(conversation);
    }
}
