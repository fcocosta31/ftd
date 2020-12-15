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


$(document).on('click', '#btnDeleteEscola', function(){
	$("#modalConfirmaDeletar").modal('open');	
});

$(document).on('click', '#btnConfirmaDeletar', function(){
	deleteescolaclick($('#idescola').val(), '');
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
	
	$("#modalConfirmaEditar").modal('open');
	
});	

$(document).on("click", "#btnConfirmaEdit", function(e) {
	
	sendAjaxEditarEscola($('#formeditarescola').serialize(), $("#cargousuariologado").val());
	
});	

$(document).on("click", "#goback", function() {
	
	window.history.go(-1);
});	

</script>

<div class="center-align div-title-padrao col s12">
		Editar Escola
</div>
<br><br>

<div class="row">

	<div class="col l10 m10 s12 offset-l1 offset-m1">

		<form action="srl" method="post" id="formeditarescola">


				<div class="row">
					<div class="col l1 m1 s6">
						<%String idescola = request.getParameter("idescola"); %>
						<label>id: </label> <input type="text" class="form-control"
							name="txtidescola" id="idescola" value="<%=idescola%>" readonly>
					</div>
					<div class="col l1 m1 s6">
						<label>Cód.FTD: </label> <input type="text" class="form-control"
							name="txtidftd" id="idftd">
					</div>										
					<div class="col l5 m5 s12">
						<label>Escola: </label> <input type="text" class="form-control"
							id="nome" name="txtnome" required>
					</div>
					<div class="col l2 m2 s6">
						<label>Dependencia: </label> <select name="txtdependencia"
							id="cmbDependencia" data-live-search="false"
							class="selectpicker form-control show-tick"
							data-selected-text-format="count>1">
							<option value="privada">Privada</option>
							<option value="publica">Pública</option>
						</select>
					</div>
					<div class="col l3 m3 s12" id="divselectclassificacao">
						<label>Perfil da Escola: </label> <select name="txtclassificacao"
							id="cmbClassificacao" data-live-search="false"
							class="selectpicker form-control show-tick"
							data-selected-text-format="count>1">
						</select>
					</div>

				</div>

				<div class="row">
					<div class="col l8 m8 s12">
						<label>Endereço/Nº.: </label> <input type="text"
							name="txtendereco" id="endereco" required class="form-control">
					</div>
					<div class="col l4 m4 s12">
						<label>Complemento: </label> <input type="text"
							name="txtcomplemento" id="complemento" class="form-control">
					</div>
				</div>


				<div class="row">
					<div class="col l4 m4 s12">
						<label>Bairro: </label> <input type="text" name="txtbairro"
							id="bairro" required class="form-control">
					</div>
					<div class="col l4 m4 s12">
						<label>Municipio: </label> <input type="text" name="txtmunicipio"
							id="municipio" required class="form-control">
					</div>
					<div class="col l2 m2 s6">
						<label>UF: </label> <input type="text" name="txtuf" required
							id="uf" class="form-control">
					</div>
					<div class="col l2 m2 s6">
						<label>CEP: </label> <input type="text" id="cep" name="cep"
							class="form-control">
					</div>
				</div>


				<div class="row" id="divoptvendedores">
					<div class="col l4 m4 s12">
						<label>E-mail: </label> <input type="text" name="txtemail"
							id="email" class="form-control">
					</div>
					<div class="col l2 m2 s12">
						<label>Telefone: </label> <input type="text" name="txttelefone"
							id="telefone" class="form-control">
					</div>

					<div class="col l3 m3 s12">
						<label>CNPJ: </label> <input type="text" id="cnpj" name="cnpj"
							onkeypress="cnpjMask(this, event)" class="form-control">
					</div>

					<div class="col l3 m3 s12" id="divselectvendedor">
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

				<div class="row">
					<div class="col s12 left-align">
						<button type="button" id="btnOpenDialogEditarEscola"
							class="waves-effect waves-light btn blue noPrint">Salvar</button>

						<button type="button" data-dismiss="modal" id="goback"
							class="waves-effect waves-light btn grey noPrint">Voltar</button>
						<c:if test="${usuariologado.cargo eq 1}">
							<button type="button" id="btnDeleteEscola"
								class="waves-effect waves-light btn red noPrint">Deletar</button>
						</c:if>
					</div>										
				</div>


		</form>
		
	</div>
</div>


<div class="modal" id="modalConfirmaEditar">
	<div class="center-align div-title-modal">
		Mensagem!
	</div>		

	<div class="modal-content">
			<h4>Confirma Alteração?</h4>
	</div>
	<div class="modal-footer">
		<a href="#!" id="btnConfirmaEdit" 
			class="modal-action modal-close waves-effect waves-green btn-flat">Enviar</a>
		<a href="#!" class="modal-action modal-close waves-effect waves-green btn-flat">Cancelar</a>	
	</div>
</div>

<div class="modal" id="modalConfirmaDeletar">
	<div class="center-align div-title-modal">
		Mensagem!
	</div>		

	<div class="modal-content">
			<h4>Tem Certeza?</h4>
	</div>
	<div class="modal-footer">
		<a href="#!" id="btnConfirmaDeletar" 
			class="modal-action modal-close waves-effect waves-green btn-flat">Sim</a>
		<a href="#!" class="modal-action modal-close waves-effect waves-green btn-flat">Não</a>	
	</div>
</div>