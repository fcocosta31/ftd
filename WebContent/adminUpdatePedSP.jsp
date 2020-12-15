<style>
	th, td { padding: 5px; }
</style>

<div class="center-align div-title-padrao col s12">
		Atualizar previsões / observações de Pedidos (SP)
</div>

<div class="container">
	<form id="formaupdatepedsp" action="srl" method="post"
		enctype="multipart/form-data">
			<br><br>
			<div class="col s12">	
				<input id="arquivo_tabela" name="uploadTabela" type="file"
					accept=".csv"> <input type="hidden" name="acao"
					value="updateitempedido"><br>
				<br><br><br>
				<button class="waves-effect waves-light btn blue" type="submit">
					Enviar
				</button>
			</div>
			
			<br><br>
			<div class="row">
			<div class="col l6 m6 s12">
				<article style="font-family: Lucida Sans Unicode">
					Arquivo .csv com a especificação abaixo:<br>
					<table class="bordered striped">
							<tr>
								<td>Coluna 1</td>
								<td>Coluna 2</td>
								<td>Coluna 3</td>
								<td>Coluna 4</td>
							</tr>
							<tr>
								<td>Cod.Pedido</td>
								<td>Código Referência</td>
								<td>Data Previsão</td>
								<td>Observação</td>
							</tr>
					</table>
				</article>
			</div>
			</div>
	</form>

</div>


<script type="text/javascript">
	
	$(document).on('submit','#formaupdatepedsp', 
			function (e){
			e.preventDefault();
			var formData = new FormData($(this)[0]);
			fileUpload(formData, $(this));
	});
		
</script>
