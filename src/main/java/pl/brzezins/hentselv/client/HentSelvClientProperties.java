package pl.brzezins.hentselv.client;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.io.Resource;
import pl.brzezins.hentselv.wssecurity.WsSecurityProperties;

@ConfigurationProperties(prefix = "hentselv.client")
public record HentSelvClientProperties(
        String address,
        Resource wsdlLocation,
        WsSecurityProperties wsSecurity) {
}
