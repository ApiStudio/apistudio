#!/usr/bin/env bash
export JAVA_HOME=/opt/jdk
export PATH=$PATH:$JAVA_HOME/bin
cd ../
./gradlew :monitor:publishToMavenLocal