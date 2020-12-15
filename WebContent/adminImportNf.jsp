<div class="center-align div-title-padrao col s12">
		Importar nota fiscal (arquivo xml)
</div>

<div class="container">

	<form id="formimportnotafiscal"
		action="srl" method="post" enctype="multipart/form-data">

			<h5>Escolha a rotina de importação</h5><br>
			
			<p>
				<input type="radio" name="tipoimport" class="with-gap" id="rd1" value="arquivo" checked>
				<label for="rd1">Importar do arquivo</label>
			</p>
			<p>
				<input type="radio" name="tipoimport" class="with-gap" id="rd2" value="email">
				<label for="rd2">Importar do E-mail</label>
			</p>
												
		    <br><br>
		    <div class="" id="div-import-file">
			    <input name="notafiscal" type="file" accept=".xml" multiple>
			    			
				<input type="hidden" name="acao" value="importnotafiscal">
				<input type="hidden" name="tipoimport" value="arquivo">
				<br><br><br>
				<button class="waves-effect waves-light btn blue" type="submit">
					Enviar
				</button>
			</div>
			<div class="noDisplay" id="div-import-email">
				<button class="waves-effect waves-light btn blue" id="btnDownloadNotasEmail">
					Download Notas Fiscais do Email
				</button>
			</div>
			
			
	</form>
</div>

<script type="text/javascript">

$('input[type=radio][name=tipoimport]').change(function(){
	if(this.value == 'arquivo'){
		$('#div-import-file').removeClass('noDisplay');
		$('#div-import-email').addClass('noDisplay');
	}else{
		$('#div-import-email').removeClass('noDisplay');
		$('#div-import-file').addClass('noDisplay');			
	}
});

$(document).on('submit','#formimportnotafiscal', 
		function (e){
		e.preventDefault();
		var formData = new FormData($(this)[0]);
		fileUpload(formData, $(this));
});

$("#btnDownloadNotasEmail").click(function(e){
	e.preventDefault();
	preloadActive();
	$.get("srl",{acao:"downloadnotasemail"},
			function(msg){
			preloadDeActive();
			mbox.alert(msg, function() {});
	});
});

</script>