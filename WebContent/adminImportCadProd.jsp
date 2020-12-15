<div class="center-align div-title-padrao col s12">
		Importar cadastro de produtos
</div>

<div class="container">

  <div class="row">
  
	<form id="formarquivotabela" action="srl" method="post"
		enctype="multipart/form-data" accept-charset="UTF-8">
			<br><br>
			<input id="arquivo_tabela" name="uploadTabela" type="file" accept=".csv">
				
			<input type="hidden" name="acao" value="importcadprod">

			<div class="col s12">
			
			<h5>Tipo de operação: </h5>
			
			<p>
				<input type="radio" name="txtacao" class="with-gap" id="rd1" value="insert" checked>
				<label for="rd1">Cadastro</label>
			</p>
			<p>
				<input type="radio" name="txtacao" class="with-gap" id="rd2" value="update">
				<label for="rd2">Alteração</label>
			</p>
			
			</div>
					
			<br><br><br>
			<div class="row"></div>
			<div class="col l4 m4 s12">

				<button class="waves-effect waves-light btn blue" type="submit">
					Enviar
				</button>
			</div>
			
			<div class="col l8 m8 s12 left-align">
				
				<a
					href="srl?acao=downloadtemplate" class="downloadmodelo"> <img
					src="resources/images/export_excel.png" title="Download Modelo"
					style="margin-left: 30%"> >>> Download do modelo para
					importação
				</a>
								
			</div>

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
	
	$(document).on('click','a.downloadmodelo', 
			function (e){
			exportaConstultaExcel(e);			
	});
	
</script>
