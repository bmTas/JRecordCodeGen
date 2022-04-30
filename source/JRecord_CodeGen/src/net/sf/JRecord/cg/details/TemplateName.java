package net.sf.JRecord.cg.details;

import net.sf.JRecord.cg.details.codes.StandardTemplates;

public class TemplateName {
	public final StandardTemplates template;
	public final String userTemplate;
	
	
	public TemplateName(StandardTemplates template, String userTemplate) {
		super();
		this.template = template;
		this.userTemplate = userTemplate;
	}
	
	public TemplateName(String templateName, String userTemplateDir) {
		if (userTemplateDir != null) {
			this.template = null;
			this.userTemplate = userTemplateDir;
		} else {
			StandardTemplates tval = null;
			for (StandardTemplates st :StandardTemplates.values()) {
				if (st.option.option.equalsIgnoreCase(templateName)) {
					tval = st;
					break;
				}
			}
			template = tval;
			userTemplate = null;
		}
	}
}
