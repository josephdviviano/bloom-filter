#!/bin/bash

rm *.class

javac BitSet.java
javac BloomFilter.java
javac test.java

java test
