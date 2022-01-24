package org.zone.config.node.limit;

import org.spongepowered.api.command.CommandResult;
import org.spongepowered.configurate.CommentedConfigurationNode;
import org.spongepowered.configurate.serialize.SerializationException;
import org.zone.ZonePlugin;
import org.zone.commands.system.CommandArgument;
import org.zone.commands.system.arguments.simple.number.IntegerArgument;
import org.zone.commands.system.context.CommandContext;
import org.zone.config.command.ConfigCommandNode;
import org.zone.config.node.ZoneNode;
import org.zone.utils.Messages;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

public class MaxOwnerNode implements ZoneNode.WithDefault<Integer> {

    class MaxOwnerConfigCommandNode implements ConfigCommandNode<Integer> {

        @Override
        public String getDisplayId() {
            return "zones.limits.owner.max";
        }

        @Override
        public CommandArgument<Integer> getCommandArgument() {
            return new IntegerArgument("max");
        }

        @Override
        public CommandResult onChange(
                CommandContext context, Integer newValue) {
            try {
                MaxOwnerNode.this.set(ZonePlugin.getZonesPlugin().getConfig(), newValue);
                return CommandResult.success();
            } catch (SerializationException e) {
                e.printStackTrace();
                return CommandResult.error(Messages.getZoneSavingError(e));
            }
        }
    }

    @Override
    public Integer getDefault() {
        return -1;
    }

    @Override
    public String[] getNode() {
        return new String[]{"limits", "zone", "owner", "max"};
    }

    @Override
    public Integer getInitialValue() {
        return -1;
    }

    @Override
    public Collection<ConfigCommandNode<?>> getNodes() {
        return Collections.singleton(new MaxOwnerConfigCommandNode());
    }

    /*@Override
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
    }*/

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
