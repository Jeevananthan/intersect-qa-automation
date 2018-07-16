#!/bin/bash

echo "Invoking CURL requests to run nightly tests"

echo "$APIKey"

echo $APIKey

ENV

echo "Calling BDD Tests for SuperMatch"
curl -H 'Authorization: '"$APIKey"'' https://turbo.hesos.net/v1/apps/26/buildjobs/5635/now/
echo "Calling BDD Tests for SuperMatch Embedded"
curl -H "Authorization: '"$APIKey"'" https://turbo.hesos.net/v1/apps/26/buildjobs/6258/now/
echo "Calling BDD Tests for SuperMatch2"
curl -H "Authorization: '$APIKey'" https://turbo.hesos.net/v1/apps/26/buildjobs/5635/now/
echo "Calling BDD Tests for SuperMatch Embedded2"
curl -H 'Authorization: "$APIKey"' https://turbo.hesos.net/v1/apps/26/buildjobs/6258/now/
echo "Calling BDD Tests for SuperMatch Embedded3"
curl -H "Authorization: {$APIKey}" https://turbo.hesos.net/v1/apps/26/buildjobs/6258/now/
