# Tile Map Creator
Tool to create 2D tile maps.

This project is intended to improve my Java skils and in future I'd be able to create a Java based game from scratch!
## Using the software
It's in source code, so you must compile it and run, to do so, one must know that the **main** method is located in the **MainWindow** class that is located on **principal** package.

Once you've runned it, you must create a new map, through the file menu on the menu bar and select a file to the program use as tile set, so every tile must be on the same initial image and they have to be the same width and height. The image will be divided in lines, so further on, it's esear to program each line as a type of block, like blockable tile or actvation tile and so on.

## Map files
The software generates a .map file, the files are in binary and contains: 
* Information about the tiles:
    * 2 integers describing the size of the tile matrix
    * 2 integers the width and height of each tile
    * a matrix of sizes equals to the first 2 numbers, it has the **RGB vector** of all tiles
* Information about the map:
    * Has only a matrix of integers

Its a free open software, use it as you wish.