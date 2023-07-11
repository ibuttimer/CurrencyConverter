# powershell script to set environment variables for development on localhost
Invoke-Expression ../misc/set-localdev-base-env.ps1

$env:CONFIG_GIT_URI='file:///' + $PSScriptRoot + '/../git-localconfig-repo'

$vars = @('CONFIG_GIT_URI')
Foreach ($e in $vars) {
    $val = Get-Childitem Env:$e
    Write-Output ("$($e) : $($val.value)")
}
