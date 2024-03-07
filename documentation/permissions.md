# Permissions

These are all permissions available in WorldShaper.<br/>
If a player has any of these permissions, they will see the WorldShaper join message upon joining the server and get
access to the `/worldshaper` command.<br/>
If a player has any `worldshaper.builder.command.edit.*` permission, they automatically get access to the
`/undo` and `/redo` commands.

## Shortcut Permissions

All permissions are categorized into builder permissions and admin permissions. This makes it possible to quickly give
broad permissions to people without having to worry about detailed permissions for all commands / actions. These will
be sufficient for most servers.

| Permission            | Description                                          |
|-----------------------|------------------------------------------------------|
| worldshaper.builder.* | Gives all builder related permissions to the player. |
| worldshaper.admin.*   | Gives all admin related permissions to the player.   |
| worldshaper.*         | Gives all permissions to the player.                 |

## Detailed Permissions

The detailed permissions allow you to make detailed configurations to your players' WorldShaper permissions.<br/>You
can combine the shortcut permissions with negative permissions to give players broad access to WorldShaper features but
then deny certain individual permissions.

| Permission                                   | Description                                                                                                                                                                                                                 |
|----------------------------------------------|-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| worldshaper.builder.command.util.worldshaper | Permission to use the `/worldshaper` command.                                                                                                                                                                               |
| worldshaper.builder.command.selection        | Permission to modify one's selection. This includes the ability to use the WorldShaper wand as well as the following commands: `/wand`, `/selectiontype` `/pos`, `/clearpositions`, `/removepos` and `/positions` commands. |
| worldshaper.builder.command.area.areatype    | Permission to use the `/areatype` command.                                                                                                                                                                                  |
| worldshaper.builder.command.area.expand      | Permission to use the `/expand` command.                                                                                                                                                                                    |
| worldshaper.builder.command.area.retract     | Permission to use the `/retract` command.                                                                                                                                                                                   |
| worldshaper.builder.command.area.movearea    | Permission to use the `/movearea` command.                                                                                                                                                                                  |
| worldshaper.builder.command.edit.set         | Permission to use the `/set` command.                                                                                                                                                                                       |
| worldshaper.builder.command.edit.replace     | Permission to use the `/replace` command.                                                                                                                                                                                   |
| worldshaper.builder.command.edit.ceiling     | Permission to use the `/ceiling` command.                                                                                                                                                                                   |
| worldshaper.builder.command.edit.floor       | Permission to use the `/floor` command.                                                                                                                                                                                     |
| worldshaper.builder.command.edit.hull        | Permission to use the `/hull` command.                                                                                                                                                                                      |
| worldshaper.builder.command.edit.walls       | Permission to use the `/walls` command.                                                                                                                                                                                     |
