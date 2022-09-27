<div class="center-align div-title-padrao col s12">
		Amarração NF x Pedido
</div>
<br><br>
<div class="container">

	<div class="row">
	<form id="formnotapedido" action="srl" method="post">

			<div class="col l6 m6 s12">
				<label>NÚMERO DO PEDIDO</label>	
				<input type="text" name="txtpedido" class="form-control" required
				maxlength="15">
			</div>

			<div class="col l6 m6 s12">
				<label>NÚMERO DA NOTA</label>
				<input type="text" name="txtnota" class="form-control" required
				maxlength="15">
			</div>

			<div class="col s12">						
				<input type="hidden" name="acao" value="notapedido">
				<button class="btn waves-effect waves-light blue"
				 type="submit">Enviar</button>
			</div>

	</form>
	</div>
</div>

<script type="text/javascript">
	
	$(document).on('submit','#formnotapedido', 
			function (e){
			e.preventDefault();
			sendAjaxNotaPedido($(this).serialize());
	});

</script>
