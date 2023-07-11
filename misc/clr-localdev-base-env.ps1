# powershell script to clear environment variables for development on localhost
$vars = @('SERVICE_URL_DEFAULT_ZONE', 'CONFIG_SERVER_URL', 'API_GATEWAY_URL', 'ZIPKIN_SERVER_URL')
Foreach ($e in $vars) {
    Remove-Item -Path Env:\$e
}

