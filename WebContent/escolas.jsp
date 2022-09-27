<script type="text/javascript">	

	$(document).on('keyup', '#txtnome', function(){
		this.value = removerAcentos(this);
	});

	$(document).on('keyup', '#nome', function(){
		this.value = removerAcentos(this);
	});

	$(document).on('keyup', '#txtendereco', function(){
		this.value = removerAcentos(this);
	});

	$(document).on('keyup', '#txtcomplemento', function(){
		this.value = removerAcentos(this);
	});

	$(document).on('keyup', '#txtbairro', function(){
		this.value = removerAcentos(this);
	});

	$(document).ready(function(){

	    $('#tableescolas').DataTable( {
	        columnDefs: [
	            {
	                targets: [ 0 ],
	                className: 'mdl-data-table__cell--non-numeric'
	            }
	        ]
	    } );
	    				
		refreshSelectMenu('cmbVendedor');
		
		$("#formcadastroescola").validate({
			rules: {
				cnpj: {verificaCNPJ: true} 
			}	
		});

		$("#cnpj").mask("99.999.999/9999-99");

		$("#cep").mask("99.999-999");
		
	});
	

	function btnDeletarEscolaClick(idescola){
		$("#modalConfirmaDeletar").modal('open');
		$("#idescola").val(idescola);
	};
	
	$(document).on("click", "#btnConfirmaDelete", function(e) {
		
		deleteescolaclick($("#idescola").val(),$("#cargousuariologado").val());
		
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

<div class="center-align div-title-padrao col s12">
		Escolas
</div>
<br><br>

<div class="row">

	<div class="row">
		<div class="col m11 s12 offset-m1">
			<input type="hidden" id="cargousuariologado"
				value="${usuariologado.cargo}">
			<div class="noPrint">
				<a href="srl?acao=listarescolasexcel"
					onclick="exportaConsultaExcel(e)"><img
					src="resources/images/export_excel.png" title="Exportar para Excel"
					style="float: left; margin-right: 1%"></a>
			</div>
			<div class="noPrint">
				<a href="javascript:void(0)" onclick="escolasPrint()"><img
					src="resources/images/printer-32.png" title="Imprimir"></a>
			</div>
		</div>
	</div>
	
<div class="col l1 m1"></div>
<div class="col l10 m10 s12 center-align">

	<table id="tableescolas" class="mdl-data-table">
		<thead>
			<tr>
				<th>Escolas</th>
			</tr>
		</thead>

		<tbody>
			<c:set var="talunos" value="0"></c:set>
			<c:forEach var="t" items="${listaescolas}">

				<tr>
					<td>
						<!-- container para o nome da escola e os botões -->
						<div class="row">
														
							<!-- nome da escola -->
							<div class="col l7 m7 s12 divschoolname">							
								<span>${t.nome} <span>(<fmt:formatNumber type="number"
												pattern="0000" value="${t.id}" />)
									</span>
								</span>
								
							</div>
							<!-- botões -->
							<div class="col l5 m5 s12 divschoolbuttons">
								<a class="btn-floating btn-small waves-effect waves-light blue noPrint"
									onclick="roteiroaddescolaclick('${t.id}', this)">
									<i class="material-icons" title="Adicionar ao roteiro">add</i>
								</a>																						
							  	<a onclick="detalheescolaclick('${t.id}','${t.vendedor.id}')" class="btn-floating btn-small waves-effect waves-light red noPrint"><i class="material-icons" title="Alterar Escola">edit</i></a>
							  	<a onclick="numeroalunosclick('${t.id}')" class="btn-floating btn-small waves-effect waves-light yellow darken-1 noPrint"><i class="material-icons" title="Número de alunos">insert_emoticon</i></a>
							  	<a onclick="btnListarProfessoresClick('${t.id}')" class="btn-floating btn-small waves-effect waves-light green noPrint"><i class="material-icons" title="Listar Professores">person</i></a>
								<!-- <a class="btn-floating btn-small waves-effect waves-light blue noPrint"
								       onclick="javascript:location.href='srl?acao=viewmap&idescola=${t.id}'">
										<i class="material-icons" title="Localização no Mapa">place</i>																	
								</a>
								-->
								<c:if test="${usuariologado.cargo eq 1}">
									<a class="btn-floating btn-small waves-effect waves-light black noPrint"
										onclick="btnDeletarEscolaClick('${t.id}')">
										<i class="material-icons" title="Deletar Escola">delete</i>
									</a>
								</c:if>
							</div>
							
						</div> <!-- container para os demais elementos -->
						<div class="row">
							<div class="col l12 m12 s12 divschooladdress">
								 Bairro/Cidade: ${t.endereco} / ${t.bairro}, ${t.municipio}-${t.uf}
								 Telefone/E-mail: ${t.telefone} / ${t.email}
								 Nº de Aluno(s): ${t.totalalunos}<br>
								 Ultimas visitas: <c:choose>
										<c:when test="${empty t.ultimavisita}">Nenhuma visita</c:when>
										<c:otherwise>
											<c:forEach var="visitas" items="${t.ultimavisita}">
																				[<fmt:formatDate value="${visitas}"
													pattern="dd/MM/yyyy" />]
																			</c:forEach>
																			 [Obs: ${t.observacao}]<br>															
																	</c:otherwise>
									</c:choose> Setor: <fmt:formatNumber type="number" pattern="00" value="${t.setor}" />
								 / Consultor: ${t.vendedor.nome}
							</div>							
						</div>
					</td>
				</tr>
				<c:set var="talunos" value="${talunos + t.totalalunos}"></c:set>
			</c:forEach>

		</tbody>

		<tfoot>
			<tr>
				<td></td>
			</tr>
		</tfoot>

	</table>

</div>
<div class="col l1 m1"></div>
</div>
<div class="row">
	<div class="col s11 offset-s1">
		<h5>Total de <c:out value="${fn:length(listaescolas)} escolas"></c:out><c:out value=" e ${talunos} alunos"></c:out> </h5>
	</div>
</div>


<div class="modal modal-large" id="modalprofessores">

	<div class="center-align div-title-modal">
		Listagem de professores
	</div>		

	<div class="modal-content">
		<div class="modaldivdetalheprofessores"></div>
	</div>

	<div class="modal-footer">
		<a href="#!" class="modal-action modal-close waves-effect waves-green btn-flat">Fechar</a>
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
								<label>Escola:</label> <select id="cmbProfEscolas"
									data-live-search="true"
									class="selectpicker form-control show-tick"
									data-selected-text-format="count>1"
									title="Selecione a escola..." name="txtescola"
									multiple="multiple">
								</select>
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
							
							<div class="col l8 m8 s12">
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
							<div class="col l2 m2 s12">
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



<div class="modal" id="modalConfirmaDeletar">

	<div class="center-align div-title-modal">
		Mensagem!
	</div>		

	<div class="modal-content">
			<h4>Confirma Deletar?</h4>
		<input type="hidden" id="idescola" value="">
	</div>

	<div class="modal-footer">
		<a href="#!" id="btnConfirmaDelete" 
			class="modal-action modal-close waves-effect waves-green btn-flat">Enviar</a>
		<a href="#!" class="modal-action modal-close waves-effect waves-green btn-flat">Cancelar</a>	
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

