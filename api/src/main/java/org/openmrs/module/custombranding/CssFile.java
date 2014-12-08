package org.openmrs.module.custombranding;

import org.openmrs.BaseOpenmrsObject;

import java.io.Serializable;


public class CssFile extends BaseOpenmrsObject implements Serializable {

	private static final long serialVersionUID = 1L;
	private Integer cssFileId;
	private String name;
	private String content;
    private String nameAndPath;

    public String getNameAndPath() { return nameAndPath;}

    public void setNameAndPath(String nameAndPath) { this.nameAndPath = nameAndPath;}

    public Integer getCssFileId() {
		return cssFileId;
	}

	public void setCssFileId(Integer cssFileId) {
		this.cssFileId = cssFileId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Override
	public Integer getId() {
		return getCssFileId();
	}
	@Override
	public void setId(Integer id) {
		setCssFileId(id);
	}


}
