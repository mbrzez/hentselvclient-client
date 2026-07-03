package pl.brzezins.hentselv.client;

import oio.skat.hentselv.ws._1_0_1.HentSelvSendDataService;
import oio.skat.hentselv.ws._1_0_1.HentSelvSendDataServicePortType;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.brzezins.hentselv.wssecurity.PasswordCallbackHandler;
import pl.brzezins.hentselv.wssecurity.WsSecurityConstants;
import pl.brzezins.hentselv.wssecurity.WsStore;

import javax.security.auth.callback.CallbackHandler;
import java.io.IOException;
import java.net.URL;

import static pl.brzezins.hentselv.wssecurity.WsSecurityConstants.PORT_NAME;

@Configuration
@EnableConfigurationProperties(HentSelvClientProperties.class)
public class HentSelvClientConfig {
    @Bean
    public HentSelvSendDataClient hentSelvSendDataClient(HentSelvClientProperties clientProperties,
            HentSelvClientWsSecurityConfigurer configurer) throws IOException {
        URL url = clientProperties.wsdlLocation().getURL();
        HentSelvSendDataService service = new HentSelvSendDataService(url, WsSecurityConstants.SERVICE_NAME);
        HentSelvSendDataServicePortType port = service.getPort(PORT_NAME, HentSelvSendDataServicePortType.class);
        configurer.configure(port);

        return new HentSelvSendDataClient(port);
    }

    @Bean
    public HentSelvClientWsSecurityConfigurer hentSelvClientWsSecurityConfigurer(HentSelvClientProperties clientProperties,
                                                                                 CallbackHandler clientPasswordCallbackHandler) {
        return new HentSelvClientWsSecurityConfigurer(clientProperties, clientPasswordCallbackHandler);
    }

    @Bean
    public CallbackHandler clientPasswordCallbackHandler(HentSelvClientProperties properties) {
        WsStore wsStore = properties.wsSecurity().keystore();
        return new PasswordCallbackHandler(wsStore.alias(), wsStore.password());
    }

}
