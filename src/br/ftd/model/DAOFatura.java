package br.ftd.model;

import java.util.ArrayList;
import java.util.List;

import br.ftd.entity.Cliente;
import br.ftd.entity.Fatura;
import br.ftd.entity.Pagamento;

public class DAOFatura {
	
	public boolean salvar(Fatura fatura){
		
		return true;
	}
	
	public boolean addPagamento(Pagamento pagamento){
		
		return true;
	}
	
	public boolean editar(Fatura fatura){
		
		return true;
	}
	
	public boolean deletar(Fatura fatura){
		
		return true;
	}
	
	public List<Cliente> listarClientes(String nome){
		List<Cliente> clientes = new ArrayList<>();
		
		return clientes;
	}
	
	public List<Fatura> listarFaturas(Cliente cliente){
		List<Fatura> faturas = new ArrayList<>();
		
		return faturas;
	}
	
	public List<Fatura> listarFaturas(String vendedor, boolean liquidada){
		List<Fatura> faturas = new ArrayList<>();
		
		return faturas;
	}
	
	public static void recarrega(Fatura fatura){
		
	}
	
	public static void recarrega(Cliente cliente){
		
	}
	
	public static void recarrega(Pagamento pagamento){
		
	}
}
