package pl.brzezins.hentselv.service;

import jakarta.xml.ws.Endpoint;
import org.apache.cxf.Bus;
import org.apache.cxf.jaxws.EndpointImpl;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.brzezins.hentselv.wssecurity.PasswordCallbackHandler;
import pl.brzezins.hentselv.wssecurity.WsSecurityProperties;

import javax.security.auth.callback.CallbackHandler;

@Configuration
@EnableConfigurationProperties(HentSelvServiceProperties.class)
public class HentSelvServiceConfig {

    @Bean
    public Endpoint hentSelvSendDataEndpoint(Bus bus,
                                             HentSelvSendDataService service,
                                             HentSelvServiceWsSecurityConfigurer configurer) {
        EndpointImpl endpoint = new EndpointImpl(bus, service);
        configurer.configure(endpoint);

        return endpoint;
    }

    @Bean
    public HentSelvSendDataService hentSelvSendDataService() {
        return new HentSelvSendDataService();
    }

    @Bean
    public HentSelvServiceWsSecurityConfigurer hentSelvServiceWsSecurityConfigurer(HentSelvServiceProperties serviceProperties,
                                                                                   CallbackHandler servicePasswordCallbackHandler) {
        return new HentSelvServiceWsSecurityConfigurer(serviceProperties, servicePasswordCallbackHandler);
    }

    @Bean
    public CallbackHandler servicePasswordCallbackHandler(HentSelvServiceProperties properties) {
        WsSecurityProperties securityProperties = properties.wsSecurity();

        return new PasswordCallbackHandler(
                securityProperties.privateKeyAlias(),
                securityProperties.privateKeyPassword()
        );
    }

}
