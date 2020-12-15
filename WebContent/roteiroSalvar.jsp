<div id="divcontent">

	<form action="srl" method="post" id="formsalvarroteiro">

		<div class="row">

			<div class="col l5 m5 s12" id="divselectvendedor">
				
				<label>Vendedor: </label>
				<input type="text" value="${vendedor.nome}" readonly>

			</div>

			<div class="col l5 m5 s12">
				<label>Data da visita: </label> 
				<div>
					<input type="date" name="dataroteiro" required>				
				</div>
			</div>

			<div class="col l2 m2 s12">
				<br />
				<button type="button" class="waves-effect waves-light btn blue"
					onclick="btnsalvarroteiroclick()">Enviar</button>
			</div>

		</div>
		<input type="hidden" name="txtsetor" value="${vendedor.setor}">
		<input type="hidden" value="salvarroteiro" name="acao">
		
	</form>	
	
</div>