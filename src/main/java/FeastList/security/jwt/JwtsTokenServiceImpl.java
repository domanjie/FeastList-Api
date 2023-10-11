package FeastList.security.jwt;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;

import java.text.ParseException;
import java.util.Date;
import java.util.Map;
import java.util.Optional;

public class JwtsTokenServiceImpl implements JwtsTokenService {
    private final JWSSigner signer;
    private final JWSVerifier verifier;

    public JwtsTokenServiceImpl(String secrete) throws JOSEException {
        this.signer=new MACSigner(secrete);
        this.verifier=new MACVerifier(secrete);
    }

    @Override
    public  String createToken(Payload payload) throws JOSEException {

        JWSObject jwsObject=new JWSObject(new JWSHeader(JWSAlgorithm.HS256),payload);

        jwsObject.sign(signer);

        return jwsObject.serialize();
    }

    @Override
    public Optional<Map<String, Object>> verifyToken(String token) throws ParseException, JOSEException {

        boolean isValid;

        JWSObject jwsObject = JWSObject.parse(token);

        if (!jwsObject.verify(verifier)) {
            return Optional.empty();
        }
        Date expirationDate = new Date((long) jwsObject.getPayload().toJSONObject().get("exp"));

        if (!expirationDate.after(new Date())) {

           return Optional.empty();

        }

        return Optional.of(jwsObject.getPayload().toJSONObject());

    }

}
