$(function () {
	$("[data-mask]").inputmask();
	
	activeItemMenuPelaUrl();
});

function activeItemMenuPelaUrl() {
	var liTreeView = activeItemMenuPelaUrlGetItem(jQuery(".sidebar-menu"));
	
	if (liTreeView) {
		jQuery(liTreeView).addClass("active");
	}
	
	var filho = activeItemMenuPelaUrlGetItem(jQuery(liTreeView));
	
	if (filho) {
		jQuery(filho).addClass("active");
	}
}

function activeItemMenuPelaUrlGetItem(pai) {
	var filho;
	var url = "";
	var splUrl = window.location.pathname.split("/");
	
	for (var i = 1; i < splUrl.length; i++) {
		url += "/" + splUrl[i]; 
		
		var lis = pai.children('li[id*="' + url + '"]');
		
		if (lis.size() == 1) {
			filho = lis[0];
			break;
		}
	}
	
	if (!filho) {
		pai.children().each(function() {
			filho = activeItemMenuPelaUrlGetItem(jQuery(this));
			if (filho) {
				return false;
			}
		});
	}
	
	return filho;
}