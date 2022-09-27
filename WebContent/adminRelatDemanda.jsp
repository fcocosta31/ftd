<div class="center-align div-title-padrao col s12">
		Relat�rio de previs�o de vendas (xls)
</div>
<br>
<div class="container bordered-shadow">
	<article>* A op��o de simula��o da previs�o deste relat�rio toma como base as s�ries temporais
	das vendas dos �ltimos 3 anos, utilizando-se do m�todo de Amortecimento Exponencial
	Duplo (m�todo de Brown)</article>
	<br/>
	<article>* A taxa de reposi��o � o fator de aplicado nos itens com ado��o mantida de um ano para o outro, por�m ignorando
	os itens da Educa��o Infantil, 1 Ano-Fund.I e 6 Ano-Fund.II, para os quais permanece 100% da
	quantidade de alunos registrada</article>				
</div>
<br><br>

<div class="container">
<div class="row">

	<form id="formdemanda" action="srl" method="post">

			<div class="row">
			<div class="col l6 m6 s12">
				<label>Previs�o para o ano:
				</label> <input type="text" id="txtano" name="txtano" class="form-control"
					required size="4">
			</div>
			</div>

			<div class="row">
			<div class="col l6 m6 s12">
				<label>Taxa de reposi��o % (padr�o = 70): </label> <input
					type="text" id="txttaxa" name="txttaxa" class="form-control"
					required size="3">
			</div>
			</div>
			
			<div class="row">
			<div class="col s12">
				<label>Itens a considerar: </label>
				<p>
					<input type="radio"	class="with-gap" name="txtitens" id="rd1" value="adotado" checked>
					<label for="rd1">Listas Escolares</label>
					<input type="radio" class="with-gap" name="txtitens" id="rd2" value="tabela">
					<label for="rd2">Itens da Tabela</label>
				</p>
			</div>
			</div>
			
			<div class="row">
			<div class="col s12">
				<label>Somente Did�ticos?</label>
				<p>
					<input type="radio"	class="with-gap" name="txtfamilia" id="rd3" value="sim" checked>
					<label for="rd3">Sim</label>
					<input type="radio" class="with-gap" name="txtfamilia" id="rd4" value="nao">
					<label for="rd4">N�o</label>
				</p>
			</div>
			</div>
			
			<div class="row">
			<div class="col s12">
				<label>Gera simula��o? (m�todo Brown): </label>
				<p>
					<input type="radio"	class="with-gap" name="txtsimula" id="rd5" value="sim">
					<label for="rd5">Sim</label>
					<input type="radio" class="with-gap" name="txtsimula" id="rd6" value="nao" checked>
					<label for="rd6">N�o</label>
				</p>
			</div>
			</div>
			
			<br />
			<div class="row">
			<div class="col s12">							
				<button class="btn waves-effect waves-light blue" type="submit">Enviar</button>
			</div>
			</div>
			
			<input type="hidden" name="acao" value="listardemanda">
	</form>
	
</div>	
</div>



<script type="text/javascript">

	$(document).on("submit", "#formdemanda", function (e) {
		
	    e.preventDefault(); //otherwise a normal form submit would occur
	    fileDownload($(this));
	    
	});
	
</script>
