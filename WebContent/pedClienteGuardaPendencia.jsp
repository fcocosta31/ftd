<div class="center-align div-title-padrao col s12">
		Guardar Pend�ncia do Pedido de Cliente
</div>
<br><br>
<div class="container">
	<div class="row">
		<form class="form-horizontal" role="form" action="srl" method="post"
			id="formaltgdpend">
				<div class="col s12">
					<h5>Guardar Pend�ncia?</h5><br>
					<p>
					<input type="radio" name="opcao" class="with-gap" id="rd2" value="0">
					<label for="rd2">Sim</label>

					<input type="radio" name="opcao" class="with-gap" id="rd1" value="1" checked>
					<label for="rd1">N�o</label>
					</p>
				
					<input type="hidden" name="acao" value="alteraguardarpendencia">
				</div>
				
				<div class="row"></div>
				<br><br><br>
				<div class="col s12" id="div-export-ftd">		
					<div class="col l6 m6 s12">
						<label>N�mero do Pedido</label>				
						<input type="number" name="txtidpedido">
					</div>				
				</div>			
				<br><br><br>
				<div class="row"></div>
				<br>
				<div class="col s12">
					<button class="btn waves-effect waves-light blue download-loader"
					onclick="alteraGuardaPendencia()" type="button">Enviar</button>
				</div>				
		</form>
	</div>
</div>

<script type="text/javascript">

	function alteraGuardaPendencia(){
		var formulario = $("#formaltgdpend").serialize();
		$.ajax({
			type:"POST",
			url:"srl",
			data:formulario,
			dataType:"json",
			success:function(msg){
  				mbox.alert(msg, function() {
  					$("#formaltgdpend").trigger('reset');
  				});					
			}
		});
	};
	
</script>