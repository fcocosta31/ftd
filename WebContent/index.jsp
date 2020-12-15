<div class="container">
<div class="row">	

<!-- Carousel Crawler FTD -->

<div class="carousel carousel-slider center" data-indicators="true" id="carousel-ftd">
<div class="carousel-inner">
	<c:forEach var="ic" items="${carrousel}">	
		<a href="${ic.urlLink}" title="${ic.titleLink}" class="carousel-item">
			<img src="${ic.urlImage}"/>
		</a>
	</c:forEach>
</div>
</div>

<div class="row"></div>

<!-- Carousel lançamentos -->		
<c:set var="at" value="${lanctos}" />
<div class="owl-carousel" id="lanctos-carousel">

	<c:forEach var="t" items="${at}">

		<div class="item-carousel">
			<a href="srl?acao=detalheproduto&txtcodigo=${t.codigo}"> <c:set var="url"
					value="resources/images/pd/sem-foto_800.jpg"></c:set> <c:if
					test="${!empty t.imagem}">
					<c:set var="url" value="${t.imagem}"></c:set>
				</c:if> <img width="96px" height="130px" alt="${t.descricao}"
				title="${t.descricao}" src="${url}">
			</a>
			<p>${t.descricao}</p>
		</div>

	</c:forEach>

</div>

<div class="row">

	<div class="col l3 m4 s6">
		<div class="card" style="height: 190px">
			<div class="card-content">
				<a href="#!" onclick="menupesquisaradocaoclick()"> <img
					align="left" src="resources/images/notepad.png"
					title="Listas escolares">
				</a>
			</div>
		</div>
	</div>

	<div class="col l3 m4 s6">
		<div class="card" style="height: 190px">
			<div class="card-content">
				<a href="#!" onclick="downloadPriceTable('${usuariologado.cargo}')">
					<img align="left" src="resources/images/Excel-96.png"
					title="Tabela de preços">
				</a>
			</div>
		</div>
	</div>

	<div class="col l2 m4 s6">
		<div class="card" style="height: 190px">
			<div class="card-image">				
				<a href="http://www.ftdsistemadeensino.com.br/"> <img
					src="resources/images/logoftdsistemaensino.png"
					alt="FTD Sistema de Ensino">
				</a>
			</div>
		</div>
	</div>

	<div class="col l2 m4 s6">
		<div class="card" style="height: 190px">
			<div class="card-image">				
				<a
					href="http://www.ftdsistemadeensino.com.br/ftd-sistema-de-ensino/outros-selos/sim-sistema-de-ensino">
					<img src="resources/images/logoftdsim.png"
					alt="FTD Sistema de Ensino">
				</a>
			</div>
		</div>
	</div>

	<div class="col l2 m4 s6">
		<div class="card" style="height: 190px">
			<div class="card-image">							
				<a href="http://digital.ftd.com.br/"> <img
					src="resources/images/logoftddigital.png" alt="FTD Digital">
				</a>
			</div>
		</div>
	</div>

</div>
	
</div>

<!-- INÍCIO DAS NOTÍCIAS -->
<div class="row">

	<c:forEach begin="0" end="4" var="ab" items="${abrelivros}">
		<div class="col s12">
			<h4>
				<a href="${ab.a_href}">${ab.a_text}</a>
			</h4>
			<p style="text-align: justify;">
				<span style="font-size: 12pt;">${ab.p_text} <span
					style="font-size: 10pt"><br>${ab.dd_text} [fonte:
						www.abrelivros.org.br]</span></span>
			</p>
		</div>
	</c:forEach>				
</div>
<!-- FIM DAS NOTÍCIAS -->

</div>


<!-- MODAL TABELA PREÇOS -->
<div class="modal" id="modalPriceTable">
	<div class="center-align div-title-modal">
		Tipo de download!
	</div>		
	<form action="srl" method="post" id="formPriceTable">
	<div class="modal-content">

		<div class="row">
			<div class="col l6 m6 s12">
				<h5>Tabela com estoque? </h5>				
				<p>
					<input type="radio"	class="with-gap" name="tabestoque" id="rd1" value="sim">
					<label for="rd1">Sim</label>
					<input type="radio" class="with-gap" name="tabestoque" id="rd2" value="nao" checked>
					<label for="rd2">Não</label>
				</p>
			</div>		
		</div>

		<div class="row">
			<div class="col l6 m6 s12">
				<h5>Inclui inativos? </h5>				
				<p>
					<input type="radio"	class="with-gap" name="tabinativo" id="rd3" value="sim">
					<label for="rd3">Sim</label>
					<input type="radio" class="with-gap" name="tabinativo" id="rd4" value="nao" checked>
					<label for="rd4">Não</label>
				</p>
			</div>		
		</div>
		<input type="hidden" name="acao" value="tabeladeprecos">
		
	</div>
	<div class="modal-footer">
		<a href="#!" id="btnPriceTable" onclick="fileDownload($(this).closest('form'))" 
			class="modal-action modal-close waves-effect waves-green btn-flat">Enviar</a>
		<a href="#!" class="modal-action modal-close waves-effect waves-green btn-flat">Cancelar</a>
	</div>
	</form>	
</div>