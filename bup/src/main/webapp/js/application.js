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
		
		var allLiItens = document.querySelectorAll('[data-menu-map]');
		var liItem = null;
		
		for (var o = 0; o < allLiItens.length; o++) {
			var liItemAux = allLiItens[o];
			var menuSplit = jQuery(liItemAux).attr("data-menu-map").split(",");
			
			for (var u = 0; u < menuSplit.length; u++) {
				if (url == menuSplit[u].trim()) {
					liItem = liItemAux;
					break;
				}
			}
			
			if (liItem != null) {
				break;
			}
		}
		
		if (liItem != null) {
			jQuery(liItem).addClass("active");
			jQuery(liItem).closest(".treeview").addClass("active");
			break;
		}
	}
}
