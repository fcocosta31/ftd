<div class="center-align div-title-padrao col s12">
		Relatório de previsão de vendas (xls)
</div>
<br>
<div class="container bordered-shadow">
	<article>* A opção de simulação da previsão deste relatório toma como base as séries temporais
	das vendas dos últimos 3 anos, utilizando-se do método de Amortecimento Exponencial
	Duplo (método de Brown)</article>
	<br/>
	<article>* A taxa de reposição é o fator de aplicado nos itens com adoção mantida de um ano para o outro, porém ignorando
	os itens da Educação Infantil, 1 Ano-Fund.I e 6 Ano-Fund.II, para os quais permanece 100% da
	quantidade de alunos registrada</article>				
</div>
<br><br>

<div class="container">
<div class="row">

	<form id="formdemanda" action="srl" method="post">

			<div class="row">
			<div class="col l6 m6 s12">
				<label>Previsão para o ano:
				</label> <input type="text" id="txtano" name="txtano" class="form-control"
					required size="4">
			</div>
			</div>

			<div class="row">
			<div class="col l6 m6 s12">
				<label>Taxa de reposição % (padrão = 70): </label> <input
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
				<label>Somente Didáticos?</label>
				<p>
					<input type="radio"	class="with-gap" name="txtfamilia" id="rd3" value="sim" checked>
					<label for="rd3">Sim</label>
					<input type="radio" class="with-gap" name="txtfamilia" id="rd4" value="nao">
					<label for="rd4">Não</label>
				</p>
			</div>
			</div>
			
			<div class="row">
			<div class="col s12">
				<label>Gera simulação? (método Brown): </label>
				<p>
					<input type="radio"	class="with-gap" name="txtsimula" id="rd5" value="sim">
					<label for="rd5">Sim</label>
					<input type="radio" class="with-gap" name="txtsimula" id="rd6" value="nao" checked>
					<label for="rd6">Não</label>
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
