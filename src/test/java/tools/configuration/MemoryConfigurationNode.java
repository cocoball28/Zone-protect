package tools.configuration;

import org.checkerframework.checker.nullness.qual.Nullable;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.configurate.*;
import org.spongepowered.configurate.serialize.SerializationException;

import java.lang.reflect.Type;
import java.util.*;
import java.util.stream.Collectors;

public class MemoryConfigurationNode implements CommentedConfigurationNode {

    private final @Nullable MemoryConfigurationNode parent;
    private final @NotNull Object key;
    private final Set<CommentedConfigurationNode> children = new HashSet<>();
    private @Nullable String comment;
    private @Nullable Object value;
    private @Nullable Class<?> setAs;

    public MemoryConfigurationNode(@NotNull Object key, @Nullable MemoryConfigurationNode parent) {
        this.key = key;
        this.parent = parent;
    }


    //needed

    @Override
    public @Nullable String comment() {
        return this.comment;
    }

    @Override
    public CommentedConfigurationNode comment(@Nullable String comment) {
        this.comment = comment;
        return this;
    }

    @Override
    public CommentedConfigurationNode commentIfAbsent(String comment) {
        throw new RuntimeException("Not implemented");
    }

    @Override
    public CommentedConfigurationNode self() {
        throw new RuntimeException("Not implemented");
    }

    @Override
    public CommentedConfigurationNode appendListNode() {
        throw new RuntimeException("Not implemented");
    }

    @Override
    public CommentedConfigurationNode copy() {
        throw new RuntimeException("Not implemented");
    }

    @Override
    public CommentedConfigurationNode node(Object... path) {
        return this.node(Arrays.asList(path));
    }

    @Override
    public CommentedConfigurationNode node(Iterable<?> path) {
        MemoryConfigurationNode configNode = this;
        for (Object node : path) {
            Optional<CommentedConfigurationNode> opNode = configNode.children
                    .stream()
                    .filter(child -> {
                        Object key = child.key();
                        if (key == null && node == null) {
                            return true;
                        }
                        if (key == null) {
                            return false;
                        }
                        return key.equals(node);
                    })
                    .findAny();
            if (opNode.isPresent()) {
                configNode = (MemoryConfigurationNode) opNode.get();
            } else {
                configNode = new MemoryConfigurationNode(node, configNode);
            }
        }
        this.children.add(configNode);
        return configNode;
    }

    @Override
    public @Nullable CommentedConfigurationNode parent() {
        return this.parent;
    }

    @Override
    public CommentedConfigurationNode from(@NotNull ConfigurationNode other) {
        throw new RuntimeException("Not implemented");

    }

    @Override
    public CommentedConfigurationNode mergeFrom(@NotNull ConfigurationNode other) {
        throw new RuntimeException("Not implemented");
    }

    @Override
    public CommentedConfigurationNode set(@Nullable Object value) throws SerializationException {
        if (value == null) {
            this.children.clear();
            this.value = null;
            this.setAs = null;
            return this;
        }
        if (value instanceof Collection collection) {
            this.setAs = Collection.class;
            if (!collection.isEmpty()) {
                this.setAs = this.getBasicType(collection.iterator().next().getClass());
            }
        } else {
            this.setAs = this.getBasicType(value.getClass());
        }
        this.value = value;
        return this;
    }

    @Override
    public CommentedConfigurationNode raw(@Nullable Object value) {
        throw new RuntimeException("Not implemented");
    }

    @Override
    public List<CommentedConfigurationNode> childrenList() {
        return new ArrayList<>(this.children);
    }

    @Override
    public Map<Object, CommentedConfigurationNode> childrenMap() {
        return this.children
                .stream()
                .collect(Collectors.toMap(ConfigurationNode::key, node -> node));
    }

    @Override
    public <V> CommentedConfigurationNode hint(
            RepresentationHint<V> hint, @Nullable V value) {
        throw new RuntimeException("Not implemented");

    }

    @Override
    public @Nullable Object key() {
        return this.key;
    }

    @Override
    public NodePath path() {
        List<Object> nodeList = new ArrayList<>();
        MemoryConfigurationNode node = this;
        while (node != null) {
            nodeList.add(node.key);
            node = this.parent;
        }
        Object[] nodeArray = new Object[nodeList.size()];
        for (int a = 0; a < nodeArray.length; a++) {
            nodeArray[a] = nodeList.get(nodeArray.length - (a - 1));
        }
        return new MemoryNodePath(nodeArray);
    }

