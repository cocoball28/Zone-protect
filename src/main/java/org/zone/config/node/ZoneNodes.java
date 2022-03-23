package org.zone.config.node;

import org.zone.config.node.limit.MaxOwnerNode;
import org.zone.config.node.price.PriceForNewLandNode;
import org.zone.config.node.title.DefaultTitleFadeInNode;

import java.lang.reflect.Modifier;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * All known nodes for the Zone Config
 */
public final class ZoneNodes {

    public static final MaxOwnerNode MAX_OWNER = new MaxOwnerNode();
    public static final PriceForNewLandNode PRICE_FOR_LAND = new PriceForNewLandNode();
    public static final DefaultTitleFadeInNode DEFAULT_TITLE_FADE_IN = new DefaultTitleFadeInNode();

    private ZoneNodes() {
        throw new RuntimeException("should not be init");

    }

    public static Set<ZoneNode<?>> getNodes() {
        return Stream
                .of(ZoneNodes.class.getDeclaredFields())
                .filter(field -> Modifier.isPublic(field.getModifiers()))
                .map(field -> {
                    try {
                        return field.get(null);
                    } catch (IllegalAccessException e) {
                        //noinspection ReturnOfNull
                        return null;
                    }
                })
                .filter(Objects::nonNull)
                .filter(value -> value instanceof ZoneNode<?>)
                .map(value -> (ZoneNode<?>) value)
                .collect(Collectors.toSet());
    }
}
