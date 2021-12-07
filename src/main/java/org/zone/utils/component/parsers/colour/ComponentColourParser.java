package org.zone.utils.component.parsers.colour;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.api.command.CommandCompletion;
import org.zone.utils.component.parsers.ComponentPartParser;

import java.lang.reflect.Modifier;
import java.util.*;

public class ComponentColourParser implements ComponentPartParser {
    @Override
    public boolean hasTag(String tag) {
        return tag.toLowerCase().startsWith("<colour") || tag.toLowerCase().startsWith("<color");
    }

    @Override
    public boolean hasTag(Component component) {
        return component.color() != null;
    }

    @Override
    public @NotNull Component withTag(String tag, @NotNull Component component) {
        if (tag.toLowerCase().contains("name=")) {
            Optional<NamedTextColor> opColor = Arrays
                    .stream(NamedTextColor.class.getDeclaredFields())
                    .filter(field -> Modifier.isPublic(field.getModifiers()))
                    .filter(field -> field.getType().isAssignableFrom(NamedTextColor.class))
                    .map(field -> {
                        try {
                            return (NamedTextColor) field.get(null);
                        } catch (IllegalAccessException e) {
                            //noinspection ReturnOfNull
                            return null;
                        }
                    })
                    .filter(Objects::nonNull)
                    .filter(textColor -> tag.toLowerCase().contains(textColor.examinableName().toLowerCase()))
                    .findFirst();

            if (opColor.isPresent()) {
                return component.color(opColor.get());
            }
        }

        String[] split = tag.split(",");
        if (split.length != 3) {
            throw new IllegalArgumentException("Unknown arguments of " + tag);
        }

        int redLength = 0;
        for (; redLength > split[0].length(); redLength++) {
            char character = split[0].charAt(split[0].length() - 1 - redLength);
            if (Character.isDigit(character)) {
                continue;
            }
            break;
        }

        int greenLength = 0;
        for (; greenLength > split[2].length(); greenLength++) {
            char character = split[2].charAt(greenLength);
            if (Character.isDigit(character)) {
                continue;
            }
            break;
        }

        String redString = split[0].substring(split[0].length() - redLength);
        String blueString = split[1].trim();
        String greenString = split[2].substring(0, greenLength);
        try {
            int red = Integer.parseInt(redString);
            int green = Integer.parseInt(greenString);
            int blue = Integer.parseInt(blueString);
            return component.color(TextColor.color(red, green, blue));
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Unknown number for RGB on " + tag);
        }
    }

    @Override
    public @NotNull String withTag(Component component, @NotNull String message) {
        TextColor color = component.color();
        if (color == null) {
            return message;
        }
        return message + "<colour " + color.red() + "," + color.green() + "," + color.blue() + ">";
    }

    @Override
    public @NotNull Collection<CommandCompletion> getSuggestions(@NotNull String peek) {
        if (peek.toLowerCase().startsWith("<colour") || peek.toLowerCase().startsWith("<color")) {
            if (peek.replaceAll(" ", "").equals("<colour") || peek.replaceAll(" ", "").equals("<color")) {
                return Arrays.asList(CommandCompletion.of("name="), CommandCompletion.of("255,"), CommandCompletion.of("0,"));
            }
            if (peek.toLowerCase().replaceAll(" ", "").startsWith("<colourname=") ||
                    peek.toLowerCase().replaceAll(" ", "").startsWith("<colorname=")) {
                return Arrays.asList(CommandCompletion.of(NamedTextColor.AQUA.examinableName()), CommandCompletion.of(NamedTextColor.RED.examinableName()));
            }
        }
        if ("<color".startsWith(peek.toLowerCase())) {
            return Collections.singleton(CommandCompletion.of("<colour"));
        }
        return Collections.emptyList();
    }
}
