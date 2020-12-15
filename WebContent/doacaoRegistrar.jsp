<script type="text/javascript">
		
	$(document).ready(function(){

		$('#btnDialogSalvarDoacao').click(function(e){

			var formulario = $('#formitensdoacao').serialize();
			
			mbox.confirm("Tem certeza que está tudo correto?", function(result) {
				if(result){
					sendAjaxSalvarDoacao(formulario);
				}			
			});
				
		});

		$('#btnDialogDescartarDoacao').click(function(e){
			
			mbox.confirm("Abandonar doação?", function(result) {
				if(result){
					sendAjaxDescartarOrcamento();
				}			
			});
				
		});

	});

	
</script>

<c:set var="titlepage" value="Registrar Doação"></c:set>
<c:if test="${!empty orcamento.professor.id}">
	<c:set var="titlepage" value="Editar Doação"></c:set>
</c:if>

<div class="center-align div-title-padrao col s12">
		${titlepage}
</div>
<br>

<div class="container">

	<form id="formitensdoacao">
	
		<div class="row">
			<div class="row">
				<div class="form-group has-feedback col l6 m6 s12">
					<label>Nome da escola:</label>
					<c:set var="nomeescola" value=""></c:set>
					<c:if test="${!empty orcamento.professor.id}">
						<c:set var="nomeescola" value="${orcamento.escola.nome}"></c:set>
					</c:if>
	           		<div>
	           			<div class="ac-input">
	                    <input type="text" id="txtdescricaoescola"
	                     placeholder="Digite o nome da escola..."
	                      name="txtdescricaoescola" value="${nomeescola}"
	                      autocomplete="off"
	                     required data-activates="singleDropdownSchool" data-beloworigin="true">		                     	                     
	                    </div>	                     
	                </div>       			
					
					<ul id="singleDropdownSchool" constrainWidth="false" class="dropdown-content ac-dropdown col s12"></ul>										
				</div>
			</div>
			<div class="row">
				<div class="col l6 m6 s12" id="divoptprofessor">
					<label>Nome do Professor: </label> <select id="cmbProfessor"
						class="form-control" name="txtidprofessor">
						<option value="${orcamento.professor.id}">${orcamento.professor.nome}</option>
					</select>
				</div>
			</div>
	
			<button type="button" class="waves-effect waves-light btn blue"
				id="btnDialogSalvarDoacao">Salvar</button>
			<button class="waves-effect waves-light btn grey"
				type="button" value="Cancelar" id="btnDialogDescartarDoacao">Cancelar</button>
	
		<div class="row"></div>
		
		<input type="hidden" value="doacaoregistrar" name="acao"> 
		<input type="hidden" value="${orcamento.escola.id}" name="txtidescola"
			id="idescola">
	
		<c:set var="itens" value="${fn:length(orcamento.itens)}" />
	
		<table class="highlight responsive-table">
			<thead>
			<tr>
				<th id="thcodigo">Código</th>
				<th width="400px">Descrição</th>
				<th id="thpreco">Preço</th>
				<th id="thestoque">Qtde</th>
				<th id="thpreco">Total</th>
				<th>......</th>
			</tr>
			</thead>
			<tbody>
			<c:forEach var="pd" items="${orcamento.itens}">
				<c:set var="qtde" value="1"></c:set>
				<c:if test="${pd.quantidade > 0}">
					<c:set var="qtde" value="${pd.quantidade}"></c:set>
				</c:if>
				<tr style="font-size: 9pt">
					<td>${pd.produto.codigo}</td>
					<td width="400px">${pd.produto.descricao}</td>
					<td style="text-align: right"><fmt:formatNumber
							value="${pd.produto.preco}" type="number" pattern="#,#00.00#" /></td>
					<td style="text-align: right"><input
						name="${pd.produto.codigo}" value="${qtde}" size="5"
						style="text-align: center;"></td>
					<td style="text-align: right"><fmt:formatNumber
							value="${pd.quantidade * pd.produto.preco}" type="number"
							pattern="#,#00.00#" /></td>
					<td><a
						href="srl?acao=remitemdoacao&codigoitem=${pd.produto.codigo}">remover</a></td>
				</tr>
	
			</c:forEach>
			</tbody>
			<tfoot>
			<tr>
				<td colspan="2" style="text-align: center; font-weight: bold;">${itens}
					itens</td>
				<td style="text-align: right; font-weight: bold;">Totais</td>
				<td style="text-align: right; font-weight: bold;">${orcamento.qtdtotal}</td>
				<td colspan="2" style="text-align: center; font-weight: bold;"><fmt:formatNumber
						value="${orcamento.total}" type="number" pattern="#,#00.00#" /></td>
			</tr>
			</tfoot>
		</table>
		
		</div>
		
	</form>
	
</div>