package org.zone.config.node;

import org.zone.config.node.limit.MaxOwnerNode;
import org.zone.config.node.price.PriceForLandNode;

import java.lang.reflect.Modifier;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@SuppressWarnings("unused")
public final class ZoneNodes {

    public static final MaxOwnerNode MAX_OWNER = new MaxOwnerNode();
    public static final PriceForLandNode PRICE_FOR_LAND = new PriceForLandNode();

    private ZoneNodes() {
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
