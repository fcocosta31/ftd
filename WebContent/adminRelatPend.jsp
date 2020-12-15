<div class="center-align div-title-padrao col s12">
		Relatório de itens pendentes (xls)
</div>
<br><br>
<div class="container">

	<form action="srl" method="post" id="formpedidodata">

		<div class="row">
			<div class="col l5 m5 s12">
				<label>Data inicial</label>
				<div>
					<input type="text" id="txtinicio" name="txtinicio" class="datepicker validate">				
				</div>
			</div>
			<div class="col l5 m5 s12">
				<label>Data final</label>
				<div>
					<input type="text" id="txtfim" name="txtfim" class="datepicker validate">					
				</div>
			</div>
			<br><br>
			<div class="col s12">
				<button class="btn waves-effect waves-light blue download-loader" type="button">Enviar</button>
			</div>
		</div>
		<input type="hidden" name="acao" value="pendentesexcel">
	</form>
	
</div>