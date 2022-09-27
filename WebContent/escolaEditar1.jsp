<script type="text/javascript">

$(document).on('keyup', '#nome', function(event){
	var pos = $(this).caret();
	this.value = removerAcentos(this);
	$(this).caret(pos);
});
	   
$(document).on('keyup', '#endereco', function(){
	var pos = $(this).caret();
	this.value = removerAcentos(this);
	$(this).caret(pos);
});

$(document).on('keyup', '#municipio', function(){
	var pos = $(this).caret();
	this.value = removerAcentos(this);
	$(this).caret(pos);
});

$(document).on('keyup', '#complemento', function(){
	var pos = $(this).caret();
	this.value = removerAcentos(this);
	$(this).caret(pos);
});

$(document).on('keyup', '#bairro', function(){
	var pos = $(this).caret();
	this.value = removerAcentos(this);
	$(this).caret(pos);
});


$(document).ready(function(){
	
	$("#cnpj").mask("99.999.999/9999-99");
	$("#cep").mask("99.999-999");
	
	$("#formeditarescola").validate({
		rules: {
			cnpj: {verificaCNPJ: true} 
		}	
	});
	
	carregarDadosEscola($("#idescola").val(), $("#idusuario").val());
});

$(document).on("click", "#btnOpenDialogEditarEscola", function(e) {
	
	$("#modalConfirmaEditar").modal('show');
	
});	

$(document).on("click", "#btnConfirmaEdit", function(e) {
	
	sendAjaxEditarEscola($('#formeditarescola').serialize(), $("#cargousuariologado").val());
	
});	

$(document).on("click", "#goback", function() {
	
	window.history.go(-1);
});	

</script>


<div class="row well">

	<div class="col-xs-12">

		<form action="srl" method="post" id="formeditarescola">

			<fieldset>
				<legend>Alterar dados da Escola</legend>

				<div class="row">
					<div class="col-md-1 col-xs-6">
						<%String idescola = request.getParameter("idescola"); %>
						<label>id: </label> <input type="text" class="form-control"
							name="txtidescola" id="idescola" value="<%=idescola%>" readonly>
					</div>
					<div class="col-md-1 col-xs-6">
						<label>Cód.FTD: </label> <input type="text" class="form-control"
							name="txtidftd" id="idftd">
					</div>										
					<div class="col-md-5 col-xs-12">
						<label>Escola: </label> <input type="text" class="form-control"
							id="nome" name="txtnome" required>
					</div>
					<div class="col-md-2 col-xs-6">
						<label>Dependencia: </label> <select name="txtdependencia"
							id="cmbDependencia" data-live-search="false"
							class="selectpicker form-control show-tick"
							data-selected-text-format="count>1">
							<option value="privada">Privada</option>
							<option value="publica">Pública</option>
						</select>
					</div>
					<div class="col-md-3 col-xs-12" id="divselectclassificacao">
						<label>Perfil da Escola: </label> <select name="txtclassificacao"
							id="cmbClassificacao" data-live-search="false"
							class="selectpicker form-control show-tick"
							data-selected-text-format="count>1">
						</select>
					</div>

				</div>

				<div class="row">
					<div class="col-md-8 col-xs-12">
						<label>Endereço/Nº.: </label> <input type="text"
							name="txtendereco" id="endereco" required class="form-control">
					</div>
					<div class="col-md-4 col-xs-12">
						<label>Complemento: </label> <input type="text"
							name="txtcomplemento" id="complemento" class="form-control">
					</div>
				</div>


				<div class="row">
					<div class="col-md-4 col-xs-12">
						<label>Bairro: </label> <input type="text" name="txtbairro"
							id="bairro" required class="form-control">
					</div>
					<div class="col-md-4 col-xs-12">
						<label>Municipio: </label> <input type="text" name="txtmunicipio"
							id="municipio" required class="form-control">
					</div>
					<div class="col-md-2 col-xs-6">
						<label>UF: </label> <input type="text" name="txtuf" required
							id="uf" class="form-control">
					</div>
					<div class="col-md-2 col-xs-6">
						<label>CEP: </label> <input type="text" id="cep" name="cep"
							class="form-control">
					</div>
				</div>


				<div class="row" id="divoptvendedores">
					<div class="col-md-4 col-xs-12">
						<label>E-mail: </label> <input type="text" name="txtemail"
							id="email" class="form-control">
					</div>
					<div class="col-md-2 col-xs-12">
						<label>Telefone: </label> <input type="text" name="txttelefone"
							id="telefone" class="form-control">
					</div>

					<div class="col-md-3 col-xs-12">
						<label>CNPJ: </label> <input type="text" id="cnpj" name="cnpj"
							onkeypress="cnpjMask(this, event)" class="form-control">
					</div>

					<div class="col-md-3 col-xs-12" id="divselectvendedor">
						<label>Vendedor: </label> <select name="txtsetor" id="cmbVendedor"
							data-live-search="false"
							class="selectpicker form-control show-tick"
							data-selected-text-format="count>1">
						</select>
					</div>

				</div>


				<br /> <input type="hidden" name="acao" value="editarescola">
				<input type="hidden" id="cargousuariologado"
					value="${usuariologado.cargo}"> <input type="hidden"
					id="idusuariologado" value="${usuariologado.id}">
				<%String idusuario = request.getParameter("idusuario"); %>
				<input type="hidden" id="idusuario" value="<%=idusuario%>">

				<div class="row input-group">
					<div class="col-md-6 col-sm-6 col-xs-6">
						<button type="button" id="btnOpenDialogEditarEscola"
							class="btn btn-primary form-control">Salvar</button>
					</div>
					<div class="col-md-6 col-sm-6 col-xs-6">
						<button type="button" data-dismiss="modal" id="goback"
							class="btn btn-default form-control">Voltar</button>
					</div>
				</div>
			</fieldset>

		</form>
	</div>
</div>


<div class="modal fade" id="modalConfirmaEditar" tabindex="-1"
	role="dialog" aria-labelledby="modalConfirmaLabel" aria-hidden="true">
	<div class="modal-dialog modal-xs">
		<div class="modal-content">
			<div class="modal-header" style="background-color: #23517c;">
				<button type="button" class="close" data-dismiss="modal">
					<span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
				</button>
				<h4 class="modal-title" id="modalConfirmaLabel"
					style="font-weight: bold; color: white;">Mensagem!</h4>
			</div>
			<div class="modal-body">
				<h4>Confirma Alteração?</h4>
			</div>
			<div class="modal-footer">
				<button type="button" data-dismiss="modal" class="btn btn-info"
					id="btnConfirmaEdit">Ok</button>
				<button type="button" data-dismiss="modal" class="btn">Cancel</button>
			</div>
		</div>
	</div>
</div>

