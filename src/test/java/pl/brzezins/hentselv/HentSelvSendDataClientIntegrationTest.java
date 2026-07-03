package pl.brzezins.hentselv;

import static org.assertj.core.api.Assertions.assertThat;

import oio.skat.hentselv.ws._1_0.HentSelvSendDataIType;
import oio.skat.hentselv.ws._1_0.HentSelvSendDataOType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import pl.brzezins.hentselv.client.HentSelvSendDataClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ActiveProfiles("test")
class HentSelvSendDataClientIntegrationTest {

    @Autowired
    private HentSelvSendDataClient client;

    @Test
    void shouldCallLocalSoapServiceWithWsSecurity() {
        HentSelvSendDataIType request = new HentSelvSendDataIType();

        HentSelvSendDataOType response = client.getHentSelvSendData(request);

        assertThat(response).isNotNull();
    }
}