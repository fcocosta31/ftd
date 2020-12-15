<script type="text/javascript">
	
	$(document).ready(function(){
		var qtde = ${orcamento.totalitens};
		if(qtde == null){qtde = 0};
		document.getElementById("qtdTotalCart").innerHTML = qtde;
	});
		
</script>


<div>


	<c:set var="count" value="0"></c:set>

	<div id="carousel-ftd-noticias" class="row carousel carousel-fade slide box effect7"
		data-ride="carousel">
		<!-- Indicators -->
		<ol class="carousel-indicators">
			<c:forEach var="ic" items="${carrousel}">
				<c:choose>
					<c:when test="${count eq 0}">
						<li data-target="#carousel-ftd-noticias" data-slide-to="${count}"
							class="active"></li>
					</c:when>
					<c:otherwise>
						<li data-target="#carousel-ftd-noticias" data-slide-to="${count}"></li>
					</c:otherwise>
				</c:choose>
				<c:set var="count" value="${count + 1}"></c:set>
			</c:forEach>
		</ol>


		<!-- Wrapper for slides -->
		<div class="carousel-inner">

			<c:set var="count" value="0"></c:set>

			<c:forEach var="ic" items="${carrousel}">
				<c:choose>
					<c:when test="${count eq 0}">
						<div class="item active img-responsive">
							<a href="${ic.urlLink}" title="${ic.titleLink}" target="_blank">
								<img src="${ic.urlImage}"
								class="attachment-post-thumbnail wp-post-image" alt="" title="" />
							</a>
						</div>
					</c:when>
					<c:otherwise>
						<div class="item img-responsive">
							<a href="${ic.urlLink}" title="${ic.titleLink}" target="_blank">
								<img src="${ic.urlImage}"
								class="attachment-post-thumbnail wp-post-image" alt="" title="" />
							</a>
						</div>
					</c:otherwise>
				</c:choose>
				<c:set var="count" value="${count + 1}"></c:set>
			</c:forEach>

		</div>
	</div>

	<div class="row">
		<br>
	</div>

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

		<div class="col-xs-12 col-sm-4 col-md-3">
			<div class="thumbnail box effect1" style="height: 190px">
				<div class="caption">
					<h4 style="color: #000066;">Listas Escolares</h4>
					<a href="#!" onclick="menupesquisaradocaoclick()"> <img
						align="left" src="resources/images/notepad.png"
						title="Listas escolares">
					</a>
				</div>
			</div>
		</div>

		<div class="col-xs-12 col-sm-4 col-md-3">
			<div class="thumbnail box effect1" style="height: 190px">
				<div class="caption">
					<h4 style="color: #000066;">Tabela de Preços</h4>
					<a href="srl?acao=tabeladeprecos" onclick="exportaConsultaExcel(e)">
						<img align="left" src="resources/images/Excel-96.png"
						title="Tabela de preços">
					</a>
				</div>
			</div>
		</div>

		<div class="col-xs-12 col-sm-4 col-md-2">
			<div class="thumbnail box effect1" style="height: 190px">
				<div class="caption">
					<h4 style="color: #000066;">FTD SE</h4>
					<a href="http://www.ftdsistemadeensino.com.br/"> <img
						src="resources/images/logoftdsistemaensino.png"
						alt="FTD Sistema de Ensino">
					</a>
				</div>
			</div>
		</div>

		<div class="col-xs-12 col-sm-4 col-md-2">
			<div class="thumbnail box effect1" style="height: 190px">
				<div class="caption">
					<h4 style="color: #000066;">FTD SIM</h4>
					<a
						href="http://www.ftdsistemadeensino.com.br/ftd-sistema-de-ensino/outros-selos/sim-sistema-de-ensino">
						<img src="resources/images/logoftdsim.png"
						alt="FTD Sistema de Ensino">
					</a>
				</div>
			</div>
		</div>

		<div class="col-xs-12 col-sm-4 col-md-2">
			<div class="thumbnail box effect1" style="height: 190px">
				<div class="caption">
					<h4 style="color: #000066;">FTD Digital</h4>
					<a href="http://digital.ftd.com.br/"> <img
						src="resources/images/logoftddigital.png" alt="FTD Digital">
					</a>
				</div>
			</div>
		</div>

	</div>

	<div class="row"><br><br></div>

	<div class="row">

		<c:forEach var="mc" items="${mec}">
			<div class="col-md-4 col-sm-4 col-xs-12">
				<img src="${mc.img_src}" title="${mc.a_text}" width="100%" height="auto">
				<h4><a href="${mc.a_href}">${mc.a_text}</a></h4>
				<span style="font-size: 10pt">[fonte: portal.mec.gov.br]</span>
			</div>
		</c:forEach>	

		<div class="row"><br><br></div>

		<c:forEach begin="0" end="4" var="ab" items="${abrelivros}">
			<div class="col-xs-12 col-sm-12 col-md-12">
				<h3>
					<a href="${ab.a_href}">${ab.a_text}</a>
				</h3>
				<p style="text-align: justify;">
					<span style="font-size: 12pt;">${ab.p_text} <span
						style="font-size: 10pt"><br>${ab.dd_text} [fonte:
							www.abrelivros.org.br]</span></span>
				</p>
			</div>
		</c:forEach>				
	</div>
	
	<div class="row"><br></div>

	<div class="row">
		<div class="col-xs-12 col-sm-12 col-md-12">
			<div class="col-md-12 col-sm-12 col-xs-12 thumbnail box effect1">
				<div class="thumbnail img-responsive col-xs-12 col-sm-7 col-md-7">
					<div>
						<img src="resources/images/IMG101628492.jpg" width="auto"
							height="280" align="left"
							class="attachment-post-thumbnail wp-post-image">
					</div>
				</div>
				<div class="thumbnail col-xs-12 col-sm-4 col-md-4 col-md-offset-1">
					<div class="google-maps">
						<iframe
							src="https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d3985.904372273367!2d-44.277250999999985!3d-2.5380730000000087!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x7f68fad7210bd61%3A0x1aa4feff5a2b9a1f!2sS%C3%A3o+Luis+Distribuidora+de+Livros+Ltda+(Editora+FTD)!5e0!3m2!1spt-BR!2sbr!4v1411397708255"
							width="600" height="450" align="left" frameborder="0"
							style="border: 0; margin-left: 10px; float: right;"></iframe>
					</div>
				</div>
			</div>
		</div>
	</div>

</div>
