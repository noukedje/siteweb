package com.gestion.commerce.Model;

import org.springframework.web.multipart.MultipartFile;

public class UploadCategoryForm extends Categorie {
 
	/*private long idcateg;
    private String description;
    private String nomcategorie;*/
    // Upload files.
    private MultipartFile[] fileDatas;
    
    public UploadCategoryForm() {
		super();
		// TODO Auto-generated constructor stub
	}

	/*public long getIdcateg() {
		return idcateg;
	}
    public void setIdCateg(long idcateg) {
    	this.idcateg=idcateg;
    }
	public String getDescription() {
        return description;
    }
      
    public String getNomcategorie() {
		return nomcategorie;
	}

	public void setNomcategorie(String nomcategorie) {
		this.nomcategorie = nomcategorie;
	}

	public void setDescription(String description) {
        this.description = description;
    }*/

    public MultipartFile[] getFileDatas() {
        return fileDatas;
    }

    public void setFileDatas(MultipartFile[] fileDatas) {
        this.fileDatas = fileDatas;
    }

}