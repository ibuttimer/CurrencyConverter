@echo off
rem Batch file to set environment variables for development on localhost

rem First check if running in command prompt (see https://stackoverflow.com/a/53464542)
setlocal
CALL :GETPARENT PARENT
IF /I "%PARENT%" == "powershell" GOTO :ISPOWERSHELL
IF /I "%PARENT%" == "pwsh" GOTO :ISPOWERSHELL
endlocal

rem Running in command prompt
set NAMING_PORT=8761
set CONFIG_PORT=8888
set API_PORT=8765
set ZIPKIN_PORT=9411
set EXCHANGE_PORT=8000
set CONVERT_PORT=8100

set SERVICE_URL_DEFAULT_ZONE=http://localhost:%NAMING_PORT%/eureka
set CONFIG_SERVER_URL=http://localhost:%CONFIG_PORT%/
set API_GATEWAY_URL=http://localhost:%API_PORT%/
set ZIPKIN_SERVER_URL=localhost:%ZIPKIN_PORT%/

setlocal enabledelayedexpansion
for %%E IN (
    SERVICE_URL_DEFAULT_ZONE, CONFIG_SERVER_URL, API_GATEWAY_URL, ZIPKIN_SERVER_URL, EXCHANGE_PORT, CONVERT_PORT
 ) do (
  echo %%E: !%%E!
)

GOTO :EOF

:GETPARENT
SET "PSCMD=$ppid=$pid;while($i++ -lt 3 -and ($ppid=(Get-CimInstance Win32_Process -Filter ('ProcessID='+$ppid)).ParentProcessId)) {}; (Get-Process -EA Ignore -ID $ppid).Name"

for /f "tokens=*" %%i in ('powershell -noprofile -command "%PSCMD%"') do SET %1=%%i

GOTO :EOF

:ISPOWERSHELL
echo. >&2
echo ERROR: This batch file may not be run from a PowerShell prompt, please use set-localdev-env.ps1. >&2
echo. >&2
exit /b 1

