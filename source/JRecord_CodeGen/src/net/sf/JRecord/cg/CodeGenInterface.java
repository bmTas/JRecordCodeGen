package net.sf.JRecord.cg;

import java.io.IOException;

import net.sf.JRecord.Details.IGetLayout;
import net.sf.JRecord.Details.LayoutDetail;
import net.sf.JRecord.ExternalRecordSelection.ExternalSelectionBuilder;
import net.sf.JRecord.cg.interfaceDtls.CGTemplates;
import net.sf.JRecord.cg.interfaceDtls.CGWalker;
import net.sf.JRecord.cg.schema.LayoutDef;
import net.sf.JRecord.cg.walker.ISchemaWalkerInterface;

/**
 * The purpose of <b>JRecord-CodeGen</b> is to generate to <b>Cobol Data</b> in <i>Java<i> 
 * using the <i>JRecord</i> package from a Cobol Copybook.
 *  
 * This class is main interface to <b>JRecord-CodeGen</b>. It has 3 static variables
 * <ul>
 *  <li><b>TEMPLATES</b> - For generating <i>CodeGen Templates</i>. This will generate
 *  will generate multiple classes from a Cobol Copybook. There are multiple <i>templates</i> available.
 *  for use. CodeGen templates are based on velocity templates.
 *  <li><b>WALKER</b> Contains  methods to generate code using a <i>File-Schema Walker</i> which calls
 *  a supplied <b>Observer</b> ({@link ISchemaWalkerObserver}. There are 2 basic interfaces<ul>
 *    <li>A <b>builder</b> which uses supplied Observers.
 *    <li>A Schema-Walker where you supply the observer.
 *  </ul>
 *  <li><b>SCHEMA_OPTIONS</b> - Options for converting a JRecord-Schema to a CodeGen-Schema.
 *  A CodeGen schema decodes JRecord values into the Equivalent Constants. e.g. 
 *  <b>codegenSchema.getJRecordFileStructureConstant()</b> will return a string
 *  like `IFileStructureConstants.IO_FIXED_LENGTH_RECORDS`. This allows you to generte JRecord code.
 *  
 * </ul>
 * 
 * @author Bruce Martin
 *
 */
public class CodeGenInterface {

	/**
	 * Access CodeGen Templates / Template Builder
	 */
	public static final CGTemplates TEMPLATES = new CGTemplates();
		
	/**
	 * Access CodeGen Copybook-Walker interface. The Copybook Walker provides.
	 * Some Examples
	 * 
	 * <pre>
	 *	ICobolWalkerInterface walker = CodeGenInterface.WALKER;
	 *	walker.newBuilder(ioBuilder)
	 *				.setOutputDirectory("/home/bruce/work/JRecord/CodeGen")
	 *				.setCobolDataClassPackageName("test.walker.Builder.GenerateCode.data")
	 *				.setReadClassGenerateParameters(walker.newClassDetailsParam()
	 *													.setPackageName("test.walker.Builder.GenerateCode.example"))
	 *				.setWriteClassGenerateParameters(walker.newClassDetailsParam()
	 *													.setPackageName("test.walker.Builder.GenerateCode.example"))
	 *			.generate();
	 *</pre>

	 * 
	 */
	public static final ISchemaWalkerInterface WALKER = new CGWalker();
	
	/**
	 * Options to convert <i>JRecord-LayoutDetail</i> (File description) to a
	 * <i>CodeGen-LayoutDef</i>
	 */
	public static final SchemaOptions SCHEMA_OPTIONS = new SchemaOptions();
	
    /**
     * Builder to create Record Selection criteria from Field test and <i>and</i> and <i>or</i> 
     * boolean operators. Example of usage
     * <pre>
     * 
     *	ExternalSelectionBuilder selectionBldr = CodeGenInterface.RECORD_SELECTION_BUILDER;
     *	CodeGenInterface.TEMPLATES.newTempateBuilder(amsPoCopybook)
     *			.setCopybookSplitOption(CopybookSplit.SPLIT_01_LEVEL)
     *			.addRecordSelection("PO-Record",       selectionBldr.newFieldSelectionEquals("Record-Type", "H1"))
     *			.addRecordSelection("Product-Record",  selectionBldr.newFieldSelectionEquals("Record-Type", "D1"))
     *			.addRecordSelection("Location-Record", selectionBldr.newFieldSelectionEquals("Record-Type", "S1"))
     *                   
     * </pre>
     */
    public static final ExternalSelectionBuilder RECORD_SELECTION_BUILDER = new ExternalSelectionBuilder();

	
	
	/**
	 * Convert a <i>JRecord-LayoutDetail</i> (File description) to a
	 * <i>CodeGen-LayoutDef</i>. The LayoutDef holds much of 
	 * 
	 * @author Bruce Martin
	 *
	 */
	public static class SchemaOptions {
		/**
		 * Convert a class (e.g. IoBuilder) that can create a JRecord LayoutDetail (File Schema)
		 * to a CodeGen LayoutDef.
		 * A LayoutDef contains LayoutDetails + various JRecord Constants (in String form). 
		 * It is intended to make it easier to Generate Java-JRecord code.
		 * 
		 * @param layoutSource Class that can create a JRecord File Schema or File Description (LayoutDetail).
		 * e.g. IoBuilder
		 * 
		 * @return A CodeGen LayoutDef 
		 */
		public static final LayoutDef createCodeGenLayoutDefinition(IGetLayout layoutSource) throws IOException {
			return createCodeGenLayoutDefinition(layoutSource.getLayout());
		}
		
		/**
		 * Convert a JRecord LayoutDetail (File Schema) to a CodeGen LayoutDef.
		 * A LayoutDef contains LayoutDetails + various JRecord Constants (in String form). 
		 * It is intended to make it easier to Generate Java-JRecord code.
		 * 
		 * @param layout JRecord File Schema or File Description
		 * 
		 * @return A CodeGen LayoutDef 
		 */
		public static final LayoutDef createCodeGenLayoutDefinition(LayoutDetail layout) {
			return new LayoutDef(layout, layout.getLayoutName(), null);
		}
		
	}
}
