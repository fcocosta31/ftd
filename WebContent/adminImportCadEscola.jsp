<style>
	th, td { padding: 5px; }
</style>
<div class="center-align div-title-padrao col s12">
		Importar cadastro de escolas
</div>

<div class="container">

 <div class="row">
 
	<form id="formarquivotabela" action="srl" method="post"
		enctype="multipart/form-data">
			
			<br><br>
			
			<input id="arquivo_tabela" name="uploadTabela" type="file"
				accept=".csv">
			<input type="hidden" name="acao" value="importcadescola">
			
			<div class="col s12">
			
			<h5>Tipo de operação: </h5>
			
				<input type="radio" name="txtacao" class="with-gap" id="rd1" value="insert" checked>
				<label for="rd1">Cadastro</label>
				<input type="radio" name="txtacao" class="with-gap" id="rd2" value="update">
				<label for="rd2">Alteração</label>
			
			</div>
			
			<br><br><br>
			<div class="row"></div>
			<div class="col s12">

				<button class="waves-effect waves-light btn blue" type="submit">
					Enviar
				</button>
			</div>
			
			<br><br>
			<div class="row"></div>

			<article style="font-family: Lucida Sans Unicode">
				Arquivo .csv com o cabeçalho abaixo seguindo esta ordem:<br>
				<table class="bordered striped">
						<tr>
							<td>Nome</td>
							<td>Classificacao</td>
							<td>Dependencia</td>
							<td>Cnpj</td>
							<td>Endereco</td>
							<td>Complemento</td>
							<td>Bairro</td>
							<td>Municipio</td>
							<td>UF</td>
							<td>CEP</td>
							<td>Email</td>
							<td>Telefone</td>
							<td>Setor</td>
							<td>IdFTD</td>
						</tr>
				</table>
				Obs: se for primeiro cadastro deixar o campo id em branco (sem dados).<br><br>
				LEGENDA:<br>
				Classificação: P1 (Purista); A2 (Aspirante); M3 (Mercantil); P4 (Popular) <br>
				Dependência: publica ou privada (em minusculo).

			</article>

	</form>

 </div>

</div>


<script type="text/javascript" src="resources/js/scripts.js"></script>

<script type="text/javascript">
	
	$(document).on('submit','#formarquivotabela', 
			function (e){
			e.preventDefault();
			var formData = new FormData($(this)[0]);
			fileUpload(formData, $(this));
	});
		
</script>
