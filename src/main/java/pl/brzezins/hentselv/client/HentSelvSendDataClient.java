package pl.brzezins.hentselv.client;

import lombok.RequiredArgsConstructor;
import oio.skat.hentselv.ws._1_0.HentSelvSendDataIType;
import oio.skat.hentselv.ws._1_0.HentSelvSendDataOType;
import oio.skat.hentselv.ws._1_0_1.HentSelvSendDataServicePortType;

@RequiredArgsConstructor
public class HentSelvSendDataClient implements HentSelvSendDataServicePortType {
    private final HentSelvSendDataServicePortType delegate;

    @Override
    public HentSelvSendDataOType getHentSelvSendData(HentSelvSendDataIType request) {
        return delegate.getHentSelvSendData(request);
    }
}
