package net.sf.JRecord.zcg.zTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Test;

import net.sf.JRecord.Common.IFileStructureConstants;
import net.sf.JRecord.cg.details.ParseArgs;
import net.sf.JRecord.cg.details.ParseUserArguments;
import net.sf.JRecord.cg.details.codes.ArgumentOption;
import test.gen.TstIoBldrGen01;

/**
 * Check the behaviour of the generate options class
 * @author Bruce Martin
 *
 */
public class TstGenerateOptions1 {

	private static final String LINE_WRAPPER_POJO_TEMPLATE = "lineWrapperPojo";
	private static final String JAVA_POJO = "javaPojo";
	private static final String STANDARD_TEMPLATE = "standard";
	private static final String MY_PACKAGE_ID = "MyPackageId";
	private static final String OUTPUT_DIR = "G:/Temp/Gen/ioBuilder";
	private static final String US_EBCDIC = "CP037";
	private static final String BASIC_TEMPLATE = "basic";

	@Test
	public void test01() {
		String[] arguments1 = {
				ArgumentOption.OPT_TEMPLATE, BASIC_TEMPLATE,
				ArgumentOption.OPT_SCHEMA, TstIoBldrGen01.class.getResource("DTAR020.cbl").getFile(),
				ArgumentOption.OPT_FILE_ORGANISATION, "FixedWidth",
				ArgumentOption.OPT_FONT_NAME, US_EBCDIC,
				ArgumentOption.OPT_DROP_COPYBOOK_NAME, "true",
				ArgumentOption.OPT_OUTPUT_DIR, OUTPUT_DIR
		};
		ParseUserArguments g = ParseUserArguments.parseArgsToHelper(new ParseArgs(arguments1));
		
		assertEquals(BASIC_TEMPLATE, g.getGenerateOptions().getTemplateDtls().getTemplate());
		chkValues(g);
		
		arguments1[1] = STANDARD_TEMPLATE;
		
		g = ParseUserArguments.parseArgsToHelper(new ParseArgs(arguments1));		
		assertFalse(g.isAllOk());
		assertEquals(1, g.getGenerateOptions().getTemplateDtls().getGenerateOptions().size());
		
		ArrayList<String> argList = new ArrayList<String>(Arrays.asList(arguments1));
		argList.add(ArgumentOption.OPT_PACKAGE);
		argList.add(MY_PACKAGE_ID);
		
		arguments1 = argList.toArray(new String[argList.size()]);
		
		g = ParseUserArguments.parseArgsToHelper(new ParseArgs(arguments1));	
		assertEquals(STANDARD_TEMPLATE, g.getGenerateOptions().getTemplateDtls().getTemplate());
		chkValues1(g);
		assertEquals(1, g.getGenerateOptions().getTemplateDtls().getGenerateOptions().size());
		
		arguments1[1] = JAVA_POJO;
		g = ParseUserArguments.parseArgsToHelper(new ParseArgs(arguments1));	
		assertEquals(JAVA_POJO, g.getGenerateOptions().getTemplateDtls().getTemplate());
		chkValues1(g);
		assertEquals(3, g.getGenerateOptions().getTemplateDtls().getGenerateOptions().size());
		assertTrue(g.getGenerateOptions().getTemplateDtls().getGenerateOptions().containsKey("bean"));
		
	}

	private void chkValues1(ParseUserArguments g) {
		chkValues(g);
		assertEquals(MY_PACKAGE_ID, g.getGenerateOptions().getPackageId());
	}

	/**
	 * @param g
	 */
	private void chkValues(ParseUserArguments g) {
		assertEquals(true, g.isAllOk());
		assertEquals(IFileStructureConstants.IO_FIXED_LENGTH, g.getGenerateOptions().getFileStructureCode().id);
		assertEquals(US_EBCDIC, g.getGenerateOptions().getFont());
		assertEquals(true, g.getGenerateOptions().isDropCopybookName());
		assertEquals(OUTPUT_DIR, g.getGenerateOptions().getOutputDir());
	}

	
	@Test
	public void test02() {
		String[] arguments1 = {
				ArgumentOption.OPT_TEMPLATE, LINE_WRAPPER_POJO_TEMPLATE,
				ArgumentOption.OPT_SCHEMA, TstIoBldrGen01.class.getResource("DTAR020.cbl").getFile(),
				ArgumentOption.OPT_FILE_ORGANISATION, "Text",
				//ArgumentOption.OPT_FONT_NAME, US_EBCDIC,
				//ArgumentOption.OPT_DROP_COPYBOOK_NAME, "true",
				ArgumentOption.OPT_SPLIT, "01",
				ArgumentOption.OPT_PACKAGE, MY_PACKAGE_ID,
				ArgumentOption.OPT_OUTPUT_DIR, OUTPUT_DIR
		};
		ParseUserArguments g = ParseUserArguments.parseArgsToHelper(new ParseArgs(arguments1));
		
		assertEquals(LINE_WRAPPER_POJO_TEMPLATE, g.getGenerateOptions().getTemplateDtls().getTemplate());
		assertEquals(true, g.isAllOk());
		assertEquals(IFileStructureConstants.IO_BIN_TEXT, g.getGenerateOptions().getFileStructureCode().id);
		assertEquals("", g.getGenerateOptions().getFont());
		//assertEquals(true, g.getGenerateOptions().isDropCopybookName());
		assertEquals(OUTPUT_DIR, g.getGenerateOptions().getOutputDir());

	}
		
}
