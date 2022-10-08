package net.sf.JRecord.cg.details.codes;

/**
 * Controls how a Cobol Copybook is split into Different Records<ul>
 * <li><b>SPLIT_NONE</b> - No Split, leave as a single record
 * <li><b>SPLIT_01_LEVEL</b> Split on 01 records
 * <li><b>SPLIT_REDEFINE</b> Split on the highest level redefines.
 * <li><b>SPLIT_HIGHEST_REPEATING</b> Split on highest repeating level. Similar to 
 * SPLIT_01_LEVEL but works on the lowest repeating level number in the copybook.
 * </ul>
 * @author Bruce Martin
 *
 */
public enum CopybookSplit {

	SPLIT_NONE(ArgumentOption.SPLIT_NONE),
	SPLIT_01_LEVEL(ArgumentOption.SPLIT_01_LEVEL),
	SPLIT_REDEFINE(ArgumentOption.SPLIT_REDEFINE),
	SPLIT_HIGHEST_REPEATING(ArgumentOption.SPLIT_HIGHEST_REPEATING),
	;
	
	/**
	 * Ensure option.codeGenCode is setup
	 */
	public static void init() {
		
	}

	public final ArgumentOption option;

	private CopybookSplit(ArgumentOption option) {
		this.option = option;
		option.setCodeGenCode(this.getClass().getSimpleName() + "." + this.name());
	}
	
}
