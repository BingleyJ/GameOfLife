GameOfLife

==========
Name : John Bingley 
Class : DataStructures
Purpose : Simulate Conway's Degree Of Awesome




Conway's Game Of Life, Java 2d. 

===============================

Controls:

	0 = Randomize Cells
	1 = Spawn Glider
	2 = Spawn Lightweight Ship
	3 = Spawn Blinker
	4 = Spawn Toad
	5 = Spawn Beacon

Rules :
	- A cell is either live or dead
	- Any live cell with fewer than two live neighbours dies, as if caused by under-population.
        - Any live cell with two or three live neighbours lives on to the next generation.
        - Any live cell with more than three live neighbours dies, as if by overcrowding.
        - Any dead cell with exactly three live neighbours becomes a live cell, as if by reproduction.
