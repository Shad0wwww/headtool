<p align="center">
  <img src="core/src/main/resources/assets/mintmc/textures/icon.png" alt="headtool icon" width="96">
</p>

<h1 align="center">headtool</h1>

<p align="center">
  A <a href="https://www.labymod.net/">Laby Client</a> addon for player heads: see whose head you are looking at and copy its texture with one key.
</p>

## Features

- **Looked At Head widget**: a HUD widget that shows the owner name of the player head under your crosshair. It hides itself when you're not looking at a player head.
- **Copy head texture**: press a key of your choice while looking at a player head to copy its texture value (the base64 `textures` property) to the clipboard. That's the value head databases and `/give` commands use. A message above the hotbar tells you whether it worked.

## Installation

1. Download `mintmc-release.jar` from the [latest release](https://github.com/Shad0wwww/headtool/releases/latest).
2. Put it in the `labymod-neo/addons` folder of your Minecraft directory.
3. Start Minecraft with the Laby Client.

## Usage

### Widget

Open the widget editor, find **Looked At Head** in the **Headtool** category and drag it onto your screen.

### Copy a head texture

1. Open the Laby Client settings and go to **Addons → headtool**.
2. Set a key for **Copy Head Texture**. No key is set by default.
3. In game, look at a player head and press the key.

The key only works while you're in a world with no menu or chat open.

## Settings

| Setting | Default | Description |
| --- | --- | --- |
| Enabled | On | Turns the whole addon on or off. |
| Copy Head Texture | None | The key that copies the texture of the looked-at head. |

## Building from source

Requires JDK 21 or newer.

```sh
./gradlew build createReleaseJar
```

The jar ends up in `build/libs/*-release.jar`. To start a dev client with the addon loaded, run `./gradlew :game-runner:client_v1.21.11`, using any version from `net.labymod.minecraft-versions` in `gradle.properties`.

## Project layout

| Path | Contents |
| --- | --- |
| `core/src/main/java/com/magnus/mintmc/core/` | Addon main class, config, HUD widget and key listener |
| `core/src/main/resources/assets/mintmc/` | Translations (`i18n/en_us.json`) and the icon |
| `api/` | Public API for other addons (currently empty) |
| `game-runner/` | Minecraft version-specific code (currently none) |
