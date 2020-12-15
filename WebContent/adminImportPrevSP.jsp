<style>
	th, td { padding: 5px; }
</style>
<div class="center-align div-title-padrao col s12">
		Importar previsão de vendas (SP)
</div>
<br><br>

<div class="container">
	<form id="formarquivoprevsp" action="srl" method="post"
		enctype="multipart/form-data">
			
			<div class="row">
				<div class="col l4 m4 s12">			
					<input id="arquivo_tabela" name="uploadTabela" type="file"
						accept=".csv">
				</div> 
			</div>						
			<div class="row">
				<div class="col l4 m4 s12">									
					<label>Tipo de operação: </label>
					<p>
						<input type="radio"	class="with-gap" name="txtacao" id="rd1" value="insert" checked>
						<label for="rd1">Cadastro</label>
					</p>
					<p>
						<input type="radio" class="with-gap" name="txtacao" id="rd2" value="update">
						<label for="rd2">Alteração</label>
					</p>					
				</div>
			</div>
			<br><br>
			<div class="row">
				<div class="col l4 m4 s12">									
					<button class="btn waves-effect waves-light blue" type="submit">Enviar</button>
				</div>
			</div>
			
					<br/><br/>
			<div class="row">
				<div class="col l6 m6 s12">										
					<article style="font-family: Lucida Sans Unicode">
						Arquivo .csv com a especificação abaixo:<br>
						<table class="bordered striped">
								<tr>
									<td>Coluna 1</td>
									<td>Coluna 2</td>
									<td>Coluna 3</td>
								</tr>
								<tr>
									<td>Ano (yyyy)</td>
									<td>Código Referência</td>
									<td>Quantidade</td>
								</tr>
						</table>
					</article>
					
				</div>		
			</div>
					<input type="hidden" name="acao"
						value="importprevisaosp"> 

	</form>

</div>


<script type="text/javascript" src="resources/js/scripts.js"></script>

<script type="text/javascript">
	
	$(document).on('submit','#formarquivoprevsp', 
			function (e){
			e.preventDefault();
			var formData = new FormData($(this)[0]);
			fileUpload(formData, $(this));
	});
		
</script>
