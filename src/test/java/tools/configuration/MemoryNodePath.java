package tools.configuration;

import org.spongepowered.configurate.NodePath;

import java.util.Iterator;

public class MemoryNodePath implements NodePath {

    private final Object[] nodes;

    public MemoryNodePath(Object... nodes) {
        this.nodes = nodes;
    }

    @Override
    public Object get(int i) {
        return this.nodes[i];
    }

    @Override
    public int size() {
        return this.nodes.length;
    }

    @Override
    public Object[] array() {
        return this.nodes;
    }

    @Override
    public NodePath withAppendedChild(Object childKey) {
        throw new RuntimeException("not implemented");
    }

    @Override
    public NodePath with(int index, Object value) throws IndexOutOfBoundsException {
        throw new RuntimeException("not implemented");
    }

    @Override
    public NodePath plus(NodePath other) {
        throw new RuntimeException("not implemented");
    }

    @Override
    public Iterator<Object> iterator() {
        throw new RuntimeException("not implemented");
    }

    @Override
    public NodePath copy() {
        return new MemoryNodePath(this.nodes);
    }
}
