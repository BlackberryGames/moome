#!/bin/sh

cd "/usr/share/moome/lib/"

find . -name "*.class" -delete
javac Main.java
