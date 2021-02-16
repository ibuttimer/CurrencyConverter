@echo off
rem batch file to set environment variables for development on localhost
set SERVICE_URL_DEFAULT_ZONE=http://localhost:8761/eureka
set CONFIG_SERVER_URL=http://localhost:8888/
set API_GATEWAY_URL=http://localhost:8765/
set ZIPKIN_SERVER_URL=localhost:9411/

setlocal enabledelayedexpansion
for %%E IN (
    SERVICE_URL_DEFAULT_ZONE, CONFIG_SERVER_URL, API_GATEWAY_URL, ZIPKIN_SERVER_URL
 ) do (
  echo %%E: !%%E!
)

