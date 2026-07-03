package pl.brzezins.hentselv.service;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.io.Resource;
import pl.brzezins.hentselv.wssecurity.WsSecurityProperties;

@ConfigurationProperties(prefix = "hentselv.service")
public record HentSelvServiceProperties(Resource wsdlLocation,
                                        WsSecurityProperties wsSecurity) {
}