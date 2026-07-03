package pl.brzezins.hentselv.client;

import jakarta.xml.ws.BindingProvider;
import lombok.RequiredArgsConstructor;
import oio.skat.hentselv.ws._1_0_1.HentSelvSendDataServicePortType;
import org.apache.cxf.endpoint.Client;
import org.apache.cxf.ext.logging.LoggingFeature;
import org.apache.cxf.frontend.ClientProxy;
import org.apache.cxf.ws.security.SecurityConstants;
import org.apache.wss4j.common.ConfigurationConstants;
import org.apache.wss4j.common.crypto.Crypto;
import pl.brzezins.hentselv.wssecurity.CryptoFactory;
import pl.brzezins.hentselv.wssecurity.WsSecurityProperties;

import javax.security.auth.callback.CallbackHandler;
import java.util.Map;

@RequiredArgsConstructor
public class HentSelvClientWsSecurityConfigurer {

    private final HentSelvClientProperties clientProperties;
    private final CallbackHandler passwordCallbackHandler;

    public void configure(HentSelvSendDataServicePortType port) {
        configureAddress(port);
        configureWsSecurity(port);
        configureLogging(port);
    }

    private void configureAddress(HentSelvSendDataServicePortType port) {
        BindingProvider bindingProvider = (BindingProvider) port;

        bindingProvider.getRequestContext().put(
                BindingProvider.ENDPOINT_ADDRESS_PROPERTY,
                clientProperties.address()
        );
    }

    private void configureWsSecurity(HentSelvSendDataServicePortType port) {
        WsSecurityProperties wsSecurity = clientProperties.wsSecurity();
        Crypto clientCrypto = CryptoFactory.create(wsSecurity);

        BindingProvider bindingProvider = (BindingProvider) port;
        Map<String, Object> context = bindingProvider.getRequestContext();

        context.put(SecurityConstants.SIGNATURE_USERNAME, wsSecurity.privateKeyAlias());
        context.put(SecurityConstants.ENCRYPT_USERNAME, wsSecurity.encryptionCertificateAlias());
        context.put(SecurityConstants.SIGNATURE_CRYPTO, clientCrypto);
        context.put(SecurityConstants.ENCRYPT_CRYPTO, clientCrypto);
        context.put(SecurityConstants.CALLBACK_HANDLER, passwordCallbackHandler);
        context.put(ConfigurationConstants.ALLOW_RSA15_KEY_TRANSPORT_ALGORITHM, "true");
    }

    private void configureLogging(HentSelvSendDataServicePortType port) {
        Client client = ClientProxy.getClient(port);

        LoggingFeature loggingFeature = new LoggingFeature();
        loggingFeature.setPrettyLogging(true);
        loggingFeature.initialize(client, client.getBus());
    }
}
