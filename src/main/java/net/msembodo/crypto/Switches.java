package net.msembodo.crypto;

import com.lexicalscope.jewel.cli.Option;

public interface Switches {
	
	@Option(shortName="e")
	String getEncrypt();
	boolean isEncrypt();
	
	@Option(shortName="d")
	String getDecrypt();
	boolean isDecrypt();
	
	@Option
	String getText();
	boolean isText();
	
	@Option
	String getCode();
	boolean isCode();
	
	@Option(shortName="l")
	boolean isList();
	
	//@Option(helpRequest = true)
	@Option
	boolean isHelp();

}
