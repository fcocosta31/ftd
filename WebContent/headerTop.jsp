<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<link href="resources/css/htopstyle.css" rel="stylesheet"
	type="text/css">

<script type="text/javascript"
	src="resources/autocomplete/jquery.ui.autocomplete.scroll.min.js"></script>

<script type="text/javascript" src="resources/js/scripts.js"></script>

<link href="resources/css/styles.css" rel="stylesheet">

<script type="text/javascript">
	
	$(document).on('click','#btnPesqAvancada',function(){
		$('#modalPesquisar').modal('show');
		loadDadosPesquisa();		
	});
	
	$(document).ready(function(){
		$('ul.dropdown-menu [data-toggle=dropdown]').on('click', function(event) {
			event.preventDefault(); 
			event.stopPropagation(); 
			$(this).parent().siblings().removeClass('open');
			$(this).parent().toggleClass('open');
		});
	});
	
	$(document).on('print',function(){
		$('#divlogofe').css('margin-top','5px');
		$('#logofe').css('width','60');
		$('#logofe').css('height','auto');
	});
	
</script>

<nav>
	<div class="col-md-2 col-sm-2 col-xs-12 pull-left"
		style="margin-top: 20px" id="divlogofe">
		<a href="index.jsp" title="Página inicial"><img
			src="resources/images/logofe.png" width="160" height="auto"
			align="left" class="img-responsive wp-post-image" id="logofe"></a>
	</div>

	<div>
		<div id="totalPedCli" class="noPrint">
			<c:if test="${! empty pedcliente.itens}">
				<c:import url="pedidoTotal.jsp"></c:import>
			</c:if>
		</div>
	</div>
</nav>


