@echo off
rem Batch file to set environment variables for development on localhost

rem First check if running in command prompt (see https://stackoverflow.com/a/53464542)
setlocal
CALL :GETPARENT PARENT
IF /I "%PARENT%" == "powershell" GOTO :ISPOWERSHELL
IF /I "%PARENT%" == "pwsh" GOTO :ISPOWERSHELL
endlocal

rem Running in command prompt
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

