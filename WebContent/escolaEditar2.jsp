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

$(document).on("click", "#btnOpenDialogEditarEscola", function(e) {
	
	$("#modalConfirmaEditar").modal('open');
	
});	


$(document).on("click", "#btnConfirmaEdit", function(e) {
	
	sendAjaxEditarEscola($('#formeditarescola').serialize(), $("#cargousuariologado").val());
	
});	

$(document).on('click', '#btn_numalunos', function(){
	numeroalunosclick($('#idescola').val());
});

$(document).on("click", "#btnEditarProfessor", function(){

	sendAjaxEditarProfessor($('#formeditarprofessor').serialize());
	
});
var idprofessor;
$(document).on("click", "#btnDeletarProfessor", function(){
	idprofessor = $("#txtidprofessor").val();
	$("#modaleditarprofessor").modal("hide");
	$("#modalConfirmaDeletarProfessor").modal('show');				
});

$(document).on('click', "#btnConfirmaDeleteProfessor", function(){
	sendAjaxDeletarProfessor(idprofessor);
});

$(document).on('click', '#btn_listarprofessores', function(){
	btnListarProfessoresClick($('#idescola').val());
});

$(document).on("click", "#goback", function() {
	
	window.history.go(-1);
});	

$(document).ready(function(){
	
	$("#cnpj").mask("99.999.999/9999-99");
	$("#cep").mask("99.999-999");
	
	$("#formeditarescola").validate({
		rules: {
			cnpj: {verificaCNPJ: true} 
		}	
	});
	
});

</script>

<div class="center-align div-title-padrao col s12">
		Editar Escola
</div>
<br><br>

<div class="row">

	<div class="col l10 m10 s12 offset-l1 offset-m1">
			
       		<div class="input-field col l7 m7 s12">
           		<div class="input-field col s12">
           			<div class="ac-input" style="margin-top: 10px">
                    <input type="text" id="txtdescricaoescola"
                     placeholder="Digite o nome da escola..."
                      name="txtdescricaoescola"
                      autocomplete="off"
                     required data-activates="singleDropdownSchool" data-beloworigin="true">		                     
                     <label for="txtdescricaoescola">Pesquisar:</label>		                     
                    </div>	                     
                  </div>       			
			</div>
							
			<p style="font-size: 8pt; color: gray;"
				id="txtdescricaoescola-description"></p>					
			<ul id="singleDropdownSchool" constrainWidth="false" class="dropdown-content ac-dropdown col s12"></ul>
	</div>			
			
</div>
<br/>	
<div class="row">

	<div class="col l10 m10 s12 offset-l1 offset-m1">

		<form action="srl" method="post" id="formeditarescola" class="noDisplay">

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
						<a class="btn-floating btn-small waves-effect waves-light brown" 
							href="#!" id="btn_numalunos">
						<i class="material-icons" title="Alunos">insert_emoticon</i></a>
							
						<a class="btn-floating btn-small waves-effect waves-light brown" 
							href="#!" id="btn_listarprofessores">
							<i class="material-icons" title="Professores">person</i>
						</a>
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


<div class="modal modal-large" id="modalAlunos">
		<div class="center-align div-title-modal" id="modalAlunosLabel">
			Quantidade de Alunos
		</div>		

		<div class="modal-content">
			<div class="row" id="modalsalvaralunos">
				<div class="col s12">
					<form id="formnumeroalunos">
						
						<span id="nome"><strong></strong></span>
						<span id="txttotalalunos"><strong></strong></span><br />
						<span id="txtatualizacao"><strong></strong></span><br />

						<div class="row">
							<div>
								<label>Educação Infantil</label>
							</div>
							<div class="col m2 s6">
								<label>0 ano</label> <input type="number" min="0" id="infantil0"
									name="txtbercario" class="form-control">
							</div>
							<div class="col m2 s6">
								<label>1 ano</label> <input type="number" min="0" id="infantil1"
									name="txtmaternal1" class="form-control">
							</div>
							<div class="col m2 s6">
								<label>2 anos</label> <input type="number" min="0" id="infantil2"
									name="txtmaternal2" class="form-control">
							</div>
							<div class="col m2 s6">
								<label>3 anos</label> <input type="number" min="0" id="infantil3"
									name="txtmaternal3" class="form-control">
							</div>
							<div class="col m2 s6">
								<label>4 anos</label> <input type="number" min="0" id="infantil4"
									name="txtinfantil1" class="form-control">
							</div>
							<div class="col m2 s6">
								<label>5 anos</label> <input type="number" min="0" id="infantil5"
									name="txtinfantil2" class="form-control">
							</div>
						</div>
						<div class="row">
							<div>
								<label>Ensino Fundamental I</label>
							</div>
							<div class="col m2 s6">
								<label>1o Ano</label> <input type="number" min="0" id="ano1"
									name="txtano1" class="form-control">
							</div>
							<div class="col m2 s6">
								<label>2o Ano</label> <input type="number" min="0" id="ano2"
									name="txtano2" class="form-control">
							</div>
							<div class="col m2 s6">
								<label>3o Ano</label> <input type="number" min="0" id="ano3"
									name="txtano3" class="form-control">
							</div>
							<div class="col m2 s6">
								<label>4o Ano</label> <input type="number" min="0" id="ano4"
									name="txtano4" class="form-control">
							</div>
							<div class="col m2 s6">
								<label>5o Ano</label> <input type="number" min="0" id="ano5"
									name="txtano5" class="form-control">
							</div>
						</div>
						<div class="row">
							<div>
								<label>Ensino Fundamental II</label>
							</div>
							<div class="col m2 s6">
								<label>6o Ano</label> <input type="number" min="0" id="ano6"
									name="txtano6" class="form-control">
							</div>
							<div class="col m2 s6">
								<label>7o Ano</label> <input type="number" min="0" id="ano7"
									name="txtano7" class="form-control">
							</div>
							<div class="col m2 s6">
								<label>8o Ano</label> <input type="number" min="0" id="ano8"
									name="txtano8" class="form-control">
							</div>
							<div class="col m2 s6">
								<label>9o Ano</label> <input type="number" min="0" id="ano9"
									name="txtano9" class="form-control">
							</div>
						</div>
						<div class="row">
							<div>
								<label>Ensino Médio / Outros</label>
							</div>
							<div class="col m2 s6">
								<label>1a Série</label> <input type="number" min="0" id="serie1"
									name="txtser1" class="form-control">
							</div>
							<div class="col m2 s6">
								<label>2a Série</label> <input type="number" min="0" id="serie2"
									name="txtser2" class="form-control">
							</div>
							<div class="col m2 s6">
								<label>3a Série</label> <input type="number" min="0" id="serie3"
									name="txtser3" class="form-control">
							</div>
							<div class="col m2 s6">
								<label>Eja</label> <input type="number" min="0" id="eja" name="txteja"
									class="form-control">
							</div>
							<div class="col m2 s6">
								<label>Supletivo</label> <input type="number" min="0" id="supletivo"
									name="txtsupl" class="form-control">
							</div>
						</div>
						<input type="hidden" value="salvaralunos" name="acao"> 
						<input type="hidden" id="id" name="txtidescola"> 
						<input type="hidden" name="tipo" value="escola">

					</form>
				</div>
			</div>
		</div>
		<div class="modal-footer">
			<a href="#!" onclick="btnsalvaralunosclick()"
				class="modal-action modal-close waves-effect waves-green btn-flat">Enviar</a>
			<a href="#!" class="modal-action modal-close waves-effect waves-green btn-flat">Cancelar</a>	
		</div>		
</div>