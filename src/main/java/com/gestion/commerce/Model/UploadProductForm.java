package com.gestion.commerce.Model;

import org.springframework.web.multipart.MultipartFile;

public class UploadProductForm extends Produit {
    MultipartFile[] fileDatas;
    private long idcategorie;
 
	public UploadProductForm() {
		super();
		// TODO Auto-generated constructor stub
	}

	public MultipartFile[] getFileDatas() {
		return fileDatas;
	}

	public void setFileDatas(MultipartFile[] fileDatas) {
		this.fileDatas = fileDatas;
	}

	public long getIdcategorie() {
		return idcategorie;
	}

	public void setIdcategorie(long idcategorie) {
		this.idcategorie = idcategorie;
	}
    
}