<nav class="navbar-static-top box effect1 noPrint gradientbackg2"
	role="navigation" style="margin-bottom: 30px">

	<c:choose>
		<c:when test="${empty usuariologado}">
			<div class="navbar-inner">
				<div class="container-fluid">
					<div class="navbar-header">
						<button type="button" class="navbar-toggle collapsed"
							data-toggle="collapse" data-target=".navbar-collapse">
							<span>Menu Principal <i class="glyphicon glyphicon-th"></i></span>
						</button>
					</div>
					<div class="collapse navbar-collapse" style="margin-top: 30px">

						<ul class="nav navbar-nav navbar-left">

							<li class="dropdown"><a class="dropdown-toggle"
								data-toggle="dropdown" href="#!"><i
									class="glyphicon glyphicon-list"></i> Listas Escolares<span
									class="caret"></span></a>
								<ul class="dropdown-menu" role="menu">
									<li role="presentation"><a role="menuitem" tabindex="-1"
										href="#!" onclick="menupesquisaradocaoclick()">Pesquisar</a></li>
								</ul></li>

							<li class="dropdown"><a class="dropdown-toggle"
								data-toggle="dropdown" href="#!"><i
									class="glyphicon glyphicon-list"></i> Tabela de preços<span
									class="caret"></span></a>
								<ul class="dropdown-menu" role="menu">
									<li role="presentation"><a role="menuitem" tabindex="-1"
										href="srl?acao=tabeladeprecos"
										onclick="exportaConsultaExcel(e)">Download(xls)</a></li>
								</ul></li>
						</ul>

						<ul class="nav navbar-nav navbar-right">
							<li class="dropdown"><a class="dropdown-toggle"
								data-toggle="dropdown" href="#!"> <i
									class="glyphicon glyphicon-lock"></i> Login<span class="caret"></span></a>
								<div class="dropdown-menu" role="menu">
									<form action="srl" method="post" class="navbar-form"
										role="form">
										<div class="form-group">
											<input type="text" placeholder="usuario ou e-mail"
												class="form-control" name="txtlogin" id="txtlogin" size="40">
										</div>

										<div class="form-group">
											<input type="password" placeholder="senha"
												class="form-control" name="txtsenha" id="txtsenha" size="40">
										</div>
										<input type="hidden" name="acao" value="logon">
										<button type="submit" class="btn btn-primary navbar-btn">Sign
											in</button>
									</form>
								</div></li>
						</ul>

						<c:import url="search.jsp"></c:import>

					</div>
				</div>
			</div>
			<!--/.navbar-collapse -->
		</c:when>

		<c:when test="${usuariologado.cargo eq 3}">

			<div class="navbar-inner">
				<div class="container-fluid">
					<div class="navbar-header">
						<button type="button" class="navbar-toggle collapsed"
							data-toggle="collapse" data-target=".navbar-collapse">
							<span>Menu Principal <i class="glyphicon glyphicon-th"></i></span>
						</button>

					</div>
					<div class="collapse navbar-collapse" style="margin-top: 30px">

						<ul class="nav navbar-nav navbar-center">
							<li class="dropdown"><a class="dropdown-toggle"
								data-toggle="dropdown" href="#!"><i
									class="glyphicon glyphicon-cog"></i> Divulgação<span
									class="caret"></span></a>

								<ul class="dropdown-menu multi-level" role="menu"
									aria-labelledby="dropdownMenu">

									<li class="dropdown dropdown-submenu"><a
										class="dropdown-toggle" data-toggle="dropdown" href="#!">Escolas</a>

										<ul class="dropdown-menu">
											<li role="presentation"><a role="menuitem" tabindex="-1"
												href="#!"
												onclick="menuadminclick('cadescola', ${usuariologado.cargo})">Cadastrar</a></li>
											<li role="presentation"><a role="menuitem" tabindex="-1"
												href="#!"
												onclick="menuadminclick('listescola', ${usuariologado.cargo})">Listar</a></li>
										</ul></li>
									<li class="divider"></li>
									<li class="dropdown dropdown-submenu"><a
										class="dropdown-toggle" data-toggle="dropdown" href="#!">Roteiros</a>

										<ul class="dropdown-menu">
											<li role="presentation"><a role="menuitem" tabindex="-1"
												href="#!"
												onclick="menuadminclick('listroteiro', ${usuariologado.cargo})">Listar</a></li>
											<li role="presentation"><a role="menuitem" tabindex="-1"
												href="#!"
												onclick="menuadminclick('resumeroteiro', ${usuariologado.cargo})">Resumo</a></li>
										</ul></li>
									<li class="divider"></li>
									<li class="dropdown dropdown-submenu"><a
										class="dropdown-toggle" data-toggle="dropdown" href="#!">Doações</a>

										<ul class="dropdown-menu">
											<li role="presentation"><a role="menuitem" tabindex="-1"
												href="#!"
												onclick="menuadminclick('listdoacao', ${usuariologado.cargo})">Listar</a></li>
											<li role="presentation"><a role="menuitem" tabindex="-1"
												href="#!"
												onclick="menuadminclick('registrodoacao', ${usuariologado.cargo})">Registrar</a></li>
										</ul></li>

									<li class="divider"></li>
									<li class="dropdown dropdown-submenu"><a
										class="dropdown-toggle" data-toggle="dropdown" href="#!">Professores</a>

										<ul class="dropdown-menu">
											<li role="presentation"><a role="menuitem" tabindex="-1"
												href="#!"
												onclick="menuadminclick('cadastroprofessor', ${usuariologado.cargo})">Cadastrar</a></li>
											<li role="presentation"><a role="menuitem" tabindex="-1"
												href="#!"
												onclick="menuadminclick('editarprofessor', ${usuariologado.cargo})">Editar</a></li>
										</ul></li>

									<li class="divider"></li>
									<li class="dropdown dropdown-submenu"><a
										class="dropdown-toggle" data-toggle="dropdown" href="#!">Adoções</a>

										<ul class="dropdown-menu">
											<li role="presentation"><a role="menuitem" tabindex="-1"
												href="#!"
												onclick="menuadminclick('termometroadocoes', ${usuariologado.cargo})">Termômetro</a></li>
										</ul></li>

								</ul></li>

							<li class="dropdown"><a class="dropdown-toggle"
								data-toggle="dropdown" href="#!"><i
									class="glyphicon glyphicon-ok-sign"></i> Cadastros<span
									class="caret"></span></a>
								<ul class="dropdown-menu multi-level" role="menu">
									<li class="dropdown dropdown-submenu"><a
										class="dropdown-toggle" data-toggle="dropdown" href="#!">Usuarios</a>

										<ul class="dropdown-menu">
											<li role="presentation"><a role="menuitem" tabindex="-1"
												href="#!"
												onclick="menuadminclick('editarusuario', ${usuariologado.cargo})">Editar</a></li>
										</ul></li>
								</ul></li>

							<li class="dropdown"><a class="dropdown-toggle"
								data-toggle="dropdown" href="#!"><i
									class="glyphicon glyphicon-list"></i> Listas<span class="caret"></span></a>
								<ul class="dropdown-menu multi-level" role="menu">
									<li class="dropdown dropdown-submenu"><a
										class="dropdown-toggle" data-toggle="dropdown" href="#!">Listas
											Escolares</a>
										<ul class="dropdown-menu">
											<li role="presentation"><a role="menuitem" tabindex="-1"
												href="#!" onclick="menupesquisaradocaoclick()">Pesquisar</a></li>
											<li role="presentation"><a role="menuitem" tabindex="-1"
												href="#!"
												onclick="menuadminclick('registraradocao', ${usuariologado.cargo})">Registrar</a></li>
										</ul></li>

								</ul></li>
						</ul>

						<li class="dropdown navbar-form navbar-right"
							style="list-style-type: none">
							<div>
								<form role="form" action="srl" method="post">
									<label style="color: grey">${usuariologado.nome}!</label> <input
										type="hidden" name="acao" value="logout">
									<button type="submit" class="btn btn-primary">Sign out</button>

								</form>
							</div>
						</li>

						<c:import url="search.jsp"></c:import>

					</div>
				</div>
			</div>
			<!--/.navbar-collapse -->

		</c:when>


		<c:when test="${usuariologado.cargo eq 1}">

			<div class="navbar-inner">
				<div class="container-fluid">
					<div class="navbar-header">
						<button type="button" class="navbar-toggle collapsed"
							data-toggle="collapse" data-target=".navbar-collapse">
							<span>Menu Principal <i class="glyphicon glyphicon-th"></i></span>
						</button>

					</div>
					<div class="collapse navbar-collapse" style="margin-top: 30px">

						<ul class="nav navbar-nav navbar-center">
							<li class="dropdown"><a class="dropdown-toggle"
								data-toggle="dropdown" href="#!"><i
									class="glyphicon glyphicon-cog"></i> Admin<span class="caret"></span></a>

								<ul class="dropdown-menu multi-level" role="menu"
									aria-labelledby="dropdownMenu">

									<li class="dropdown dropdown-submenu"><a
										class="dropdown-toggle" data-toggle="dropdown" href="#!">Pedidos</a>
										<ul class="dropdown-menu">
											<li role="presentation"><a href="#!"
												onclick="menuadminclick('importped', ${usuariologado.cargo})">Import
													Ped(SP)</a></li>
											<li role="presentation"><a href="#!"
												onclick="menuadminclick('relatped', ${usuariologado.cargo})">Relat.Pedidos</a></li>
											<li role="presentation"><a href="#!"
												onclick="menuadminclick('relatpend', ${usuariologado.cargo})">Ped.Pend.(xls)</a></li>
										</ul></li>

									<li class="divider"></li>

									<li class="dropdown dropdown-submenu"><a
										class="dropdown-toggle" data-toggle="dropdown" href="#!">Notas
											Fiscais</a>
										<ul class="dropdown-menu">
											<li role="presentation"><a role="menuitem" tabindex="-1"
												href="#!"
												onclick="menuadminclick('importnf', ${usuariologado.cargo})">Import
													NF(xml)</a></li>
											<li role="presentation"><a role="menuitem" tabindex="-1"
												href="#!"
												onclick="menuadminclick('exportnf', ${usuariologado.cargo})">Export
													NF(csv)</a></li>
											<li role="presentation"><a role="menuitem" tabindex="-1"
												href="#!"
												onclick="menuadminclick('listarnf', ${usuariologado.cargo})">Listar
													NF</a></li>
											<li role="presentation"><a href="#!"
												onclick="menuadminclick('relnfped', ${usuariologado.cargo})">Reg.Ped.p/
													NF</a></li>
										</ul></li>

									<li class="divider"></li>

									<li class="dropdown dropdown-submenu"><a
										class="dropdown-toggle" data-toggle="dropdown" href="#!">Tabelas</a>

										<ul class="dropdown-menu">
											<li role="presentation"><a role="menuitem" tabindex="-1"
												href="#!"
												onclick="menuadminclick('importtabpre', ${usuariologado.cargo})">Import
													Tab.Prc.</a></li>
											<li role="presentation"><a role="menuitem" tabindex="-1"
												href="#!"
												onclick="menuadminclick('exporttabpre', ${usuariologado.cargo})">Export
													Tab.Prc.</a></li>
										</ul></li>

									<li class="divider"></li>

									<li class="dropdown dropdown-submenu"><a
										class="dropdown-toggle" data-toggle="dropdown" href="#!">Pedido
											de Clientes</a>

										<ul class="dropdown-menu">
											<li role="presentation"><a href="#!"
												onclick="menuadminclick('importorcam', ${usuariologado.cargo})">Import.Orçamento</a></li>
											<li role="presentation"><a role="menuitem" tabindex="-1"
												href="#!"
												onclick="menuadminclick('listarpedcliente', ${usuariologado.cargo})">Listar</a></li>
										</ul></li>
									<c:if test="${usuariologado.id eq 1}">
										<li class="divider"></li>

										<li class="dropdown dropdown-submenu"><a
											class="dropdown-toggle" data-toggle="dropdown" href="#!">Relatórios</a>

											<ul class="dropdown-menu">
												<li role="presentation"><a role="menuitem" href="#!"
													onclick="menuadminclick('relatdemanda', ${usuariologado.cargo})">Demanda(xls)</a></li>
												<li role="presentation"><a role="menuitem"
													href="srl?acao=relatorioestoque">Estoques(xls)</a></li>
												<li role="presentation"><a role="menuitem" href="#!"
													onclick="menuadminclick('vendassintetico', ${usuariologado.cargo})">Vendas
														Sintético (xls)</a></li>
												<li role="presentation"><a role="menuitem" href="#!"
													onclick="menuadminclick('vendasprodutos', ${usuariologado.cargo})">Vendas
														Produtos (xls)</a></li>
											</ul></li>
									</c:if>
								</ul></li>

							<li class="dropdown"><a class="dropdown-toggle"
								data-toggle="dropdown" href="#!"><i
									class="glyphicon glyphicon-ok-sign"></i> Cadastros<span
									class="caret"></span></a>
								<ul class="dropdown-menu multi-level" role="menu">
									<li class="dropdown dropdown-submenu"><a
										class="dropdown-toggle" data-toggle="dropdown" href="#!">Produtos</a>
										<ul class="dropdown-menu">
											<li role="presentation"><a role="menuitem" tabindex="-1"
												href="#!"
												onclick="menuadminclick('cadastrarproduto', ${usuariologado.cargo})"">Cadastrar</a></li>
											<li role="presentation"><a role="menuitem" tabindex="-1"
												href="#!"
												onclick="menuadminclick('exportaprodutos', ${usuariologado.cargo})"">Exporta
													Cad.Prod.</a></li>
											<li role="presentation"><a role="menuitem" tabindex="-1"
												href="#!"
												onclick="menuadminclick('importcadprod', ${usuariologado.cargo})">Import
													Cad.Prod.</a></li>

										</ul></li>
									<li class="divider"></li>

									<li class="dropdown dropdown-submenu"><a
										class="dropdown-toggle" data-toggle="dropdown" href="#!">Usuarios</a>

										<ul class="dropdown-menu">
											<li role="presentation"><a role="menuitem" tabindex="-1"
												href="#!"
												onclick="menuadminclick('cadastrousuario', ${usuariologado.cargo})">Cadastrar</a></li>
											<li role="presentation"><a role="menuitem" tabindex="-1"
												href="#!"
												onclick="menuadminclick('editarusuario', ${usuariologado.cargo})">Editar</a></li>
										</ul></li>
								</ul></li>

							<li class="dropdown"><a class="dropdown-toggle"
								data-toggle="dropdown" href="#!"><i
									class="glyphicon glyphicon-list"></i> Divulgação<span
									class="caret"></span></a>
								<ul class="dropdown-menu multi-level" role="menu">
									<li class="dropdown dropdown-submenu"><a
										class="dropdown-toggle" data-toggle="dropdown" href="#!">Listas
											Escolares</a>
										<ul class="dropdown-menu">
											<li role="presentation"><a role="menuitem" tabindex="-1"
												href="#!" onclick="menupesquisaradocaoclick()">Pesquisar</a></li>
											<li role="presentation"><a role="menuitem" tabindex="-1"
												href="#!"
												onclick="menuadminclick('registraradocao', ${usuariologado.cargo})">Registrar</a></li>
										</ul></li>
									<li class="divider"></li>

									<li class="dropdown dropdown-submenu"><a
										class="dropdown-toggle" data-toggle="dropdown" href="#!">Escolas</a>

										<ul class="dropdown-menu">
											<li role="presentation"><a role="menuitem" tabindex="-1"
												href="#!"
												onclick="menuadminclick('cadescola', ${usuariologado.cargo})">Cadastrar</a></li>
											<li role="presentation"><a role="menuitem" tabindex="-1"
												href="#!"
												onclick="menuadminclick('listescola', ${usuariologado.cargo})">Listar</a></li>
										</ul></li>
									<li class="divider"></li>
									<li class="dropdown dropdown-submenu"><a
										class="dropdown-toggle" data-toggle="dropdown" href="#!">Roteiros</a>

										<ul class="dropdown-menu">
											<li role="presentation"><a role="menuitem" tabindex="-1"
												href="#!"
												onclick="menuadminclick('listroteiro', ${usuariologado.cargo})">Listar</a></li>
											<li role="presentation"><a role="menuitem" tabindex="-1"
												href="#!"
												onclick="menuadminclick('resumeroteiro', ${usuariologado.cargo})">Resumo</a></li>
										</ul></li>

									<li class="divider"></li>

									<li class="dropdown dropdown-submenu"><a
										class="dropdown-toggle" data-toggle="dropdown" href="#!">Doações</a>

										<ul class="dropdown-menu">
											<li role="presentation"><a role="menuitem" tabindex="-1"
												href="#!"
												onclick="menuadminclick('listdoacao', ${usuariologado.cargo})">Listar</a></li>
											<li role="presentation"><a role="menuitem" tabindex="-1"
												href="#!"
												onclick="menuadminclick('registrodoacao', ${usuariologado.cargo})">Registrar</a></li>
											<c:if test="${usuariologado.id eq 1}">
												<li role="presentation"><a role="menuitem"
													tabindex="-1" href="#!"
													onclick="menuadminclick('acertodoacoes', ${usuariologado.cargo})">Acertos</a></li>
											</c:if>
										</ul></li>
									<li class="divider"></li>
									<li class="dropdown dropdown-submenu"><a
										class="dropdown-toggle" data-toggle="dropdown" href="#!">Professores</a>

										<ul class="dropdown-menu">
											<li role="presentation"><a role="menuitem" tabindex="-1"
												href="#!"
												onclick="menuadminclick('cadastroprofessor', ${usuariologado.cargo})">Cadastrar</a></li>
											<li role="presentation"><a role="menuitem" tabindex="-1"
												href="#!"
												onclick="menuadminclick('editarprofessor', ${usuariologado.cargo})">Editar</a></li>
										</ul></li>
									<li class="divider"></li>
									<li class="dropdown dropdown-submenu"><a
										class="dropdown-toggle" data-toggle="dropdown" href="#!">Adoções</a>

										<ul class="dropdown-menu">
											<li role="presentation"><a role="menuitem" tabindex="-1"
												href="#!"
												onclick="menuadminclick('termometroadocoes', ${usuariologado.cargo})">Termômetro</a></li>
										</ul></li>

								</ul></li>
						</ul>

						<li class="dropdown navbar-form navbar-right"
							style="list-style-type: none">
							<div>
								<form role="form" action="srl" method="post">
									<label style="color: grey">${usuariologado.nome}!</label> <input
										type="hidden" name="acao" value="logout">
									<button type="submit" class="btn btn-primary">Sign out</button>

								</form>
							</div>
						</li>

						<c:import url="search.jsp"></c:import>

					</div>
				</div>
			</div>
			<!--/.navbar-collapse -->

		</c:when>

		<c:when test="${usuariologado.cargo eq 2}">

			<div class="navbar-inner">
				<div class="container-fluid">
					<div class="navbar-header">
						<button type="button" class="navbar-toggle collapsed"
							data-toggle="collapse" data-target=".navbar-collapse">
							<span>Menu Principal <i class="glyphicon glyphicon-th"></i></span>
						</button>

					</div>
					<div class="collapse navbar-collapse" style="margin-top: 30px">

						<ul class="nav navbar-nav navbar-center">
							<li class="dropdown"><a class="dropdown-toggle"
								data-toggle="dropdown" href="#!"><i
									class="glyphicon glyphicon-cog"></i> Admin<span class="caret"></span></a>

								<ul class="dropdown-menu multi-level" role="menu"
									aria-labelledby="dropdownMenu">

									<li class="dropdown dropdown-submenu"><a
										class="dropdown-toggle" data-toggle="dropdown" href="#!">Pedidos</a>
										<ul class="dropdown-menu">
											<li role="presentation"><a role="menuitem" tabindex="-1"
												href="#!"
												onclick="menuadminclick('relatped', ${usuariologado.cargo})">Relat.Pedidos</a></li>
											<li role="presentation"><a role="menuitem" tabindex="-1"
												href="#!"
												onclick="menuadminclick('relatpend', ${usuariologado.cargo})">Ped.Pend.(xls)</a></li>
										</ul></li>

									<li class="divider"></li>

									<li class="dropdown dropdown-submenu"><a
										class="dropdown-toggle" data-toggle="dropdown" href="#!">Notas
											Fiscais</a>
										<ul class="dropdown-menu">
											<li role="presentation"><a role="menuitem" tabindex="-1"
												href="#!"
												onclick="menuadminclick('exportnf', ${usuariologado.cargo})">Export
													NF(csv)</a></li>
											<li role="presentation"><a role="menuitem" tabindex="-1"
												href="#!"
												onclick="menuadminclick('listarnf', ${usuariologado.cargo})">Listar
													NF</a></li>
										</ul></li>

									<li class="divider"></li>

									<li class="dropdown dropdown-submenu"><a
										class="dropdown-toggle" data-toggle="dropdown" href="#!">Pedido
											de Clientes</a>

										<ul class="dropdown-menu">
											<li role="presentation"><a href="#!"
												onclick="menuadminclick('importorcam', ${usuariologado.cargo})">Import.Orçamento</a></li>
											<li role="presentation"><a role="menuitem" tabindex="-1"
												href="#!"
												onclick="menuadminclick('listarpedcliente', ${usuariologado.cargo})">Listar</a></li>
										</ul></li>

								</ul></li>

							<li class="dropdown"><a class="dropdown-toggle"
								data-toggle="dropdown" href="#!"><i
									class="glyphicon glyphicon-ok-sign"></i> Cadastros<span
									class="caret"></span></a>
								<ul class="dropdown-menu multi-level" role="menu">
									<li class="dropdown dropdown-submenu"><a
										class="dropdown-toggle" data-toggle="dropdown" href="#!">Produtos</a>
										<ul class="dropdown-menu">
											<li role="presentation"><a role="menuitem" tabindex="-1"
												href="#!"
												onclick="menuadminclick('cadastrarproduto', ${usuariologado.cargo})"">Cadastrar</a></li>
										</ul></li>
									<li class="divider"></li>

									<li class="dropdown dropdown-submenu"><a
										class="dropdown-toggle" data-toggle="dropdown" href="#!">Usuarios</a>

										<ul class="dropdown-menu">
											<li role="presentation"><a role="menuitem" tabindex="-1"
												href="#!"
												onclick="menuadminclick('editarusuario', ${usuariologado.cargo})">Editar</a></li>
										</ul></li>
								</ul></li>

							<li class="dropdown"><a class="dropdown-toggle"
								data-toggle="dropdown" href="#!"><i
									class="glyphicon glyphicon-list"></i> Divulgação<span
									class="caret"></span></a>
								<ul class="dropdown-menu multi-level" role="menu">
									<li class="dropdown dropdown-submenu"><a
										class="dropdown-toggle" data-toggle="dropdown" href="#!">Listas
											Escolares</a>
										<ul class="dropdown-menu">
											<li role="presentation"><a role="menuitem" tabindex="-1"
												href="#!" onclick="menupesquisaradocaoclick()">Pesquisar</a></li>
											<li role="presentation"><a role="menuitem" tabindex="-1"
												href="#!"
												onclick="menuadminclick('registraradocao', ${usuariologado.cargo})">Registrar</a></li>
										</ul></li>
									<li class="divider"></li>

									<li class="dropdown dropdown-submenu"><a
										class="dropdown-toggle" data-toggle="dropdown" href="#!">Escolas</a>

										<ul class="dropdown-menu">
											<li role="presentation"><a role="menuitem" tabindex="-1"
												href="#!"
												onclick="menuadminclick('cadescola', ${usuariologado.cargo})">Cadastrar</a></li>
											<li role="presentation"><a role="menuitem" tabindex="-1"
												href="#!"
												onclick="menuadminclick('listescola', ${usuariologado.cargo})">Listar</a></li>
										</ul></li>

								</ul></li>

						</ul>


						<li class="dropdown navbar-form navbar-right"
							style="list-style-type: none">
							<div>
								<form role="form" action="srl" method="post">
									<label style="color: grey">${usuariologado.nome}!</label> <input
										type="hidden" name="acao" value="logout">
									<button type="submit" class="btn btn-primary">Sign out</button>

								</form>
							</div>
						</li>

						<c:import url="search.jsp"></c:import>

					</div>
				</div>
			</div>
			<!--/.navbar-collapse -->

		</c:when>

		<c:otherwise>

			<div class="navbar-inner">
				<div class="container-fluid">
					<div class="navbar-header">
						<button type="button" class="navbar-toggle collapsed"
							data-toggle="collapse" data-target=".navbar-collapse">
							<span>Menu Principal <i class="glyphicon glyphicon-th"></i></span>
						</button>

					</div>
					<div class="collapse navbar-collapse" style="margin-top: 30px">

						<ul class="nav navbar-nav navbar-center">
							<li class="dropdown"><a class="dropdown-toggle"
								data-toggle="dropdown" href="#!"><i
									class="glyphicon glyphicon-list"></i> Listas Escolares<span
									class="caret"></span></a>
								<ul class="dropdown-menu multi-level" role="menu">
									<li class="dropdown-submenu"><a href="#!">Listas
											Escolares</a>
										<ul class="dropdown-menu">
											<li role="presentation"><a role="menuitem" tabindex="-1"
												href="#!" onclick="menupesquisaradocaoclick()">Pesquisar</a></li>
										</ul></li>
								</ul></li>
						</ul>


						<li class="dropdown navbar-form navbar-right"
							style="list-style-type: none">
							<div>
								<form role="form" action="srl" method="post">
									<label style="color: grey">${usuariologado.nome}!</label> <input
										type="hidden" name="acao" value="logout">
									<button type="submit" class="btn btn-primary">Sign out</button>

								</form>
							</div>
						</li>

						<c:import url="search.jsp"></c:import>

					</div>
				</div>
			</div>
			<!--/.navbar-collapse -->

		</c:otherwise>
	</c:choose>
