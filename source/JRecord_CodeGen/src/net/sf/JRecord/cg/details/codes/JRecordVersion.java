package net.sf.JRecord.cg.details.codes;

import java.math.BigDecimal;

/**
 * JRecord Versions supported by CodeGen. The versions listed
 * are where changes that affect CodeGen where made so use
 * VERSION_0_81_5 for JRecord versions 0.81.5 up to JRecord 0.90.0.
 * Versions currently available<ul>
 * <li><b>VERSION_0_81_5</b>
 * <li><b>VERSION_0_90_0</b>
 * <li><b>VERSION_9_30_0</b>
 * </ul>
 * 
 * @author Bruce Martin
 *
 */
public enum JRecordVersion {

	/**
	 * JRecord versions  0.81.5 --> 0.90.0
	 */
	VERSION_0_81_5(815, "JRecord 0.81.5"),
	/**
	 * JRecord versions 0.90.0 --> 1.20.0
	 */
	VERSION_0_90_0(900, "JRecord 0.90.0"),
	/**
	 * JRecord versions 0.90.0 --> 1.20.0
	 */
	VERSION_0_93_0(930, "JRecord 0.93.0 or later"),

	;
	public final int jrecordVersionCode;
	public final BigDecimal jrecordVersion;
	public final String description;
	
	
	private JRecordVersion(int jrecordVersionCode, String description) {
		this.jrecordVersionCode = jrecordVersionCode;
		this.jrecordVersion = BigDecimal.valueOf(jrecordVersionCode, 3);
		this.description = description;
	}
	
}
