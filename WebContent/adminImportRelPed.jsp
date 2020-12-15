<div class="center-align div-title-padrao col s12">
		Importar relatório de pedidos do Fornecedor
</div>
<div class="container">
<form class="form-horizontal" role="form" id="formarquivopedido"
	action="srl" method="post" enctype="multipart/form-data">
	
	<div class="row">
			
			<h5>Escolha a rotina de importação</h5><br>
			
			<p>
				<input type="radio" name="tipoimport" class="with-gap" id="rd1" value="arquivo">
				<label for="rd1">Importar do arquivo</label>
			</p>
			<p>
				<input type="radio" name="tipoimport" class="with-gap" id="rd2" value="textarea" checked>
				<label for="rd2">Importar da área de transferência</label>
			</p>
			<div class="noDisplay" id="div-import-file">
				
				<h5>Escolha o arquivo a ser importado:</h5><br>			
				<input id="uploadPedido" type="file" name="uploadPedido" id="uploadPedido" accept=".csv, text/plain" data-url="srl" multiple>
						   
			    <h5>Arquivo separado por: </h5>
			    <p>
				   <input type="radio" id="rd3" class="with-gap" name="separador" value="pontovirgula">
				   <label for="rd3">Ponto e virgula (.csv)</label>
				</p>
				<p> 
				   <input type="radio" id="rd4" class="with-gap" name="separador" value="espaco" checked="checked">
				   <label for="rd4">Espaço (.txt)</label>
				</p>
			</div>
			
			<div class="" id="div-import-text">
				<h5>Cole o texto do pedido no espaço abaixo:</h5><br>
				<div class="col l8 m8 s12">
					<textarea rows="15" cols="50" name="txtpedcolado"></textarea>
				</div>
			</div>
			<br><br><br>
			<div class="col s12">
				<button class="waves-effect waves-light btn blue" id="btnFileUpload"
					type="submit">Enviar</button>		
			</div>
			
			<input type="hidden" name="acao" value="importped">
				
	</div>
	
</form>
</div>
<script type="text/javascript">
	
	$('input[type=radio][name=tipoimport]').change(function(){
		if(this.value == 'arquivo'){
			$('#div-import-file').removeClass('noDisplay');
			$('#div-import-text').addClass('noDisplay');
		}else{
			$('#div-import-text').removeClass('noDisplay');
			$('#div-import-file').addClass('noDisplay');			
		}
	});
	
	$(document).on('submit','#formarquivopedido', 
			function (e){
			
			var tipo = document.getElementsByName("tipoimport")[0].value;
			
			if(tipo === 'arquivo'){
				e.preventDefault();
				var formData = new FormData($(this)[0]);
				fileUpload(formData, $(this));
			}else{
				$('#formarquivopedido').submit();
			}
	});

</script>
