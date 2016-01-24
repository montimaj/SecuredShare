package main;

import java.io.IOException;

import org.apache.pdfbox.ExtractText;

import crypto.Encrypt;
import crypto.signature.GenSig;

import util.FileOperations;
import util.Log;
import util.QRCode;
import util.ZipCreator;

/**
 * Takes plain text file path as input and uses 
 * <p>
 * {@link crypto.Encrypt} to generate out.zip which consists of a key file, cipher, signature and public key
 * @author Sayantan Majumdar
 * @since 1.0
 */
public class UserInput 
{

	/**
	 * Main module 
	 * @param args Input arguments where
	 * <p>
	 * args[0]->Input file
	 * args[1]->Output directory
	 * </p>
	 * @throws IOException 
	 * @throws InterruptedException 
	 */
	public static void main(String[] args) throws IOException, InterruptedException
	{
		Process p=null;
		String[] inFiles={""};
		try
		{
			if(args.length<2 || args[0].isEmpty() || args[1].isEmpty())
				throw new IOException("Invalid Input"); 
			String[] x={"zenity","--progress","--pulsate","--no-cancel","--text=Encrypting..."};
			p=new ProcessBuilder(x).start();
			if(FileOperations.is_pdf(args[0]))
		    {	    	  
		    	  String[] s={args[0], args[1]+"/out.txt"};
		    	  ExtractText.main(s); //extract text from pdf file
		    	  args[0]=args[1]+"/out.txt";
		    }			
			new Encrypt(args[0],args[1]);
			new GenSig(args[0],args[1]);			
			String[] files={args[1]+"/cipher.txt",args[1]+"/key.txt",args[1]+"/pub.txt",args[1]+"/sig.txt"};
			inFiles=files;
			ZipCreator.createZip(args[1]+"/out.zip", files);
			new QRCode(args[1]+"/out.zip", args[1]);			
			p.destroy();
			String x1[]={"zenity","--info","--title=Result","--text=Done!"};
			p=new ProcessBuilder(x1).start();
			p.waitFor();
		}
		catch(Exception e)
		{
			if(p!=null)
				p.destroy();
			String s=Log.createLog(args[1], e), x[]={"zenity","--error","--text="+s};
			p=new ProcessBuilder(x).start();
			p.waitFor();
		}
		finally {
			FileOperations.deleteFiles(inFiles);
		}
	}
}
