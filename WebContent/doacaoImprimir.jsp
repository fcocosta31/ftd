<style>
@media all {
	.page-break {
		display: none;
	}
}

@media print {
	.page-break {
		display: block;
		page-break-before: avoid;
	}
}
</style>

<body onload="window.print()" onfocus="window.close()">

	<div class="container">


		<h4 style="text-align: center">
			<strong>Comprovante de doação de livros do aluno para o
				professor</strong>
		</h4>

		<div class="bordered">
			<span style="text-align: center; font-size: 8pt;">
				Emissão:
				<fmt:formatDate value="${doacao.emissao}" pattern="dd/MM/yyyy" />
				- Vendedor: ${doacao.usuario.nome} <span style="font-size: 7">
					- Reimpressão: ${doacao.reimpressao }</span>
			</span><br/>

			<span>Nome: ${doacao.professor.nome} <span>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; ***
					${doacao.escola.nome} *** </span></span> <br /> <span>Cargo:
				${doacao.professor.cargo}</span> <br /> <label>Disciplina(s): <c:forEach
					var="ds" items="${doacao.professor.disciplinas}"> ${ds}&nbsp;&nbsp;</c:forEach></label>
			<br /> <label>Nivel(is): <c:forEach var="nv"
					items="${doacao.professor.niveis}"> ${nv} &nbsp;&nbsp;</c:forEach></label>

		</div>

		<br />
		
		<div class="col s12 bordered">
			<table
				style="font-size: 9pt;">

				<tr class="border_bottom" style="color: white; background-color: #5599FF">
					<th id="thcodigo">Código</th>
					<th width="400px">Descrição</th>
					<th id="thpreco">Preço</th>
					<th id="thestoque">Qtde</th>
					<th id="thpreco">Total</th>
					<th id="thpreco">Ok?</th>
				</tr>

				<c:forEach var="pd" items="${doacao.itens}">

					<tr class="border_bottom" style="font-size: 7pt;">
						<td>${pd.produto.codigo}</td>
						<td>${pd.produto.descricao}</td>
						<td style="text-align: right"><fmt:formatNumber
								value="${pd.produto.preco}" type="number" pattern="#,#00.00#" /></td>
						<td style="text-align: right">${pd.quantidade}</td>
						<td style="text-align: right"><fmt:formatNumber
								value="${pd.quantidade * pd.produto.preco}" type="number"
								pattern="#,#00.00#" /></td>
						<td></td>
					</tr>

				</c:forEach>
				<tr class="border_bottom">
					<td colspan="2" style="text-align: center; font-weight: bold;">${doacao.qtditens}
						item(ns)</td>
					<td style="text-align: right; font-weight: bold;">Totais</td>
					<td style="text-align: right; font-weight: bold;">${doacao.qtdtotal}</td>
					<td colspan="2" style="text-align: center; font-weight: bold;"><fmt:formatNumber
							value="${doacao.total}" type="number" pattern="#,#00.00#" /></td>
				</tr>

			</table>
		</div>
		<br />
		<div class="bordered">
			<legend style="text-align: left; font-size: 8pt;">Controle
				nº ${doacao.id}</legend>
			<br /> <br /> <br /> <label>Professor:
				________________________________________ Data: _____/_____/_________</label>
			<br /> <br /> <br /> <br /> <label>Coordenador:
				________________________ &nbsp;&nbsp;&nbsp; Gerente:
				___________________________</label>
		</div>

	</div>

</body>