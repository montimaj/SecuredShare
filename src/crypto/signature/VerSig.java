package crypto.signature;

import java.io.FileInputStream;
import java.io.BufferedInputStream;

import java.security.KeyFactory;
import java.security.Security;
import java.security.Signature;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import util.FileOperations;

/**
 * Verifies whether the input file is authentic  
 * @author Sayantan Majumdar
 * @since 1.0
*/
public class VerSig {
    /**
     * Return verification result
     * @param sign Signature file
     * @param data Input file to be verified
     * @return true if authentic false otherwise
     * @throws Exception
     */
	public static boolean verify(String data, String sign, String pub) throws Exception {           
            byte[] encKey = FileOperations.readFile(pub, true);           
            Security.insertProviderAt(new BouncyCastleProvider(), 1);
            X509EncodedKeySpec pubKeySpec = new X509EncodedKeySpec(encKey);
            KeyFactory keyFactory = KeyFactory.getInstance("ECDSA","BC");
            PublicKey pubKey = keyFactory.generatePublic(pubKeySpec);            
            byte[] sigToVerify = FileOperations.readFile(sign, true);            
            Signature sig = Signature.getInstance("SHA256withECDSA","BC");  
            sig.initVerify(pubKey);           
            FileInputStream datafis = new FileInputStream(data); 
            BufferedInputStream bufin = new BufferedInputStream(datafis);
            byte[] buffer = new byte[256];
            int len;
            while (bufin.available() != 0) {
                len = bufin.read(buffer);
                sig.update(buffer, 0, len);
            }
            bufin.close();
            return sig.verify(sigToVerify);                  
    }    
}