#!/bin/sh

cd "$(dirname "$0")/lib"

find . -name "*.class" -delete
javac Main.java
