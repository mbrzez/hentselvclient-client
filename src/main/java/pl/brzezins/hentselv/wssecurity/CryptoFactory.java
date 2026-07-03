package pl.brzezins.hentselv.wssecurity;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.wss4j.common.crypto.Crypto;
import org.apache.wss4j.common.crypto.Merlin;

import java.io.InputStream;
import java.security.KeyStore;
import java.security.KeyStoreException;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class CryptoFactory {

    public static Crypto create(WsSecurityProperties properties) {
        try {
            return createCrypto(properties);
        } catch (Exception e) {
            throw new IllegalArgumentException("Failed to create Crypto instance", e);
        }
    }

    private static Crypto createCrypto(WsSecurityProperties properties) throws Exception {
        KeyStore keyStore = loadKeyStore(properties.keystore());
        KeyStore trustStore = loadKeyStore(properties.truststore());

        validateKeyStore(keyStore, properties.keystore());
        validateTrustStore(trustStore, properties.truststore());

        Merlin crypto = new Merlin();

        crypto.setKeyStore(keyStore);
        crypto.setTrustStore(trustStore);
        crypto.setDefaultX509Identifier(properties.keystore().alias());

        return crypto;
    }

    private static KeyStore loadKeyStore(WsStore store) throws Exception {
        KeyStore keyStore = KeyStore.getInstance(store.type());

        try (InputStream input = store.location().getInputStream()) {
            keyStore.load(input, store.password().toCharArray());
        }

        return keyStore;
    }

    private static void validateKeyStore(KeyStore keyStore, WsStore wsStore) throws KeyStoreException {
        String alias = wsStore.alias();

        if (!keyStore.containsAlias(alias)) {
            throw new IllegalStateException("Keystore does not contain alias: " + alias);
        } else if (keyStore.getCertificate(alias) == null) {
            throw new IllegalStateException("Keystore alias does not contain certificate: " + alias);
        } else if (!keyStore.isKeyEntry(alias)) {
            throw new IllegalStateException("Keystore alias is not a private key entry: " + alias);
        }
    }

    private static void validateTrustStore(KeyStore trustStore, WsStore wsStore) throws Exception {
        String alias = wsStore.alias();

        if (!trustStore.containsAlias(alias)) {
            throw new IllegalStateException("Truststore does not contain alias: " + alias);
        } else if (trustStore.getCertificate(alias) == null) {
            throw new IllegalStateException("Truststore alias does not contain certificate: " + alias);
        } else if (trustStore.isKeyEntry(alias)) {
            throw new IllegalStateException("Truststore alias should not be a private key entry: " + alias);
        }
    }
}