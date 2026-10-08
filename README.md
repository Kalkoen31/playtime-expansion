## AI disclosure

The code and documentation in this repository were generated with Claude Sonnet 5.5 (Anthropic) and then reviewed and tested by the repository owner.

# Playtime Expansion for PlaceholderAPI

A small [PlaceholderAPI](https://wiki.placeholderapi.com/) expansion that gives every player a playtime placeholder. It uses the same placeholder for everyone and shows each player's own time played, so it works well in tab lists, scoreboards and chat formats.

It reads Minecraft's built-in play time statistic. It does not depend on any other plugin, and it stores no data of its own.

This is an unofficial project and is not affiliated with PlaceholderAPI or PaperMC.

## Placeholders

| Placeholder | Description | Example output |
|---|---|---|
| `%playtime_formatted%` | Readable playtime: minutes under 1 hour, hours and minutes under 1 day, otherwise days and hours | `45m`, `5h 12m`, `3d 7h` |
| `%playtime_hours%` | Total hours played | `79` |
| `%playtime_days%` | Total days played | `3` |
| `%playtime_minutes%` | Total minutes played | `4740` |
| `%playtime_seconds%` | Total seconds played | `284400` |

All values are rounded down. The `hours`, `days`, `minutes` and `seconds` placeholders are totals, not remainders. For example, 3 days and 7 hours of playtime gives `79` for `%playtime_hours%`.

An unknown placeholder, such as `%playtime_foo%`, is left unparsed by PlaceholderAPI. If no player is available (for example `/papi parse --null`), the result is empty.

## Requirements

- A Paper server (developed for Paper 26.2)
- [PlaceholderAPI](https://wiki.placeholderapi.com/)
- Java 8 or newer. The jar is compiled for Java 8, so it runs on whatever Java version your server already needs.

## Compatibility

The expansion is built and aimed at **Paper 26.2**. It only uses two long-standing Bukkit API calls (`Statistic.PLAY_ONE_MINUTE` and `OfflinePlayer#getStatistic`), so it should also work on other versions:

| Server version | Expected result |
|---|---|
| Paper 26.2.x and newer | Should work |
| Paper 1.15 – 1.21.x | Should work. `OfflinePlayer#getStatistic` is documented in the API from 1.15 on |
| Older than 1.15 | Unknown, may fail with a `NoSuchMethodError` when a placeholder is used |
| Spigot | Probably works, since only the Bukkit API is used, but not tested |

Only Paper 26.2 is actively tested. If you try another version, please report the result in an issue. Very old PlaceholderAPI versions have not been checked either.

If a placeholder is used on an unsupported version, you may see an error in the server console.

## Installation

1. Download the jar, or [build it yourself](#building).
2. If you used another playtime expansion before, remove its jar from `plugins/PlaceholderAPI/expansions/`. PlaceholderAPI will not load an external expansion if another one already uses the same identifier (`playtime`).
3. Put the jar in `plugins/PlaceholderAPI/expansions/`.
4. Run `/papi reload` (or restart the server).
5. Check that it works:

   ```
   /papi parse me %playtime_formatted%
   ```

## Usage

Use the placeholders anywhere a plugin supports PlaceholderAPI.

## How it works

- Playtime comes from the vanilla play time statistic (`Statistic.PLAY_ONE_MINUTE` in the Bukkit API, a misleading legacy name). It is counted in ticks, 20 per second.
- Statistics are stored per player **UUID**, not per username. Playtime survives a name change.
- Because this is the vanilla statistic, it includes time spent AFK, and it counts from the start of the player's statistics, not from when this expansion was installed.

## Building

You need JDK 25 and Maven 3.9 or newer. The JDK has to be 25 because the Paper 26.2 API is itself compiled for Java 25, even though the jar that is produced targets Java 8.

```
mvn clean package
```

The jar is created at `target/playtime-expansion-1.0.0.jar`.

JDK 25 prints a few warnings that Java 8 as a target is "obsolete". These are harmless.

## License

This project is licensed under the [GNU General Public License v3.0](LICENSE), or (at your option) any later version.
