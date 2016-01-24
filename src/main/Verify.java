package main;
import crypto.signature.VerSig;

/**
 * Module to display verification result 
 * @author Sayantan Majumdar
 * @since 1.0
 */
class Verify
{
	public static void main(String args[]) throws Exception
	{
		try
		{
			boolean v=VerSig.verify(args[0],args[1],args[2]);
			String[] x={"zenity","--info","--text="+"Verification result: "+v};
			Process p=new ProcessBuilder(x).start(); //Display verification result in a new window
			p.waitFor();
		}
		catch(Exception e)
		{		
			String[] x={"zenity","--error","--text="+e.getMessage()};
			Process p=new ProcessBuilder(x).start(); //Show error window
			p.waitFor();
		}      
	}
}