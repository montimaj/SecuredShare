package crypto;

import java.io.IOException;
import java.security.Key;
import java.security.Security;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import stegano.DoStegano;
import util.Log;
import util.Unzip;
import util.FileOperations;
/**
 * Decrypt a cipher text
 * @author Sayantan Majumdar
 * @since 1.0
 */

public class Decrypt {	
	private static void doDecrypt(String cipherText, String secretKey, String outDir) throws Exception {
		byte[] allBytes=FileOperations.readFile(secretKey, true);
		byte[] secretKeyBytes=getSecretKeyBytes(allBytes);	
		byte[] iv=getIVBytes(allBytes);
        byte[] cipherTextBytes=FileOperations.readFile(cipherText, true);
        Key decryptionKey = new SecretKeySpec(secretKeyBytes, "AES");  
        Security.insertProviderAt(new BouncyCastleProvider(), 1);
        Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding", "BC");
        cipher.init(Cipher.DECRYPT_MODE, decryptionKey, new IvParameterSpec(iv));
        byte[] plainText=cipher.doFinal(cipherTextBytes);
        FileOperations.writeToFile(plainText, outDir+"/decrypted.txt", false);   
	}
	private static byte[] getSecretKeyBytes(byte[] b) {
		byte[] skey=new byte[b.length-16];		
		for(int i=0;i<skey.length;++i)
			skey[i]=b[i];
		return skey;
	}
	private static byte[] getIVBytes(byte[] b) {
		byte[] iv=new byte[16];
		for(int i=b.length-16,j=0;i<b.length;++i,++j)
			iv[j]=b[i];		
		return iv;
	}
	/**
	 * Main module
	 * @param args input arguments where
	 * <p>
	 * args[0]-> steg image
	 * args[1]-> output directory
	 * @throws InterruptedException
	 * @throws IOException
	 */
	public static void main(String[] args) throws InterruptedException, IOException	{
		Process p=null;
		try	{			
			if(args.length<2 || args[0].isEmpty() || args[1].isEmpty())
				throw new IOException("Invalid input");	
			String[] x={"zenity","--progress","--pulsate","--no-cancel","--text=Decrypting..."};
			p=new ProcessBuilder(x).start();
			String zipFile=new DoStegano(args[0], args[1]).getExtractedFile();
			String extractedFiles[]=Unzip.unzip(zipFile,args[1]);
			doDecrypt(extractedFiles[0],extractedFiles[1], args[1]);
			p.destroy();
			String x1[]={"zenity","--info","--title=Result","--text=Done!"};
			p=new ProcessBuilder(x1).start();
			p.waitFor();			
		}catch(Exception e)	{
			if(p!=null)
				p.destroy();
			String s=Log.createLog(args[1],e), x[]={"zenity","--error","--text="+s};
			p=new ProcessBuilder(x).start();
			p.waitFor();
		}finally {
			FileOperations.deleteFiles(args[1]+"/extracted.zip");
		}
	}
}
