package com.chiliasmstudio.ChunkDestroyer.Commands;

import com.bekvon.bukkit.residence.Residence;
import com.bekvon.bukkit.residence.api.ResidenceApi;
import com.bekvon.bukkit.residence.containers.ResidencePlayer;
import com.bekvon.bukkit.residence.protection.ClaimedResidence;
import com.chiliasmstudio.ChunkDestroyer.ChunkDestroyer;
import com.chiliasmstudio.ChunkDestroyer.EconomyIO;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.tcoded.folialib.FoliaLib;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.threadedregions.scheduler.RegionScheduler;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.BlockState;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.awt.print.Paper;
import java.util.concurrent.TimeUnit;

public class ConfirmRemove {

    public static int ConfirmRemove(CommandContext<CommandSourceStack> ctx){
        final CommandSender sender = ctx.getSource().getSender();
        final Player player = (Player) sender;
        Location loc = player.getLocation().getBlock().getLocation();
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

        ClaimedResidence res = ResidenceApi.getResidenceManager().getByLoc(loc);
        if(res==null){
            sender.sendRichMessage("<red>You must do this in your residence</red>");
            return 0;
        }else {
            ResidencePlayer rPlayer = Residence.getInstance().getPlayerManager().getResidencePlayer(player);
            boolean canBreak= rPlayer.canBreakBlock(loc.getBlock(), true);
            if(!canBreak){
                sender.sendRichMessage("<red>no permission (residence)</red>");
                return 0;
            }
        }



        if(loc.getWorld().getEnvironment().equals(World.Environment.NORMAL)){
            EconomyIO.takeBalance(player,32500D);
            player.sendRichMessage("<green>已從您的帳戶扣除: 32500</green>");
            player.sendRichMessage("<green>確認執行, 注意安全!</green>");

            for (int y = 64; y >= -63; y--) {
                for (int x = 0; x <= 15; x++) {
                    for (int z = 0; z <= 15; z++) {
                        loc.getChunk().getBlock(x, y, z).setType(Material.AIR);
                    }
                }
            }
        }else if(loc.getWorld().getEnvironment().equals(World.Environment.NETHER)){
            EconomyIO.takeBalance(player,32500D);
            player.sendRichMessage("<green>已從您的帳戶扣除: 32500</green>");
            player.sendRichMessage("<green>確認執行, 注意安全!</green>");

            for (int y = 127; y >= 1; y--) {
                for (int x = 0; x <= 15; x++) {
                    for (int z = 0; z <= 15; z++) {
                        loc.getChunk().getBlock(x, y, z).setType(Material.AIR);
                    }
                }
            }
        }else if(loc.getWorld().getEnvironment().equals(World.Environment.THE_END)){
            sender.sendRichMessage("<red>Illegal operation</red>");
            return 0;
        }





        player.sendRichMessage("<green>作業完成!</green>");
        return Command.SINGLE_SUCCESS;
    }

}
