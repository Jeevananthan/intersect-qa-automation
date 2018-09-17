#!/bin/bash

echo "Invoking Maven Install lifecycle"
echo "Profile: HS1-QA"
echo "Browser: GridDriver"

mvn -version
mvn install -P HS2-QA -Dbrowser=GridDriver
