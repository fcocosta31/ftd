<div class="center-align div-title-padrao col s12">
		Atualizar tabela de preços
</div>

<div class="container">
	<form id="formarquivopreco" action="srl" method="post"
		enctype="multipart/form-data">
			
			<br><br>
			<input id="arquivo_preco" name="arqpreco" type="file"
				accept=".csv, text/plain"> 
			
			<input type="hidden" name="acao"
				value="atualizarprc">
						
			<div class="col s12">
			
			<h5>Arquivo separado por: </h5>
			
			<p>
				<input type="radio" name="separador" class="with-gap" id="rd1" value="pontovirgula" checked>
				<label for="rd1">Ponto e virgula (.csv)</label>
			</p>
			<p>
				<input type="radio" name="separador" class="with-gap" id="rd2" value="espaco">
				<label for="rd2">Espaço (.txt)</label>
			</p>
			
			</div>
			
			<br>
			<div class="row"></div>
			<div class="col s12">

				<button class="waves-effect waves-light btn blue" type="submit">
					Enviar
				</button>
			</div>
			
	</form>
</div>


<script type="text/javascript" src="resources/js/scripts.js"></script>

<script type="text/javascript">
	
	$(document).on('submit','#formarquivopreco', 
			function (e){
			e.preventDefault();
			var formData = new FormData($(this)[0]);
			fileUpload(formData, $(this));
	});

</script>
