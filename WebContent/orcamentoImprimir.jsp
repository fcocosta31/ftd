<script type="text/javascript">
   
   (function(i,s,o,g,r,a,m){i['GoogleAnalyticsObject']=r;i[r]=i[r]||function(){
	  (i[r].q=i[r].q||[]).push(arguments)},i[r].l=1*new Date();a=s.createElement(o),
	  m=s.getElementsByTagName(o)[0];a.async=1;a.src=g;m.parentNode.insertBefore(a,m)
	  })(window,document,'script','//www.google-analytics.com/analytics.js','ga');

	  ga('create', 'UA-56835143-1', 'auto');
	  ga('send', 'pageview');
	
	

	$(document).on("click",'#btnConfirmaPedidoEmail', function(){
		
		var idempresa = $("#txtidempresa").val();
		var nomec = $("#txtnomec").val();
		var fonecontato = $("#txtfonecontato").val();
		var transporte = $("#txttransportadora").val();
		var observ = $("#txtobsc").val();
		var emailcli = 'true';
		var guardapend = '1';
		
		if (document.querySelector('input[name="emailcliente"]:checked') != null){
			emailcli = document.querySelector('input[name="emailcliente"]:checked').value;
			guardapend = document.querySelector('input[name="guardapend"]:checked').value;		
		}
		
		if(idempresa == ''){
			document.getElementById("diverrorname").innerHTML = '<p  style="color: red; font-size: 10pt; padding: 0;">Cliente inválido. Pesquise novamente!</p>';
		}else{
			$("#modalSendPedidoMail").modal("close");
			enviarEmailPedidoOrcam(nomec, idempresa, fonecontato, transporte, observ, emailcli, guardapend);			
		}
		
	});
		
	$(document).on("click", "#btnConfirmaEmail", function(){
		enviarEmailOrcamento($("#txtnome").val(), $("#txtfone").val(), $("#txtemail").val());
	});

	$(document).on("click", "#btnConfirmaEmailNf", function(){
		var formulario = $("#formsendmailnf").serialize();
		enviarEmailNotaFiscal(formulario);
	});

</script>


<div class="center-align div-title-padrao col s12">
		Orçamento de Livros
</div>

