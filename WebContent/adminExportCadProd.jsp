<script type="text/javascript">
	$(document).ready(function(){
		loadDadosPesquisa();
	});
</script>

<div class="center-align div-title-padrao col s12">
		Exportar cadastro de produtos (txt)
</div>

<div class="container">
	<div class="row">
	<form action="srl" method="post" id="formexportprc">

			<input type="hidden" name="acao" value="exportaprodutostxt">
				
			<div class="col s12">
			
			<h5>Habilita filtro por palavra chave? </h5>
			
				<input type="radio" name="filtro" class="with-gap" id="rd1" value="sim">
				<label for="rd1">Sim</label>
				<input type="radio" name="filtro" class="with-gap" id="rd2" value="nao" checked>
				<label for="rd2">Não</label>
				<br>
				<input type="text" id="txtdescricao" size="50"
				placeholder="Digite a palavra chave de pesquisa do filtro..."
				name="txtdescricao">			
			
			</div>

			
			<br/><br/>
			<div class="col s12">
				<button class="waves-effect waves-light btn blue download-loader"
					type="button">Exportar
				</button>					
			</div>
	
	</form>
	</div>
</div>