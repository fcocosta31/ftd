
<div class="col-md-8 col-sm-8 col-xs-12"
	style="margin-top: 30px; margin-bottom: 30px">

	<div class="input-group has-feedback">
		<input type="text" id="txtdescricao" size="60"
			onkeydown="descricaoclick(event)" class="form-control noPrint"
			placeholder="Buscar por Nome do livro, Autor, Disciplina, Coleção..."
			name="txtdescricao"> <span
			class="btn btn-primary input-group-addon noPrint"> <a
			href="javascript:void(0)"><i onclick="descricaoclick('clicou')"
				class="glyphicon glyphicon-search white"></i></a> <a
			href="javascript:void(0)" title="Pesquisa avançada"><i
				id="btnPesqAvancada" class="glyphicon glyphicon-new-window white"></i></a>
		</span>

		<div id="totalCart"
			class="noPrint col-md-2 col-sm-2 col-xs-2 pull-left">
			<c:import url="orcamentoTotal.jsp"></c:import>
		</div>

	</div>

</div>
