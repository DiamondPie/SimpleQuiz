package com.diamondpie.simplequiz;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CommandSimpleQuiz implements CommandExecutor, TabCompleter {

    private final Main plugin;

    public CommandSimpleQuiz(Main plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length == 0) {
            if (!sender.hasPermission("simplequiz.use")) {
                sender.sendMessage(Component.text("你没有权限执行此命令", NamedTextColor.RED));
                return true;
            }
            sendInfo(sender);
            return true;
        }

        if (!sender.hasPermission("simplequiz.admin")) {
            sender.sendMessage(Component.text("你没有权限执行此命令", NamedTextColor.RED));
            return true;
        }

        String sub = args[0].toLowerCase();
        switch (sub) {
            case "reload":
                plugin.getQuizManager().reload();
                sender.sendMessage(Component.text("配置已重载", NamedTextColor.GREEN));
                break;
            case "start":
                String type = null;
                if (args.length > 1) {
                    if (args[1].equalsIgnoreCase("text")) type = "text";
                    if (args[1].equalsIgnoreCase("math")) type = "math";
                }
                if (plugin.getQuizManager().isQuizRunning()) {
                    sender.sendMessage(Component.text("当前已有问答正在进行中", NamedTextColor.RED));
                } else {
                    plugin.getQuizManager().startQuiz(type);
                    sender.sendMessage(Component.text("已强制开始问答", NamedTextColor.GREEN));
                }
                break;
            case "encodehand":
                if (!(sender instanceof Player player)) {
                    sender.sendMessage(Component.text("只有玩家可以使用此命令", NamedTextColor.RED));
                    return true;
                }
                ItemStack hand = player.getInventory().getItemInMainHand();
                if (hand.getType().isAir()) {
                    sender.sendMessage(Component.text("手中没有物品", NamedTextColor.RED));
                    return true;
                }
                String base64 = ItemUtil.itemStackToBase64(hand, plugin.getLogger());
                if (base64 != null) {
                    Component msg = Component.text("物品编码成功，点击复制: ", NamedTextColor.GREEN)
                            .append(Component.text("[点击复制]", NamedTextColor.AQUA)
                                    .clickEvent(ClickEvent.copyToClipboard(base64)));
                    sender.sendMessage(msg);
                } else {
                    sender.sendMessage(Component.text("编码失败", NamedTextColor.RED));
                }
                break;
            default:
                sender.sendMessage(Component.text("未知子命令", NamedTextColor.RED));
        }
        return true;
    }

    private void sendInfo(CommandSender sender) {
        Component title = Component.text("--- SimpleQuiz Info ---", NamedTextColor.GOLD);
        sender.sendMessage(title);
        sender.sendMessage(Component.text("版本: " + plugin.getPluginMeta().getVersion(), NamedTextColor.WHITE));
        sender.sendMessage(Component.text("开发者: DiamondPie", NamedTextColor.WHITE));

        long diff = plugin.getQuizManager().getNextRoundTime() - System.currentTimeMillis();
        String timeStr;
        if (plugin.getQuizManager().isQuizRunning()) {
            timeStr = "进行中";
        } else if (diff <= 0) {
            timeStr = "仍未开始";
        } else {
            timeStr = (diff / 1000) + "秒后";
        }
        sender.sendMessage(Component.text("下一轮: " + timeStr, NamedTextColor.AQUA));
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length == 1) {
            List<String> list = new ArrayList<>();
            if (sender.hasPermission("simplequiz.admin")) {
                list.add("reload");
                list.add("start");
                list.add("encodehand");
            }
            return filter(list, args[0]);
        }
        if (args.length == 2 && args[0].equalsIgnoreCase("start") && sender.hasPermission("simplequiz.admin")) {
            List<String> list = new ArrayList<>();
            list.add("text");
            list.add("math");
            return filter(list, args[1]);
        }
        return Collections.emptyList();
    }

    private List<String> filter(List<String> input, String check) {
        List<String> result = new ArrayList<>();
        for (String s : input) {
            if (s.toLowerCase().startsWith(check.toLowerCase())) result.add(s);
        }
        return result;
    }
}