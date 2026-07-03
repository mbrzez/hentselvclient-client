package pl.brzezins.hentselv;

import static org.assertj.core.api.Assertions.assertThat;

import oio.skat.hentselv.ws._1_0.HentSelvSendDataIType;
import oio.skat.hentselv.ws._1_0.HentSelvSendDataOType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import pl.brzezins.hentselv.client.HentSelvSendDataClient;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class HentSelvSendDataClientIntegrationTest {

    @Autowired
    private HentSelvSendDataClient client;

    @Test
    void shouldCallLocalSoapServiceWithWsSecurity() {
        // given
        HentSelvSendDataIType request = new HentSelvSendDataIType();

        // when
        HentSelvSendDataOType response = client.getHentSelvSendData(request);

        // then
        assertThat(response).isNotNull();
    }
}