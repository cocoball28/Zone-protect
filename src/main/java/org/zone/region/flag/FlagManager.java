package org.zone.region.flag;

import org.zone.Identifiable;

import java.util.*;

public class FlagManager {

    private Collection<FlagType<?>> flags = new TreeSet<>(Comparator.comparing(Identifiable::getId));

    public Collection<FlagType<?>> getRegistered(){
        return Collections.unmodifiableCollection(this.flags);
    }

    public void register(FlagType<?> type){
        this.flags.add(type);
    }
}
