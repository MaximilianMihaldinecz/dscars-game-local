# DSCars Game
A simple 2D car racing game that allows two players to play a car race game. Created for learning purposes. Written in Java 8, using Swing.

Features:

- 4 selectable cars, all with transparent backgrounds. 
- 2 selectable arenas. 
- Enhanced arena graphics, which can be turned on/off. 
- Collision strategies: 
-- Car to edge: Car stops or slows down based on collision’s angle.
-- Car to grass: Car slows down. 
-- Car to car: Cars are crashing, end of the race. 
-- Collision detection is based on visible pixels (ignoring transparent). 
- Sound effects: o Car collision, car acceleration, car crash and background music. All of them can be turned on/off. 
- In-game heads up display for each player showing the current speed between 0-100. 
- A single player mode for practising.  
- Help messages about the game, controls and collisions.  
- Enhanced car movement physics, which overrides the acceleration method specified in the requirements:
-- Variable speed acceleration based on current speed, max speed and terrain. 
-- Different speed limits for reversing, forwarding and off-the-road driving. 
-- Acceleration and slowdown do not require repeated pressing of the forward/back buttons (one continuous press/release is sufficient)

