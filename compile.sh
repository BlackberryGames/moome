#!/bin/sh

cd "$(dirname "$0")/lib"

find . -name "*.class" -delete
javac Main.java

cd "../data/maps/"

zip -jr map.zip map/map.txt map/map1.txt map/properties.txt
