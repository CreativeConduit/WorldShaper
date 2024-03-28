# Getting Started

This is a quick start guide that explains the basics of WorldShaper and gets you going within a few minutes.

## The Wand

To get started, you want to get yourself the Selection Wand by calling `/wand`.

## The Selection

Every player has a Selection. The Selection is a list of control points that is used for many things, most notably
defining Areas. <br>
You can view your current selection with `/selection` and can be cleared with `/clearselection`.

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
| `/walls [mask] <pattern>`   | Sets all wall blocks of the mask to the given pattern. A block is considered part of the wall of an area if at least one of its side neighbours is not in the Area. |
| `/floor [mask] <pattern>`   | Sets all floor blocks of the mask to the given pattern. A block is considered part of the floor of an area if the block beneath it is not in the Area.              |
| `/ceiling [mask] <pattern>` | Sets all ceiling blocks of the mask to the given pattern. A block is considered part of the ceiling of an area if the block above it is not in the Area.            |
| `/hull [mask] <pattern>`    | Sets all hull blocks of the mask to the given pattern. A block is considered part of the hull of an area if any of its neighbours are not in the Area.              |

## Patterns and Masks

### The Pattern

The Pattern is a parameter type that needs to be provided to most operation commands.<br>
A pattern is a comma-separated list of pattern entries `<pattern-entry-1>,<pattern-entry-2>,<pattern-entry-3>,...`.<br>
Each pattern entry consists of 2 parts. The first part is the percentage value. This one is optional. The second part
is the non-optional block string (like `oak_stairs[facing=south,waterlogged=true]`).<br>
Each entry therefor has the format `[<percentage>%]<block-string>`.

An example of a full pattern might therefor be `80%wheat[age=7],wheat[age=6],wheat[age=5]`.

The percentage values define what percentage of the pattern the block makes up. There are however some quirks in how the
actual percentages are calculated from these values:
- If the percentages add up to 100%, all blocks will be affected by the pattern. according to those percentages.
  - Example: The pattern `70%stone,20%cobblestone,10%andesite` will lead to 70% of blocks being set to stone, 20% being set to cobblestone and 10% being set to andesite.
- If the percentages add up to less than 100%, but all pattern entries have a percentage value attached, the remaining percent of blocks will be unaffected by the pattern.
  - Example: The pattern `70%stone,20% cobblestone` will lead to 70% of blocks being set to stone, 20% being set to cobblestone and the remaining 10% being unaffected.
- If the percentages add up to less than 100%, but some pattern entries have no percentage attached, the remaining percent will be filled in equal parts by the blocks that have no percentages attached.
  - Example: The pattern `70%stone,cobblestone,andesite` will lead to 70% of blocks being set to stone, 15% being set to cobblestone and 15% being set to andesite.
- If the percentages add up to more than 100%, all percentage values will be scaled down accordingly. In effect, the percentage value act as weights instead in this case. Any entries without percentage values will effectively be set to 0%.
  - Example: The pattern `90%stone,30%cobblestone` will lead to 75% of blocks being set to stone and 25% of blocks being set to cobblestone.
  - Example: The pattern `90%stone,30%cobblestone,andesite` will lead to 75% of blocks being set to stone and 25% of blocks being set to cobblestone. No blocks will be set to andesite despite it being in the pattern.

### The Mask

The Mask is another parameter type used in a lot of operation commands.<br>
A mask is a comma-separated list of mask entries `<mask-entry-1>,<mask-entry-2>,<mask-entry-3>,...`.<br>
Each mask entry is a block type (notably not the full block state like in the pattern) and an optional modifier before it.<br>
The format is therefor `[modifier]<block-type>`.<br>
There are currently 3 modifiers:
- `!` negates the block type, meaning a block must not be this block type to match.
  - A common use is `!air`
- `>` means "above", so a block must be above another block with this block type to match.
  - Common uses are `>farmland`, `>grass_block`
- `<` means "below", so a block must be below another block with this block type to match.

A mask entry can only ever have one modifier.<br>

The mask entries are effectively or-connected, so a block must match any of the provided mask entries to match the mask as a whole.
