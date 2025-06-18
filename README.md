# Simple Shulkers

![](https://github.com/eightyseven76/simpleshulkers/blob/015709a2ac560ece99f978b13a50918f3fbe746a/images/shulkerboxes.gif)
## 🌟 Quick Overview

Tired of early-game inventory struggles and constant trips back to base? Simple Shulkers revolutionizes your early Minecraft adventures by introducing lightweight, wooden shulker boxes! Craftable from common Overworld resources, these portable storage solutions offer much-needed inventory expansion long before you even think about venturing into The End. Say goodbye to clutter and hello to seamless, on-the-go storage with Oak, Spruce, and Birch shulker boxes, all designed to feel like a natural extension of vanilla Minecraft.

## 📋 Key Information

* **Mod Author**: EightySeven
* **Mod Version**: 1.0.1+neoforge
* **Supported Minecraft Version**: 1.21.1
* **Required Mod Loader**: NeoForge 21.1.172 (or compatible versions)
* **License**: MIT License
* **Mod ID**: `simpleshulkers`
* **Client/Server**: Works on both client and server. Required on both for multiplayer.
* **Download Links**:
    * [CurseForge](https://www.curseforge.com/minecraft/mc-mods/simple-shulkers)
    * [Modrinth](https://modrinth.com/mod/simple-shulkers)
* **Issue Tracker/Support**:
    * [GitHub Issues page](https://github.com/eightyseven76/simpleshulkers/issues)

## 📖 About Simple Shulkers: Early Game Portability!

Simple Shulkers grants the wish of early-game shulker box access! This mod introduces a new, accessible tier of movable storage crafted from common woods, giving you a taste of portable convenience in the early to mid-game. No more tedious back-and-forth trips to your base when you're out exploring, mining, or building – just grab your wooden shulker box and keep your essentials at hand!

## ✨ Core Features:

* **Wooden Shulker Boxes**: Adds three new, distinct types of shulker boxes: Oak, Spruce, Birch, Acacia, and Jungle..
* **Early-Game Crafting**: Obtainable with readily available Overworld resources like logs and vanilla chests.
* **Portable Storage**: Each wooden shulker box offers a practical 9 inventory slots.
* **Vanilla-Friendly Mechanics**: Designed to integrate seamlessly, feeling like a natural part of Minecraft.

## 🛠️ Feature Deep Dive

### 📦 Wooden Shulker Boxes & Crafting

Get your hands on these handy storage solutions with a straightforward two-step crafting process:

1.  **Craft Wooden Shulker Shells**:
    You'll first need to craft specialized wooden shulker shells from their respective logs. Each recipe uses 5 logs in a upside down 'U' shape with an extra log on each side of the top row.
    * **Oak Shulker Shell**: Crafted using 5 Stripped Oak Logs.
    * **Spruce Shulker Shell**: Crafted using 5 Stripped Spruce Logs.
    * **Birch Shulker Shell**: Crafted using 5 Stripped Birch Logs.
    * **Acacia Shulker Shell**: Crafted using 5 Stripped Acacia Logs.
    * **Jungle Shulker Shell**: Crafted using 5 Stripped Jungle Logs.
    * **Acacia Shulker Shell**: Crafted using 5 Stripped Dark Oak Logs.
    * **Jungle Shulker Shell**: Crafted using 5 Stripped Mangrove Logs.
    * **Jungle Shulker Shell**: Crafted using 5 Stripped Cherry Logs.
      
    ![Shulker Shell Recipes](https://github.com/eightyseven76/simpleshulkers/blob/015709a2ac560ece99f978b13a50918f3fbe746a/images/shulkershellrecipes.gif)
3.  **Craft Wooden Shulker Boxes**:
    Once you have two shulker shells of the same wood type, combine them with a standard vanilla Chest to create your wooden shulker box. The recipe is a vertical arrangement: Shell, Chest, Shell.
    * **Oak Shulker Box**: Crafted with 2 Oak Shulker Shells and 1 Chest.
    * **Spruce Shulker Box**: Crafted with 2 Spruce Shulker Shells and 1 Chest.
    * **Birch Shulker Box**: Crafted with 2 Birch Shulker Shells and 1 Chest.
    * **Acacia Shulker Box**: Crafted with 2 Acacia Shulker Shells and 1 Chest.
    * **Jungle Shulker Box**: Crafted with 2 Jungle Shulker Shells and 1 Chest.
    * **Dark Oak Shulker Box**: Crafted with 2 Dark Oa Shulker Shells and 1 Chest.
    * **Mangrove Shulker Box**: Crafted with 2 Mangrove Shulker Shells and 1 Chest.
    * **Cherry Shulker Box**: Crafted with 2 Cherry Shulker Shells and 1 Chest.
      
    ![Wooden Shulker Box Recipe](https://github.com/eightyseven76/simpleshulkers/blob/015709a2ac560ece99f978b13a50918f3fbe746a/images/shulkerboxrecipes.gif)

### ⚙️ Functionality & Behavior

Our wooden shulker boxes are designed to be intuitive and useful:

* **Inventory Space**: Each box provides 9 slots to store your items.
* **Smooth Animations & Sounds**: Enjoy satisfying opening and closing animations, complete with distinct sound effects.
* **Item Restrictions**: Just like their vanilla counterparts, wooden shulker boxes cannot be placed inside other shulker boxes (vanilla or wooden), based on the `simpleshulkers:disallowed_in_shulker_boxes` item tag.
* **At-a-Glance Tooltip**: Hover over a wooden shulker box in your inventory to instantly see its contents!
    * Displays up to 5 items.
    * If more than 5 items are present, it will show "\[X] more items...".
    * If empty, the tooltip will clearly state "Empty".
* **Preserves Contents**: When a placed wooden shulker box is broken, it drops itself as an item, keeping all its contents safely inside.
* **Dispenser Friendly**: Wooden shulker boxes can be placed and dispensed by Dispensers, just like vanilla shulker boxes.

### 🎨 Creative Mode Integration

All new items, including the wooden shulker shells and the three types of wooden shulker boxes, are conveniently located under a dedicated "Simple Shulkers" tab in the Creative inventory for easy access.

## 🚀 Getting Started: Installation (NeoForge Example)

1.  Ensure Minecraft 1.21.1 is installed.
2.  **Install NeoForge**: Download and install NeoForge version 21.1.172 (or a compatible version for Minecraft 1.21.1). You can find installation guides on the official NeoForge website.
3.  **Download Simple Shulkers**: Grab the latest version of the Simple Shulkers mod from one of the official download links provided in the "Key Information" section above.
4.  **Install the Mod**: Place the downloaded .jar file directly into your `.minecraft/mods` folder. If the mods folder doesn't exist, simply create it.
5.  **Launch Minecraft**: Select your NeoForge profile in the Minecraft Launcher and hit Play!

## 🔧 Configuration

Currently, Simple Shulkers (v1.0.3) does not have any specific *user-facing* configuration options, though the code includes flags to enable/disable each box type. Future updates may introduce configurable settings based on community feedback.

## 🤝 Compatibility

Simple Shulkers is designed to be compatible with most other mods. As this is an early release, if you encounter any compatibility issues, please report them to our issue tracker!
* **Known Incompatibilities**: None at this time.

## 💬 Support & Bug Reporting

Encounter an issue or have a suggestion? We'd love to hear from you!

* **Preferred Method: GitHub Issues**: Please report all bugs, crashes, and detailed suggestions on our [GitHub Issues page](https://github.com/eightyseven76/simpleshulkers/issues).
    When reporting, please include:
    * Mod Version (e.g., simpleshulkers-1.0.1+neoforge)
    * Minecraft Version (1.21.1)
    * NeoForge Version (21.1.172)
    * A list of other mods installed (if any)
    * Clear steps to reproduce the issue
    * Any relevant crash logs (use a service like Pastebin for long logs)
## ❓ Frequently Asked Questions (FAQ)

* **Q: Can I use this mod in my modpack?**
    * A: Yes! You are absolutely free to include Simple Shulkers in your modpacks under the terms of the MIT License. We appreciate attribution to the mod and a link back to this page, but it's not strictly required by the license.
* **Q: Will you be adding more wood types or features?**
    * A: We've already added Dark Oak, Mangrove and Cherry wood types in the latest update! We are always open to feedback and suggestions for additional wood types or other features. Join our community discussions or create a suggestion on our [GitHub Issues page](https://github.com/eightyseven76/simpleshulkers/issues).
* **Q: Is this mod compatible with other storage mods like Refined Storage or Applied Energistics 2?**
    * A: Simple Shulkers is designed to be highly compatible. While we don't have specific integrations, they function like vanilla shulker boxes and should generally work. If you find issues, please report them!
* **Q: Will this mod be updated for future Minecraft versions?**
    * A: Our goal is to keep Simple Shulkers updated as long as development remains feasible and there is community interest.
* **Q: Why don't the wooden shulker boxes offer more inventory slots?**
    * A: The 9-slot design is intentional to provide a significant early-game boost without replacing vanilla shulker boxes, maintaining game progression.

## 💡 Planned Features

We have lots of ideas for the future of Simple Shulkers! Here's a glimpse of what we're considering:

* **More Wood Types**: Expanding the collection to include shulker boxes for all vanilla wood types (Crimson, Warped etc.).
* **Dyeable Wooden Shulkers**: Adding the ability to dye wooden shulker boxes for better organization and personalization, just like vanilla ones.
* **Potential Upgrades**: Exploring balanced upgrade paths for wooden shulkers (e.g., slightly increased capacity or minor utility features) while keeping them distinct from End-game shulkers.
* **Enhanced Configuration**: Implementing more detailed server-side configuration options for pack makers, such as tweaking recipes, capacities, or which shulkers are enabled.
* **Visual Polish**: Continuously improving models, textures, and animations for a premium feel.

Have an idea? Let us know on our [GitHub Issues page](https://github.com/eightyseven76/simpleshulkers/issues)!

## 📜 Changelog
**Version 1.0.3 (Release Date: JUNE 18th, 2025)**
* **Added**:
    * Dark Oak Shulker Box, Dark Oak Shulker Shell
    * Mangrove Shulker Box, Mangrove Shulker Shell
    * Cherry Shulker Box, Cherry Shulker Shell
 
* **Changes**:
  * Updated the recipe to use stripped logs instead of regular logs for crafting shulker shells.
  * Adjusted the textures of all shulker shell items and shulker boxes to reflect this change.

**Version 1.0.2 (Release Date: JUNE 1st, 2025)**
* **Added**:
    * Acacia Shulker Box, Acacia Shulker Shell
    * Jungle Shulker Box, Jungle Shulker Shell

* **Fixes**:
    * Fixed some textures for existing wooden shulker boxes.

**Version 1.0.1 (Release Date: MAY 30th, 2025)**
* **Added**:
    * Piston compatibility.
 
* **Fixes**:
    * Fixed some piston/dispenser bugs.
    * Now works almost similar to vanilla shulker boxes.


Fixed some piston/dispenser bugs.
**Version 1.0.0-alpha (Release Date: MAY 28th, 2025)**

* **Added**:
    * Oak Shulker Box, Spruce Shulker Box, Birch Shulker Box
    * Oak Shulker Shell, Spruce Shulker Shell, Birch Shulker Shell
    * Crafting recipes for all new shells and boxes.
    * Open/close animations and sound effects.
    * Tooltip display for contents.
    * Dispenser compatibility.
    * "Simple Shulkers" Creative Mode tab.

## ⚖️ Permissions & Licensing

* **Modpack Policy**: Feel free to include this mod in your modpacks under the terms of the MIT License; attribution is appreciated!
* **License**: This mod is distributed under the MIT License.

## ❤️ Credits & Acknowledgements

* **Mod Author**: EightySeven

## 🎉 Enjoy Your Early Game Storage!

We hope Simple Shulkers makes your early Minecraft experience smoother and more enjoyable. Download it today and let us know what you think! Your feedback is invaluable.