    @Override
    public boolean hasChild(Object... path) {
        throw new RuntimeException("Not implemented");

    }

    @Override
    public boolean hasChild(Iterable<?> path) {
        throw new RuntimeException("Not implemented");
    }

    @Override
    public boolean virtual() {
        throw new RuntimeException("Not implemented");
    }

    @Override
    public ConfigurationOptions options() {
        return ConfigurationOptions.defaults();
    }

    @Override
    public boolean isNull() {
        return this.value == null;
    }

    @Override
    public boolean isList() {
        if (this.value == null) {
            return false;
        }
        return this.value instanceof Collection<?>;
    }

    @Override
    public boolean isMap() {
        if (this.value == null) {
            return false;
        }
        return this.value instanceof Map<?, ?>;
    }

    @Override
    public boolean empty() {
        return this.value == null;
    }

    @Override
    public @Nullable Object get(Type type) throws SerializationException {
        if (this.setAs == type) {
            return this.value;
        }
        throw new SerializationException("The saved value is not of type '" +
                type.getTypeName() +
                "'");
    }

    @Override
    public @Nullable <V> List<V> getList(Class<V> type) throws SerializationException {
        if (this.setAs == type && this.value instanceof Collection value) {
            return new ArrayList<V>(value);
        }

        return CommentedConfigurationNode.super.getList(type);
    }

    @Override
    public @Nullable Object raw() {
        throw new RuntimeException("Not implemented");
    }

    @Override
    public @Nullable Object rawScalar() {
        if (this.value == null) {
            return null;
        }
        if (this.value instanceof CharSequence) {
            return this.value;
        }
        if (this.value instanceof Integer) {
            return this.value;
        }
        if (this.value instanceof Double) {
            return this.value;
        }


        throw new RuntimeException("Invalid type of " + this.value.getClass().getSimpleName());
    }

    @Override
    public boolean removeChild(@NotNull Object key) {
        Optional<CommentedConfigurationNode> opToRemove = this.children
                .stream()
                .filter(node -> node.key() != null)
                .filter(node -> key.equals(node.key()))
                .findAny();
        if (opToRemove.isPresent()) {
            this.children.remove(opToRemove.get());
            return true;
        }
        return false;
    }

    @Override
    public <S, T, E extends Exception> T visit(
            ConfigurationVisitor<S, T, E> visitor, S state) throws E {
        throw new RuntimeException("Not implemented");
    }

    @Override
    public <S, T> T visit(ConfigurationVisitor.Safe<S, T> visitor, S state) {
        throw new RuntimeException("Not implemented");

    }

    @Override
    public <V> @Nullable V hint(RepresentationHint<V> hint) {
        throw new RuntimeException("Not implemented");
    }

    @Override
    public <V> @Nullable V ownHint(RepresentationHint<V> hint) {
        throw new RuntimeException("Not implemented");
    }

    @Override
    public Map<RepresentationHint<?>, ?> ownHints() {
        throw new RuntimeException("Not implemented");
    }

    private Class<?> getBasicType(Class<?> type) throws SerializationException {
        if (CharSequence.class.isAssignableFrom(type)) {
            return String.class;
        }
        if (type.isAssignableFrom(char.class) || type.isAssignableFrom(Character.class)) {
            return char.class;
        }
        if (type.isAssignableFrom(int.class) || type.isAssignableFrom(Integer.class)) {
            return int.class;
        }
        if (type.isAssignableFrom(double.class) || type.isAssignableFrom(Double.class)) {
            return double.class;
        }
        throw new SerializationException("Cannot accept type of " +
                type.getSimpleName() +
                " on set");
    }

    public String nodePath() {
        StringBuilder builder = new StringBuilder();
        MemoryConfigurationNode node = this;
        while (node != null && !node.key.equals("root")) {
            builder.append(node.key);
            node = this.parent;
        }
        return builder.toString();
    }

    @Override
    public int hashCode() {
        return this.nodePath().hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof MemoryConfigurationNode node)) {
            return false;
        }
        return this.nodePath().equals(node.nodePath());
    }
}
