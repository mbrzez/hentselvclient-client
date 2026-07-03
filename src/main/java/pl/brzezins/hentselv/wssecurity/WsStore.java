package pl.brzezins.hentselv.wssecurity;

import org.springframework.core.io.Resource;

public record WsStore(Resource location,
                      String type,
                      String password,
                      String alias) {
}
