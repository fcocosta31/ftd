
<script type="text/javascript">
$(document).ready(function(){
	  		    
	loadComboClientes();
	
	$('.select-with-search').select2({width: "100%"});
	
	$("#btn_enviar_pedido").click(function(){
		nr_pedido = $("#txtpedido").val();
		inicio = $("#dataini").val();
		fim = $("#datafim").val();
		if (nr_pedido === ''){
			console.log("+++++ ===> sem numero de pedido++++");
			if(inicio === ''){
				mbox.alert("Preencha o campo Data Inicial!!");
			}else if(fim === ''){
				mbox.alert("Preencha o campo Data Final!!");
			}else{
				$("#formpedidodata").submit();
			}
		}else{
			$("#formpedidodata").submit();
		}
	});
	
});
</script>

<div class="center-align div-title-padrao col s12">
		Listar pedidos de clientes
</div>
<br>
<div class="container">
<div class="row">
<div class="col l10 m10 s12">
 <form action="srl" method="post" id="formpedidodata">
	<div class="row">
			<div class="col l12 m12 s12">
				<label>Cliente</label><br><br>
				<select id="cmbClientes" name="txtcliente"
				 class="select-with-search">
				</select>				
			</div>
			<div class="row"></div>														
			<br>			
			<div class="col l6 m6 s12">
				<label>Data inicial</label>
				<div>
					<input type="text" id="dataini" name="dataini" class="datepicker validate">				
				</div>
			</div>
			<div class="col l6 m6 s12">
				<label>Data final</label>
				<div>
					<input type="text" id="datafim" name="datafim" class="datepicker validate">					
				</div>
			</div>
			
   </div>
		<div class="row">
			<div class="col l12 m12 s12">
				<div>
				<h4>OU</h4>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col l6 m6 s12">
				<div class="input-field">
					<input type="number" id="txtpedido" name="txtpedido">
					<label for="txtpedido">Número do pedido</label>
				</div>
			</div>
			<div class="col l6 m6 s12 right-align">
				<button class="btn waves-effect waves-light blue" type="button" id="btn_enviar_pedido">
					Enviar
				</button>
			</div>		
		</div>
			
			
		<input type="hidden" name="acao" value="listarpedcliente">
			
	</form>
</div>
</div>
</div>