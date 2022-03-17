package tools;

import org.spongepowered.api.block.BlockState;
import org.spongepowered.api.data.Key;
import org.spongepowered.api.data.persistence.DataContainer;
import org.spongepowered.api.data.persistence.DataView;
import org.spongepowered.api.data.persistence.InvalidDataException;
import org.spongepowered.api.data.value.MergeFunction;
import org.spongepowered.api.data.value.Value;
import org.spongepowered.api.world.LocatableBlock;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import java.util.Optional;
import java.util.Set;
import java.util.function.Function;

public class TestLocatableBlock implements LocatableBlock {

    public Location<?, ?> location;


    @Override
    public BlockState blockState() {
        throw new RuntimeException("Not implemented");
    }

    @Override
    public LocatableBlock withRawData(DataView container) throws InvalidDataException {
        throw new RuntimeException("Not implemented");
    }

    @Override
    public boolean validateRawData(DataView container) {
        throw new RuntimeException("Not implemented");
    }

    @Override
    public LocatableBlock copy() {
        TestLocatableBlock block = new TestLocatableBlock();
        block.location = this.location;
        return block;
    }

    @Override
    public <E> Optional<LocatableBlock> transform(
            Key<? extends Value<E>> key, Function<E, E> function) {
        throw new RuntimeException("Not implemented");
    }

    @Override
    public <E> Optional<LocatableBlock> with(Key<? extends Value<E>> key, E value) {
        throw new RuntimeException("Not implemented");
    }

    @Override
    public Optional<LocatableBlock> with(Value<?> value) {
        throw new RuntimeException("Not implemented");
    }

    @Override
    public Optional<LocatableBlock> without(Key<?> key) {
        throw new RuntimeException("Not implemented");
    }

    @Override
    public LocatableBlock mergeWith(
            LocatableBlock that, MergeFunction function) {
        throw new RuntimeException("Not implemented");
    }

    @Override
    public int contentVersion() {
        return 0;
    }

    @Override
    public DataContainer toContainer() {
        throw new RuntimeException("Not implemented");
    }

    @Override
    public <E> Optional<E> get(Key<? extends Value<E>> key) {
        throw new RuntimeException("Not implemented");
    }

    @Override
    public <E, V extends Value<E>> Optional<V> getValue(Key<V> key) {
        throw new RuntimeException("Not implemented");
    }

    @Override
    public boolean supports(Key<?> key) {
        throw new RuntimeException("Not implemented");
    }

    @Override
    public Set<Key<?>> getKeys() {
        throw new RuntimeException("Not implemented");
    }

    @Override
    public Set<Value.Immutable<?>> getValues() {
        throw new RuntimeException("Not implemented");
    }

    @Override
    public World<?, ?> world() {
        if (this.location == null) {
            throw new RuntimeException("Location must be set in LocatableBlock");
        }
        return this.location.world();
    }

    @Override
    public Location<?, ?> location() {
        if (this.location == null) {
            throw new RuntimeException("Location must be set in LocatableBlock");
        }
        return this.location;
    }
}
