<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div id="divcontent">
<form id="formobservacao">

	<textarea rows="5" cols="40" class="form-control" name="txtobservacao">${detalheescola.observacao}</textarea>
	<input type="hidden" name="txtidescola" value="${detalheescola.id}">
	<input type="hidden" name="acao" value="roteirosalvarobservacao">

</form>
</div>