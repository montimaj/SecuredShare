#!/bin/bash

clear
export MAIN_DIALOG='
<window title="SecuredShare">
  
  <notebook labels="Sign & Encrypt|Decrypt & Verify">
    
    <vbox>
    
    <frame Select Input File>
	  <hbox>
	    <entry>
	      <variable>FILE</variable>
	    </entry>
	    <button>
	      <input file stock="gtk-open"></input>
	      <variable>FILE_BROWSE</variable>
	      <action type="fileselect">FILE</action>
	    </button>
	  </hbox>
     </frame>
     <frame Select JPEG File>
	  <hbox>
	    <entry>
	      <variable>JFILE</variable>
	    </entry>
	    <button>
	      <input file stock="gtk-open"></input>
	      <variable>FILE_BROWSE</variable>
	      <action type="fileselect">JFILE</action>
	    </button>
	  </hbox>
     </frame>
    
      <frame Select Output Directory>
	<hbox>
	  <entry accept="directory">
	    <variable>FILE_DIRECTORY</variable>
	  </entry>
	  <button>
	    <input file stock="gtk-open"></input>
	    <variable>FILE_BROWSE_DIRECTORY</variable>
	    <action type="fileselect">FILE_DIRECTORY</action>
	  </button>
	</hbox>
      </frame>
    
      <vbox>
	<button>
	  <height>20</height>
	  <width>40</width>
	  <label>Encrypt</label>
	  <action signal="clicked">clear; java main.UserInput "$FILE" "$JFILE" "$FILE_DIRECTORY"</action>
	  <variable>"flag"</variable>
	</button>
      </vbox>
    
    </vbox>
    
    <vbox>
      <expander label="Extract Zip" expanded="false" use-underline="true">
	<frame Unzip>
	  <hbox>
	    <entry>
	      <variable>ZFILE</variable>
	    </entry>
	    <button>
	      <input file stock="gtk-open"></input>
	      <variable>zfile</variable>
	      <action type="fileselect">ZFILE</action>
	    </button>
	  </hbox>
	  <vbox>
	    <button>
	      <height>20</height>
	      <width>40</width>
	      <label>Extract</label>
	      <action signal="clicked">unzip -o "$ZFILE" -d "$(dirname "$ZFILE")"; zenity --info --text="Unzip Successful!"</action>
	    </button>
	  </vbox>
	</frame>
      </expander>
      <vbox>         
      <expander label="Decryption" expanded="true">
	<vbox>
	<frame Choose Steg Image>
	    <hbox>
	      <entry>
		<variable>IFILE</variable>
	      </entry>
	      <button>
		<input file stock="gtk-open"></input>
		<variable>ifile</variable>
		<action type="fileselect">IFILE</action>
	      </button>
	    </hbox>
	  </frame>	  
	  	  
	 <frame Select Output Directory>
	<hbox>
	  <entry accept="directory">
	    <variable>DIR</variable>
	  </entry>
	  <button>
	    <input file stock="gtk-open"></input>
	    <variable>DIR_BROWSE</variable>
	    <action type="fileselect">DIR</action>
	  </button>
	</hbox>
      </frame>
    
	  <vbox>
	    <button>
	      <height>20</height>
	      <width>40</width>
	      <label>Decrypt</label>
	      <action signal="clicked">clear;java crypto.Decrypt "$IFILE" "$DIR"</action>
	    </button>
	  </vbox>
	 </vbox>
      </expander>
    
    </vbox>
      
      <expander label="Verification" expanded="false">
	<vbox>
	  <frame Choose File to be Tested>
	    <hbox>
	      <entry>
		<variable>XFILE</variable>
	      </entry>
	      <button>
		<input file stock="gtk-open"></input>
		<variable>xfile</variable>
		<action type="fileselect">XFILE</action>
	      </button>
	    </hbox>
	  </frame>
      
	  <frame Choose Corresponding Signature>
	    <hbox>
	      <entry>
		<variable>XSIG</variable>
	      </entry>
	      <button>
		<input file stock="gtk-open"></input>
		<variable>pkey</variable>
		<action type="fileselect">XSIG</action>
	      </button>
	    </hbox>
	  </frame>
    
	  <frame Choose Appropriate Public Key>
	    <hbox>
	      <entry>
		<variable>PKEY</variable>
	      </entry>
	      <button>
		<input file stock="gtk-open"></input>
		<variable>pkey</variable>
		<action type="fileselect">PKEY</action>
	      </button>
	    </hbox>
	  </frame>
    
	  <vbox>
	    <button>
	      <height>20</height>
	      <width>40</width>
	      <label>Verify</label>
	      <action signal="clicked">clear; java main.Verify "$XFILE" "$XSIG" "$PKEY"</action>
	    </button>
	  </vbox>
	 </vbox>
      </expander>
    
    </vbox>
   
   </notebook>
   
</window>
'
gtkdialog --program=MAIN_DIALOG
