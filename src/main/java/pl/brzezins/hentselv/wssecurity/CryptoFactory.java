package pl.brzezins.hentselv.wssecurity;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.wss4j.common.crypto.Crypto;
import org.apache.wss4j.common.crypto.Merlin;

import java.io.InputStream;
import java.security.KeyStore;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class CryptoFactory {

    public static Crypto create(WsSecurityProperties party) {
        try {
            return createCrypto(party);
        } catch (Exception e) {
            throw new IllegalArgumentException("Failed to create Crypto instance", e);
        }
    }

    private static Crypto createCrypto(WsSecurityProperties party) throws Exception {
        KeyStore keyStore = KeyStore.getInstance(party.keyStoreType());

        try (InputStream input = party.keyStoreLocation().getInputStream()) {
            keyStore.load(input, party.keyStorePassword().toCharArray());
        }

        validateKeyStore(keyStore, party);

        Merlin crypto = new Merlin();

        crypto.setKeyStore(keyStore);
        crypto.setTrustStore(keyStore);
        crypto.setDefaultX509Identifier(party.privateKeyAlias());

        return crypto;
    }

    private static void validateKeyStore(KeyStore keyStore, WsSecurityProperties party) throws Exception {
        String privateKeyAlias = party.privateKeyAlias();
        String encryptionCertificateAlias = party.encryptionCertificateAlias();

        if (!keyStore.containsAlias(privateKeyAlias)) {
            throw new IllegalStateException("Keystore does not contain private key alias: " + privateKeyAlias);
        } else if (!keyStore.isKeyEntry(privateKeyAlias)) {
            throw new IllegalStateException("Alias is not a private key entry: " + privateKeyAlias);
        } else if (!keyStore.containsAlias(encryptionCertificateAlias)) {
            throw new IllegalStateException("Keystore does not contain certificate alias: " + encryptionCertificateAlias);
        } else if (keyStore.getCertificate(encryptionCertificateAlias) == null) {
            throw new IllegalStateException("Alias does not contain certificate: " + encryptionCertificateAlias);
        }
    }
}