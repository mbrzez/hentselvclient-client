package pl.brzezins.hentselv.service;

import oio.skat.hentselv.ws._1_0.HentSelvSendDataIType;
import oio.skat.hentselv.ws._1_0.HentSelvSendDataOType;
import oio.skat.hentselv.ws._1_0_1.HentSelvSendDataServicePortType;

public class HentSelvSendDataService implements HentSelvSendDataServicePortType {
    @Override
    public HentSelvSendDataOType getHentSelvSendData(HentSelvSendDataIType request) {
        return createStubResponse();
    }

    private HentSelvSendDataOType createStubResponse() {
        return new HentSelvSendDataOType();
    }
}
