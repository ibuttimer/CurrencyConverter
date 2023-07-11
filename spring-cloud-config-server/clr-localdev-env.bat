@echo off
rem Batch file to clear environment variables for development on localhost

rem First check if running in command prompt (see https://stackoverflow.com/a/53464542)
setlocal
CALL :GETPARENT PARENT
IF /I "%PARENT%" == "powershell" GOTO :ISPOWERSHELL
IF /I "%PARENT%" == "pwsh" GOTO :ISPOWERSHELL
endlocal

rem Running in command prompt
call ../misc/clr-localdev-base-env.bat

set CONFIG_GIT_URI=

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
