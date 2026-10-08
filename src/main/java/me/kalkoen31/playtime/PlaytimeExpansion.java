/*
 * Playtime Expansion for PlaceholderAPI
 * Copyright (C) 2026 kalkoen31
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */
package me.kalkoen31.playtime;

import java.util.Locale;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.OfflinePlayer;
import org.bukkit.Statistic;
import org.jetbrains.annotations.NotNull;

/**
 * External PlaceholderAPI expansion: drop the jar into plugins/PlaceholderAPI/expansions/.
 * Reads the vanilla play time statistic (in ticks, 20 per second).
 */
public class PlaytimeExpansion extends PlaceholderExpansion {

    @Override
    public @NotNull String getIdentifier() {
        return "playtime";
    }

    @Override
    public @NotNull String getAuthor() {
        return "kalkoen31"; // change this
    }

    @Override
    public @NotNull String getVersion() {
        return "1.0.0";
    }

    // No persist() override: that is only for expansions registered from inside a plugin.
    // An external jar should be reloaded by /papi reload, which is the default behaviour.

    @Override
    public String onRequest(OfflinePlayer player, @NotNull String params) {
        if (player == null) {
            return "";
        }

        // Statistic.PLAY_ONE_MINUTE is the (misleadingly named) play time statistic, in ticks.
        long totalSeconds = player.getStatistic(Statistic.PLAY_ONE_MINUTE) / 20L;
        long totalMinutes = totalSeconds / 60L;
        long totalHours = totalMinutes / 60L;
        long totalDays = totalHours / 24L;

        switch (params.toLowerCase(Locale.ROOT)) {
            case "formatted":
                if (totalMinutes < 60) return totalMinutes + "m";
                if (totalHours < 24) return totalHours + "h " + (totalMinutes % 60) + "m";
                return totalDays + "d " + (totalHours % 24) + "h";
            case "seconds":
                return String.valueOf(totalSeconds);
            case "minutes":
                return String.valueOf(totalMinutes);
            case "hours":
                return String.valueOf(totalHours);
            case "days":
                return String.valueOf(totalDays);
            default:
                return null; // tells PlaceholderAPI the placeholder is invalid
        }
    }
}