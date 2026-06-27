package pl.brzezins.hentselv.wssecurity;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

import javax.xml.namespace.QName;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class WsSecurityConstants {
    public static final String NAMESPACE = "urn:oio:skat:hentselv:ws:1.0.1";
    public static final QName PORT_NAME = new QName(NAMESPACE, "HentSelvSendDataServicePort");
    public static final QName SERVICE_NAME = new QName(NAMESPACE, "HentSelvSendDataService");
}
