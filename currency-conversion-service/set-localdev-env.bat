@echo off
rem batch file to set environment variables for development on localhost
call ../misc/set-localdev-base-env.bat

set MYSQL_CONVERSION_URL=//localhost:3306/currency_conversion
set CURRENCY_EXCHANGE_URL=http://localhost:8000

setlocal enabledelayedexpansion
for %%E IN (
    MYSQL_CONVERSION_URL, CURRENCY_EXCHANGE_URL
 ) do (
  echo %%E: !%%E!
)
