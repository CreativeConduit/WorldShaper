# Permissions

These are all permissions available in WorldShaper.<br/>If a player has any of these permissions, they will see the
WorldShaper join message upon joining the server and get access to the `/worldshaper` command.

## Shortcut Permissions

Shortcut permissions can be used to quickly give broad permissions to people without having to worry about detailed
permissions for all commands / actions. These will be sufficient for most servers. Note that these will override detail
permissions.

| Permission          | Description                                                    |
|---------------------|----------------------------------------------------------------|
| worldshaper.builder | Gives all builder related permissions to the player.           |
| worldshaper.admin   | Gives all admin and builder related permissions to the player. |

## Detailed Permissions

The detailed permissions allow you to make detailed configurations to your players' WorldShaper permissions.<br/>Note
that these will always be overridden by the shortcut permissions. This means that even giving a negative permission
will not prevent a player from using a command / interaction if they have the corresponding shortcut permission.

| Permission                           | Description                                                                                                                                                        |
|--------------------------------------|--------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| worldshaper.interact.wand            | Permission to use the WorldShaper wand to make and edit selections. Also grants permission to the `/wand` and `/selectiontype` commands.                           |
| worldshaper.command.util.worldshaper | Permission to use the `/worldshaper` command.                                                                                                                      |
| worldshaper.command.selection        | Permission to modify one's selection. These are the `/pos`, `/clearpositions`, `/removepos` and `/positions` commands.                                             |
| worldshaper.command.area.areatype    | Permission to use the `/areatype` command.                                                                                                                         |
| worldshaper.command.area.expand      | Permission to use the `/expand` command.                                                                                                                           |
| worldshaper.command.area.retract     | Permission to use the `/retract` command.                                                                                                                          |
| worldshaper.command.area.movearea    | Permission to use the `/movearea` command.                                                                                                                         |
| worldshaper.command.edit.history     | Permission to use the `/undo` and `/redo` commands. This permission is also implicitly granted if the player has any other `worldshaper.command.edit.*` permission |
| worldshaper.command.edit.set         | Permission to use the `/set` command.                                                                                                                              |
| worldshaper.command.edit.replace     | Permission to use the `/replace` command.                                                                                                                          |
| worldshaper.command.edit.ceiling     | Permission to use the `/ceiling` command.                                                                                                                          |
| worldshaper.command.edit.floor       | Permission to use the `/floor` command.                                                                                                                            |
| worldshaper.command.edit.hull        | Permission to use the `/hull` command.                                                                                                                             |
| worldshaper.command.edit.walls       | Permission to use the `/walls` command.                                                                                                                            |
