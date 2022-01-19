# Breakout Design
## Cynthia France


## Design Goals
* Make this as fun as possible!
* My goal is to implement everything in my plan

## High-Level Design
* At the very core, there will be a Breakout class which uses classes such as Ball, Paddle, and
  Block to play the game
* Ball, Paddle, and Block will interact with each other in the game to produce desired effects

## Assumptions or Simplifications
* Set to 3 lives per level
* Arbitrary ball, paddle, window sizes were chosen
* Arbitrary ball & block speeds were chosen

## Changes from the Plan
* I did not implement power-up/disadvantage/mixed snow flurries, or any flurries at all
* I also slightly changed a planned power-up and added another cheat key.
* NOTE: I was not aware of this document, so I documented all power-up and cheat key changes
    in the ``PLAN.md`` document
  * The only exception is snow flurries, I kept that part in

## How to Add New Features
### New Levels
Create ``.txt`` block map with the following format:
* First line: 2 numbers, separated by space, of the size of the map: ``numCols`` ``numRows``
* The next ``numRows`` rows: the actual block map, with the characters:
  * ``0``: no block
  * ``1``: snow block
  * ``2``: wood block
  * ``3``: brick block
  * ``4``: concrete block
  * ``5``: steel block
  * ``A``: snow angel block
  * ``B``: black ice block
* blocks should be separated by a **space**
* a newline character (just hit enter) should follow the last block in a row
  * NO SPACES AFTER THE LAST BLOCK
* Place file in the ``resources`` folder (``src/main/resources``)
* Create a new constant with its file path (``src/main/resources/lvlName.txt``)
* Update ``Main``'s ``NUM_LVLS`` CONSTANT
* Add the new level to the ``getMap()`` function in ``Breakout.java``
* Document this feature in the ``README`` markdown file

### New Block Types
* Create a new subclass that extends ``Block`` with all desired features
* Designate the character it will be represented by in the block map
* Add its images (normal, iced, and whatever additional images) to ``BreakoutImages``
* Document this feature in the ``README`` markdown file

### New Effects
* Add new effect to its appropriate constants ``List`` (``POWERUPS``, ``DISADVGS``)
* If the effect is not linked to any event outside ``BlackIceBlock``, add a
  function that performs the desired action and link accordingly
* If the effect is linked to some other event, connect accordingly
* Document this feature in the ``README`` markdown file

### New Paddles & Balls
* Create subclasses of ``Paddle`` and ``Ball`` class & connect to your specifications
* Document this feature in the ``README`` markdown file

## Future Changes
* creating an ``Effects`` class with ``PowerUp`` and ``Disadvantage`` subclasses
  * move all effects-related operations to these classes
* Some type of ``BlockMap`` class that can read the block map and build the
  map/create the 2D array of ``Blocks``






