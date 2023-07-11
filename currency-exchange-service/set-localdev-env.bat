@echo off
rem Batch file to set environment variables for development on localhost

rem First check if running in command prompt (see https://stackoverflow.com/a/53464542)
setlocal
CALL :GETPARENT PARENT
IF /I "%PARENT%" == "powershell" GOTO :ISPOWERSHELL
IF /I "%PARENT%" == "pwsh" GOTO :ISPOWERSHELL
endlocal

rem Running in command prompt
call ../misc/set-localdev-base-env.bat

rem See currency-exchange-service/README.MD#"MySQL connection string" for information
rem regarding the MySQL connection string
set MYSQL_EXCHANGE_URL=//localhost:3306/currency_exchange?useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC
rem Enable/disable zipkin; set to true/false
set TRACING_ENABLED=false

setlocal enabledelayedexpansion
for %%E IN (
    MYSQL_EXCHANGE_URL, TRACING_ENABLED
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

