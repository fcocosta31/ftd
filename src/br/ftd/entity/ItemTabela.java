package br.ftd.entity;

public class ItemTabela implements Comparable<Object>{
	private Produto item;

	public Produto getItem() {
		return item;
	}

	public void setItem(Produto item) {
		this.item = item;
	}

	public boolean changeFamilia(ItemTabela b){
		
		int i = 0, j = 0, k = 0;
		
		if(this.item.getFamilia() != null && b.item.getFamilia() != null) {
			
			if(this.item.getFamilia().equalsIgnoreCase("02-Paradidatico")){
				this.item.setFamilia("03-Literatura");
				this.item.setNivel("05-Geral");
				i = 1;
			}else if(this.item.getFamilia().equalsIgnoreCase("03-Literatura")){
				this.item.setNivel("05-Geral");
				i = 1;
			}
			
			if(b.item.getFamilia().equalsIgnoreCase("02-Paradidatico")){
				b.item.setFamilia("03-Literatura");
				b.item.setNivel("05-Geral");
				j = 1;
			}else if(b.item.getFamilia().equalsIgnoreCase("03-Literatura")){
				b.item.setNivel("05-Geral");
				j = 1;
			}
	
			if(this.item.getFamilia().equalsIgnoreCase("01-DIDLE")){
				this.item.setFamilia("01-Didatico");
				k = 1;
			}
	
			if(b.item.getFamilia().equalsIgnoreCase("01-DIDLE")){
				b.item.setFamilia("01-Didatico");
				k = 1;
			}
			
		}else if (this.item.getFamilia() != null){
			
			if(this.item.getFamilia().equalsIgnoreCase("02-Paradidatico")){
				this.item.setFamilia("03-Literatura");
				this.item.setNivel("05-Geral");
				i = 1;
			}else if(this.item.getFamilia().equalsIgnoreCase("03-Literatura")){
				this.item.setNivel("05-Geral");
				i = 1;
			}
			if(this.item.getFamilia().equalsIgnoreCase("01-DIDLE")){
				this.item.setFamilia("01-Didatico");
				k = 1;
			}
			
			b.item.setFamilia("01-Didatico");
			b.item.setNivel("05-Geral");
			
		}else if(b.item.getFamilia() != null) {
			if(b.item.getFamilia().equalsIgnoreCase("02-Paradidatico")){
				b.item.setFamilia("03-Literatura");
				b.item.setNivel("05-Geral");
				j = 1;
			}else if(b.item.getFamilia().equalsIgnoreCase("03-Literatura")){
				b.item.setNivel("05-Geral");
				j = 1;
			}
			if(b.item.getFamilia().equalsIgnoreCase("01-DIDLE")){
				b.item.setFamilia("01-Didatico");
				k = 1;
			}			
			
			this.item.setFamilia("01-Didatico");
			this.item.setNivel("05-Geral");
			
		}else {
			this.item.setFamilia("01-Didatico");
			this.item.setNivel("05-Geral");
			b.item.setFamilia("01-Didatico");
			b.item.setNivel("05-Geral");
			k = 1; j = 1; i = 1;
		}
		
		return i == 1 || j == 1 || k == 1;
	}
	
	@Override
	public int compareTo(Object o) {
		// TODO Auto-generated method stub
		ItemTabela a = (ItemTabela)o;
		
		changeFamilia(a);
		
		int i = this.item.getFamilia().compareTo(a.item.getFamilia());
		if (i != 0) return i;
		
		i = this.item.getNivel().compareTo(a.item.getNivel());
		if (i != 0) return i;

		String c1, c2;
		
		c1 = a.getItem().getDescricao();
		c2 = this.getItem().getDescricao();
		if (i != 0) return i;
		
		c1 = a.getItem().getCodigo();
		c2 = this.getItem().getCodigo();		
		i = c2.compareTo(c1);
		
		return i;
		
	}
	
	@Override
	public boolean equals(Object o){
		if(this == o) return true;
		if(!(o instanceof ItemTabela)) return false;
		ItemTabela that = (ItemTabela) o;
		return this.item.getCodigo().equals(that.item.getCodigo());
	}

}
