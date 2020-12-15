<script type="text/javascript">
$(document).on('click', '#btnDialogCancelar',function(event){
	event.preventDefault();
	$("#acao").val("descartarbonus");
	$("#formbonus").submit();
});
$(document).on('click', '#btnDialogDeletar',function(event){
	event.preventDefault();
	$("#acao").val("bonusdeletar");
	mbox.confirm("Tem certeza?", function(yes){
		if(yes){
			$("#formbonus").submit();	
		}			
	})	
});
</script>

<div class="center-align div-title-padrao col s12">
		Editar Bônus
</div>
<br><br>

<div class="container">
  <div class="row">	
	<div class="col l8 m8 s12">
		<form id="formbonus" action="srl" method="post">
		<div class="row">		
			<div class="col s12">
				<label>Nome da escola: </label>
				<input type="text" id="txtdescricaoescola" value="${bonus.escola.nome}"
					class="form-control" readonly
					name="txtdescricaoescola">				
			</div>
			<div class="row"></div>
			<div class="col l6 m6 s12">
				<label>Ano: </label> <input type="text"
					id="txtyear" name="txtyear" value="${bonus.ano}" readonly>
			</div>		
			<div class="col l6 m6 s12">
				<label>Valor ou % Desconto do Bônus: </label> <input type="text"
					id="txtdesconto" name="txtdesconto" value="${bonus.desconto}">
			</div>
			<div class="row"></div>		
			<div class="col s12">
				<label>Descrição: </label>
				<textarea rows="4" cols="40" name="txtdescricao">
					${bonus.descricao}
				</textarea>				
			</div>
			<div class="row"></div><br>		
			<c:set var="checkvalor" value=""></c:set>
			<c:set var="checkdesconto" value=""></c:set>
			<c:choose>
				<c:when test="${bonus.tipo eq 'valor'}"><c:set var="checkvalor" value="checked"></c:set></c:when>
				<c:otherwise><c:set var="checkdesconto" value="checked"></c:set></c:otherwise>
			</c:choose>			
			<div class="col s12">
				<input type="radio" name="txttipo" class="with-gap" id="rd2" value="valor" ${checkvalor}>
				<label for="rd2">Valor(R$)</label>
	
				<input type="radio" name="txttipo" class="with-gap" id="rd1" value="desconto" ${checkdesconto}>
				<label for="rd1">Desconto(%)</label>
			</div>
			<div class="row"></div><br>
			<div class="col s12">
				<button class="waves-effect waves-light btn blue" type="submit" id="btnDialogSalvar">Salvar</button>			
				<button class="waves-effect waves-light btn grey" type="button" id="btnDialogCancelar">Cancelar</button>			
				<button class="waves-effect waves-light btn red" type="button" id="btnDialogDeletar">Deletar</button>			
			</div>
			<input type="hidden" name="acao" id="acao" value="bonuseditar">
			<input type="hidden" name="txtidescola" id="idescola" value="${bonus.escola.id}">
		</div>
		</form>
	</div>
  </div>
</div>