#!/bin/bash

echo "Invoking Maven Install lifecycle"
echo "Profile: SM-QA"
echo "Browser: GridDriver"

mvn -version
mvn -DSuperMatchEnv=Standalone -P SM-QA -Dbrowser=GridDriver install
