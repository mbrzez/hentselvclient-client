package pl.brzezins.hentselv.service;

import lombok.RequiredArgsConstructor;
import org.apache.cxf.jaxws.EndpointImpl;
import org.apache.cxf.ws.security.SecurityConstants;
import org.apache.wss4j.common.ConfigurationConstants;
import org.apache.wss4j.common.crypto.Crypto;
import pl.brzezins.hentselv.wssecurity.CryptoFactory;
import pl.brzezins.hentselv.wssecurity.WsSecurityConstants;
import pl.brzezins.hentselv.wssecurity.WsSecurityProperties;

import javax.security.auth.callback.CallbackHandler;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
public class HentSelvServiceWsSecurityConfigurer {

    private final HentSelvServiceProperties serviceProperties;
    private final CallbackHandler passwordCallbackHandler;

    public void configure(EndpointImpl endpoint) throws IOException {
        endpoint.setServiceName(WsSecurityConstants.SERVICE_NAME);
        endpoint.setEndpointName(WsSecurityConstants.PORT_NAME);
        endpoint.setWsdlLocation(serviceProperties.wsdlLocation().getURL().toExternalForm());
        endpoint.setProperties(createWsSecurityProperties());
        endpoint.publish("/HentSelvSendDataService");
    }

    private Map<String, Object> createWsSecurityProperties() {
        WsSecurityProperties wsSecurityProperties = serviceProperties.wsSecurity();
        Crypto serviceCrypto = CryptoFactory.create(wsSecurityProperties);

        Map<String, Object> properties = new HashMap<>();
        properties.put(SecurityConstants.SIGNATURE_USERNAME, wsSecurityProperties.privateKeyAlias());
        properties.put(SecurityConstants.ENCRYPT_USERNAME, wsSecurityProperties.encryptionCertificateAlias());
        properties.put(SecurityConstants.SIGNATURE_CRYPTO, serviceCrypto);
        properties.put(SecurityConstants.ENCRYPT_CRYPTO, serviceCrypto);
        properties.put(SecurityConstants.CALLBACK_HANDLER, passwordCallbackHandler);
        properties.put(ConfigurationConstants.ALLOW_RSA15_KEY_TRANSPORT_ALGORITHM, "true");

        return properties;
    }
}
