package org.alexdev.havana.messages.incoming.rooms;

import org.alexdev.havana.game.player.Player;
import org.alexdev.havana.messages.outgoing.rooms.FLOOR_MAP;
import org.alexdev.havana.messages.outgoing.rooms.HEIGHTMAP;
import org.alexdev.havana.messages.outgoing.rooms.HEIGHTMAP_UPDATE;
import org.alexdev.havana.messages.outgoing.rooms.user.USER_OBJECTS;
import org.alexdev.havana.messages.outgoing.rooms.user.YOUARESPECTATOR;
import org.alexdev.havana.messages.types.MessageEvent;
import org.alexdev.havana.server.netty.streams.NettyRequest;
import org.alexdev.havana.game.games.player.GamePlayer;

import java.util.List;

public class GET_FLOORMAP implements MessageEvent {
    @Override
    public void handle(Player player, NettyRequest reader) throws Exception {
        if (player.getRoomUser().getRoom() == null) {
            return;
        }

        player.send(new FLOOR_MAP(player.getRoomUser().getRoom().getModel()));

        if (!player.getRoomUser().getRoom().isPublicRoom()) {
            player.send(new HEIGHTMAP_UPDATE(player.getRoomUser().getRoom(), player.getRoomUser().getRoom().getModel()));
        }

        player.send(new USER_OBJECTS(List.of()));

        GamePlayer gamePlayer = player.getRoomUser().getGamePlayer();

        if (gamePlayer != null && gamePlayer.isSpectator()) {
            player.send(new YOUARESPECTATOR());
        }
    }
}
