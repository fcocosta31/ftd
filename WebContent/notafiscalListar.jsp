<script type="text/javascript">

	$(document).on('submit','#formlistarnota', 
			function (e){
			e.preventDefault();
			sendAjaxListarNota($(this).serialize());
	});

</script>
<div class="center-align div-title-padrao col s12">
		Listar Notas Fiscais recebidas
</div>
<br><br>

<div class="container">

	<form action="srl" method="post" id="formlistarnota">

			<div class="row">
				<div class="col l5 m5 s12">
					<label>Data inicial</label>
					<div>
						<input type="text" id="dataini" name="dataini" class="datepicker validate">				
					</div>
				</div>
				<div class="col l5 m5 s12">
					<label>Data final</label>
					<div>
						<input type="text" id="datafim" name="datafim" class="datepicker validate">					
					</div>
				</div>
				
				<br>
				
				<div class="col s12">
					<button class="waves-effect waves-light btn blue" type="submit">
						Enviar
					</button>
				</div>
			</div>
		<input type="hidden" value="listarnotafiscal" name="acao">

	</form>

</div>
