<script type="text/javascript">

(function(i,s,o,g,r,a,m){i['GoogleAnalyticsObject']=r;i[r]=i[r]||function(){
	  (i[r].q=i[r].q||[]).push(arguments)},i[r].l=1*new Date();a=s.createElement(o),
	  m=s.getElementsByTagName(o)[0];a.async=1;a.src=g;m.parentNode.insertBefore(a,m)
	  })(window,document,'script','//www.google-analytics.com/analytics.js','ga');

	  ga('create', 'UA-56835143-1', 'auto');
	  ga('send', 'pageview');
	
	

	$(document).on("click",'#btnConfirmaPedidoEmail', function(){
		enviarEmailPedidoOrcam($("#txtnomec").val(), $("#txtdescontoc").val(), $("#txtformapgto").val());		
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
		Pesquisar Lista Escolar
</div>

<form id="formpesqadocao">
	<div class="col s10 offset-s1 noPrint">		
		<div id="divpesquisarescola" class="row">
			<div class="form-group has-feedback col l8 m8 s12">				
				
				<!-- INICIO DIV SEARCH SCHOOL -->		
				<div class="col l8 m8 s12">
				<h5>Lista Escolar</h5>
					<div class="row">
		             		<div class="input-field col s12">
		             			<div class="ac-input" style="margin-top: 10px">
				                    <input type="text" id="txtdescricaoescola"
				                     placeholder="Digite sua busca..."
				                      name="txtdescricaoescola"
				                      autocomplete="off"
				                     required data-activates="singleDropdownSchool" data-beloworigin="true">		                     
				                     <label for="txtdescricaoescola">Pesquisar</label>		                     
			                     </div>	                     
		                    </div>
							 <input type="hidden" id="txtdescricaoescola-id" name="txtidescola">
							 <input type="hidden" value="detalharadocao" name="acao">
							<p style="font-size: 8pt; color: gray;"	id="txtdescricaoescola-description"></p>
		            </div>
		            <ul id="singleDropdownSchool" constrainWidth="false" class="dropdown-content ac-dropdown col s12"></ul>                                            
		            
				</div>
				<!-- FIM DIV SEARCH SCHOOL -->
				
			</div>

			<div class="col l4 m4 s12">
				<h5>Tabela de Preços: </h5>				
				<p>
					<input type="radio"	class="with-gap" name="txttabela" id="rd1" value="atual" checked>
					<label for="rd1">Atual</label>
				</p>
				<p>
					<input type="radio" class="with-gap" name="txttabela" id="rd2" value="original">
					<label for="rd2">Original</label>
				</p>
			</div>

		</div>

		<div class="row" id="divoptserieescola">
			
			<div class="col l12 m12 s12">
				<div class="col l3 m3 s12" id="divselanoadocao">
					<select id="cmbAnoAdocao" name="txtano" onchange="loadComboSeries()">
					</select>
				</div>
	
				<div class="col l3 m3 s12">
					<select id="cmbSerie" name="txtserie" multiple>
					</select>
				</div>
	
				<div class="col l6 m6 s12">
					<button class="waves-effect waves-light btn blue" id="btnconfirmaadocao"
						type="submit">Enviar</button>
					<button class="waves-effect waves-light btn green" id="btnclearadocao"
						onclick="javascript:$('#txtdescricaoescola').val(''); $('#txtdescricaoescola').focus()">Nova</button>
					<c:if
						test="${not empty usuariologado and usuariologado.cargo eq 1 and usuariologado.id eq 1}">
						<button class="waves-effect waves-light btn red" id="btndeleteadocao"
							onclick="sendAjaxDeletarSerieAdocao(event)">Deletar</button>
					</c:if>
				</div>
			</div>
			
		</div>
	</div>
</form>
<div id="formtabela"></div>

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

			<div class="modal-content">

				<div class="modal-content">
					<label>Nome cliente:</label>
					<input type="text" id="txtnomec" class="form-control">					
					<label>Desconto(%):</label>
					<input type="text" id="txtdescontoc" class="form-control">									
					<label>Forma de pagamento:</label>
					<input type="text" id="txtformapgto" class="form-control">										
				</div>
				<div class="modal-footer">
					<a href="#!" id="btnConfirmaPedidoEmail" 
						class="modal-action modal-close waves-effect waves-green btn-flat">Enviar</a>
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
