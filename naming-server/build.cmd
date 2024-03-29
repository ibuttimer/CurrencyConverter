@REM ----------------------------------------------------------------------------
@REM Project build and run Batch script
@REM
@REM Required ENV vars:
@REM JAVA_HOME - location of a JDK home dir
@REM ----------------------------------------------------------------------------

@echo off
@REM set title of command window
title %0

@REM ==== START VALIDATION ====
if not "%JAVA_HOME%" == "" goto OkJHome

echo.
echo Error: JAVA_HOME not found in your environment. >&2
echo Please set the JAVA_HOME variable in your environment to match the >&2
echo location of your Java installation. >&2
echo.
goto error

:OkJHome
if exist "%JAVA_HOME%\bin\java.exe" goto init

echo.
echo Error: JAVA_HOME is set to an invalid directory. >&2
echo JAVA_HOME = "%JAVA_HOME%" >&2
echo Please set the JAVA_HOME variable in your environment to match the >&2
echo location of your Java installation. >&2
echo.
goto error

@REM ==== END VALIDATION ====

:init

@REM build jar
mvnw.cmd clean package %*
@REM If the build fails with a 'The forked VM terminated without saying properly goodbye. VM crash or System.exit called?'
@REM from the maven-surefire-plugin, the issue may be excess logging. Try the following command, and view build.log for results.
@REM mvnw.cmd clean package %* > build.log
goto end

:error
exit /b 1

:end