</nav>




<div class="modal fade" id="modalPesquisar" tabindex="-1" role="dialog"
	aria-labelledby="modalPesquisaLabel" aria-hidden="true">
	<div class="modal-dialog modal-lg">
		<div class="modal-content">
			<div class="modal-header" style="background-color: #23517c;">
				<button type="button" class="close" data-dismiss="modal">
					<span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
				</button>
				<h4 class="modal-title" id="modalPesquisaLabel"
					style="font-weight: bold; color: white;">Pesquisa Avançada</h4>
			</div>
			<div class="modal-body" id="modalpesquisaavancada">

				<div class="row-fluid well">
					<form action="srl" method="post" id="formpesqavancada">

						<div class="row">

							<div class="col-md-3 col-sm-6 col-xs-12">
								<select class="form-control" id="comboFamilia" name="familia">
									<option value="familia" selected>Familia</option>
								</select>
							</div>
							<div class="col-md-3 col-sm-6 col-xs-12">
								<select class="form-control" id="comboNivel" name="nivel">
									<option value="nivel" selected>Nível</option>
								</select>
							</div>
							<div class="col-md-3 col-sm-6 col-xs-12">
								<select class="form-control" id="comboDisciplina"
									name="disciplina">
									<option value="disciplina" selected>Disciplina</option>
								</select>
							</div>
							<div class="col-md-3 col-sm-6 col-xs-12">
								<select class="form-control" id="comboSerie" name="serie">
									<option value="serie" selected>Série</option>
								</select>
							</div>

						</div>

					</form>
				</div>

			</div>
			<div class="modal-footer">
				<button type="button" data-dismiss="modal" class="btn btn-primary"
					onclick="btnpesqavancadaclick()">Enviar</button>
				<button type="button" data-dismiss="modal" class="btn">Cancel</button>
			</div>
		</div>
	</div>
</div>
