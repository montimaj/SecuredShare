package crypto.signature;

import java.io.BufferedInputStream;
import java.io.FileInputStream;

import java.security.SecureRandom;
import java.security.Security;
import java.security.Signature;
import java.security.KeyPairGenerator;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.jce.spec.ECParameterSpec;

import util.FileOperations;

import org.bouncycastle.jce.ECNamedCurveTable;

/**
 * Writes the signature and public key to files 
 * @author Sayantan Majumdar
 * @see java.security
 * @since 1.0
 *</p>
*/
public class GenSig
{
  
	/**
	 * Call this constructor to generate the signature and public key where
	 * ECDSA is used along with SHA256 
	 * prime256v1 curve is used as elliptic curve
	 * @param inFile input file to be signed 
	 * @param outDir output directory
	 * @throws Exception
	 */
	public GenSig(String inFile, String outDir) throws Exception {
		genSig(inFile, outDir);
	}   
	private void genSig(String inFile, String outDir) throws Exception	{    
		Security.insertProviderAt(new BouncyCastleProvider(), 1);
		ECParameterSpec ecSpec = ECNamedCurveTable.getParameterSpec("prime256v1");
		KeyPairGenerator keyGen = KeyPairGenerator.getInstance("ECDSA","BC"); 		
		keyGen.initialize(ecSpec, new SecureRandom()); 
		KeyPair pair = keyGen.generateKeyPair();
		PrivateKey priv = pair.getPrivate();
		PublicKey pub = pair.getPublic();
		FileOperations.writeToFile(pub.getEncoded(), outDir+"/pub.txt", true);
		Signature ecdsa = Signature.getInstance("SHA256withECDSA","BC");
		ecdsa.initSign(priv); 
		FileInputStream fis = new FileInputStream(inFile);
		BufferedInputStream bufin = new BufferedInputStream(fis);
		byte[] buffer = new byte[256];
		int len;
		while (bufin.available() != 0) {
			len = bufin.read(buffer);
			ecdsa.update(buffer, 0, len);
		}
		bufin.close();
		byte[] realSig = ecdsa.sign();   
		FileOperations.writeToFile(realSig, outDir+"/sig.txt", true);
	}  
}