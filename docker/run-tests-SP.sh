#!/bin/bash

echo "Invoking Maven Install lifecycle"
echo "Profile: SP-QA"
echo "Browser: GridDriver"

mvn -version
mvn install -P SP-QA -Dbrowser=GridDriver
