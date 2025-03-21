package com.chiliasmstudio.ChunkDestroyer;

import com.chiliasmstudio.ChunkDestroyer.Commands.ConfirmRemove;
import com.chiliasmstudio.ChunkDestroyer.Commands.RemoveChunk;
import com.mojang.brigadier.arguments.LongArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.tree.LiteralCommandNode;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.Commands;
import io.papermc.paper.command.brigadier.argument.ArgumentTypes;
import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents;
import net.kyori.adventure.text.event.ClickEvent;
import org.bukkit.plugin.java.JavaPlugin;

public final class ChunkDestroyer extends JavaPlugin {

    public static ChunkDestroyer chunkDestroyer;

    @Override
    public void onEnable() {
        chunkDestroyer = this;
        EconomyIO economyIO = new EconomyIO(this);
        LiteralArgumentBuilder<CommandSourceStack> command = Commands.literal("chunkd")
                .then(Commands.literal("remove").executes(RemoveChunk::RemoveChunk)
                        .then(Commands.literal("confirm").executes(ConfirmRemove::ConfirmRemove)
                        )
                )
                ;

        LiteralCommandNode<CommandSourceStack> buildCommand = command.build();
        this.getLifecycleManager().registerEventHandler(LifecycleEvents.COMMANDS, commands -> {
            commands.registrar().register(buildCommand);
        });
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
