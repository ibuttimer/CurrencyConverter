# powershell script to set environment variables for development on localhost
Invoke-Expression ../misc/clr-localdev-base-env.ps1

$vars = @('MYSQL_CONVERSION_URL', 'CURRENCY_EXCHANGE_URL', 'TRACING_ENABLED')
Foreach ($e in $vars) {
    Remove-Item -Path Env:\$e
}
