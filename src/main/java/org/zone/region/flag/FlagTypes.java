package org.zone.region.flag;

import org.zone.region.flag.entity.monster.block.explode.creeper.CreeperGriefFlagType;
import org.zone.region.flag.entity.monster.block.explode.enderdragon.EnderDragonGriefFlagType;
import org.zone.region.flag.entity.monster.block.explode.wither.WitherGriefFlagType;
import org.zone.region.flag.entity.monster.block.hatch.EnderMiteGriefFlagType;
import org.zone.region.flag.entity.monster.block.ignite.SkeletonGriefFlagType;
import org.zone.region.flag.entity.monster.block.knock.ZombieGriefFlagType;
import org.zone.region.flag.entity.monster.block.take.EnderManGriefFlagType;
import org.zone.region.flag.entity.monster.move.PreventMonsterFlagType;
import org.zone.region.flag.entity.nonliving.block.farmland.FarmTramplingFlagType;
import org.zone.region.flag.entity.nonliving.block.tnt.TnTDefuseFlagType;
import org.zone.region.flag.entity.player.damage.attack.EntityDamagePlayerFlagType;
import org.zone.region.flag.entity.player.damage.fall.PlayerFallDamageFlagType;
import org.zone.region.flag.entity.player.damage.fire.PlayerFireDamageFlagType;
import org.zone.region.flag.entity.player.interact.block.destroy.BlockBreakFlagType;
import org.zone.region.flag.entity.player.interact.block.place.BlockPlaceFlagType;
import org.zone.region.flag.entity.player.interact.door.DoorInteractionFlagType;
import org.zone.region.flag.entity.player.interact.itemframe.ItemFrameInteractFlagType;
import org.zone.region.flag.entity.player.move.greetings.GreetingsFlagType;
import org.zone.region.flag.entity.player.move.leaving.LeavingFlagType;
import org.zone.region.flag.entity.player.move.preventing.PreventPlayersFlagType;
import org.zone.region.flag.meta.eco.balance.BalanceFlagType;
import org.zone.region.flag.meta.eco.payment.buy.BuyFlagType;
import org.zone.region.flag.meta.eco.shop.ShopsFlagType;
import org.zone.region.flag.meta.edit.EditingFlagType;
import org.zone.region.flag.meta.invite.InviteFlagType;
import org.zone.region.flag.meta.member.MembersFlagType;
import org.zone.region.flag.meta.request.join.JoinRequestFlagType;
import org.zone.region.flag.meta.request.visibility.ZoneVisibilityFlagType;
import org.zone.region.flag.meta.tag.TagsFlagType;
import org.zone.region.flag.meta.service.ban.flag.BanFlagType;

/**
 * All known default flag types found within the zones plugin
 *
 * @since 1.0.0
 */
public final class FlagTypes {

    public static final PreventMonsterFlagType PREVENT_MONSTER = new PreventMonsterFlagType();
    public static final ZoneVisibilityFlagType ZONE_VISIBILITY = new ZoneVisibilityFlagType();
    public static final FarmTramplingFlagType FARM_TRAMPLING = new FarmTramplingFlagType();
    public static final JoinRequestFlagType JOIN_REQUEST = new JoinRequestFlagType();
    public static final MembersFlagType MEMBERS = new MembersFlagType();
    public static final DoorInteractionFlagType DOOR_INTERACTION = new DoorInteractionFlagType();
    public static final BlockBreakFlagType BLOCK_BREAK = new BlockBreakFlagType();
    public static final BalanceFlagType ECO = new BalanceFlagType();
    public static final EditingFlagType EDITING = new EditingFlagType();
    public static final BlockPlaceFlagType BLOCK_PLACE = new BlockPlaceFlagType();
    public static final GreetingsFlagType GREETINGS = new GreetingsFlagType();
    public static final PreventPlayersFlagType PREVENT_PLAYERS = new PreventPlayersFlagType();
    public static final LeavingFlagType LEAVING = new LeavingFlagType();
    public static final TagsFlagType TAGS = new TagsFlagType();
    public static final ItemFrameInteractFlagType ITEM_FRAME_INTERACT = new ItemFrameInteractFlagType();
    public static final PlayerFireDamageFlagType PLAYER_FIRE_DAMAGE = new PlayerFireDamageFlagType();
    public static final EntityDamagePlayerFlagType ENTITY_DAMAGE_PLAYER = new EntityDamagePlayerFlagType();
    public static final PlayerFallDamageFlagType PLAYER_FALL_DAMAGE = new PlayerFallDamageFlagType();
    public static final BuyFlagType BUY = new BuyFlagType();
    public static final TnTDefuseFlagType TNT_DEFUSE = new TnTDefuseFlagType();
    public static final CreeperGriefFlagType CREEPER_GRIEF = new CreeperGriefFlagType();
    public static final EnderManGriefFlagType ENDER_MAN_GRIEF = new EnderManGriefFlagType();
    public static final ZombieGriefFlagType ZOMBIE_GRIEF = new ZombieGriefFlagType();
    public static final SkeletonGriefFlagType SKELETON_GRIEF = new SkeletonGriefFlagType();
    public static final EnderDragonGriefFlagType ENDER_DRAGON_GRIEF = new EnderDragonGriefFlagType();
    public static final WitherGriefFlagType WITHER_GRIEF = new WitherGriefFlagType();
    public static final EnderMiteGriefFlagType ENDER_MITE_GRIEF = new EnderMiteGriefFlagType();
    public static final ShopsFlagType SHOPS = new ShopsFlagType();
    public static final BanFlagType BAN = new BanFlagType();
    public static final InviteFlagType INVITE = new InviteFlagType();

    private FlagTypes() {
        throw new RuntimeException("Should not init");
    }

}
