/*  -------------------------------------------------------------------------
 *
 *            Sub-Project: JRecord CodeGen
 *    
 *    Sub-Project purpose: Generate Java - JRecord source code 
 *                        to read/write cobol data files.
 *    
 *                 Author: Bruce Martin
 *    
 *                License: GPL 3 or later
 *                
 *    Copyright (c) 2016, Bruce Martin, All Rights Reserved.
 *   
 *    This library is free software; you can redistribute it and/or
 *    modify it under the terms of the GNU General Public License
 *    as published by the Free Software Foundation; either
 *    version 3.0 of the License, or (at your option) any later version.
 *   
 *    This library is distributed in the hope that it will be useful,
 *    but WITHOUT ANY WARRANTY; without even the implied warranty of
 *    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *    GNU General Public License for more details.
 *
 * ------------------------------------------------------------------------ */
      
package net.sf.JRecord.cg;

import java.io.IOException;

import javax.xml.stream.FactoryConfigurationError;
import javax.xml.stream.XMLStreamException;

import net.sf.JRecord.cg.details.ParseUserArguments;
import net.sf.JRecord.cg.details.WriteCodeGenCall;
import net.sf.JRecord.cg.details.GenerateOptions;
import net.sf.JRecord.cg.details.ParseArgs;
import net.sf.JRecord.cg.velocity.GenerateVelocity;

public class Generate {

	public static void main(String[] args) throws IOException, XMLStreamException, FactoryConfigurationError {
		ParseArgs pa = new ParseArgs(args);

		if (pa.get2Args("-h", "-help", pa.getArg("-?")) != null) {
			ParseUserArguments.printOptions();
		} else {		
			ParseUserArguments opts = ParseUserArguments.parseArgsToHelper(pa);
			if (opts.isAllOk()) {
				GenerateOptions options = opts.getGenerateOptions();
				
				new GenerateVelocity(options, "CodeGen");
				
				String copybook = options.getCopybookFileName();
				if (copybook != null && ! copybook.toLowerCase().endsWith(".xml")) {
					new WriteCodeGenCall(options, copybook)
							.writeTemplateBuilderCode();
				}
			}
		}
	}
}
