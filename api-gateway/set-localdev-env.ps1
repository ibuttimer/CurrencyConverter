# powershell script to set environment variables for development on localhost
Invoke-Expression ../misc/set-localdev-base-env.ps1

# Enable/disable zipkin; set to true/false
$env:TRACING_ENABLED='false'

$vars = @('TRACING_ENABLED')
Foreach ($e in $vars) {
    $val = Get-Childitem Env:$e
    Write-Output ("$($e) : $($val.value)")
}
