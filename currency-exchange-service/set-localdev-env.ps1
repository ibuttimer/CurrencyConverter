# powershell script to set environment variables for development on localhost
Invoke-Expression ../misc/set-localdev-base-env.ps1

# See currency-exchange-service/README.MD#"MySQL connection string" for information
# regarding the MySQL connection string
$env:MYSQL_EXCHANGE_URL='//localhost:3306/currency_exchange?useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC'
# Enable/disable zipkin; set to true/false
$env:TRACING_ENABLED='false'

$vars = @('MYSQL_EXCHANGE_URL', 'TRACING_ENABLED')
Foreach ($e in $vars) {
    $val = Get-Childitem Env:$e
    Write-Output ("$($e) : $($val.value)")
}
