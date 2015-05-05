$(function () {
	$("[data-mask]").inputmask();
	
	activeItemMenuPelaUrl();
});

/**
 * Busco um li do documento com o atributo 'data-menu-map' igual a url da pagina. Ativo ele e o pai com a classe treeview.
 */
function activeItemMenuPelaUrl() {
	var url = "";
	var splUrl = window.location.pathname.split("/");
	
	for (var i = 1; i < splUrl.length; i++) {
		url += "/" + splUrl[i]; 
		
		var liItem = document.querySelectorAll('[data-menu-map="' + url + '"]');
		
		if (liItem.length == 1) {
			jQuery(liItem[0]).addClass("active");
			jQuery(liItem[0]).closest(".treeview").addClass("active");
			break;
		}
	}
}