<div class="row" id="divcontent">

	<form action="srl" method="post" id="formDescartarOrcamento">

		<div class="div-title-topo col l12 m12 s12">
			<c:set var="itens" value="${fn:length(orcamento.itens)}" />
			<div class="left-align div-title-inner col l4 m4 s12">
							<img src="resources/images/logofenovo.png"
					align="left" class="responsive-img">
					
					<c:if test="${!empty orcamento.escola.nome and !empty usuariologado}">
						<c:if test="${usuariologado.cargo != 4}">
							<div class="div-title-text-topo first-title">
								<div class="col s12 right-align" style="font-size: 8pt; color: #5599FF">
									${orcamento.vendedor.nome}
								</div>
								<div class="right-align" style="font-size: 8pt; color: #1A407A">
									<span>Ultima alteração: ${orcamento.escola.audit.usuario.nome}
							 		${orcamento.escola.audit.usuario.sobrenome}</span><br>
							 		<span> Data/Hora: ${orcamento.escola.audit.created}</span>
							 	</div>
						 	</div>
					 	</c:if>
					 </c:if>									
			</div>
			<div class="center-align div-title-inner col l4 m4 s12">
				<div class="div-title-text-topo second-title">
				<span>
				<c:if test="${!empty orcamento.escola.nome}">
						<span> ${orcamento.escola.nome} </span><br> 
						<span style="font-size: 10pt; color: #5599FF">${orcamento.serie} / ${orcamento.ano}</span>
				</c:if>
				</span>	
				</div>
				
			</div>
			<div class="noPrint right-align col l4 m4 s12 input-group div-title-bottom">

				<c:if
					test="${!empty usuariologado and (usuariologado.cargo lt 3)}">
					<a class="btn-floating btn-small waves-effect waves-light blue modal-trigger" 
					title="Adicionar ao pedido" href="#modalConfirmaAdicionar">
					<i class="material-icons">add</i></a>
					<a class="btn-floating btn-small waves-effect waves-light grey modal-trigger" 
					title="Enviar orçamento para o cliente por e-mail" href="#modalSendMail">
					<i class="material-icons">email</i></a>					
				</c:if>

				<c:if
					test="${!empty usuariologado}">
					<a class="btn-floating btn-small waves-effect waves-light black modal-trigger" 
					title="Enviar como pedido!" href="#modalSendPedidoMail">
					<i class="material-icons">contact_mail</i></a>
				</c:if>	

					<a class="btn-floating btn-small waves-effect waves-light brown modal-trigger" 
					title="Imprimir" href="javascript:void(0)"
					onclick="window.print()">
					<i class="material-icons">local_printshop</i></a>
									
					<a class="btn-floating btn-small waves-effect waves-light red modal-trigger" 
					title="Excluir orçamento" href="javascript:void(0)"
					onclick="javascript:$('#formDescartarOrcamento').submit();">
					<i class="material-icons">delete_forever</i></a>
					 
				<c:if test="${!empty usuariologado and usuariologado.cargo != 4}">	
					<a href="javascript:void(0)" style="padding: 10px"
					onclick="javascript:$('#formOrcamentoToExcel').submit();"><img
					src="resources/images/Excel.png" title="Esportar para Excel"></a>
	
					<a href="#modalSendMailNf" style="padding: 10px" class="modal-trigger"><img
					src="resources/images/sendnf.png" title="Solicitar Nota Fiscal"></a>
	
				</c:if>
								
			</div>
			

		</div>
		
		<input type="hidden" name="acao" value="descartar">
		
	</form>
	
	<c:if test="${!empty usuariologado}">
		<form action="srl" method="post" id="formOrcamentoToExcel">
			<input type="hidden" name="acao" value="orcamentoToExcel"> <input
				type="hidden" name="taxadesconto" id="taxadesconto" value="0">
		</form>
	</c:if>
	
	<br><br><br>
	<div class="col s12">
	<table class="responsive-table">
		<thead>
		<tr>
				<th>Código</th>
				<th>Descrição</th>
				<th>Pr.Unit.</th>
				<th>Pr.Liq.</th>
				<th>Qtde</th>
				<th>Total</th>
				<th class="noPrint">#</th>
				<c:if test="${!empty usuariologado and usuariologado.cargo != 4}">
					<th class="noPrint">Estq</th>
					<th class="noPrint">Previsao</th>
				</c:if>
		</tr>
		</thead>
		<tbody>
		<c:forEach var="pd" items="${orcamento.itens}">
			
			<tr>
				<td>${pd.produto.codigo}</td>
				<td>${pd.produto.descricao}</td>
				<td><fmt:formatNumber
						value="${pd.produto.preco}" type="number" pattern="#,#00.00#" /></td>
				<td><fmt:formatNumber
						value="${pd.precoliquido}" type="number" pattern="#,#00.00#" /></td>
				<td>${pd.quantidade}</td>
				<td><fmt:formatNumber
						value="${pd.quantidade * pd.produto.preco}" type="number"
						pattern="#,#00.00#" /></td>
				<td class="noPrint"><a href="javascript:void(0)"
					onclick="remitemclick('${pd.produto.codigo}')">remover</a></td>
				<c:if test="${!empty usuariologado and usuariologado.cargo != 4}">
					<td class="noPrint">${pd.produto.estoque }</td>
					<td class="noPrint">
						<c:choose>
							<c:when test="${!empty pd.produto.previsao}">
								<span> <fmt:formatDate
										value="${pd.produto.previsao}" pattern="dd/MM/yyyy" />
								</span>
							</c:when>
							<c:otherwise>
								<span style="color: gray; font-size: 8pt;"> ... </span>
							</c:otherwise>
						</c:choose>
					</td>					
				</c:if>
				
			</tr>
			
		</c:forEach>
		</tbody>
		</table>
		
		<table class="table">
		<tbody>
		<tr style="font-size: 9pt">
			<td colspan="3" style="text-align: center; font-weight: bold;">${itens}
				itens</td>
			<td style="font-weight: bold;">Totais</td>
			<td style="font-weight: bold;">${orcamento.qtdtotal}</td>
			<td style="font-weight: bold;"><fmt:formatNumber
					value="${orcamento.total}" type="number" pattern="#,#00.00#" /></td>
			<td colspan="3" class="noPrint"></td>
		</tr>

		<tr>
			<td style="font-weight: bold; font-size: 9pt;" class="right-align">Desconto(%)</td>
			<td style="font-weight: bold;" class="row" colspan="2">
			    <div class="col l4 m6 s12"> <input
				type="text" size="2" id="txtdesconto"
				value="<fmt:formatNumber
					value="${orcamento.desconto * 100}" type="number" pattern="#,#0.#" />"
				placeholder="0"
				style="font-weight: bold; font-family: arial; font-size: 8pt;"
				onkeydown="setDesconto(this, event)">
				</div>
			</td>
			<td style="font-weight: bold; font-size: 9pt;"><fmt:formatNumber
					value="${orcamento.valordesconto}" type="number"
					pattern="#,#00.00#" /></td>
			<td></td>		
			<td style="font-weight: bold;"
				id="tdtotal"><fmt:formatNumber value="${orcamento.totaliquido}"
					type="number" pattern="#,#00.00#" /></td>
			<td colspan="3" class="noPrint"></td>
		</tr>
		<tr>
			<td colspan="5"></td>
			<td colspan="4" class="noPrint"></td>
		</tr>
		</tbody>
	</table>
	</div>
