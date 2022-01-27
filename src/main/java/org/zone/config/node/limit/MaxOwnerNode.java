package org.zone.config.node.limit;

import org.spongepowered.api.command.CommandCompletion;
import org.spongepowered.configurate.CommentedConfigurationNode;
import org.spongepowered.configurate.serialize.SerializationException;
import org.zone.config.node.ZoneNode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class MaxOwnerNode implements ZoneNode.WithDefault<Integer> {
    @Override
    public Integer getDefault() {
        return -1;
    }

    @Override
    public String[] getNode() {
        return new String[]{"limits", "zone", "owner", "max"};
    }

    @Override
    public String getDisplayKey() {
        return "zones.limits.owner.max";
    }

    @Override
    public Integer getInitialValue() {
        return -1;
    }

    @Override
    public List<CommandCompletion> getSuggestions(String peek) {
        if (peek.isBlank()) {
            List<CommandCompletion> completion = new ArrayList<>();
            for (int i = 0; i < 10; i++) {
                completion.add(CommandCompletion.of(i + ""));
            }
            return completion;
        }
        int value;
        try {
            value = Integer.parseInt(peek);
        } catch (NumberFormatException e) {
            return Collections.emptyList();
        }
        if (value <= 0) {
            return Collections.emptyList();
        }
        List<CommandCompletion> completion = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            completion.add(CommandCompletion.of(value + "" + i));
        }
        return completion;
    }

    @Override
    public void set(CommentedConfigurationNode node, Integer integer) throws
            SerializationException {
        node.set(integer);
    }

    @Override
    public Optional<Integer> get(CommentedConfigurationNode node) {
        int value = node.getInt();
        if (value < -1) {
            return Optional.empty();
        }
        if (value == 0) {
            return Optional.empty();
        }

        return Optional.of(value);
    }
}
