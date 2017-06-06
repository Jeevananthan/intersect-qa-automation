#!/bin/bash

if [ -z "$ENVIRONMENT" ]; then
    ENVIRONMENT="qaEnv"
fi

if [ -z "$BROWSER" ]; then
    BROWSER="GridDriver"
fi

echo "Environment: $ENVIRONMENT"
echo "Browser: $BROWSER"

if [ -z "$TAGS" ]; then
    mvn install -Denvironment="$ENVIRONMENT" -Dbrowser="$BROWSER" -PHE-QA
else
    echo "Tags: $TAGS"
    mvn install -Denvironment="$ENVIRONMENT" -Dbrowser="$BROWSER" -Dtags="$TAGS" -PHE-QA
fi


