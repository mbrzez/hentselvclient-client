package pl.brzezins.hentselv.wssecurity;

import lombok.RequiredArgsConstructor;
import org.apache.wss4j.common.ext.WSPasswordCallback;

import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.UnsupportedCallbackException;

@RequiredArgsConstructor
public class PasswordCallbackHandler implements CallbackHandler {

    private final String privateKeyAlias;
    private final String privateKeyPassword;

    @Override
    public void handle(Callback[] callbacks) throws UnsupportedCallbackException {

        for (Callback callback : callbacks) {
            if (callback instanceof WSPasswordCallback passwordCallback) {
                String requestedAlias = passwordCallback.getIdentifier();

                if (privateKeyAlias.equals(requestedAlias)) {
                    passwordCallback.setPassword(privateKeyPassword);
                } else {
                    throw new UnsupportedCallbackException(callback, "Missing password for alias: " + requestedAlias);
                }
            } else {
                throw new UnsupportedCallbackException(callback, "Callback type not supported: " + callback.getClass().getName());
            }
        }
    }
}
