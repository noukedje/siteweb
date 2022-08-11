package com.gestion.commerce.Model;
import javax.persistence.*;

import java.awt.Image;
import java.io.Serializable;
import java.util.List;
import javax.validation.constraints.*;

import org.springframework.web.multipart.MultipartFile;
@Entity
public class Categorie implements Serializable {
   /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
@Id
   @GeneratedValue(strategy=GenerationType.AUTO)
   private Long idcategorie;
   @Size(max=30)
   @NotNull  //validation backend (BD), alors @NotEmpty=validation frontend 
   private String nomcategorie;
   private String nomphoto;
   @Lob
   private byte[] photo;
   private String description;
   @OneToMany(mappedBy="categorie",fetch = FetchType.EAGER)
   private List<Produit> produits;
   
   public Categorie(){
	   super();
   }
   
    public byte[] getPhoto() {
	return photo;
	}
	
	public void setPhoto(byte[] photo) {
		this.photo = photo;
	}

	public String getNomphoto() {
		return nomphoto;
	}
    public void setNomphoto(String nomphoto) {
		this.nomphoto = nomphoto;
	}
	public Long getIdcategorie() {
		return idcategorie;
	}
	public void setIdcategorie(Long idcategorie) {
		this.idcategorie = idcategorie;
	}
	public String getNomcategorie() {
		return nomcategorie;
	}
	public void setNomcategorie(String nomcategorie) {
		this.nomcategorie = nomcategorie;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public List<Produit> getProduits() {
		return produits;
	}
	public void setProduits(List<Produit> produits) {
		this.produits = produits;
	}
	@Transient   //n'a pas une propriété équivalente stockée en base de données
	public int getNbProduits() {
	   return produits.size();	
	}
}