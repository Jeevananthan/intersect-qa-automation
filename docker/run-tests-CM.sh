#!/bin/bash

echo "Invoking Maven Install lifecycle"
echo "Profile: CM-QA"
echo "Browser: GridDriver"

mvn -version
mvn install -P CM-QA -Dbrowser=GridDriver
