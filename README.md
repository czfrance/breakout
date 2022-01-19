# BREAKOUT: WINTER WONDERLAND
## Cynthia France


This project implements the game of Breakout.

### Timeline

Start Date: Tuesday, January 11th

Finish Date: Tuesday, January 18th

Hours Spent: 30 hours

### Resources Used
* [Java11 Docs](https://docs.oracle.com/en/java/javase/11/docs/api/index.html)
* [Javafx Docs](https://openjfx.io/javadoc/17/index.html)
* [Using ImagePattern](https://stackoverflow.com/questions/22848829/how-do-i-add-an-image-inside-a-rectangle-or-a-circle-in-javafx)
* [brick pattern](https://www.vectorstock.com/royalty-free-vector/brick-pattern-seamless-red-wall-background-vector-14779866)
* [ice dripping](https://www.vectorstock.com/royalty-free-vectors/1823577-vectors)
* [concrete](https://www.istockphoto.com/photo/modern-beige-concrete-wall-background-texture-gm925546658-253982483)
* [gold](https://www.istockphoto.com/vector/shiny-gold-texture-paper-or-metal-golden-vector-background-gm1271398716-374000746)
* [metal](https://www.vectorstock.com/royalty-free-vector/metal-vector-23651096)
* [wood](https://www.shutterstock.com/image-photo/natural-wood-wooden-parquet-floor-seamless-527640499)
* [paddle](https://www.vectorstock.com/royalty-free-vector/stone-frame-rubble-rocks-banner-stones-block-vector-23571904)
* [ice](https://www.vectorstock.com/royalty-free-vector/three-snow-blocks-ice-iceberg-vector-7641729)
* [carbon fiber](https://www.shutterstock.com/image-vector/vector-black-carbon-fiber-seamless-background-489026752)

### Running the Program

To play the game, run the Main class: ``src/main/java/breakout/Main.java``

Files needed:
* Code: ``src/main/java/breakout/``
  * ``Ball``
  * ``BlackIceBlock``
  * ``Block``
  * ``Breakout``
  * ``BreakoutImages``
  * ``BreakoutText``
  * ``Paddle``
  * ``ResultsScreen``
  * ``Screen``
  * ``SnowAngelBlock``
  * ``SplashScreen``
* Data & Resources: ``src/main/java/resources/``
  * ``*.png``
  * ``november.txt``
  * ``december.txt``
  * ``january.txt``

Key/Mouse inputs:
* ``ENTER``: Move between screens
  * Game prompts user to do so
* ``SPACE``: set the ball in motion
  * Use: At beginning of level to start the game, after losing a life
* ``RIGHT``, ``LEFT``: Move the paddle
  * Use: Whenever ball is in motion
* ``S``, ``L``, ``E``, ``D``, ``F``, ``R``, ``DIGIT1``-``DIGIT9``: cheat keys, see below

Cheat keys:
* ``DIGIT1``-``DIGIT9``: Move between levels
  * Use: Anytime
  * Required
* ``S``: Increases ball speed per press
  * Use: Whenever game screen is displayed
  * Additional feature
* ``L``: Increases lives by 1 per press
  * Use: Whenever game screen is displayed
  * Required
* ``E``: Increase paddle size per press
  * Use: Whenever game screen is displayed 
  * Additional feature
* ``D``: "Deep Freeze" power-up, all blocks turn brittle/become iced and require fewer hits to break
  * Use: Whenever game screen is displayed
  * Additional feature
* ``F``: Automatic level pass
  * Use: Whenever game screen is displayed
  * Additional feature
* ``R``: Resets ball and paddle to original size, location, and speed
  * Use: Whenever game screen is displayed
  * Required

### Notes/Assumptions

Assumptions or Simplifications:
* Set to 3 lives per level
* Arbitrary ball, paddle, window sizes were chosen
* Arbitrary ball & block speeds were chosen

Known Bugs:
* No known bugs
* When pushing ball speed to the limit, user may see some glitches (ie, ball 
  phasing/passing through objects)

### About the game

##### Theme: Winter Wonderland

##### Game Levels
* Game has 3 levels: November, December, and January, increasing in difficulty just as coldness
    increases through the months
* In Nov, only normal blocks are used
  * no non-cheat key power-ups
* In Dec & Jan, the blocks begin moving, starting slow and making their way up to max 
  speed
  * maxSpeed(Dec) < maxSpeed(Jan).
  * all blocks are in play, so all power-ups are in play
* Each level's block configuration creates a different image:
  * November: giant snowflake
  * December: many small snowflakes/twinkles
  * January: Christmas (because we all miss the holidays)
  
##### Paddle Features
* "Salted Paddle": The more times the ball bounces off the paddle, the more erratic and 
  random the angles at which the ball bounce back become
  * Similar to how roads become bumpy and uneven when salted, as time goes on,
    the way in which the ball bounces off the paddle will also change. Specifically, the angles
    at which the ball bounce off of the paddle will become more and more unpredictable/unexpected
    as time goes on. (ie greater likelihood of it being 'not expected')
  * Always active
* "Slippery Paddle": When this disadvantage is acquired, the paddle increases the angle
  taken from vertical at which the ball bounces back from the paddle
  * paddle gets "frozen over". This would result in the ball
    "slipping" on the paddle, so the angle it bounces off at will not be a reflection of the
    angle it came in at, but rather a larger angle (ie in at 60˚ off horizontal, out at 45˚)
  
##### Block Features
* Normal Blocks: require anywhere from 1 to 5 hits to destroy, based on type
  * Snow: 1 hit
  * Wood: 2 hits
  * Brick: 3 hits
  * Concrete: 4 hits
  * Steel: 5 hits
* Snow Angle Block: when destroyed, turns blocks near it into iced/brittle ones, which
  will require fewer hits to break (see "Power-ups")
  * require 3 hits to break
* Black Ice Block: are more similar in coloration to the background, so they're harder
  to hit/pay attention to. Since you never know if it's there, destroying this block will randomly
  give the player either a power-up or disadvantage (see "Power-ups", "Disadvantages")
  * require 3 hits to break
  
##### Power-ups
* "Winter Freeze": All blocks freeze in place for a certain amount of time (set to 15 secs)
* "Avalanche": Ball becomes larger, similar to a snowball being rolled up
  * in place for a certain amount of time (set to 15 secs)
* "Spread the Holiday Cheer": Blocks near the black ice block will get destroyed, regardless of
  the amount of hits left to break them
    * Note: power-ups can only be obtained from black ice blocks
* "Deep Freeze": All blocks turn brittle/become iced and require fewer hits to break
    * Note: this is only available as a cheat key feature
    * Since it is a cheat key feature, power-up is activated for the remainder of the level

##### Disadvantages
* "Slippery Paddle": see "Block Features"

##### Cheat Keys
* See above cheat key section

##### Other Notes
* Ball bounces off at the same angle when hit close edge of block
* Only one power-up and one disadvantage at a time

### Impressions
I had so much fun with this project! I really liked that it was so open-ended. That way, it is 
as interesting as you'd like to make it.