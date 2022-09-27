<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div class="center-align div-title-padrao col s12">
		Importar Orçamento (csv)
</div>
<br><br>
<div class="container">
	<form id="formarquivopreco" action="srl" method="post"
		enctype="multipart/form-data">

			<input id="arquivo_preco" name="arqpreco" type="file"
				accept=".csv, text/plain"> 
			
			<input type="hidden" name="acao"
				value="importaorcam">

			<br><br><br>
			<button class="waves-effect waves-light btn blue" type="submit">
				Enviar
			</button>
			<br>

	</form>
</div>
