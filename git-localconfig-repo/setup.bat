rem this script configures the local git repository for use by the Configuration Server

rem initialise the repository
git init -b main

rem add all files
git add -A

rem commit all files
git commit -m "initial commit"
