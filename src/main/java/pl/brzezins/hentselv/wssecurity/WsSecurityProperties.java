package pl.brzezins.hentselv.wssecurity;

public record WsSecurityProperties(WsStore keystore,
                                   WsStore truststore) {
}
