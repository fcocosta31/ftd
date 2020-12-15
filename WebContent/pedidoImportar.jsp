<script type="text/javascript">

$(document).on("click", "#btnEnviarArquivo", function(e) {
	
	uploadFile($('#formarquivopedido').serialize());

	
});	

</script>

<form id="formarquivopedido" action="srl" method="post"
	enctype="multipart/form-data">
	<div class="input-group">
		<label>Importar do arquivo: </label> <input id="arquivo_tabela"
			name="uploadPedido" type="file" accept=".csv"> <br> <input
			data-dismiss="modal" class="btn btn-primary" type="button"
			value="Enviar" id="btnEnviarArquivo">
	</div>
	<input type="hidden" name="acao" value="importpedido">

</form>
