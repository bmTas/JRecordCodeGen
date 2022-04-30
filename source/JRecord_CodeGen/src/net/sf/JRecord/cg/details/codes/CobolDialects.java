package net.sf.JRecord.cg.details.codes;

/**
 * The Cobol Dialect. Different Cobol dialects use different binary formats. e.g. in Mainframe Cobol
 * <b>pic S99 comp</b> is 2 bytes while in GNU Cobol it is 1 byte. Possible Dialects include<ul>
 * <li><b>MAINFRAME</b>
 * <li><b>FUJITSU</b> Old Fujitsu PC format
 * <li><b>GNU_COBOL</b>
 * </ul>
 * 
 * @author Bruce Martin
 *
 */
public enum CobolDialects {

	MAINFRAME(ArgumentOption.MAINFRAME_DIALECT),
	FUJITSU(ArgumentOption.FUJITSU), 
	GNU_COBOL(ArgumentOption.GNU_COBOL), 
	GNU_COBOL_MF(ArgumentOption.GNU_COBOL_MF), 
	GNU_COBOL_FS2000(ArgumentOption.GNU_COBOL_FS2000)
	
	;
	
	/**
	 * Ensure option.codeGenCode is setup
	 */
	public static void init() {
		
	}
	
	
	public final ArgumentOption option;

	private CobolDialects(ArgumentOption option) {
		this.option = option;
		option.setCodeGenCode(this.getClass().getSimpleName() + "." + this.name());
	}
}
