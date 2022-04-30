package net.sf.JRecord.cg.details.codes;

/**
 * Standard Templates that come with JRecord-CodeGen<ul>
 * <li><b>STANDARD</b> JRecord Example read/write program + Class with
 * the Cobol field names
 * <li><b>LINE_WRAPPER</b> Create a *wrapper* class where you can get/set the Cobol Fields. 
 * Example Read/Write programs are also generated.
 * <li><b>POJO</b> Generate<ul>
 *  <li>Java Pojo's for the Cobol Records
 *  <li>Conversion code to convert the Cobol Records to/from the Java pojo's
 *  <li>Example Read/Write programs.
 * </ul>
 * <li><b>POJO_INTERFACE</b> Sames as <b>POJO</b> bu also generate Interfaces for the Pojo.
 * </ul>
 * 
 * @author Bruce Martin
 *
 */
public enum StandardTemplates {

	STANDARD(ArgumentOption.TEMPLATE_STANDARD),
	LINE_WRAPPER(ArgumentOption.TEMPLATE_LINE_WRAPPER),
	POJO(ArgumentOption.TEMPLATE_POJO),
	POJO_INTERFACE(ArgumentOption.TEMPLATE_POJO_INTERFACE),
	BASIC(ArgumentOption.TEMPLATE_BASIC),
	
	/**
	 * Suggest using <b>TEMPLATE_POJO_INTERFACE</b> instead
	 */
	LINE_WRAPPER_POJO(ArgumentOption.TEMPLATE_LINE_WRAPPER_POJO),

	;
	
	public final ArgumentOption option;

	private StandardTemplates(ArgumentOption option) {
		this.option = option;
		option.setCodeGenCode(this.getClass().getSimpleName() + "." + this.name());
	}

}
