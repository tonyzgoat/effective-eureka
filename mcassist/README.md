# MCAssist

A legitimate Fabric client-side utility mod for Minecraft 1.21.4.

## Features
- Toggleable HUD
- Coordinates, FPS, biome, and system clock
- Simple waypoint saving
- Simple hotbar sorting
- Optional custom crosshair overlay
- Persistent JSON config saved to `config/mcassist.json`
- In-game config screen via Mod Menu when installed
- Admin Command Panel for manually prefilling or copying preset admin commands
- TNT-cart setup assistant with item checks and manual step prompts only

## Default keybinds
- Right Shift: toggle HUD
- = : add waypoint
- \\ : cycle crosshair style
- H : sort hotbar

## Build
```bash
./gradlew build
```

The built jar should appear under `build/libs/`.

## GitHub Actions build
This project includes a workflow at `.github/workflows/build.yml`.

To use it:
1. Create a GitHub repository
2. Upload the contents of the `mcassist` project
3. Push to `main` or run the workflow manually from the Actions tab
4. Download the built `.jar` from the workflow artifacts (`mcassist-build`)
