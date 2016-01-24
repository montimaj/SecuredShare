package crypto;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Security;

import javax.crypto.Cipher;
import javax.crypto.KeyAgreement;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;

import org.bouncycastle.jce.ECNamedCurveTable;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.jce.spec.ECNamedCurveParameterSpec;

import util.FileOperations;
/**
 * Main Encryption module
 * @author Sayantan Majumdar
 * @since 1.0
 *
 */

public class Encrypt {
	
    /**
     * Call this constructor to encrypt a file
     * @param inFile Input file to be encrypted using AES192 bit and ECDH as the key agreement protocol
     * @param outDir output directory to store cipher and secret key
     * @throws Exception
     */
	public Encrypt(String inFile, String outDir) throws Exception {
    	doEncrypt(inFile, outDir);   	   	
    }
    private void doEncrypt(String inFile, String outDir) throws Exception {               
        Security.insertProviderAt(new BouncyCastleProvider(), 1);	        
        KeyPair keyPair = generateECKeys();               
        SecretKey secretKey = generateSharedSecret(keyPair.getPrivate(), keyPair.getPublic());       
        byte[] iv=SecureRandom.getSeed(16), skey=secretKey.getEncoded();
        byte[] allBytes= copyByteArray(skey,iv);
        FileOperations.writeToFile(allBytes, outDir+"/key.txt", true);
        byte[] plainText = FileOperations.readFile(inFile, false); 
        byte[] cipherTextBytes=encryptString(secretKey, plainText, iv);
        FileOperations.writeToFile(cipherTextBytes, outDir+"/cipher.txt", true);        
    }
    private byte[] copyByteArray(byte[] skey, byte[] iv) {
    	int i;
    	byte[] b=new byte[skey.length+iv.length];
    	for(i=0;i<skey.length;++i)
    		b[i]=skey[i];
    	for(int j=i,k=0;k<iv.length;++j,++k)
    		b[j]=iv[k];
    	return b;
    }
    private KeyPair generateECKeys() throws Exception {     
        ECNamedCurveParameterSpec parameterSpec = ECNamedCurveTable.getParameterSpec("brainpoolp256r1");
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("ECDH", "BC");
        keyPairGenerator.initialize(parameterSpec, new SecureRandom());
        KeyPair keyPair = keyPairGenerator.generateKeyPair();           
        return keyPair;     
    }
    private SecretKey generateSharedSecret(PrivateKey privateKey, PublicKey publicKey) throws Exception {
    	KeyAgreement keyAgreement = KeyAgreement.getInstance("ECDH", "BC");
        keyAgreement.init(privateKey);
        keyAgreement.doPhase(publicKey, true);
        SecretKey key = keyAgreement.generateSecret("AES");       
        return key;        
    }    
    private byte[] encryptString(SecretKey key, byte[] plainText, byte[] iv) throws Exception {
        Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding", "BC");
        cipher.init(Cipher.ENCRYPT_MODE, key, new IvParameterSpec(iv));
        return cipher.doFinal(plainText);
              
    }    
}