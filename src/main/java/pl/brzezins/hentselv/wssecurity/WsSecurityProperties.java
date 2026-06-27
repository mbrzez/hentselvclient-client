package pl.brzezins.hentselv.wssecurity;

import org.springframework.core.io.Resource;

public record WsSecurityProperties(Resource keyStoreLocation,
                                   String keyStoreType,
                                   String keyStorePassword,
                                   String privateKeyAlias,
                                   String privateKeyPassword,
                                   String encryptionCertificateAlias) {
}