</div>
<!-- Fim da divcontent -->
		
	<div class="modal noPrint" id="modalConfirmaAdicionar">
		<div class="center-align div-title-modal">
			Mensagem?
		</div>		
	
		<div class="modal-content">
			<h4>Criar ou Adicionar ao Pedido?</h4>
		</div>
		<div class="modal-footer">
			<a href="#!" id="btnConfirmaAdicao" onclick="adicionarOrcamAoPedido()"
				class="modal-action modal-close waves-effect waves-green btn-flat">Ok</a>
			<a href="#!" class="modal-action modal-close waves-effect waves-green btn-flat">Cancelar</a>
		</div>
	</div>
	
	<div class="modal noPrint" id="modalSendMail">
			<div class="center-align div-title-modal">
				Enviar Orçamento por E-mail
			</div>		

			<div class="modal-content">
					
					<label>Nome:</label>
					<input type="text" id="txtnome" class="form-control">					
					<label>Fone:</label>
					<input type="text" id="txtfone" class="form-control">
					<label>E-mail:</label>
					<input type="email" required id="txtemail" class="form-control">
				<div class="modal-footer">
					<a href="#!" id="btnConfirmaEmail" 
						class="modal-action modal-close waves-effect waves-green btn-flat">Enviar</a>
					<a href="#!" class="modal-action modal-close waves-effect waves-green btn-flat">Cancelar</a>
				</div>
			</div>
	</div>

	<div class="modal noPrint" id="modalSendPedidoMail">
			<div class="center-align div-title-modal">
				Enviar Orçamento como Pedido
			</div>		
			<c:set var="nomecli" value=""></c:set>
			<c:set var="codigocli" value=""></c:set>

			<c:if test="${!empty usuariologado and usuariologado.cargo == 4}">
				<c:set var="nomecli" value="${usuariologado.nome}"></c:set>
				<c:set var="codigocli" value="${usuariologado.codigoftdempresa}"></c:set>				
			</c:if>
			<div class="modal-content">

				<div class="modal-content">
					<label>Nome cliente:</label>
					<input type="text" id="txtdescricaoempresa"
						placeholder="Digite o nome do cliente..."
						name="txtdescricaoempresa" autocomplete="off"
						data-activates="singleDropdownEmpresa" data-beloworigin="true"
						value="${nomecli}">
					<p style="font-size: 8pt"
						id="txtdescricaoempresa-description"></p>
						<input type="hidden" id="txtidempresa" name="txtidempresa"
							value="${codigocli}">						
					<ul id="singleDropdownEmpresa" constrainWidth="false" class="dropdown-content ac-dropdown col s12"></ul>													
					<div id="diverrorname"></div>																						
					<label>Telefone de Contato:</label>
					<input type="text" id="txtfonecontato" class="form-control">
					<label>Transportadora:</label>
					<input type="text" id="txttransportadora" class="form-control" required>
					<label>Observações:</label>
					<textarea rows="30" cols="50" id="txtobsc"></textarea>
					
					<c:if test="${!empty usuariologado and usuariologado.cargo != 4}">
					<div class="noPrint left-align col l3 m3 s12 input-group div-title-bottom">
							<h6>Enviar e-mail para o cliente? </h6>				
							<input type="radio"	class="with-gap" name="emailcliente" id="rd01" value="true" checked>
							<label for="rd01">Sim</label>
							<input type="radio" class="with-gap" name="emailcliente" id="rd02" value="false">
							<label for="rd02">Não</label>
					</div>
					<div class="noPrint left-align col l3 m3 s12 input-group div-title-bottom">
							<h6>Guardar Pendência? </h6>				
							<input type="radio"	class="with-gap" name="guardapend" id="rd1" value="0">
							<label for="rd1">Sim</label>
							<input type="radio" class="with-gap" name="guardapend" id="rd2" value="1" checked>
							<label for="rd2">Não</label>
					</div>													
					</c:if>
																				
				</div>
				<div class="modal-footer">
					<a href="#!" id="btnConfirmaPedidoEmail" 
						class="modal-action btn waves-effect waves-light blue">Enviar</a>
					<a href="#!" class="modal-action modal-close btn waves-effect waves-light red">Cancelar</a>
				</div>
		</div>
	</div>

	<div class="modal noPrint" id="modalSendMailNf">
			<div class="center-align div-title-modal">
				Solicitação de Nota Fiscal
			</div>		

			<div class="modal-content">

					<form id="formsendmailnf">
						<label>Nome:</label>
						<input type="text" name="txtnome" class="form-control">					
						<label>Endereço:</label>
						<input type="text" name="txtendereco" class="form-control">					
						<label>Bairro:</label>
						<input type="text" name="txtbairro" class="form-control">					
						<label>Cidade:</label>
						<input type="text" name="txtcidade" class="form-control">
						<label>CEP:</label>
						<input type="text" name="txtcep" class="form-control">																
						<label>Fone:</label>
						<input type="text" name="txtfone" class="form-control">
						<label>E-mail:</label>
						<input type="email" name="txtemail" class="form-control">
						<label>CPF/CNPJ nº:</label>
						<input type="text" name="txtcpf" class="form-control">
						<label>Observação:</label>
						<input type="text" name="txtobservacao" class="form-control">
						<input type="hidden" value="sendmailnf" name="acao">					
					</form>
				<div class="modal-footer">
					<a href="#!" id="btnConfirmaEmailNf" 
						class="modal-action modal-close waves-effect waves-green btn-flat">Enviar</a>
					<a href="#!" class="modal-action modal-close waves-effect waves-green btn-flat">Cancelar</a>
				</div>
		   </div>
	</div>

