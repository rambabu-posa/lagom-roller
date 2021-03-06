/*
 * Copyright (C) 2016-2017 Lightbend Inc. <https://www.lightbend.com>
 */
package edu.knoldus.hello.impl;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;
import com.lightbend.lagom.javadsl.persistence.AggregateEvent;
import com.lightbend.lagom.javadsl.persistence.AggregateEventShards;
import com.lightbend.lagom.javadsl.persistence.AggregateEventTag;
import com.lightbend.lagom.serialization.Jsonable;

import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;

/**
 * This interface defines all the events that the Hello entity supports.
 * <p>
 * By convention, the events should be inner classes of the interface, which
 * makes it simple to get a complete picture of what events an entity has.
 */
public interface HelloEvent extends AggregateEvent<HelloEvent>, Jsonable {


    int NUM_SHARDS = 20;

    AggregateEventShards<HelloEvent> TAG =
            AggregateEventTag.sharded(HelloEvent.class, NUM_SHARDS);

    @Override
    default AggregateEventShards<HelloEvent> aggregateTag() {
        return TAG;
    }


    /**
     * An event that represents a change in greeting message.
     */
    @SuppressWarnings("serial")
    @Immutable
    @JsonDeserialize
    public final class GreetingMessageChanged implements HelloEvent {
        public final String message;

        @JsonCreator
        public GreetingMessageChanged(String message) {
            this.message = Preconditions.checkNotNull(message, "message");
        }

        @Override
        public boolean equals(@Nullable Object another) {
            if (this == another)
                return true;
            return another instanceof GreetingMessageChanged && equalTo((GreetingMessageChanged) another);
        }

        private boolean equalTo(GreetingMessageChanged another) {
            return message.equals(another.message);
        }

        @Override
        public int hashCode() {
            int h = 31;
            h = h * 17 + message.hashCode();
            return h;
        }

        @Override
        public String toString() {
            return MoreObjects.toStringHelper("GreetingMessageChanged").add("message", message).toString();
        }
    }
}
