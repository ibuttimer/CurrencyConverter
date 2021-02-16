@echo off
rem batch file to set environment variables for development on localhost
call ../misc/set-localdev-base-env.bat

set CONFIG_GIT_URI=file:///%cd%/../git-localconfig-repo

setlocal enabledelayedexpansion
for %%E IN (
    CONFIG_GIT_URI
 ) do (
  echo %%E: !%%E!
)
