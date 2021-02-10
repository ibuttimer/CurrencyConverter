#!/bin/bash
# this script configures the local git repository for use by the Configuration Server

# initialise the repository
git init

# add all files
git add -A

# commit all files
git commit -m "initial commit"
