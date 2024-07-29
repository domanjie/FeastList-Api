package FeastList.security.jwt;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.Date;
import java.util.Map;
import java.util.Optional;

@Component
public class JwtsTokenServiceImpl implements JwtsTokenService {


    @Override
    @SneakyThrows
    public  String createToken(Payload payload,String secrete)  {

        JWSSigner  signer=new MACSigner(secrete);

        JWSObject jwsObject=new JWSObject(new JWSHeader(JWSAlgorithm.HS256),payload);

        jwsObject.sign(signer);

        return jwsObject.serialize();
    }

    @Override
    @SneakyThrows
    public Optional<Map<String, Object>> verifyToken(String token,String secrete)  {

        JWSVerifier verifier=new MACVerifier(secrete);

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
