<script type="text/javascript">
		$(document).ready(function(){
			refreshCombosSeries();
		});
		
    	$(document).on('click','#btncancelarregistro',function(){
    		abandonaRegistroAdocao();
    	});

    	$(document).on('click','#btnfinalizarlista',function(){
			
    		var nome = $("#txtdescricaoescola").val();
    		
    		if(nome == ''){
    			alert("Digite o nome da Escola");
    		}else{
    		
	    		var formulario = $("#formFinalizarListaAdocao").serialize();
				
				finalizaRegistroAdocao(formulario);				
    		}
    	});

    	$(document).on("click", "#btnConfirmaCadastro", function(e) {
    		gravaRegistroAdocao();    		
    	});	
    	
    	$(document).on("click", "#btnOpenDialogSalvarAdocao", function(){
    		
    		$("#modalConfirmaCadastro").modal('open');
    		
    	});
    	
</script>

<div class="center-align div-title-padrao col s12">
		Registro de Lista Escolar
</div>

<div class="container noPrint">

	<div id="divpesquisarescola" class="row">
	
   		<div class="form-group has-feedback col l8 m8 s12">
  			<div class="ac-input" style="margin-top: 10px">
           <input type="text" id="txtdescricaoescola" value="${orcamento.escola.nome}"
            placeholder="Digite sua busca..."
             name="txtdescricaoescola"
             autocomplete="off"
            required data-activates="singleDropdownSchool" data-beloworigin="true">		                     
            <label for="txtdescricaoescola">Pesquisar</label>
	        <p style="font-size: 8pt; color: gray;"
			id="txtdescricaoescola-description">${orcamento.escola.endereco}</p>	          		                    
           </div>	                     
         </div>
		 <ul id="singleDropdownSchool" constrainWidth="false" class="dropdown-content ac-dropdown col s12"></ul>
		 
		<br/>
		
		<div class="col l4 m4 s12" style="margin-top: 5px">
			<button class="waves-effect waves-light btn blue" type="button" id="btnfinalizarlista">
				Salvar
			</button>
			<button class="waves-effect waves-light btn red" type="button" id="btncancelarregistro">
				Cancelar
			</button>	

		</div>
	</div>
	
	<br>

</div>


<div class="container">

	<form id="formFinalizarListaAdocao">

		<table class="highlight bordered">

			<tr>
				<th>Descrição</th>
				<th>Adotado na Série</th>
			</tr>

			<c:set var="aux" value="0"></c:set>

			<c:forEach var="it" items="${orcamento.itens}">

				<tr>
					<td><span style="font-size: 11pt; color: #000099;"><strong>${it.produto.descricao}</strong></span><br>
						<span style="color: gray; font-size: 8pt;">${it.produto.codigo}
							/ </span> <span style="color: gray; font-size: 8pt;">${it.produto.autor}
							/ </span> <span style="color: gray; font-size: 8pt;">${it.produto.obs}</span>
						<input type="hidden" value="${it.produto.serie}"
						id="${aux}${it.produto.codigo}" /></td>
					<c:set var="aux" value="${aux + 1}"></c:set>
					<td><select name="${it.produto.codigo}${aux}" id="${aux}"
						data-live-search="false"
						class="selectpicker form-control show-tick">
							<c:forEach var="serie" items="${seriesproduto}">
								<c:choose>
									<c:when test="${it.produto.serie eq serie}">
										<option selected value="${serie}">${serie}</option>
									</c:when>
									<c:otherwise>
										<option value="${serie}">${serie}</option>
									</c:otherwise>
								</c:choose>
							</c:forEach>
					</select></td>
				</tr>
			</c:forEach>

		</table>

		<input type="hidden" name="acao" value="finalizarlistaadocao">
		<input type="hidden" id="txtdescricaoescola-id" name="txtidescola">
		<input type="hidden" name="txtidadocao"> <input type="hidden"
			id="countcmb" value="${aux+1}">

	</form>

</div>


<div class="modal" id="modaldetalheadocao">

	<div class="center-align div-title-modal" id="modalAdocaoLabel">		
	</div>		

	<div class="modal-content">
		<div class="modal-body modaldivdetalheadocao"></div>
	</div>
	<div class="modal-footer">
		<a href="#!" id="btnOpenDialogSalvarAdocao" 
			class="modal-action modal-close waves-effect waves-green btn-flat">Salvar</a>
		<a href="#!" class="modal-action modal-close waves-effect waves-green btn-flat">Cancelar</a>
	</div>
</div>

<div class="modal" id="modalConfirmaCadastro">
	<div class="center-align div-title-modal">
		Mensagem?
	</div>		

	<div class="modal-content">
	
		<h4>Tem certeza que está tudo certo?</h4>
		
	</div>
	<div class="modal-footer">
		<a href="#!" id="btnConfirmaCadastro" 
			class="modal-action modal-close waves-effect waves-green btn-flat">Ok</a>
		<a href="#!" class="modal-action modal-close waves-effect waves-green btn-flat">Cancelar</a>
	</div>
	
</div>

