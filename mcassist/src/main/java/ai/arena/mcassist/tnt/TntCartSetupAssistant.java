package ai.arena.mcassist.tnt;

import ai.arena.mcassist.ChatUtil;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.item.BowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.text.Text;

public final class TntCartSetupAssistant {
    private TntCartSetupAssistant() {}

    public static boolean hasRequiredItems(ClientPlayerEntity player) {
        return findHotbarSlot(player, Items.RAIL) >= 0
                && findHotbarSlot(player, Items.TNT_MINECART) >= 0
                && findHotbarSlot(player, Items.BOW) >= 0
                && countHotbarItem(player, Items.ARROW) > 0;
    }

    public static int findHotbarSlot(ClientPlayerEntity player, net.minecraft.item.Item item) {
        for (int i = 0; i < 9; i++) {
            ItemStack stack = player.getInventory().getStack(i);
            if (!stack.isEmpty() && stack.isOf(item)) {
                return i;
            }
        }
        return -1;
    }

    public static int countHotbarItem(ClientPlayerEntity player, net.minecraft.item.Item item) {
        int count = 0;
        for (int i = 0; i < 9; i++) {
            ItemStack stack = player.getInventory().getStack(i);
            if (!stack.isEmpty() && stack.isOf(item)) {
                count += stack.getCount();
            }
        }
        return count;
    }

    public static boolean isHoldingUsableBow(ClientPlayerEntity player) {
        return player.getMainHandStack().getItem() instanceof BowItem || player.getOffHandStack().getItem() instanceof BowItem;
    }

    public static void announceStatus(MinecraftClient client) {
        if (client.player == null) return;
        if (!hasRequiredItems(client.player)) {
            ChatUtil.sendClientMessage(client, "Missing items: need Rail, TNT Minecart, Bow, and Arrow in hotbar.");
            return;
        }
        ChatUtil.sendClientMessage(client, "TNT-cart setup assistant ready.");
        ChatUtil.sendClientMessage(client, "Step 1: Place a rail on the targeted block.");
        ChatUtil.sendClientMessage(client, "Step 2: Place a TNT minecart on that rail.");
        ChatUtil.sendClientMessage(client, "Step 3: Switch to your bow and fire manually when safe.");
    }

    public static Text getOverlayText(MinecraftClient client) {
        if (client.player == null) return Text.literal("No player");
        if (!hasRequiredItems(client.player)) {
            return Text.literal("TNT Assist: Missing Rail / TNT Minecart / Bow / Arrow");
        }
        int rail = findHotbarSlot(client.player, Items.RAIL);
        int cart = findHotbarSlot(client.player, Items.TNT_MINECART);
        int bow = findHotbarSlot(client.player, Items.BOW);
        return Text.literal("TNT Assist Ready | Rail:" + (rail + 1) + " Cart:" + (cart + 1) + " Bow:" + (bow + 1));
    }
}
