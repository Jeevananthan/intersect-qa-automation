#!/bin/bash

echo "Invoking Maven Install lifecycle"
echo "Profile: HE-QA"
echo "Browser: GridDriver"

mvn -version
mvn help:active-profiles
mvn install -P HE-QA -Dbrowser=GridDriver
mvn help:active-profiles
