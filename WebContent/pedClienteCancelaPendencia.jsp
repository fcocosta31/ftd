<div class="center-align div-title-padrao col s12">
		Cancelar Pendências dos Pedidos de Clientes
</div>
<br><br>
<div class="container">
	<div class="row">
		<form class="form-horizontal" role="form" action="srl" method="post"
			id="formcancgdpend">
				<div class="col s12">
					<h5>Pedidos implantados no período de:</h5><br>
					<div class="row"></div>														
							
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
				
					<input type="hidden" name="acao" value="cancelaguardarpendencia">
				</div>
				
				<br><br><br>
				<div class="row"></div>
				<br>
				<div class="col s12">
					<button class="btn waves-effect waves-light blue download-loader"
					onclick="cancelaGuardaPendencia()" type="button">Enviar</button>
				</div>				
		</form>
	</div>
</div>

<script type="text/javascript">

	function cancelaGuardaPendencia(){
		var formulario = $("#formcancgdpend").serialize();
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