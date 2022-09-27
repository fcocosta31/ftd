<div class="center-align div-title-padrao col s12">
	Escolas com listas cadastradas [${glass.year}]<br>
</div>
<br>

<div class="container">
	<div class="row">
		<div style="padding-left: 15px; border-bottom: 1px solid; height: 50px;">
			<c:choose>
			<c:when test="${glass.consultor.setor eq 0}">
			<div class="col s6">
				<h6>Setor: Todos</h6>
			</div>
			</c:when>
			<c:otherwise>
			<div class="col s6">
				<h6>Setor: ${glass.consultor.setor} - ${glass.consultor.nome}</h6>
			</div>	
			</c:otherwise>
			</c:choose>
			<div class="col s6">	
				<h6>${glass.listas} listas cadastradas</h6>
			</div>		
					
			<div class="col s6"><h6>Nome da Escola</h6></div>
			<div class="col s6"><h6>Bairro - Município</h6></div>
		</div>
		<ul class="collapsible">	
			<c:forEach var="t" items="${glasslista}">

					<li>
						<!-- nome da escola -->
						<div class="collapsible-header" style="padding-left: 15px;">
							<div class="col l8 m8 s12">
								<strong><a style="font-size: 12pt; color: inherit;"
									href="javascript:void(0)" onclick="carregarComboSeries('${t.id}', '${glass.year}')">
										${t.nome} <span>(<fmt:formatNumber type="number"
												pattern="0000" value="${t.id}" />)
									</span>
								</a> </strong>
							</div>
							<div class="col l4 m4 s12">	
								<span style="color: gray; font-size: 9pt;">
									${t.bairro} - ${t.municipio}</span>
							</div>
						</div>

						<div class="collapsible-body">
							<div class="row">
								<form id="formpesqadocao${t.id}">
									
									<input type="hidden" value="${t.id}" id="id${t.id}" name="txtidescola">
									<input type="hidden" value="original" name="txttabela">
									<input type="hidden" value="detalharadocao" name="acao">
									
									<div class="col l3 m3 s12">						
										<input type="text" name="txtano" value="${glass.year}" readonly class="form-control">
									</div>
		
									<div class="col l5 m5 s12">
										<select id="cmbSerie${t.id}" name="txtserie" multiple>
										</select>
									</div>
						
									<div class="col l4 m4 s12">
										<div class="button-group">
												<button class="waves-effect waves-light btn blue" id="btnconfirmaadocao"
												onclick="enviarpesquisaadocaoclick(event, '${t.id}')">Enviar</button>
										</div>
									</div>
									
								</form>
							</div>		
						</div>					
					</li>

															
			</c:forEach>
		</ul>
	</div>
</div>

