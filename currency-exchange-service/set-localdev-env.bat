@echo off
rem batch file to set environment variables for development on localhost
call ../misc/set-localdev-base-env.bat

set MYSQL_EXCHANGE_URL=//localhost:3306/currency_exchange

setlocal enabledelayedexpansion
for %%E IN (
    MYSQL_EXCHANGE_URL
 ) do (
  echo %%E: !%%E!
)

