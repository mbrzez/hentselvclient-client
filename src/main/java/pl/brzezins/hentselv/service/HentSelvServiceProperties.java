package pl.brzezins.hentselv.service;

import org.springframework.boot.context.properties.ConfigurationProperties;
import pl.brzezins.hentselv.wssecurity.WsSecurityProperties;

@ConfigurationProperties(prefix = "hentselv.service")
public record HentSelvServiceProperties(String wsdlLocation,
                                        WsSecurityProperties wsSecurity) {
}