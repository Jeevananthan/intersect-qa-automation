#!/bin/bash

echo "Invoking Maven Install lifecycle"
echo "Profile: HUBS-QA"
echo "Browser: GridDriver"

mvn -version
mvn install -P HUBS-QA -Dbrowser=GridDriver
