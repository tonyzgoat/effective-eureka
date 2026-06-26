package ai.arena.mcassist;

import net.minecraft.client.MinecraftClient;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public final class InventoryHelper {
    private InventoryHelper() {}

    public static void sortHotbar(MinecraftClient client) {
        if (client.player == null || client.interactionManager == null) return;

        var inventory = client.player.getInventory();
        List<ItemStack> stacks = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            stacks.add(inventory.getStack(i).copy());
        }

        stacks.sort(Comparator.comparing(stack -> stack.getItem().getName().getString()));

        for (int i = 0; i < 9; i++) {
            inventory.setStack(i, stacks.get(i));
        }

        ChatUtil.sendClientMessage(client, "Sorted hotbar alphabetically.");
    }
}
