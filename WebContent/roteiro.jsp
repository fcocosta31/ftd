<script type="text/javascript">
	

	$(document).ready(function(){
		
		//$("#totalroteiro").load('roteiroTotalSalvar.jsp #divcontent');
		
		$("#formpedidodata").validate({
			rules: {
				dataroteiro: {dateBR: true}
			}
		});
		
	});

	
	$(document).on("click", "#btnOpenDialogEditarEscola", function(e) {
		
		$("#modaldetalheescola").modal("close");
		$("#modalConfirmaEditar").modal('open');
		
	});	


	$(document).on("click", "#btnOpenDialogExcluirEscola", function(e) {
		var idescola = $(this).val();
		$("#modalConfirmaDeletar").modal('close');	
		$("#btnConfirmaDelete").val(idescola);
	});	

	$(document).on("click", "#btnConfirmaDelete", function(e) {
		
		excluiescolaroteiroclick($(this).val(),$("#cargousuariologado").val());
		
	});	
	
</script>

<div class="center-align div-title-padrao col s12">
		Roteiro de Divulgação
</div>
<br><br>

<div class="row">

	<div class="row">
	
		<div class="col m11 s12 offset-m1">
						<c:choose>
							<c:when test="${!empty roteiro}">
								<div>
									<h5>Nº Sequencial: ${roteiro.id}</h5>
									<h5>Vendedor: ${roteiro.vendedor.nome}</h5>
									<h5>
										Data:
										<fmt:formatDate value="${roteiro.data}" pattern="dd/MM/yyyy" />
									</h5>
								</div>
								<a href="javascript:void(0)" onclick="roteiroPrint()"><img
									src="resources/images/printer-32.png" title="Imprimir"></a>
								<input type="hidden" id="cargousuariologado"
									value="${roteiro.vendedor.cargo}">
								<c:set var="escolas" value="${roteiro.escolas}"></c:set>
							</c:when>
							<c:otherwise>
								<h5>Vendedor: ${vendedor.nome}</h5>
								<input type="hidden" id="cargousuariologado"
									value="${usuariologado.cargo}">
								<c:set var="escolas" value="${roteiroescolas}"></c:set>

								<label style="text-align: left; font-weight: bold;">${fn:length(escolas)}
									escola(s)</label>
								<div class="noPrint col-md-5 col-sm-5 col-xs-12 input-group"
									style="padding-bottom: 20px;" id="orcamButtons">
									<a href="javascript:void(0)"
										onclick="detalhesalvarroteiroclick()" title="Salvar"> <img
										src="resources/images/save.png" width="32px" height="auto" title="Salvar">
									</a> <a href="javascript:void(0)" onclick="cancelarroteiroclick()"
										title="Cancelar"> <img src="resources/images/cancel.png" width="32px" height="auto"
										title="Cancelar" style="margin-left: 5%">
									</a>
								</div>
							</c:otherwise>
						</c:choose>
		</div>
	</div>
	
<div class="col l1 m1"></div>
<div class="col l10 m10 s12 center-align">

						
		<table class="highlight">
		
			<thead>
				<tr>
					<th>Descrição</th>
					<th>No.Alunos</th>
					<th>Vendedor</th>
				</tr>
			</thead>

			<tbody>
				<c:forEach var="t" items="${escolas}">

					<tr>
						<td><span class="divschoolname">${t.nome}<span>(<fmt:formatNumber
											type="number" pattern="0000" value="${t.id}" />)
								</span>
							</span><br />
							<span style="color: gray; font-size: 8pt;"><strong>Endereço:
							</strong>${t.endereco}, ${t.complemento}, ${t.bairro},
								${t.municipio}-${t.uf}</span><br /> <span
							style="color: gray; font-size: 8pt;"><strong>CEP:
							</strong>${t.cep}</span><br /> <span style="color: gray; font-size: 8pt;"><strong>Telefone/E-mail:
							</strong>${t.telefone} / ${t.email}</span><br /> <span
							style="color: gray; font-size: 8pt;"><strong>Classificação:
							</strong>${t.classificacao}</span><br /> 
							<span style="color: gray; font-size: 8pt;">
							<strong>Observação: </strong> ${t.observacao}</span>
							
						<td>${t.totalalunos}</td>
						<td>${t.vendedor.nome}<br /> <br />
							<div class="col s12">
								<c:if test="${!empty roteiro}">
									<a onclick="detalheescolaclick('${t.id}','${t.vendedor.id}')" 
									   class="btn-floating btn-small waves-effect waves-light red noPrint">
									<i class="material-icons" title="Alterar Escola">edit</i></a>
									<a onclick="numeroalunosclick('${t.id}')" 
									  class="btn-floating btn-small waves-effect waves-light yellow darken-1 noPrint">
									  <i class="material-icons" title="Número de alunos">insert_emoticon</i></a>
									<a href="#!"
										class="btn-floating btn-small waves-effect waves-light brown noPrint"
										onclick="roteiroobservacaoclick('${t.id}')">
										<i class="material-icons" title="Observações">border_color</i>
									</a>
								</c:if>
								<c:if test="${!empty roteiroescolas}">
									<button type="button"
										class="btn-floating btn-small waves-effect waves-light black noPrint" value="${t.id}"
										id="btnOpenDialogExcluirEscola">
										<i class="material-icons"
											title="Excluir do Roteiro">delete</i>
									</button>
								</c:if>
							</div>
						</td>
					</tr>
				</c:forEach>

			</tbody>

			<tfoot>
				<tr style="color: gray;">
					<td colspan="3"></td>
				</tr>
			</tfoot>

		</table>
						
</div>
<div class="col l1 m1"></div>						

</div>

	<div class="modal modal-large" id="modaldetalheescola">

		<div class="center-align div-title-modal">
			Editar dados da escola
		</div>		

		<div class="modal-content">
			<div class="modal-body modaldivdetalheescola"></div>
		</div>
		<div class="modal-footer">			
			<button type="button" id="btnOpenDialogEditarEscola"
				class="waves-effect waves-light btn blue">Salvar</button>
			<button type="button" data-dismiss="modal"	
				class="waves-effect waves-light btn green">Fechar</button>
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


	<div class="modal fade" id="modalObservacao">

		<div class="center-align div-title-modal">
			Observações
		</div>		

		<div class="modal-content">
			<div class="modal-body" id="divmodalobservacao"></div>
		</div>

		<div class="modal-footer">
			<a href="#!" onclick="btnsalvarobservacaoclick()"
				class="modal-action modal-close waves-effect waves-green btn-flat">Enviar</a>
			<a href="#!" class="modal-action modal-close waves-effect waves-green btn-flat">Cancelar</a>	
		</div>
		
	</div>


	<div class="modal" id="modalConfirmaDeletar">

		<div class="center-align div-title-modal">
			Mensagem!
		</div>		

		<div class="modal-content">
			<div class="modal-body">
				<h4>Confirma Exclusão do Roteiro?</h4>
			</div>
		</div>
		<div class="modal-footer">
			<a href="#!" id="btnConfirmaDelete"
				class="modal-action modal-close waves-effect waves-green btn-flat">Ok</a>
			<a href="#!" class="modal-action modal-close waves-effect waves-green btn-flat">Cancelar</a>	
		</div>
		
	</div>



	<div class="modal" id="modalRoteiro">
		<div class="center-align div-title-modal">
			Salvar Roteiro!
		</div>		
		<div class="modal-content">
			<div class="modal-body" id="modalsalvarroteiro"></div>
		</div>
		<div class="modal-footer">
		</div>		
	</div>


