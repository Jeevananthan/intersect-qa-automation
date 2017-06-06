#!/bin/bash

echo "Invoking Maven Install lifecycle"
echo "Profile: HE-QA"
echo "Browser: GridDriver"

mvn install -P HE-QA -Dbrowser=GridDriver


