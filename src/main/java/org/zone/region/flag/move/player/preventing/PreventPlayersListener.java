package org.zone.region.flag.move.player.preventing;

import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.entity.MoveEntityEvent;
import org.spongepowered.api.event.filter.Getter;
import org.zone.ZonePlugin;
import org.zone.region.Zone;
import org.zone.region.flag.FlagTypes;
import org.zone.region.flag.move.player.greetings.GreetingsFlag;

import java.util.Optional;

public class PreventPlayersListener {
    @Listener
    public void onPlayerMove(MoveEntityEvent event, @Getter("entity") Player player){
        if (event.originalPosition().toInt().equals(event.destinationPosition().toInt())) {
            //ignores this event if the player didn't move, but instead rotated
            return;
        }

        Optional<Zone> opPreviousZone = ZonePlugin
                .getZonesPlugin()
                .getZoneManager()
                .getPriorityZone(event.entity().world(), event.originalPosition());

        if(opPreviousZone.isPresent()){
            /*
             Player is already in a zone. No need to prevent from them entering unless they are
             coming from one zone to another, that is out of scope of this tutorial
             */
            return;
        }

        Optional<Zone> opNextZone = ZonePlugin
                .getZonesPlugin()
                .getZoneManager()
                .getPriorityZone(event.entity().world(), event.destinationPosition());

        if(opNextZone.isEmpty()){
            //player is not moving into a zone, ignore this flag
            return;
        }

        Zone zone = opNextZone.get();
        Optional<PreventPlayersFlag> opFlag = zone.getFlag(FlagTypes.PREVENT_PLAYERS);
        if(opFlag.isEmpty()){

            return;
        }

        // gets the flag -> then gets the message -> then if the message is present -> send that message to the player
        if (opFlag.get().hasPermission(zone, player.uniqueId())) {
            return;
        }
        event.setCancelled(true);
    }

}