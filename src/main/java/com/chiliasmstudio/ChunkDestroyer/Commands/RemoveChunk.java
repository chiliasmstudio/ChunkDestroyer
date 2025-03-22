package com.chiliasmstudio.ChunkDestroyer.Commands;

import com.chiliasmstudio.ChunkDestroyer.EconomyIO;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class RemoveChunk {

    public static int RemoveChunk(CommandContext<CommandSourceStack> ctx) throws CommandSyntaxException {
        final CommandSender sender = ctx.getSource().getSender();
        final Player player = (Player) sender;
        if (!sender.hasPermission("ChunkDestroyer.remove")) {
            sender.sendRichMessage("<red>no permission</red>");
            return 0;
        } else if (!(sender instanceof Player)) {
            sender.sendRichMessage("<red>Only players can do this</red>");
            return 0;
        }else if(!EconomyIO.hasBlance(player,32500D)){
            sender.sendRichMessage("<red>餘額不足</red>");
            return 0;
        }

        Location loc = player.getLocation();

        if(loc.getWorld().getEnvironment().equals(World.Environment.NORMAL)){
            TextComponent msgc = Component.text("請點此")
                    .color(TextColor.color(0,255,0))
                    .hoverEvent(HoverEvent.showText(Component.text("點此執行")));
            TextComponent msg = Component.text("如果確定要執行 ");
            msgc = msgc.clickEvent(ClickEvent.clickEvent(ClickEvent.Action.RUN_COMMAND,"/chunkd remove confirm"));
            player.sendRichMessage("<red>這會移除你目前所在的區塊y64以下的方塊</red>");
            player.sendRichMessage("<red>執行前請確認所在位置安全</red>");
            player.sendMessage(msg.append(msgc));
        }else if(loc.getWorld().getEnvironment().equals(World.Environment.NETHER)){
            TextComponent msgc = Component.text("請點此")
                    .color(TextColor.color(0,255,0))
                    .hoverEvent(HoverEvent.showText(Component.text("點此執行")));
            TextComponent msg = Component.text("如果確定要執行 ");
            msgc = msgc.clickEvent(ClickEvent.clickEvent(ClickEvent.Action.RUN_COMMAND,"/chunkd remove confirm"));
            player.sendRichMessage("<red>這會移除你目前所在的區塊y127以下的方塊</red>");
            player.sendRichMessage("<red>執行前請確認所在位置安全</red>");
            player.sendMessage(msg.append(msgc));
        }else if(loc.getWorld().getEnvironment().equals(World.Environment.THE_END)){
            sender.sendRichMessage("<red>Illegal operation</red>");
            return 0;
        }



        return Command.SINGLE_SUCCESS;
    }
}
