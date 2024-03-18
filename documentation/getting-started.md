# Getting Started

This is a quick start guide that explains the basics of WorldShaper and gets you going within a few minutes.

## The Wand

To get started, you want to get yourself the Selection Wand by calling `/wand`.

## The Selection

Every player has a Selection. The Selection is a list of control points that is used for many things, most notably
defining Areas. <br>
You can view your current selection with `/positions` and can be cleared with `/clearpositions`.

### Modifying your Selection

The main way to modify your selection is using the Selection Wand. You can use `/selectiontype` to see and modify your
Selection Type. This Selection Type modifies how the Wand behaves.<br>
When using Selection Type `twoPositions`, a left click will set position 1 and a right click will set position 2.<br>
When using Selection Type `indefinitePositions`, a left click will clear the selection and set position 1 and a right
click will add positions to the end of the list.<br><br>

You can also add a control point by using the `/pos` command. This will add your current position to the selection.
Using this command without parameters will add a point to the nearest free index. You can specify an index, in which
case the new control point is added to that index, overriding the existing one if the index is already has one.

## The Area

Every player has an Area. The Area is one of the most important concepts in WorldShaper. It describes a set of blocks
that are to be affected by operations the player performs. There are different Area Types that determine how the area
is calculated from the player's Selection.

### Area Types

Every Player can change their Area Type using `/areatype`. Each Area Type also has a default Selection Type attached
that will automatically be switched to when the Area Type is switched.<br>
Currently the following Area Types are available in WorldShaper.

| Area Type | Description                                                                                                                                                                       |
|-----------|-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| cuboid    | The first and second position of the Selection define the 3d diagonal of a cuboid, that is thus spanned along the world's grid. All other positions in the selection are ignored. |
| points    | The points in the area are identical to the points in the  selection.                                                                                                             |

### Modifying your Area

You can modify your Area in the following ways. Keep in mind that modifying the Selection in any way will recalculate
the Area and discard all modifications you made.

| Modification | Command                            | Description                                                                                                                                         |
|--------------|------------------------------------|-----------------------------------------------------------------------------------------------------------------------------------------------------|
| move         | `/movearea <distance> [direciton]` | Moves the Area the given distance in the given direction. If no direction is provided the player's view direction is used.                          |
| expand       | `/expand <amount> [direction]`     | Limited to Cuboid Area. Expands the Area the given amount in the given direction. If no direction is provided the player's view direction is used.  |
| retract      | `/retract <amount> [direction]`    | Limited to Cuboid Area. Retracts the Area the given amount in the given direction. If no direction is provided the player's view direction is used. |

### Area Operations

The following commands can be used to perform area operations.

| Command                     | Description                                                                                                                                                         |
|-----------------------------|---------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| `/set <pattern>`            | Sets all blocks in the area to the given pattern.                                                                                                                   |
| `/replace <mask> <pattern>` | Replaces all blocks that match the mask with the given pattern.                                                                                                     |
| `/walls <pattern>`          | Sets all wall blocks of the mask to the given pattern. A block is considered part of the wall of an area if at least one of its side neighbours is not in the Area. |
| `/floor <pattern>`          | Sets all floor blocks of the mask to the given pattern. A block is considered part of the floor of an area if the block beneath it is not in the Area.              |
| `/ceiling <pattern>`        | Sets all ceiling blocks of the mask to the given pattern. A block is considered part of the ceiling of an area if the block above it is not in the Area.            |
| `/hull <pattern>`           | Sets all hull blocks of the mask to the given pattern. A block is considered part of the hull of an area if any of its neighbours are not in the Area.              |
