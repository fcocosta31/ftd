package br.ftd.quality;

public class MediaFamilia {
	private String familia;
	private float media;
	
	public MediaFamilia(String familia, float media){
		this.familia = familia;
		this.media = media;
	}
	
	public String getFamilia() {
		return familia;
	}
	public void setFamilia(String familia) {
		this.familia = familia;
	}
	public float getMedia() {
		return media;
	}
	public void setMedia(float media) {
		this.media = media;
	}
}
