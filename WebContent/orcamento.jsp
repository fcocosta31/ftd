<script type="text/javascript">
   
   (function(i,s,o,g,r,a,m){i['GoogleAnalyticsObject']=r;i[r]=i[r]||function(){
	  (i[r].q=i[r].q||[]).push(arguments)},i[r].l=1*new Date();a=s.createElement(o),
	  m=s.getElementsByTagName(o)[0];a.async=1;a.src=g;m.parentNode.insertBefore(a,m)
	  })(window,document,'script','//www.google-analytics.com/analytics.js','ga');

	  ga('create', 'UA-56835143-1', 'auto');
	  ga('send', 'pageview');
	
	

	$(document).on("click",'#btnConfirmaPedidoEmail', function(){
		
		var idempresa = $("#txtidempresa").val();
		
		if(idempresa == ''){
			document.getElementById("diverrorname").innerHTML = '<p  style="color: red; font-size: 10pt; padding: 0;">Cliente inválido. Pesquise novamente!</p>';
		}else{
			$("#modalSendPedidoMail").modal("close");
			enviarEmailPedidoOrcam($("#txtnomec").val(), $("#txtidempresa").val(), $("#txtcnpjc").val(), 
					$("#txtdescontoc").val(), $("#txtformapgto").val(), $("#txtobsc").val());			
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
							<img src="resources/images/logofe.png"
					align="left" class="responsive-img">
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

		</div>
		
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
				<th>Tes</th>
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
				<td>${pd.tes}</td>
			</tr>
			
		</c:forEach>
		</tbody>
		<tfoot>
		<tr style="font-size: 9pt">
			<td colspan="3" style="text-align: center; font-weight: bold;">${itens}
				itens</td>
			<td style="font-weight: bold;">Totais</td>
			<td style="font-weight: bold;">${orcamento.qtdtotal}</td>
			<td style="font-weight: bold;"><fmt:formatNumber
					value="${orcamento.total}" type="number" pattern="#,#00.00#" /></td>
			<td class="noPrint"></td>
		</tr>

		<tr>
			<td style="font-weight: bold; font-size: 9pt;" class="right-align">Desconto(%)</td>
			<td style="font-weight: bold;" class="row">
			    <div class="col l2 m2 s12"> <input
				type="text" size="2" id="txtdesconto"
				value="<fmt:formatNumber
					value="${orcamento.desconto * 100}" type="number" pattern="#,#0.#" />"
				placeholder="0"
				style="font-weight: bold; font-family: arial; font-size: 8pt;"
				onkeydown="setDesconto(this, event)">
				</div>
			</td>
			<td style="font-weight: bold; font-size: 9pt;">Desconto(R$)</td>
			<td style="font-weight: bold; font-size: 9pt;"><fmt:formatNumber
					value="${orcamento.valordesconto}" type="number"
					pattern="#,#00.00#" /></td>
			<td></td>		
			<td style="font-weight: bold;"
				id="tdtotal"><fmt:formatNumber value="${orcamento.totaliquido}"
					type="number" pattern="#,#00.00#" /></td>
			<td class="noPrint"></td>
		</tr>
		<tr>
			<td colspan="5"></td>
			<td colspan="2" class="noPrint"></td>
		</tr>
		</tfoot>
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
			<c:set var="desccli" value=""></c:set>
			<c:set var="cnpjcli" value=""></c:set>
			<c:if test="${usuariologado.cargo eq 4}">
				<c:set var="nomecli" value="${usuariologado.nome}"></c:set>
				<c:set var="codigocli" value="${usuariologado.codigoftdempresa}"></c:set>
				<c:set var="desccli" value="${usuariologado.desconto}"></c:set>
				<c:set var="cnpjcli" value="${usuariologado.cnpj}"></c:set>				
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
					<label>Desconto(%):</label>
					<input type="text" id="txtdescontoc" value="<fmt:formatNumber
					value="${desccli}" type="number"
					pattern="#,#00.00#" />" class="form-control">																							
					<label>Forma de pagamento:</label>
					<input type="text" id="txtformapgto" class="form-control">
					<label>Observações:</label>
					<textarea rows="30" cols="50" id="txtobsc"></textarea>															
				</div>
				<div class="modal-footer">
					<a href="#!" id="btnConfirmaPedidoEmail" 
						class="modal-action waves-effect waves-green btn-flat">Enviar</a>
					<a href="#!" class="modal-action modal-close waves-effect waves-green btn-flat">Cancelar</a>
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