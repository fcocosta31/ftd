<script type="text/javascript">

	$(document).on('keyup', '#txtnome', function(){
		this.value = removerAcentos(this);
	});
	
	$(document).on('keyup', '#nome', function(){
		this.value = removerAcentos(this);
	});

	$(document).on("click", "#btnEditarProfessor", function(){

		sendAjaxEditarProfessor($('#formeditarprofessor').serialize());
		
	});

	var idprofessor;
	
	$(document).on("click", "#btnDeletarProfessor", function(){
		idprofessor = $("#txtidprofessor").val();
		$("#modaleditarprofessor").modal("close");
		$("#modalConfirmaDeletarProfessor").modal('open');				
	});
	
	$(document).on('click', "#btnConfirmaDeleteProfessor", function(){
		sendAjaxDeletarProfessor(idprofessor);
	});

	
</script>
	
<%String escola = request.getParameter("escola"); %>	
<div class="center-align div-title-padrao col s12">
		Listagem de professores da escola <%=escola%>
</div>
<br><br>

<div class="container">

	<div class="row" id="divcontent">
		
		<table class="highlight datables-table">
			<c:choose>
				<c:when test="${!empty professores}">
				<thead>	
					<tr>
						<th>Id</th>
						<th>Professor</th>
						<th>Cargo</th>
						<th>Endereço</th>
						<th>#</th>
					</tr>
				</thead>
					<c:set var="aux" value="0"></c:set>
				<tbody>
					<c:forEach var="p" items="${professores}">
						<tr>
							<c:set var="aux" value="${aux + 1}"></c:set>
							<td>${p.id}<br>
							<td>${p.nome}</td>
							<td>${p.cargo}</td>
							<td>${p.endereco} - ${p.bairro} / ${p.municipio}</td>
							<td><a href="javascript:void(0)"
								onclick="carregarDadosProfessor(${p.id})">Editar</a></td>
						</tr>
					</c:forEach>
				</tbody>
				<tfoot>	
					<tr style="font-weight: bold; font-size: 9pt">
						<td colspan="5">${aux} professor(es)</td>
					</tr>
				</tfoot>	
				</c:when>
				
				<c:otherwise>
					<h3>Escola sem professores cadastrados!!!</h3>
				</c:otherwise>
	
			</c:choose>
		</table>
	
	</div>
	
</div>
	

<div class="modal modal-large" id="modaleditarprofessor">

	<div class="center-align div-title-modal" id="modalEditarProfessorLabel">
		Editar Professor
	</div>		
	
		<div class="modal-content">

			<div class="modaldiveditarprofessor">
			
				<div class="row">

					<form action="srl" method="post" id="formeditarprofessor">

							<div class="col l2 m2 s12">
								<label>Id: </label> <input type="text" class="form-control"
									id="txtidprofessor" name="txtidprofessor" readonly>
							</div>

							<div class="col l6 m6 s12">
								<label>Nome: </label> <input type="text" class="form-control"
									id="txtnome" name="txtnome" required>
							</div>

							<div class="col l4 m4 s12">
								<label>Cargo: </label> <select
									class="form-control selectpicker" title="Selecione o Cargo"
									name="txtcargo" id="cmbCargo">
									<option value="Professor">Professor</option>
									<option value="Coordenador">Coordenador</option>
									<option value="Diretor">Diretor</option>
									<option value="Secretario">Secretario</option>
									<option value="Outro">Outro</option>
								</select>
							</div>


							<div class="col l7 m7 s12">
								<label>E-mail: </label> <input type="text" name="txtemail"
									class="form-control" id="txtemail">
							</div>
							<div class="col l3 m3 s12">
								<label>Telefone: </label> <input type="text" name="txttelefone"
									value="" class="form-control" id="txttelefone">
							</div>
							<div class="col l2 m2 s12">
								<label>Aniversário: </label> <input type="text"
									id="txtaniversario" name="txtaniversario" placeholder="dd/mm"
									size="5" class="form-control">
							</div>

							<div class="col l4 m4 s12">
								<label>Escola:</label>
			                    <input type="text" id="txtdescricaoescola"
			                     placeholder="Digite o nome da escola..."
			                      name="txtdescricaoescola"
			                      autocomplete="off"
			                     required data-activates="singleDropdownSchool" data-beloworigin="true">
			                     <ul id="singleDropdownSchool" constrainWidth="false" class="dropdown-content ac-dropdown col s12"></ul>
			                     <input type="hidden" name="txtescola" id="idescola">		                     
							</div>

							<div class="col l4 m4 s12">
								<label>Nivel de ensino:</label> <select id="cmbProfNivel"
									class="selectpicker form-control show-tick"
									data-selected-text-format="count>1"
									title="Selecione o nivel..." name="txtnivel"
									multiple="multiple">
									<option value="01-Infantil">Ed.Infantil</option>
									<option value="02-Fundamental_I">Ens.Fundamental I (1
											ao 5 Ano)</option>
									<option value="03-Fundamental_II">Ens.Fundamental II
											(6 ao 9 Ano)</option>
									<option value="04-Medio">Ens.Medio</option>
									<option value="05-Geral">Outros</option>
								</select>
							</div>

							<div class="col l4 m4 s12">
								<label>Disciplina:</label> <select id="cmbProfDisciplinas"
									data-live-search="true"
									class="selectpicker form-control show-tick"
									data-selected-text-format="count>1"
									title="Selecione a disciplina..." name="txtdisciplina"
									multiple="multiple">
								</select>
							</div>
							
							<div class="col s12">
								<label>Endereço/Nº.: </label> <input type="text"
									name="txtendereco" id="txtendereco" required class="form-control">
							</div>
				
							<div class="col l4 m4 s12">
								<label>Bairro: </label> <input type="text" name="txtbairro"
									id="txtbairro" required class="form-control">
							</div>
							<div class="col l4 m4 s12">
								<label>Municipio: </label> <input type="text" name="txtmunicipio"
									id="txtmunicipio" required class="form-control">
							</div>
							<div class="col l4 m4 s12">
								<label>UF: </label> <input type="text" name="txtuf" required
									id="txtuf" class="form-control">
							</div>

							<input type="hidden" name="acao" value="editarprofessor">

					</form>

				</div>

			</div>
	</div>

	<div class="modal-footer">
		<button type="button" id="btnEditarProfessor"
			class="modal-action modal-close waves-effect waves-light btn blue noPrint">Salvar</button>

		<button type="button"
			class="modal-action modal-close waves-effect waves-light btn grey noPrint">Fechar</button>

		<button type="button" id="btnDeletarProfessor"
			class="modal-action modal-close waves-effect waves-light btn red noPrint">Deletar</button>
	</div>
	
</div>


<div class="modal" id="modalConfirmaDeletarProfessor">

	<div class="center-align div-title-modal">
		Mensagem!
	</div>		

	<div class="modal-content">
			<h4>Confirma Deletar?</h4>
		<input type="hidden" id="idprofessor" value="">
	</div>

	<div class="modal-footer">
		<a href="#!" id="btnConfirmaDeleteProfessor" 
			class="modal-action modal-close waves-effect waves-green btn-flat">Enviar</a>
		<a href="#!" class="modal-action modal-close waves-effect waves-green btn-flat">Cancelar</a>	
	</div>
	
</div>