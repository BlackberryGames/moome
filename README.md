# About

Moome is an entertaining 8-bit, multiplayer platformer game written in Java by Mooncat39 and Arinerron that was actively developed from around 2014 to 2016.

# Installation

## Manual

Ensure you have your distribution's packages that provide `javac` (for compiling) and `java` installed.

```
$ git clone git@github.com:BlackberryCode/moome.git && cd moome
$ ./compile.sh
$ ./run.sh
```

## AUR

Install the `moome` AUR package:

```
$ trizen -S moome
$ moome
```

## `makepkg`

```
$ git clone git@github.com:BlackberryCode/moome.git && cd moome
# makepkg -sif .
$ moome
```

# Notes

* You can run your own server using the code at https://github.com/BlackberryCode/moome-server
* Run with `--leveleditor` to launch the map editor
