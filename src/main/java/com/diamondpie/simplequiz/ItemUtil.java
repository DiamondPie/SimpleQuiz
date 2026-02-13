package com.diamondpie.simplequiz;

import org.bukkit.inventory.ItemStack;

import org.jetbrains.annotations.Nullable;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ItemUtil {

    @Nullable
    public static String itemStackToBase64(ItemStack item, Logger logger) {
        if (item == null || item.getType().isAir()) return null;
        try {
            byte[] bytes = item.serializeAsBytes();
            return Base64.getEncoder().encodeToString(bytes);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Failed to serialize ItemStack to Base64", e);
            return null;
        }
    }

    @Nullable
    public static ItemStack itemStackFromBase64(String data, Logger logger) {
        if (data == null || data.isEmpty()) return null;
        try {
            byte[] bytes = Base64.getDecoder().decode(data);
            return ItemStack.deserializeBytes(bytes);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Failed to deserialize ItemStack from Base64", e);
            return null;
        }
    }
}
