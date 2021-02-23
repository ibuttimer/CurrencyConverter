@echo off
rem batch file to set environment variables for development on localhost

set CURRENCY_EXCHANGE_URL=http://localhost:8000

setlocal enabledelayedexpansion
for %%E IN (
    CURRENCY_EXCHANGE_URL
 ) do (
  echo %%E: !%%E!
)
