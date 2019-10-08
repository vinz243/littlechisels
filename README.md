## LittleChisels

Chisel and Bits dev has stopped, and LittleTiles development is thriving. In order to transition from C&B to LT I made this small project that loads a world and convert your designs for full compatibility.

Please note that transition from C&B to LT is not immediate : the two mods store the informations differently. C&B stores the individual material for each voxel, whereas LittleTiles stores everything using cuboids, which often contains several voxels. So this tool main purpose is to convert between the two formats, which is a process close to triangulisation, which can take time and not yield the most optimized result

## Disclaimer
This tool is still in development and errors might occurs. Backup your world first !

## Prequisites
Java 11

## Installation
Just download the latest release jar from https://github.com/vinz243/littlechisels/releases then run :


```
java -jar littlechisels-1.0.0.jar <path to world>
```

With quotes and a trailing slash :

```
java -jar littlechisels-1.0.0.jar "C:\Users\user\AppData\Roaming\.minecraft\saves\My World\\"
```
