# Breakout Plan
### Cynthia France


## Interesting Breakout Variants

 * I thought the idea of moving blocks/bricks was really cool! Believe it or not, I actually had
    thought of incorporating that into my project even before I read about all the different 
    breakout variants. I think it's a pretty unique concept. Most variations of Breakout 
    focus mainly on changing the properties of the ball rather than the block (ie special powers,
    many balls, etc.), and when block properties are altered, it's usually regarding its physical/visual
    traits instead of the way they move. Thus, I thought it'd be cool to change it up and move the 
    block instead of the ball

 * This isn't so much a specific game variant as an overall "feel" variation, but I think it'd be 
    cool to have a theme to my overall breakout game, similar to Fairy Treasure, except instead
    of fantasy, it would be something else. Since I want to incorporate moving blocks into my game,
    maybe a winter wonderland theme? Power ups/features could mimic winter weather (ie freezing 
    blocks in place, power ups/blocks falling like snow, frozen blocks would be brittle and require
    fewer hits to break, etc). I'm not sure how many of these I can implement and I will probably 
    think of different ones later on, but these are my preliminary ideas!

**A note from here on out: most of my ideas will be building off of the "winter" theme, although they
could be general ideas too.

## Paddle Ideas

 * "Slippery Paddle": Going off of the "winter" theme, if the player does something, perhaps 
    catch a disadvantage, the paddle gets "frozen over". This would result in the ball 
    "slipping" on the paddle, so the angle it bounces off at will not be a reflection of the 
    angle it came in at, but rather a larger angle (ie in at 60˚ off horizontal, out at 45˚)

 * "Salted Paddle": Similar to how roads become bumpy and uneven when salted, as time goes on,
    the way in which the ball bounces off the paddle will also change. Specifically, the angles
    at which the ball bounce off of the paddle will become more and more unpredictable/unexpected
    as time goes on. (ie greater likelihood of it being 'not expected')
 * It seems as if all of my paddle ideas are disadvantages... perhaps I will make one an advantage
 

## Block Ideas

 * Multiple hits to destroy (different level of blocks = different amount of hits)

 * "Snow Angel Blocks": blocks that when destroyed, turn other blocks into iced ones, which
    will require fewer hits to break

 * "Black Ice": blocks that are more similar in coloration to the background, so they're harder
    to hit/pay attention to. Since you never know if it's there, destroying this block will randomly
    give the player either a power-up or disadvantage


## Power-up Ideas

 * "Winter Freeze": All blocks, or the blocks within a certain range, freeze in place for a certain
    amount of time. 

 * "Avalanche": Ball becomes larger, similar to a snowball being rolled up. The paddle would probably
    also need to be enlarged. Effect is in place for a certain amount of time

 * "Hailstorm": When a key is clicked, or at some certain time after power-up is obtained, 
    the ball will explode into many hail pieces and destroy blocks in their immediate vicinity


## Cheat Key Ideas

 * (s) Extra Lives

 * (f) Automatic level pass

 * (e) Enlarge paddle

 * (d) "Deep Freeze": All blocks turn brittle/become iced and require fewer hits to break


## Level Descriptions

 * November: Blocks do not move, salted paddle, no slippery paddle, only basic blocks that
    require different numbers of hits, occasional power-up flurries

 * December: Blocks move around, salted paddle, snow angel blocks are in play, flurries now include
    power-ups and disadvantages (slippery paddle)

 * January: Blocks move around faster, all blocks are in play, all power-ups and disadvantages
    in play


## Class Ideas

 * Ball
   * explode(): explodes into many pieces (for hailstorm power-up)

 * Paddle
   * grow(): increases in size (for cheat)

 * Block
   * hit(): decreases count needed to break, called when is hit by a ball

 * Power_up
   * caught(): does/changes something when power-up is caught/obtained
 
 * BreakoutGameLoop
   * step(): move all components that need moving and set component colors/attributes. 
     Updates game

